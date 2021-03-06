/*
 * Package: bedsidemonitor
 *
 * File: BedsideMonitor.java
 *
 * Date: Nov 3, 2011
 * 
 */
package bedsidemonitor;

import historylogging.HistoryLogging;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Queue;
import java.util.Timer;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import nursestation.notificationservice.VitalSignMessage;

import alarm.AlarmStatus;
import bedsidemonitor.callbutton.CallButtonController;
import bedsidemonitor.sensor.SensorInterface;
import bedsidemonitor.vitalsigncollection.VitalSignCollection;
import bedsidemonitor.vitalsigncollection.VitalSignConfiguration;
import bedsidemonitor.vitalsigncollection.VitalSignController;
import bedsidemonitor.vitalsigncollection.VitalSignProcessing;


/**
 * The bedside monitor for managing a patient's call button, vital signs,
 * and notification services for who wants to receive notifications of updates.
 * 
 * @author Jason Smith
 */
public class BedsideMonitor extends Observable implements Observer, Runnable {

    /**
     * The name of the patient
     */
    private String patientName;
    
    /**
     * The subscribable interface for patient observers
     */
    private BedsideMonitorSubscribeImpl subscribe;
    
    /**
     * The call button manager
     */
    private CallButtonController callButtonController;
    
    /**
     * The vital signs for bedside monitor
     */
    private Map<String, VitalSignController> vitalSigns;

    private LinkedBlockingQueue<String> acknowledgedAlarms;
    
    /**
     * Constructs a BedsideMonitor object with a patient name and a sensor
     * lookup service.
     * 
     * @param patientName the name of the patient
     * @param sensorLookup the sensor lookup service
     * 
     * @throws RemoteException If the remote connection fails
     * @throws MalformedURLException 
     */
    public BedsideMonitor(String patientName, 
            Map<String, SensorInterface> sensors) 
                    throws RemoteException, MalformedURLException {
        this.acknowledgedAlarms = new LinkedBlockingQueue<String>();
        
        this.subscribe = new BedsideMonitorSubscribeImpl(acknowledgedAlarms);
        this.patientName = patientName;
        this.callButtonController = new CallButtonController();
        this.vitalSigns = new HashMap<String, VitalSignController>();
        
        Naming.rebind(patientName, subscribe);
        
        for(String sensorName: sensors.keySet()){
            SensorInterface sensor = sensors.get(sensorName);
            VitalSignController vitalSign = 
                    new VitalSignController(sensorName, sensor);
            vitalSign.addObserver(this);
            vitalSigns.put(sensorName, vitalSign);
        }
        
        Thread acknowledgeTask = new Thread(this);
        acknowledgeTask.start();
    }
    
    /**
     * Gets the vital sign controller for the specified vital sign name.
     * 
     * @param vitalSignName the name of the vital sign to get
     * @return the vital sign controller if it exists, null otherwise
     */
    public VitalSignController getVitalSign(String vitalSignName) {
        return vitalSigns.get(vitalSignName);
    }
    
    /**
     * @return the current status of the call button
     */
    public boolean getCallButton(){
        return this.callButtonController.getCallStatus();
    }
    
    /**
     * Sets the call status of the button.
     * 
     * @param callStatus the new status of the call button
     */
    public void setCallButton(boolean callStatus){
        this.callButtonController.setCallStatus(callStatus);
        setChanged();
        notifyObservers(this);
    }

    /**
     * Upon receiving an update to a vital sign, this will create a vital
     * sign message and notify both the local and remote observers
     * of the bedside monitor.
     * 
     * @param observable the observable object (vital sign processing)
     * @param arg (ignored)
     */
    public void update(Observable observable, Object arg) {
        if(observable instanceof VitalSignProcessing) {
            VitalSignProcessing processor = (VitalSignProcessing) observable;
            
            VitalSignConfiguration configuration = processor.getConfiguration();
            String vitalSignName = configuration.getName();
            AlarmStatus alarmStatus = processor.getAlarmStatus();
            
            VitalSignMessage msg = new VitalSignMessage(
                    patientName, vitalSignName, alarmStatus);
            
            setChanged();
            notifyObservers(msg);
            
            long start = System.currentTimeMillis();
            this.subscribe.publishVitalSign(msg);
            long end = System.currentTimeMillis();
            
            HistoryLogging.getInstance().logMessage("Vital sign Push Time, " + 
                    vitalSignName + ", " + (end - start));
        }
    }
    
    public void run() {
        while(true) {
            String vitalSignName = null;
            
            try {
                vitalSignName = acknowledgedAlarms.poll(1, TimeUnit.MINUTES);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            
            if(vitalSignName != null) {
                long start = System.currentTimeMillis();
                VitalSignController vitalSign = vitalSigns.get(vitalSignName);
                
                if(vitalSign != null) {
                    vitalSign.acknowledgeAlarm();
                    long end = System.currentTimeMillis();
                    HistoryLogging.getInstance().logMessage("Acknowledge Alarm Processing Time, " + 
                            vitalSignName + ", " + (end - start));
                }
            }
        }
    }

    public void unpublish() {
        try {
            Naming.unbind(patientName);
        } catch (RemoteException ex) {
            ex.printStackTrace();
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        } catch (NotBoundException ex) {
            ex.printStackTrace();
        }
    }
    
} // BedsideMonitor

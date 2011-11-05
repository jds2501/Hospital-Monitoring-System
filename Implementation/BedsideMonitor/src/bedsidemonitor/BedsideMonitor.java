/*
 * Package: bedsidemonitor
 *
 * File: BedsideMonitor.java
 *
 * Date: Nov 3, 2011
 * 
 */
package bedsidemonitor;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Queue;
import java.util.Timer;
import java.util.concurrent.LinkedBlockingQueue;

import nursestation.notificationservice.VitalSignMessage;

import alarm.AlarmStatus;
import bedsidemonitor.callbutton.CallButtonController;
import bedsidemonitor.sensor.SensorInterface;
import bedsidemonitor.sensor.RemoteSensorLookup;
import bedsidemonitor.sensor.SensorLookupInterface;
import bedsidemonitor.vitalsigncollection.VitalSignCollectionController;
import bedsidemonitor.vitalsigncollection.VitalSignConfiguration;
import bedsidemonitor.vitalsigncollection.VitalSignProcessing;


/**
 * The bedside monitor for managing a patient's call button, vital signs,
 * and notification services for who wants to receive notifications of updates.
 * 
 * @author Jason Smith
 */
public class BedsideMonitor extends Observable implements Observer {

    /**
     * The name of the patient
     */
    private String patientName;
    
    /**
     * The subscribable interface for patient observers
     */
    private BedsideMonitorSubscribeImpl subscribe;
    
    /**
     * The sensor lookup service
     */
    private SensorLookupInterface sensorLookup;
    
    /**
     * The call button manager
     */
    private CallButtonController callButtonController;
    
    /**
     * The vital sign collection processes
     */
    private Map<String, VitalSignCollectionController> vitalSignCollections;
    
    /**
     * The vital sign processing tasks
     */
    private Map<String, VitalSignProcessing> vitalSignProcessings;
    
    /**
     * The schedule timer for vital sign processing tasks
     */
    private Timer timer;
    
    /**
     * Constructs a BedsideMonitor object with a remote sensor lookup object.
     * 
     * @param patientName the name of the patient
     */
    public BedsideMonitor(String patientName) throws RemoteException {
        this(patientName, new RemoteSensorLookup());
        
        try {
            Naming.rebind(patientName, subscribe);
        } catch (RemoteException ex) {
            ex.printStackTrace();
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Constructs a BedsideMonitor object with a patient name and a sensor
     * lookup service.
     * 
     * @param patientName the name of the patient
     * @param sensorLookup the sensor lookup service
     * 
     * @throws RemoteException If the remote connection fails
     */
    public BedsideMonitor(String patientName, 
            SensorLookupInterface sensorLookup) throws RemoteException {
        this.subscribe = new BedsideMonitorSubscribeImpl();
        this.patientName = patientName;
        this.sensorLookup = sensorLookup;
        callButtonController = new CallButtonController();
        vitalSignCollections = new HashMap<String, VitalSignCollectionController>();
        vitalSignProcessings = new HashMap<String, VitalSignProcessing>();
        timer = new Timer();
    }
    
    /**
     * Adds a new sensor to the bedside monitor with the given configuration.
     * 
     * @param configuration the vital sign configuration to add a sensor for
     */
    public void addSensor(VitalSignConfiguration configuration){
        String name = configuration.getName();
        SensorInterface sensor = sensorLookup.getSensorByName(name);
        
        if(sensor != null) {
            
            Queue<Integer> vitalSignMsgQueue = new LinkedBlockingQueue<Integer>();
            VitalSignCollectionController controller = 
                    new VitalSignCollectionController(sensor, vitalSignMsgQueue);
            VitalSignProcessing processor = 
                    new VitalSignProcessing(vitalSignMsgQueue, configuration);
            processor.addObserver(this);
            
            Thread processorTask = new Thread(processor);
            processorTask.start();
            timer.scheduleAtFixedRate(controller, new Date(), 
                    configuration.getCollectionRate());
            
            vitalSignProcessings.put(name, processor);
            vitalSignCollections.put(name, controller);
            
        } else {
            throw new IllegalArgumentException("Sensor " + name + 
                    " requested was not found");
        }
    }
    
    /**
     * Removes the sensor and the associated vital sign from the bedside
     * monitor.
     * 
     * @param sensorName the name of the sensor to remove
     */
    public void removeSensor(String sensorName){
        if(vitalSignProcessings.containsKey(sensorName)){
            VitalSignProcessing processor = vitalSignProcessings.remove(sensorName);
            VitalSignCollectionController controller = vitalSignCollections.remove(sensorName);
            controller.cancel();
            processor.setActive(false);
        }else{
            throw new IllegalArgumentException("Sensor " + sensorName + " does not exist");
        }
    }
    
    /**
     * Gets the vital sign configuration for the given vital sign.
     * 
     * @param vitalSignName the name of the vital sign
     * 
     * @return the vital sign configuration for the given name if it exists,
     * null otherwise
     */
    public VitalSignConfiguration getConfiguration(String vitalSignName){
        VitalSignConfiguration configuration = null;
        
        if(vitalSignProcessings.containsKey(vitalSignName)){
            configuration = vitalSignProcessings.get(vitalSignName).getConfiguration();
        }
        
        return configuration;
    }
    
    /**
     * Changes the vital sign configuration to the given configuration.
     * 
     * @param configuration the new vital sign configuration
     * 
     * @throws IllegalArgumentException if the given vital sign does
     * not exist
     */
    public void changeConfiguration(VitalSignConfiguration configuration){
        String name = configuration.getName();
        
        if(vitalSignProcessings.containsKey(name)){
            vitalSignProcessings.get(name).setConfiguration(configuration);
        }else{
            throw new IllegalArgumentException("Configuration name " + name + " does not exist");
        }
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
            long patientID = this.hashCode();
            long vitalSignID = processor.hashCode();
            
            VitalSignConfiguration configuration = processor.getConfiguration();
            String vitalSignName = configuration.getName();
            Double vitalSignValue = processor.getVitalSignValue();
            AlarmStatus alarmStatus = processor.getAlarmStatus();
            
            VitalSignMessage msg = new VitalSignMessage(patientID, patientName,
                    vitalSignName, vitalSignID, vitalSignValue, alarmStatus);
            
            setChanged();
            notifyObservers(msg);
            
            this.subscribe.publishVitalSign(msg);
        }
    }
    
} // BedsideMonitor

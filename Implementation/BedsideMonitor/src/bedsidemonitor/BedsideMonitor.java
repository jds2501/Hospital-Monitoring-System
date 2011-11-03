/*
 * Package: bedsidemonitor
 *
 * File: BedsideMonitor.java
 *
 * Date: Nov 3, 2011
 * 
 */
package bedsidemonitor;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observer;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.LinkedBlockingQueue;

import nursestation.notificationservice.NotificationService;

import bedsidemonitor.callbutton.CallButtonController;
import bedsidemonitor.sensor.SensorInterface;
import bedsidemonitor.sensor.SensorLookup;
import bedsidemonitor.vitalsigncollection.VitalSignCollectionController;
import bedsidemonitor.vitalsigncollection.VitalSignConfiguration;
import bedsidemonitor.vitalsigncollection.VitalSignProcessing;


/**
 * 
 * 
 * @author Jason Smith
 */
public class BedsideMonitor implements BedsideMonitorInterface {

    private String patientName;
    private CallButtonController callButtonController;
    private Map<String, VitalSignCollectionController> vitalSignCollections;
    private Map<String, VitalSignProcessing> vitalSignProcessings;
    private List<NotificationService> notificationServices;
    private Timer timer;
    
    public BedsideMonitor(String patientName){
        this.patientName = patientName;
        notificationServices = new ArrayList<NotificationService>();
        callButtonController = new CallButtonController(notificationServices);
        vitalSignCollections = new HashMap<String, VitalSignCollectionController>();
        vitalSignProcessings = new HashMap<String, VitalSignProcessing>();
        timer = new Timer();
    }
    
    public void addSensor(VitalSignConfiguration configuration){
        String name = configuration.getName();
        SensorInterface sensor = SensorLookup.getInstance().getSensorByName(name);
        
        if(sensor != null) {
            
            Queue<Integer> vitalSignMsgQueue = new LinkedBlockingQueue<Integer>();
            VitalSignCollectionController controller = 
                    new VitalSignCollectionController(sensor, vitalSignMsgQueue);
            VitalSignProcessing processor = 
                    new VitalSignProcessing(vitalSignMsgQueue, configuration,
                            this.notificationServices);
            
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
    
    public void removeSensor(String sensorName){
        VitalSignProcessing processor = vitalSignProcessings.remove(sensorName);
        VitalSignCollectionController controller = vitalSignCollections.remove(sensorName);
        controller.cancel();
        processor.setActive(false);
    }
    
    public void addListener(Observer observer){
       for(VitalSignProcessing processing: vitalSignProcessings.values()){
            processing.addObserver(observer);
       }
       
       callButtonController.addObserver(observer);
    }
    
    public void subscribe(NotificationService service){
        synchronized(notificationServices) {
            notificationServices.add(service);
        }
    }
    
    public void unsubscribe(NotificationService service){
        synchronized(notificationServices) {
            notificationServices.remove(service);
        }
    }
    
    public VitalSignConfiguration getConfiguration(String vitalSignName){
        return vitalSignProcessings.get(vitalSignName).getConfiguration();
    }
    
    public void changeConfiguration(VitalSignConfiguration configuration){
        String name = configuration.getName();
        vitalSignProcessings.get(name).setConfiguration(configuration);
    }
    
    public boolean getCallButton(){
        return this.callButtonController.getCallStatus();
    }
    
    public void setCallButton(boolean callStatus){
        this.callButtonController.setCallStatus(callStatus);
    }
    
    public String getPatientName() {
        return patientName;
    }
    
} // BedsideMonitor

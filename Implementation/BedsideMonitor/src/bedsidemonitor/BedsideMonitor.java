/*
 * Package: bedsidemonitor
 *
 * File: BedsideMonitor.java
 *
 * Date: Nov 3, 2011
 * 
 */
package bedsidemonitor;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observer;

import nursestation.notificationservice.NotificationService;

import bedsidemonitor.callbutton.CallButtonController;
import bedsidemonitor.sensor.SensorInterface;
import bedsidemonitor.vitalsigncollection.VitalSignCollectionController;
import bedsidemonitor.vitalsigncollection.VitalSignConfiguration;
import bedsidemonitor.vitalsigncollection.VitalSignProcessing;


/**
 * @author Jason
 *
 */
public class BedsideMonitor implements BedsideMonitorInterface {

    private CallButtonController callButtonController;
    private Map<String, VitalSignCollectionController> vitalSignCollections;
    private Map<String, VitalSignProcessing> vitalSignProcessings;
    private List<NotificationService> notificationServices;
    
    public BedsideMonitor(){
        callButtonController = new CallButtonController();
        vitalSignCollections = new HashMap<String, VitalSignCollectionController>();
        vitalSignProcessings = new HashMap<String, VitalSignProcessing>();
        notificationServices = new ArrayList<NotificationService>();
    }
    
    public void addSensor(String sensorName, long millisecondDelay){
        // TODO: Lookup sensor based on the name in the remote object registry
        // TODO: Build the configuration off the name
        // TODO: Build a message queue for vital sign collection
        // TODO: Build the controller
        // TODO: Build the processor
        // TODO: Start the controller
    }
    
    public void removeSensor(String sensorName){
        // TODO: Remove the vital sign collection and processing objects
        // TODO: Cancel the timer task for these vital sign objects
    }
    
    public void addListener(Observer observer){
       for(VitalSignProcessing processing: vitalSignProcessings.values()){
            processing.addObserver(observer);
       }
       
       callButtonController.addObserver(observer);
    }
    
    public void subscribe(NotificationService service){
        
    }
    
    public void unsubscribe(NotificationService service){
        
    }
    
    public VitalSignConfiguration getConfiguration(String vitalSignName){
        return null;
    }
    
    public void changeConfiguration(VitalSignConfiguration configuration){
        
    }
    
}

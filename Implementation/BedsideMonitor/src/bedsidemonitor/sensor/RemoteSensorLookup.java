/*
 * Package: bedsidemonitor.sensor
 *
 * File: SensorLookup.java
 *
 * Date: Nov 3, 2011
 * 
 */
package bedsidemonitor.sensor;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.Map;

/**
 * This singleton class acts as a lookup service for sensor objects.
 * 
 * @author Jason Smith
 */
public class RemoteSensorLookup implements SensorLookupInterface {
    
    /**
     * Registry object for RMI
     */
    private Registry registry;
    
    /**
     * Constructs a SensorLookup service.
     */
    public RemoteSensorLookup(){
        System.setSecurityManager(new RMISecurityManager());
        try {
            this.registry = LocateRegistry.getRegistry();
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Looks up a sensor object in the RMI registry.
     * 
     * @param sensorName the object to lookup
     * @return a sensor object if it exists, null otherwise
     */
    public SensorInterface getSensorByName(String sensorName){
        SensorInterface sensor = null;
        
        if(registry != null){
            try {
                sensor = (SensorInterface) registry.lookup(sensorName);
            } catch (AccessException ex) {
            } catch (RemoteException ex) {
            } catch (NotBoundException ex) {
            }
        }
        
        return sensor;
    }
    
    /**
     * Looks up a group of sensors in the RMI registry.
     * 
     * @param sensorNames the objects to lookup
     * 
     * @return a map of sensor names mapping to sensor objects found
     */
    public Map<String, SensorInterface> getSensorsbyName(String[] sensorNames){
        Map<String, SensorInterface> sensors =
                new HashMap<String, SensorInterface>();
        
        for(String sensorName: sensorNames){
            SensorInterface sensor = getSensorByName(sensorName);
            
            if(sensor != null) {
                sensors.put(sensorName, sensor);
            } else {
                System.err.println("Sensor " + sensorName + " was not found");
            }
        }
        
        return sensors;
    }
    
} // RemoteSensorLookup

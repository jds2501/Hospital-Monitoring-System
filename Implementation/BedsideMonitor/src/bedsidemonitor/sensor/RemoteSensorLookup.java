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
    
} // SensorLookup
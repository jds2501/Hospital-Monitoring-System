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
public class SensorLookup {

    /**
     * Singleton object for this class
     */
    private static SensorLookup instance;
    
    /**
     * Registry object for RMI
     */
    private Registry registry;
    
    /**
     * Constructs a SensorLookup service.
     */
    private SensorLookup(){
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
                ex.printStackTrace();
            } catch (RemoteException ex) {
                ex.printStackTrace();
            } catch (NotBoundException ex) {
                ex.printStackTrace();
            }
        }
        
        return sensor;
    }
    
    /**
     * Gets the singleton instance of this class.
     * 
     * @return the singleton instance of this class
     */
    public static SensorLookup getInstance() {
        if(instance == null){
            instance = new SensorLookup();
        }
        
        return instance;
    }
    
} // SensorLookup

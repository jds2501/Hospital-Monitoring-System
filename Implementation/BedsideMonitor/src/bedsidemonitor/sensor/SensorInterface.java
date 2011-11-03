/*
 * Package: bedsidemonitor.vitalsigncollection
 *
 * File: SensorInterface.java
 *
 * Date: Nov 1, 2011
 * 
 */
package bedsidemonitor.sensor;

import java.rmi.Remote;
import java.rmi.RemoteException;


/**
 * Abstract interface to allow callers to get a vital sign measurement
 * off of a sensor.
 * 
 * @author Jason Smith
 */
public interface SensorInterface extends Remote {

    /**
     * Gets the vital sign off of the sensor.
     * 
     * @return an integer value of 0 - 100 off of the sensor
     * @throws RemoteException if the call fails
     */
    public int getVitalSign() throws RemoteException;
    
} // SensorInterface

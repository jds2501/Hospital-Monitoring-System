/*
 * Package: bedsidemonitor.sensor
 *
 * File: FakeSensor.java
 *
 * Date: Nov 4, 2011
 * 
 */
package bedsidemonitor.sensor;

import java.rmi.RemoteException;


/**
 * Represents a fake sensor for testing sensors in the bedside monitor
 * independent of the network.
 * 
 * @author Jason Smith
 */
public class FakeSensor implements SensorInterface {

    /**
     * The vital to return
     */
    private int vital;
    
    /**
     * Constructs a FakeSensor object with a specified vital.
     * 
     * @param vital the vital to return when getting a vital
     */
    public FakeSensor(int vital){
        this.vital = vital;
    }

    /**
     * @return the vital sign given to this sensor
     */
    public int getVitalSign() throws RemoteException {
        return vital;
    }

} // FakeSensor

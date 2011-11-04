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
 * @author Jason
 *
 */
public class FakeSensor implements SensorInterface {

    private int vital;
    
    public FakeSensor(int vital){
        this.vital = vital;
    }

    public int getVitalSign() throws RemoteException {
        return vital;
    }

}

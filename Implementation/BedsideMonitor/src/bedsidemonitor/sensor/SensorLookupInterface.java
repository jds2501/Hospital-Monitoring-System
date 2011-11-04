/*
 * Package: bedsidemonitor.sensor
 *
 * File: SensorLookupInterface.java
 *
 * Date: Nov 4, 2011
 * 
 */
package bedsidemonitor.sensor;


/**
 * @author Jason
 *
 */
public interface SensorLookupInterface {

    public SensorInterface getSensorByName(String sensorName);
    
}

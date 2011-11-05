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
 * This interface defines the sensor lookup service for getting a sensor
 * by name.
 * 
 * @author Jason Smith
 */
public interface SensorLookupInterface {

    /**
     * Gets a sensor by the given name.
     * 
     * @param sensorName the name of the sensor to get
     * 
     * @return the sensor if it exists, null otherwise
     */
    public SensorInterface getSensorByName(String sensorName);
    
} // SensorLookupInterface

/*
 * Package: bedsidemonitor.sensor
 *
 * File: FakeSensorLookup.java
 *
 * Date: Nov 4, 2011
 * 
 */
package bedsidemonitor.sensor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * This class represents a fake sensor lookup to simulate looking up
 * sensors independent of the network.
 * 
 * @author Jason Smith
 */
public class FakeSensorLookup implements SensorLookupInterface {
    
    /**
     * The map of local sensor objects
     */
    private Map<String, SensorInterface> sensors;

    /**
     * Constructs a FakeSensorLookup object.
     */
    public FakeSensorLookup(){
        sensors = new HashMap<String, SensorInterface>();
    }
    
    /**
     * Adds a sensor to the map of local sensors.
     * 
     * @param name the name of the sensor
     * @param sensor the sensor to get when requested
     */
    public void addSensor(String name, SensorInterface sensor){
        sensors.put(name, sensor);
    }
    
    /**
     * Gets the specified sensor by name
     * 
     * @param sensorName the name of the sensor to get
     * 
     * @return the sensor if it exists
     */
    public SensorInterface getSensorByName(String sensorName) {
        return sensors.get(sensorName);
    }
    
    
    /**
     * @return the sensors
     */
    public Map<String, SensorInterface> getSensors() {
    
        return sensors;
    }
    
} // FakeSensorLookup

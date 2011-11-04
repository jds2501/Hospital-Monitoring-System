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
 * @author Jason
 *
 */
public class FakeSensorLookup implements SensorLookupInterface {
    
    private Map<String, SensorInterface> sensors;
    
    public FakeSensorLookup(){
        sensors = new HashMap<String, SensorInterface>();
    }
    
    public void addSensor(String name, SensorInterface sensor){
        sensors.put(name, sensor);
    }
    
    public SensorInterface getSensorByName(String sensorName) {
        return sensors.get(sensorName);
    }
    
}

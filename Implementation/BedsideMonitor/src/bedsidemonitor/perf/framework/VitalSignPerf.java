/*
 * Package: bedsidemonitor.perf
 *
 * File: VitalSignPerf.java
 *
 * Date: Nov 6, 2011
 * 
 */
package bedsidemonitor.perf.framework;

import bedsidemonitor.sensor.RemoteSensorLookup;
import bedsidemonitor.sensor.SensorInterface;
import bedsidemonitor.sensor.SensorLookupInterface;
import bedsidemonitor.vitalsigncollection.VitalSignConfiguration;


/**
 * @author Jason
 *
 */
public class VitalSignPerf {

    private String name;
    private SensorInterface sensor;
    private double conversionFactor;
    private long collectionRate;
    
    public VitalSignPerf(String sensorName, double conversionFactor,
            long collectionRate) {
        
        if(!(conversionFactor >= 0 && conversionFactor <= 100)) {
            throw new IllegalArgumentException("Alarm firing rate must be from 0.0 to 1.0");
        }
        
        this.name = sensorName;
        this.conversionFactor = conversionFactor;
        this.collectionRate = collectionRate;
        
        SensorLookupInterface sensorLookup = new RemoteSensorLookup();
        this.sensor = sensorLookup.getSensorByName(sensorName);
        
        if(sensor == null) {
            throw new IllegalArgumentException("Sensor specified was not found");
        }
    }
    
    public SensorInterface getSensor() {
        return sensor;
    }
    
    public VitalSignConfiguration getConfiguration() {
        return new VitalSignConfiguration(
                name, conversionFactor, 0.0, 100.0, collectionRate);
    }
    
}

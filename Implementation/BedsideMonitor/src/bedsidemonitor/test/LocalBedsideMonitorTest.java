/*
 * Package: bedsidemonitor.test
 *
 * File: BedsideMonitorTest.java
 *
 * Date: Nov 4, 2011
 * 
 */

package bedsidemonitor.test;

import java.rmi.RemoteException;
import java.util.Map;

import bedsidemonitor.BedsideMonitor;
import bedsidemonitor.sensor.FakeSensor;
import bedsidemonitor.sensor.FakeSensorLookup;
import bedsidemonitor.sensor.SensorInterface;
import bedsidemonitor.sensor.SensorLookupInterface;
import bedsidemonitor.vitalsigncollection.VitalSignConfiguration;
import junit.framework.TestCase;

/**
 * This class represents a test class to simulate a bedside monitor
 * independent of the network.
 * 
 * @author Jason Smith
 */
public class LocalBedsideMonitorTest extends SensorTestCase {
    
    /**
     * Sensor lookup interface for getting sensors
     */
    private FakeSensorLookup sensorLookup;
    
    /**
     * Tears down each test case by removing each sensor.
     */
    public void tearDown() {
        Map<String, SensorInterface> sensors = this.sensorLookup.getSensors();
        
        for(String sensorName: sensors.keySet()){
            this.bedsideMonitor.removeSensor(sensorName);
        }
    }

    public BedsideMonitor buildBedsideMonitor() throws RemoteException {
        this.sensorLookup = new FakeSensorLookup();
        return new BedsideMonitor("sample", sensorLookup);
    }

    public void addSensor(String sensorName) {
        SensorInterface sensor = new FakeSensor(50);
        this.sensorLookup.addSensor(sensorName, sensor);
    }

    public void removeSensor(String sensorName) {
        this.sensorLookup.getSensors().remove(sensorName);
    }
    
} // BedsideMonitorTest

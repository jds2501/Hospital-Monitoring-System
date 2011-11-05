/*
 * Package: bedsidemonitor.test
 *
 * File: LocalBedsideMonitorTest.java
 *
 * Date: Nov 4, 2011
 * 
 */

package bedsidemonitor.test;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

import bedsidemonitor.BedsideMonitor;
import bedsidemonitor.sensor.FakeSensor;
import bedsidemonitor.sensor.SensorInterface;

/**
 * This class represents a test class to simulate a bedside monitor
 * independent of the network.
 * 
 * @author Jason Smith
 */
public class LocalBedsideMonitorTest extends BedsideMonitorTestCase {
    
    private Map<String, SensorInterface> sensors;
    
    /**
     * Tears down each test case by removing each sensor.
     */
    public void tearDown() {
        for(String sensorName: sensors.keySet()){
            this.bedsideMonitor.removeSensor(sensorName);
        }
    }

    /**
     * Builds the bedside monitor for a local test case.
     * 
     * @return a bedside monitor referencing a local storage
     */
    public BedsideMonitor buildBedsideMonitor() throws RemoteException {
        this.sensors = new HashMap<String, SensorInterface>();
        return new BedsideMonitor("sample", sensors);
    }

    /**
     * Adds a sensor to the local storage.
     * 
     * @param sensorName the name of the sensor to add
     */
    public void addSensor(String sensorName) {
        this.sensors.put(sensorName, new FakeSensor(50));
    }

    /**
     * Removes a sensor from the local storage
     * 
     * @param sensorName the name of the sensor to remove
     */
    public void removeSensor(String sensorName) {
        this.sensors.remove(sensorName);
    }
    
} // LocalBedsideMonitorTest

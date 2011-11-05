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
public class LocalBedsideMonitorTest extends TestCase {

    /**
     * The bedside monitor used for testing
     */
    private BedsideMonitor bedsideMonitor;
    
    /**
     * Sensor lookup interface for getting sensors
     */
    private FakeSensorLookup sensorLookup;
    
    /**
     * Sets up a bedside monitor object for each test case.
     */
    public void setUp() throws RemoteException {
        this.sensorLookup = new FakeSensorLookup();
        this.bedsideMonitor = new BedsideMonitor(sensorLookup);
    }
    
    /**
     * Tears down each test case by removing each sensor.
     */
    public void tearDown() {
        Map<String, SensorInterface> sensors = this.sensorLookup.getSensors();
        
        for(String sensorName: sensors.keySet()){
            this.bedsideMonitor.removeSensor(sensorName);
        }
    }
    
    /**
     * Test to verify that adding a non existent sensor should throw
     * an exception.
     */
    public void testAddNonExistentSensor(){
        boolean sensorFound = true;
        
        VitalSignConfiguration configuration = 
                new VitalSignConfiguration("not found", 0, 0, 0, 0);
        try{
            this.bedsideMonitor.addSensor(configuration);
        }catch(IllegalArgumentException ex){
            sensorFound = false;
        }
        
        assertFalse(sensorFound);
    }
    
    /**
     * Test to verify that a sensor that exists results in a new
     * vital sign processor and controller started.
     */
    public void testAddExistentSensor() {
        VitalSignConfiguration configuration = 
                new VitalSignConfiguration("valid", 1, 1, 2, 100);
        SensorInterface sensor = new FakeSensor(50);
        this.sensorLookup.addSensor("valid", sensor);
        this.bedsideMonitor.addSensor(configuration);
        
        assertEquals(this.bedsideMonitor.getConfiguration("valid"),
                     configuration);
    }
    
    /**
     * Test to verify that removing a sensor that does not exist
     * throws an exception.
     */
    public void testRemoveNonExistentSensor() {
        boolean sensorFound = true;
        
        try{
            this.bedsideMonitor.removeSensor("not found");
        }catch(IllegalArgumentException ex){
            sensorFound = false;
        }
        
        assertFalse(sensorFound);
    }
    
    /**
     * Test to verify that removing a sensor that exists removes
     * the vital sign from the bedside monitor.
     */
    public void testRemoveExistentSensor() {
        this.testAddExistentSensor();
        this.bedsideMonitor.removeSensor("valid");
        this.sensorLookup.getSensors().remove("valid");
        
        assertEquals(this.bedsideMonitor.getConfiguration("valid"), null);
    }
    
} // BedsideMonitorTest

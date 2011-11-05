/*
 * Package: bedsidemonitor.test
 *
 * File: BedsideMonitorTestCase.java
 *
 * Date: Nov 4, 2011
 * 
 */
package bedsidemonitor.test;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import bedsidemonitor.BedsideMonitor;
import bedsidemonitor.vitalsigncollection.VitalSignConfiguration;
import junit.framework.TestCase;


/**
 * Abstraction of a test case for verifying the bedside monitor functionality.
 * 
 * @author Jason Smith
 */
public abstract class BedsideMonitorTestCase extends TestCase {

    /**
     * The bedside monitor to test
     */
    protected BedsideMonitor bedsideMonitor;
    
    /**
     * Sets up each test case with a new bedside monitor.
     */
    public void setUp() throws RemoteException {
        this.bedsideMonitor = this.buildBedsideMonitor();
    }
    
    /**
     * @return A bedside monitor specific to the concrete test case.
     */
    public abstract BedsideMonitor buildBedsideMonitor() throws RemoteException;
    
    /**
     * Adds a sensor to the concrete test case.
     * 
     * @param sensorName the name of the sensor to add
     */
    public abstract void addSensor(String sensorName) throws RemoteException,
        MalformedURLException, AlreadyBoundException;
    
    /**
     * Removes a sensor to the concrete test case.
     * 
     * @param sensorName the name of the sensor to remove
     */
    public abstract void removeSensor(String sensorName) throws RemoteException,
        MalformedURLException, NotBoundException;
    
    /**
     * Test to verify that adding a non existent sensor should throw
     * an exception.
     */
    public void testAddNonExistentSensor(){
        boolean sensorFound = true;
        
        VitalSignConfiguration configuration = 
                new VitalSignConfiguration("not found", 0, 0, 0, 0);
        try{
            this.bedsideMonitor.enableMeasurement(configuration);
        }catch(IllegalArgumentException ex){
            sensorFound = false;
        }
        
        assertFalse(sensorFound);
    }
    
    /**
     * Test to verify that a sensor that exists results in a new
     * vital sign processor and controller started.
     */
    public void testAddExistentSensor() throws RemoteException,
        MalformedURLException, AlreadyBoundException {
        VitalSignConfiguration configuration = 
                new VitalSignConfiguration("valid", 1, 1, 2, 100);
        this.addSensor("valid");
        this.bedsideMonitor.enableMeasurement(configuration);
        
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
            this.bedsideMonitor.disableMeasurement("not found");
        }catch(IllegalArgumentException ex){
            sensorFound = false;
        }
        
        assertFalse(sensorFound);
    }
    
    /**
     * Test to verify that removing a sensor that exists removes
     * the vital sign from the bedside monitor.
     */
    public void testRemoveExistentSensor() throws RemoteException,
        MalformedURLException, AlreadyBoundException, NotBoundException {
        this.testAddExistentSensor();
        this.bedsideMonitor.disableMeasurement("valid");
        this.removeSensor("valid");
        
        assertEquals(this.bedsideMonitor.getConfiguration("valid"), null);
    }
    
} // BedsideMonitorTestCase

/*
 * Package: bedsidemonitor.test
 *
 * File: SensorTestHelper.java
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
import bedsidemonitor.sensor.FakeSensor;
import bedsidemonitor.sensor.SensorInterface;
import bedsidemonitor.vitalsigncollection.VitalSignConfiguration;
import junit.framework.TestCase;


/**
 * @author Jason
 *
 */
public abstract class BedsideMonitorTestCase extends TestCase {

    protected BedsideMonitor bedsideMonitor;
    
    public void setUp() throws RemoteException {
        this.bedsideMonitor = this.buildBedsideMonitor();
    }
    
    public abstract BedsideMonitor buildBedsideMonitor() throws RemoteException;
    
    public abstract void addSensor(String sensorName) throws RemoteException, MalformedURLException, AlreadyBoundException;
    
    public abstract void removeSensor(String sensorName) throws RemoteException, MalformedURLException, NotBoundException;
    
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
     * @throws AlreadyBoundException 
     * @throws MalformedURLException 
     * @throws RemoteException 
     */
    public void testAddExistentSensor() throws RemoteException, MalformedURLException, AlreadyBoundException {
        VitalSignConfiguration configuration = 
                new VitalSignConfiguration("valid", 1, 1, 2, 100);
        this.addSensor("valid");
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
     * @throws AlreadyBoundException 
     * @throws MalformedURLException 
     * @throws RemoteException 
     * @throws NotBoundException 
     */
    public void testRemoveExistentSensor() throws RemoteException, MalformedURLException, AlreadyBoundException, NotBoundException {
        this.testAddExistentSensor();
        this.bedsideMonitor.removeSensor("valid");
        this.removeSensor("valid");
        
        assertEquals(this.bedsideMonitor.getConfiguration("valid"), null);
    }
    
}

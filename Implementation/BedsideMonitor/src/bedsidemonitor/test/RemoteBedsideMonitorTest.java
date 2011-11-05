/*
 * Package: bedsidemonitor.test
 *
 * File: RemoteBedsideMonitorTest.java
 *
 * Date: Nov 4, 2011
 * 
 */
package bedsidemonitor.test;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import bedsidemonitor.BedsideMonitor;
import bedsidemonitor.sensor.SensorImpl;


/**
 * This group of test cases focusing on exercising bedside monitor functionality
 * with remote sensors.
 * 
 * @author Jason Smith
 */
public class RemoteBedsideMonitorTest extends BedsideMonitorTestCase {
    
    /**
     * The list of sensors active in the registry
     */
    private List<String> sensorsActive;
    
    /**
     * Removes all known sensors from the registry
     */
    public void tearDown() {
        
        
        for(String sensorName: sensorsActive){
            try {
                Naming.unbind(sensorName);
            } catch (RemoteException ex) {
                ex.printStackTrace();
            } catch (MalformedURLException ex) {
                ex.printStackTrace();
            } catch (NotBoundException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Builds a bedside monitor with a remote lookup service.
     * 
     * @return a bedside monitor pointing to the RMI registry
     */
    public BedsideMonitor buildBedsideMonitor() throws RemoteException {
        this.sensorsActive = new ArrayList<String>();
        return null;
        //return new BedsideMonitor("patient");
    }

    /**
     * Adds a sensor to the RMI registry
     * 
     * @param sensorName the sensor to add
     */
    public void addSensor(String sensorName) throws RemoteException,
        MalformedURLException, AlreadyBoundException {
        SensorImpl sensor = new SensorImpl();
        Naming.bind(sensorName, sensor);
        sensorsActive.add(sensorName);
    }

    /**
     * Removes a sensor from the RMI registry
     * 
     * @param sensorName the sensor to remove
     */
    public void removeSensor(String sensorName) throws RemoteException,
        MalformedURLException, NotBoundException {
        Naming.unbind(sensorName);
        sensorsActive.remove(sensorName);
    }
    
} // RemoteBedsideMonitorTest

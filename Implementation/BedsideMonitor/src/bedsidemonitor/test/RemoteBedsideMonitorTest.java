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
import bedsidemonitor.vitalsigncollection.VitalSignConfiguration;
import junit.framework.TestCase;


/**
 * @author Jason
 *
 */
public class RemoteBedsideMonitorTest extends SensorTestCase {
    
    private List<String> sensorsActive;
    
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

    public BedsideMonitor buildBedsideMonitor() throws RemoteException {
        this.sensorsActive = new ArrayList<String>();
        return new BedsideMonitor("patient");
    }

    public void addSensor(String sensorName) throws RemoteException, MalformedURLException, AlreadyBoundException {
        SensorImpl sensor = new SensorImpl();
        Naming.bind(sensorName, sensor);
        sensorsActive.add(sensorName);
    }

    public void removeSensor(String sensorName) throws RemoteException, MalformedURLException, NotBoundException {
        Naming.unbind(sensorName);
    }
    
}

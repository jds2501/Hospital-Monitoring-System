/*
 * Package: bedsidemonitor
 *
 * File: BedsideMonitorMain.java
 *
 * Date: Nov 2, 2011
 * 
 */

package bedsidemonitor;

import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import bedsidemonitor.sensor.SensorInterface;

/**
 * Main starting point to start up the bedside monitor.
 * 
 * @author Jason Smith
 */
public class BedsideMonitorMain {

    /**
     * Main method of execution to startup the bedside monitor system.
     * 
     * @param args[0] Number of sensors
     */
    public static void main(String[] args){
    	
    	Registry registry;
		try {
			System.setSecurityManager(new RMISecurityManager());
			registry = LocateRegistry.getRegistry();
			SensorInterface sensor = (SensorInterface)registry.lookup("sensor");
			System.out.println(sensor.getVitalSign());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	 
    }
    
} // BedsideMonitorMain

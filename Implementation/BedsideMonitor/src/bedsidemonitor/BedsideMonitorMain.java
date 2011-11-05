/*
 * Package: bedsidemonitor
 *
 * File: BedsideMonitorMain.java
 *
 * Date: Nov 2, 2011
 * 
 */

package bedsidemonitor;

<<<<<<< HEAD
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import bedsidemonitor.sensor.SensorInterface;
=======
import java.rmi.RemoteException;

import bedsidemonitor.userinterface.BedsideMonitorView;
import bedsidemonitor.userinterface.VitalStatDisplay;
>>>>>>> f207d6ef488732aa079efa9dae51577840bacbfc

/**
 * Main starting point to start up the bedside monitor.
 * 
 * @author Jason Smith
 */
public class BedsideMonitorMain {
	
	private BedsideMonitorView view;
	private VitalStatDisplay vitalStatDisplay;
	
	/**
     * Constructor for bedside monitor that shows GUI.
     */
	public BedsideMonitorMain(String patientName) {
        try {
            BedsideMonitor bedsideMonitor = new BedsideMonitor(patientName);
            view = new BedsideMonitorView(bedsideMonitor);
            view.setMonitorName("Dummy Bedside Monitor View");
            vitalStatDisplay = new VitalStatDisplay(view);
            view.setVitalStatDisplay(vitalStatDisplay);
            vitalStatDisplay.paintPatientPanels();
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
	}

    /**
     * Main method of execution to startup the bedside monitor system.
     * 
     * @param args[0] name of bedside monitor
     */
    public static void main(String[] args){
<<<<<<< HEAD
    	
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
    	 
=======
        if(args.length == 1){        
            new BedsideMonitorMain(args[0]);
        } else {
            System.err.println("Usage: java BedsideMonitorMain patientName");
        }
>>>>>>> f207d6ef488732aa079efa9dae51577840bacbfc
    }
    
} // BedsideMonitorMain

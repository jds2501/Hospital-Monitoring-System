/*
 * Package: bedsidemonitor
 *
 * File: BedsideMonitorMain.java
 *
 * Date: Nov 2, 2011
 * 
 */

package bedsidemonitor;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;

import bedsidemonitor.userinterface.BedsideMonitorView;
import bedsidemonitor.userinterface.PatientStatsPanel;

/**
 * Main starting point to start up the bedside monitor.
 * 
 * @author Jason Smith
 */
public class BedsideMonitorMain {
	
	private BedsideMonitorView view;
	private PatientStatsPanel patientDisplay;
	
	/**
     * Constructor for bedside monitor that shows GUI.
     */
	public BedsideMonitorMain(String patientName) {
        try {
            BedsideMonitor bedsideMonitor = new BedsideMonitor(patientName);
            Naming.rebind(patientName, bedsideMonitor);
            view = new BedsideMonitorView(bedsideMonitor);
            view.setMonitorName("Dummy Bedside Monitor View");
            patientDisplay = new PatientStatsPanel(view);
            patientDisplay.paintPatientPanels();
        } catch (RemoteException ex) {
            ex.printStackTrace();
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }
	}

    /**
     * Main method of execution to startup the bedside monitor system.
     * 
     * @param args[0] name of bedside monitor
     */
    public static void main(String[] args){
        if(args.length == 1){        
            new BedsideMonitorMain(args[0]);
        } else {
            System.err.println("Usage: java BedsideMonitorMain patientName");
        }
    }
    
} // BedsideMonitorMain

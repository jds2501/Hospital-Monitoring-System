/*
 * Package: bedsidemonitor
 *
 * File: BedsideMonitorMain.java
 *
 * Date: Nov 2, 2011
 * 
 */

package bedsidemonitor;

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
	public BedsideMonitorMain() {
        view = new BedsideMonitorView();
        view.setMonitorName("Dummy Bedside Monitor View");
        patientDisplay = new PatientStatsPanel(view);
        patientDisplay.paintPatientPanels();
	}

    /**
     * Main method of execution to startup the bedside monitor system.
     * 
     * @param args[0] name of bedside monitor
     * @param args[1] Number of sensors
     */
    public static void main(String[] args){
        new BedsideMonitorMain();
        
        
    }
    
} // BedsideMonitorMain

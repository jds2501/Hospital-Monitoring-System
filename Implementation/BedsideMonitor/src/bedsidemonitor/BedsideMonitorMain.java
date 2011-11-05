/*
 * Package: bedsidemonitor
 *
 * File: BedsideMonitorMain.java
 *
 * Date: Nov 2, 2011
 * 
 */

package bedsidemonitor;

import java.rmi.RemoteException;
import java.util.Random;
import java.util.Timer;

import bedsidemonitor.userinterface.BedsideMonitorView;
import bedsidemonitor.userinterface.VitalStatDisplay;

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

            view.addVitalSign("Heart Rate");
            view.addVitalSign("Systolic Blood Pressure");
            view.addVitalSign("Respiratory Rate");
            view.addVitalSign("Weight (kg)");
            view.addVitalSign("Weight (lb)");

	    	//TODO ** PURELY TEST CODE - REMOVE AFTER INTEGRATION **
            while (true) {
            	try {
            		Thread.sleep(800);
            	}
            	catch (InterruptedException ie) {
            		ie.printStackTrace();
            	}
            	Random rand = new Random();
            	view.updateVitalSign("Heart Rate", rand.nextInt(100 - 1 + 1) + 1);
            	view.updateVitalSign("Systolic Blood Pressure", rand.nextInt(100 - 1 + 1) + 1);
            	view.updateVitalSign("Respiratory Rate", rand.nextInt(100 - 1 + 1) + 1);
            	view.updateVitalSign("Weight (kg)", rand.nextInt(100 - 1 + 1) + 1);
            	view.updateVitalSign("Weight (lb)", rand.nextInt(100 - 1 + 1) + 1);
            }
            
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
        if(args.length == 1){        
            new BedsideMonitorMain(args[0]);
        } else {
            System.err.println("Usage: java BedsideMonitorMain patientName");
        }
    }
    
} // BedsideMonitorMain

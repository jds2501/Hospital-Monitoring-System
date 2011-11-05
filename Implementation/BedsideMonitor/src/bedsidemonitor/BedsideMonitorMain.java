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
import java.util.HashMap;
import java.util.Map;

import bedsidemonitor.sensor.RemoteSensorLookup;
import bedsidemonitor.sensor.SensorImpl;
import bedsidemonitor.sensor.SensorInterface;
import bedsidemonitor.sensor.SensorLookupInterface;
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
	public BedsideMonitorMain(String patientName, String[] sensorNames) {
        try {
            SensorLookupInterface sensorLookup = new RemoteSensorLookup();
            Map<String, SensorInterface> sensors = sensorLookup.getSensorsbyName(sensorNames);
            BedsideMonitor bedsideMonitor = new BedsideMonitor(patientName, sensors);
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
     * @param args[1..N] the sensor names to lookup
     */
    public static void main(String[] args){
        if(args.length >= 1){
            String[] sensorNames = new String[args.length - 1];
            
            for(int i = 1; i < args.length; i++){
                sensorNames[i - 1] = args[i];
            }
            
            new BedsideMonitorMain(args[0], sensorNames);
        } else {
            System.err.println("Usage: java BedsideMonitorMain patientName");
        }
    }
    
} // BedsideMonitorMain

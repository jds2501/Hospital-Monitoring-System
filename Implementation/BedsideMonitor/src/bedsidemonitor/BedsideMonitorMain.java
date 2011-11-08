/*
 * Package: bedsidemonitor
 *
 * File: BedsideMonitorMain.java
 *
 * Date: Nov 2, 2011
 * 
 */

package bedsidemonitor;

import java.awt.FlowLayout;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.Map;

import javax.swing.JPanel;

import bedsidemonitor.sensor.RemoteSensorLookup;
import bedsidemonitor.sensor.SensorInterface;
import bedsidemonitor.sensor.SensorLookupInterface;
import bedsidemonitor.userinterface.BedsideMonitorView;

/**
 * Main starting point to start up the bedside monitor.
 * 
 * @author Jason Smith
 */
public class BedsideMonitorMain {
	
	private BedsideMonitorView view;
	
	/**
     * Constructor for bedside monitor that shows GUI.
     */
	public BedsideMonitorMain(String patientName, String[] sensorNames) {
        try {
            SensorLookupInterface sensorLookup = new RemoteSensorLookup();
            Map<String, SensorInterface> sensors = sensorLookup.getSensorsbyName(sensorNames);
            BedsideMonitor bedsideMonitor = new BedsideMonitor(patientName, sensors);
            view = new BedsideMonitorView(bedsideMonitor);
            view.setMonitorName("Bedside Monitor for Patient: " + patientName);
            
            // Add all of the sensors
            for(String sensorName: sensors.keySet()) {
                view.addVitalSign(sensorName);
            }
            
            // Display the sensors
            view.showVitalStatDisplay();
            
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
            System.err.println("Usage: java BedsideMonitorMain patientName [vital stat name]...");
        }
    }
    
} // BedsideMonitorMain

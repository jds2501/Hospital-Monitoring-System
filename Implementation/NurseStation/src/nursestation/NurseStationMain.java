package nursestation;

import java.awt.FlowLayout;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.swing.JPanel;

import nursestation.notificationservice.NotificationServiceImpl;
import nursestation.notificationservice.NotificationServiceTask;
import nursestation.userinterface.NurseStationView;
import bedsidemonitor.BedsideMonitorSubscribeInterface;

/**
 * Main starting point to start up the nurse station
 * 
 * @author Chris Bentivenga
 */
public class NurseStationMain {
	
    /**
     * Nurse's Station
     */
    private NurseStation nurseStation;
    
    /**
     * The view of the nurse station UI
     */
	private NurseStationView view;
	
	/**
	 * Sets up the execution of the nurses' station by subscribing
	 * this nurses' station to the given patients and starting the GUI.
	 * 
	 * @param patientNames the names of the patients to subscribe to
	 */
	public NurseStationMain(String[] patientNames) {
	    try {
	        nurseStation = new NurseStation(patientNames);
            this.constructNurseStationGUI(patientNames);
        } catch (RemoteException ex) {
            ex.printStackTrace();
        } catch (NotBoundException ex) {
            ex.printStackTrace();
        }
	}
	
	/**
	 * Constructs the Nurse Station GUI and starts it up.
	 */
	private void constructNurseStationGUI(String[] patientNames){
        view = new NurseStationView(nurseStation.getServiceTask());
        view.setStationNameBox("Dummy Nurse Station");
        view.setPatientDisplay(new JPanel(new FlowLayout()));
        
        for (String patientName : patientNames) {
        	view.addPatient(patientName);
        }
        view.setPatientNumber(patientNames.length);
	}
	
	/**
	 * Main method of execution to startup the nurse station.
	 * 
	 * @param args patients to admit
	 */
	public static void main(String[] args){
        new NurseStationMain(args);
	}
	
} // NurseStationMain

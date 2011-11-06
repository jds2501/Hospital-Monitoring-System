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
     * The notification service used for receiving patient messages
     */
    private NotificationServiceImpl service;
    
    /**
     * The view of the nurse station UI
     */
	private NurseStationView view;

	/**
	 * The service task for collecting patient data
	 */
    private NotificationServiceTask serviceRunnable;
	
	/**
	 * Sets up the execution of the nurses' station by subscribing
	 * this nurses' station to the given patients and starting the GUI.
	 * 
	 * @param patientNames the names of the patients to subscribe to
	 */
	public NurseStationMain(String[] patientNames) {
	    try {
	        this.service = new NotificationServiceImpl();
	        this.serviceRunnable = new NotificationServiceTask(service);
	        
	        for(String patientName: patientNames) {
	            this.serviceRunnable.addPatient(patientName);
	        }
	        
	        Thread serviceTask = new Thread(serviceRunnable);
	        serviceTask.start();
	        
            this.subscribeToPatients(patientNames);
            this.constructNurseStationGUI(patientNames);
        } catch (RemoteException ex) {
            ex.printStackTrace();
        } catch (NotBoundException ex) {
            ex.printStackTrace();
        }
	}
	
	/**
	 * Subscribes this nurse station to the specified patients.
	 * 
	 * @param patientNames the names of the patients to subscribe to
	 * 
	 * @throws RemoteException If the remote connection fails
	 * @throws NotBoundException If the remote object does not exist in the
	 * RMI registry
	 */
	private void subscribeToPatients(String[] patientNames) 
	        throws RemoteException, NotBoundException {
	    System.setSecurityManager(new RMISecurityManager());
	    Registry registry = LocateRegistry.getRegistry();
	    
	    for(String patientName: patientNames){
	        BedsideMonitorSubscribeInterface bedsideMonitor = 
	                (BedsideMonitorSubscribeInterface) registry.lookup(patientName);
	        bedsideMonitor.subscribe(service);
	    }
	}
	
	/**
	 * Constructs the Nurse Station GUI and starts it up.
	 */
	private void constructNurseStationGUI(String[] patientNames){
        view = new NurseStationView();
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

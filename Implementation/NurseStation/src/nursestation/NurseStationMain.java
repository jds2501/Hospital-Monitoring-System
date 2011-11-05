package nursestation;

import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import bedsidemonitor.BedsideMonitorSubscribeInterface;
import nursestation.notificationservice.NotificationService;
import nursestation.notificationservice.NotificationServiceImpl;
import nursestation.notificationservice.NotificationServiceTask;
import nursestation.userinterface.NurseStationView;
import nursestation.userinterface.PatientDisplay;

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
	 * The display of the patient UI
	 */
	private PatientDisplay patientDisplay;

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
	        Thread serviceTask = new Thread(serviceRunnable);
	        serviceTask.start();
	        
            this.subscribeToPatients(patientNames);
            this.constructNurseStationGUI();
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
	private void constructNurseStationGUI(){
        view = new NurseStationView();
        view.setStationNameBox("Dummy Nurse Station");
        view.setPatientNumber(7);
        patientDisplay = new PatientDisplay(view);
        view.setPatientDisplay(patientDisplay);
        patientDisplay.paintPatientPanels();
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

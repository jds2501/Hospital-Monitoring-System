package nursestation;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;

import nursestation.notificationservice.NotificationServiceImpl;
import nursestation.userinterface.NurseStationView;
import nursestation.userinterface.PatientDisplay;

/**
 * Main starting point to start up the nurse station
 * 
 * @author Chris Bentivenga
 */
public class NurseStationMain {
	
	private NurseStationView view;
	private PatientDisplay patientDisplay;
	
	/**
	 * Constructor which creates and displays the GUI.
	 */
	public NurseStationMain() {
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
	 * @param args
	 */
	public static void main(String[] args){
		//try {
            //NotificationServiceImpl service = new NotificationServiceImpl();
            //Naming.rebind("nurse-station", service);
            
            // Instantiate the GUI
            new NurseStationMain();
            
//        } catch (RemoteException ex) {
//            ex.printStackTrace();
//        } catch (MalformedURLException ex) {
//            ex.printStackTrace();
//        }
	}
	
} // NurseStationMain

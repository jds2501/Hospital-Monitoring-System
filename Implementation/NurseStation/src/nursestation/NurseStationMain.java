package nursestation;

import nursestation.userinterface.NurseStationView;
import nursestation.userinterface.PatientDisplay;

/**
 * Main starting point to start up the nurse station
 * 
 * @author Chris Bentivenga
 */
public class NurseStationMain {
	
    /**
     * The view of the nurse station UI
     */
	private NurseStationView view;
	
	/**
	 * The display of the patient UI
	 */
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
	 * @param args (ignored)
	 */
	public static void main(String[] args){
        new NurseStationMain();
	}
	
} // NurseStationMain

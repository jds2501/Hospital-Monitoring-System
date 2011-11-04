/*
 * Package: nursestation.userinterface
 *
 * File: PatientDisplay.java
 *
 * Date: Nov 2, 2011
 * 
 */

package nursestation.userinterface;

import java.awt.FlowLayout;

import javax.swing.JPanel;

import alarm.AlarmStatus;


/**
 * PatientDisplay - The main JPanel that individual patient panels are placed within
 * 
 * @author Anthony Barone
 */

@SuppressWarnings("serial")
public class PatientDisplay extends JPanel {

	private NurseStationView view;

	/**
	 * Constructor
	 * 
	 * @param gui - Reference to the graphical user interface of the system
	 */
	public PatientDisplay(NurseStationView gui) {

		// Call parent constructor
		super();

		// Instantiate values / construct panel
		view = gui;
		this.setLayout(new FlowLayout());
		
	}

	/**
	 * Paint the patients which this Nurse Station is currently subscribed to
	 * @param set - a set containing images to display
	 * @param currentReconImage - a set containing the current recon image (needed for next study image in recon mode)
	 * @param mode - the current display mode of the program (int value)
	 */
	public void paintPatientPanels(/*TODO Pass in a patient map for populating data */) {
		// remove images before adding more to the same panel
		removeAll();
		add(new PatientPanel("Test Patient 1", AlarmStatus.INACTIVE.name()));
		add(new PatientPanel("Test Patient 2", AlarmStatus.ACTIVE.name()));
		add(new PatientPanel("Test Patient 3", AlarmStatus.INACTIVE.name()));
		add(new PatientPanel("Test Patient 4", AlarmStatus.INACTIVE.name()));
		add(new PatientPanel("Test Patient 5", AlarmStatus.INACTIVE.name()));
		add(new PatientPanel("Test Patient 6", AlarmStatus.ACTIVE.name()));
		add(new PatientPanel("Test Patient 7", AlarmStatus.ACTIVE.name()));
		refresh();
	}

	/**
	 * Ensure the display of the components
	 */
	public void refresh() {
		// refresh the interface
		revalidate();
		repaint();
		updateUI();
		view.validate();
		view.repaint();
	}
}
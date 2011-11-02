/*
 * Package: nursestation.userinterface
 *
 * File: PatientDisplay.java
 *
 * Date: Nov 2, 2011
 * 
 */

package nursestation.userinterface;

import java.awt.GridLayout;

import javax.swing.JPanel;

/**
 * PatientDisplay - The main JPanel that individual patient panels are placed within
 * 
 * @author Anthony Barone
 */

@SuppressWarnings("serial")
public class PatientDisplay extends JPanel {

	private GridLayout grid;
	private NurseStationView view;

	/**
	 * Constructor
	 * 
	 * @param gui - Reference to the graphical user interface of the system
	 */
	public PatientDisplay(NurseStationView gui) {

		// Call parent constructor
		super();

		// instantiate values
		view = gui;
		grid = new GridLayout(0, 1, 5, 5);
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
		add(new PatientPanel("Test Patient", "", ""));
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
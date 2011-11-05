/*
 * Package: bedsidemonitor.userinterface
 *
 * File: VitalStatDisplay.java
 *
 * Date: Nov 3, 2011
 * 
 */

package bedsidemonitor.userinterface;

import java.awt.FlowLayout;

import javax.swing.JPanel;

import alarm.AlarmStatus;


/**
 * VitalStatDisplay - The main JPanel that shows vital sign collections.
 * 
 * @author Anthony Barone
 */

@SuppressWarnings("serial")
public class VitalStatDisplay extends JPanel {

	private BedsideMonitorView view;

	/**
	 * Constructor
	 * 
	 * @param gui - Reference to the graphical user interface of the system
	 */
	public VitalStatDisplay(BedsideMonitorView gui) {

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
		add(new VitalStatRow("Vital Stat 1", AlarmStatus.INACTIVE.name()));
		add(new VitalStatRow("Vital Stat 2", AlarmStatus.ACTIVE.name()));
		add(new VitalStatRow("Vital Stat 3", AlarmStatus.INACTIVE.name()));
		add(new VitalStatRow("Vital Stat 4", AlarmStatus.INACTIVE.name()));
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
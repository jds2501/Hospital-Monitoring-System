/*
 * Package: bedsidemonitor.userinterface
 *
 * File: PatientStatsPanel.java
 *
 * Date: Nov 3, 2011
 * 
 */

package bedsidemonitor.userinterface;

import java.awt.GridLayout;

import javax.swing.JPanel;


/**
 * PatientStatsPanel - The main JPanel that shows vital sign collection, alarm status, 
 * and call button status.
 * 
 * @author Anthony Barone
 */

@SuppressWarnings("serial")
public class PatientStatsPanel extends JPanel {

	private BedsideMonitorView view;

	/**
	 * Constructor
	 * 
	 * @param gui - Reference to the graphical user interface of the system
	 */
	public PatientStatsPanel(BedsideMonitorView gui) {

		// Call parent constructor
		super();

		// Instantiate values / construct panel
		view = gui;
		this.setLayout(new GridLayout(0, 2, 5, 5));
		
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
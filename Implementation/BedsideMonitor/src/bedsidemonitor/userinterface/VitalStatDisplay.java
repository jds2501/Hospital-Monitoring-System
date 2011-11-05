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
import java.util.HashMap;
import java.util.Map;

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
	// Collection of vital sign id -> vitalstatrow
	private Map<String,JPanel> allVitalSigns;
	

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
		allVitalSigns = new HashMap<String,JPanel>();
		this.setLayout(new FlowLayout());
	}

	/**
	 * Add a vital sign row to this display
	 * @param name Vital sign name
	 */
	public void addVitalSign(String vitalName) {
		if (allVitalSigns.containsKey(vitalName)) {
			System.err.println("This vital sign already exists on the bedside monitor.");
		} else {
			VitalStatRow newVital = new VitalStatRow(vitalName, AlarmStatus.INACTIVE.name());
			allVitalSigns.put(vitalName, newVital);
			this.add(newVital);
		}
	}
	
	/**
	 * Add a vital sign row to this display
	 * @param name Vital sign name
	 */
	public void updateVitalSign(String vitalName, int vitalValue) {
		VitalStatRow tempRow = (VitalStatRow) allVitalSigns.get(vitalName);
		tempRow.updateValue(vitalValue);
	}
	
}
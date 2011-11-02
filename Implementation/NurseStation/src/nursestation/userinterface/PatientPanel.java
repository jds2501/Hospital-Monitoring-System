/*
 * Package: nursestation.userinterface
 *
 * File: PatientPanel.java
 *
 * Date: Nov 2, 2011
 * 
 */

package nursestation.userinterface;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * PatientPanel - A panel to hold a single patients' vitals and current state.
 * 
 * @author Anthony Barone
 */

@SuppressWarnings("serial")
public class PatientPanel extends JPanel {

	private String patientName;
	private String callState;
	private String alarmState;

	/**
	 * Constructor
	 * @param display - the image display
	 * @param img - the image to be displayed
	 */
	public PatientPanel(String patientName, String callState, String alarmState) {

		// Call parent constructor
		super();

		// Instantiate attributes
		this.setLayout(new GridLayout(2, 5, 5, 5));
		this.setBorder(BorderFactory.createEtchedBorder());

		this.add(new JLabel(patientName));
	}
}

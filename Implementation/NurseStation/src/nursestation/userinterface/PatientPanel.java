/*
 * Package: nursestation.userinterface
 *
 * File: PatientPanel.java
 *
 * Date: Nov 2, 2011
 * 
 */

package nursestation.userinterface;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import nursestation.enums.AlarmStatus;
import nursestation.enums.CallStatus;


/**
 * PatientPanel - A panel to hold a single patients' vitals and current state.
 * 
 * @author Anthony Barone
 */

@SuppressWarnings("serial")
public class PatientPanel extends JPanel {

	private JPanel callPanel, alarmPanel;
	private JButton acknowlCallButton, acknowlAlarmButton;
	private JLabel alarmStatus, callStatus;

	private String patientName, callState, alarmState;

	/**
	 * Constructor
	 * @param display - the image display
	 * @param img - the image to be displayed
	 */
	public PatientPanel(String patientName, String callState, String alarmState) {
		
		// Call parent constructor
		super();

		// Instantiate values / construct panel
		this.setLayout(new GridLayout(1, 3, 5, 5));
		this.setBorder(BorderFactory.createEtchedBorder());
		
		this.add(new JLabel("Patient Name: " + patientName));
		
		callPanel = new JPanel(new FlowLayout());
		callPanel.add(new JLabel("Call state: "));
		callStatus = new JLabel(callState);
		callPanel.add(callStatus);
		
		acknowlCallButton = new JButton("Respond");
		if (callState.equals(CallStatus.ACTIVE.name())) {
			acknowlCallButton.setVisible(true);
		} else {
			acknowlCallButton.setVisible(false);
		}
		acknowlCallButton.addActionListener(new AcknowlCallListener());
		callPanel.add(acknowlCallButton);
		
		alarmPanel = new JPanel(new FlowLayout());
		alarmPanel.add(new JLabel("Alarm state: "));
		alarmStatus = new JLabel(alarmState);
		alarmPanel.add(alarmStatus);
		
		acknowlAlarmButton = new JButton("Respond");
		if (alarmState.equals(AlarmStatus.ACTIVE.name())) {
			acknowlAlarmButton.setVisible(true);
		} else {
			acknowlAlarmButton.setVisible(false);
		}
		acknowlAlarmButton.addActionListener(new AcknowlAlarmListener());
		alarmPanel.add(acknowlAlarmButton);

		
		this.add(callPanel);
		this.add(alarmPanel);
	}

	/**
	 * Inner class for listener on Acknowledge Alarm button
	 */
	private class AcknowlCallListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			callStatus.setText(CallStatus.ACKNOWLEDGED.name());
			acknowlCallButton.setVisible(false);
			//TODO
		}
	}

	/**
	 * Inner class for listener on Acknowledge Alarm button
	 */
	private class AcknowlAlarmListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			alarmStatus.setText(AlarmStatus.ACKNOWLEDGED.name());
			acknowlAlarmButton.setVisible(false);
			//TODO
		}
	}
}

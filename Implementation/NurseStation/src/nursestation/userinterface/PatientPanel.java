/*
 * Package: nursestation.userinterface
 *
 * File: PatientPanel.java
 *
 * Date: Nov 2, 2011
 * 
 */

package nursestation.userinterface;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import alarm.AlarmStatus;


/**
 * PatientPanel - A panel to hold a single patients' vitals and current state.
 * 
 * @author Anthony Barone
 */

@SuppressWarnings("serial")
public class PatientPanel extends JPanel {

	private JPanel alarmPanel, alarmButtonPanel;
	private JButton acknowlAlarmButton;
	private JLabel alarmStatus;

	/**
	 * Constructor
	 * @param display - the image display
	 * @param img - the image to be displayed
	 */
	public PatientPanel(String patientName, String alarmState) {
		
		// Call parent constructor
		super();

		// Instantiate values / construct panel
		this.setLayout(new GridLayout(1, 3, 5, 5));
		this.setBorder(BorderFactory.createEtchedBorder());
		
		this.add(new JLabel("Patient Name: " + patientName));
		
		alarmPanel = new JPanel();
		alarmPanel.setLayout(new BoxLayout(alarmPanel, BoxLayout.LINE_AXIS));
		alarmPanel.add(new JLabel("Alarm state: "));
		alarmStatus = new JLabel(alarmState);
		alarmPanel.add(alarmStatus);
		
		alarmButtonPanel = new JPanel();
		alarmButtonPanel.setLayout(new BoxLayout(alarmButtonPanel, BoxLayout.LINE_AXIS));
		acknowlAlarmButton = new JButton("Respond");
		if (alarmState.equals(AlarmStatus.ACTIVE.name())) {
			acknowlAlarmButton.setEnabled(true);
			alarmStatus.setFont(new Font("Tahoma", Font.BOLD, 14));
			alarmStatus.setForeground(Color.RED);
		} else {
			acknowlAlarmButton.setEnabled(false);
			alarmStatus.setFont(new Font("Tahoma", Font.PLAIN, 14));
			alarmStatus.setForeground(Color.GRAY);
		}
		acknowlAlarmButton.addActionListener(new AcknowlAlarmListener());
		alarmButtonPanel.add(acknowlAlarmButton);
		
		this.add(alarmPanel);
		this.add(alarmButtonPanel);
		
		this.setSize(this.getPreferredSize().width, 40);
	}

	/**
	 * Called by external sources to update this patient and show alarm has been triggered
	 */
	public void triggerAlarm() {
		alarmStatus.setText(AlarmStatus.ACTIVE.name());
		alarmStatus.setFont(new Font("Tahoma", Font.BOLD, 14));
		alarmStatus.setForeground(Color.RED);
		acknowlAlarmButton.setEnabled(true);
	}

	/**
	 * Inner class for listener on Acknowledge Alarm button
	 */
	private class AcknowlAlarmListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			alarmStatus.setText(AlarmStatus.ACKNOWLEDGED.name());
			alarmStatus.setFont(new Font("Tahoma", Font.BOLD, 14));
			alarmStatus.setForeground(Color.BLUE);
			acknowlAlarmButton.setEnabled(false);
			//TODO logging, updating external sources, etc
		}
	}
}

/*
 * Package: nursestation.userinterface
 *
 * File: PatientPanel.java
 *
 * Date: Nov 2, 2011
 * 
 */

package nursestation.userinterface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
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

	private JPanel alarmPanel, alarmButtonPanel, alarmPanelOuter;
	private JButton acknowlAlarmButton;
	private JLabel alarmStatusLabel, alarmStatus;

	/**
	 * Constructor
	 * @param display - the image display
	 * @param img - the image to be displayed
	 */
	public PatientPanel(String patientName, String alarmState) {
		
		// Call parent constructor
		super();

		// Instantiate values / construct panel
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createEtchedBorder());
		
		alarmPanelOuter = new JPanel(new BorderLayout());
		alarmPanel = new JPanel();
		alarmPanel.setLayout(new GridLayout(2, 1));
		
		JPanel p1 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JPanel p2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		
		
		
		alarmStatusLabel = new JLabel("Alarm:  ");
		alarmStatusLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
		p1.add(alarmStatusLabel);
		alarmPanel.add(p1);
		
		alarmStatus = new JLabel(alarmState);
		alarmStatus.setAlignmentX(Component.RIGHT_ALIGNMENT);
		p2.add(alarmStatus);
		alarmPanel.add(p2);
		
		alarmPanelOuter.add(alarmPanel, BorderLayout.LINE_END);
		
		alarmButtonPanel = new JPanel();
		alarmButtonPanel.setLayout(new BorderLayout());
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
		alarmButtonPanel.add(acknowlAlarmButton, BorderLayout.EAST);

		this.add(new JLabel("Patient Name:  " + patientName), BorderLayout.WEST);
		this.add(alarmPanelOuter, BorderLayout.CENTER);
		this.add(alarmButtonPanel, BorderLayout.EAST);
		
		this.setPreferredSize(new Dimension(600, 50));
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

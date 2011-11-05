/*
 * Package: nursestation.userinterface
 *
 * File: VitalStatRow.java
 *
 * Date: Nov 2, 2011
 * 
 */

package bedsidemonitor.userinterface;

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
import javax.swing.UIManager;

import alarm.AlarmStatus;


/**
 * VitalStatRow - A panel to hold a single vital stat of a patient and it's alarm state/off button
 * 
 * @author Anthony Barone
 */

@SuppressWarnings("serial")
public class VitalStatRow extends JPanel {

	private JPanel alarmPanel, alarmButtonPanel, alarmPanelOuter;
	private JButton acknowlAlarmButton;
	private JLabel alarmStatusLabel, alarmStatus, vitalStatNameLabel, vitalStatName;

	/**
	 * Constructor
	 */
	public VitalStatRow(String name, String alarmState) {
		
		// Call parent constructor
		super();

		// Instantiate values / construct panel
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createEtchedBorder());
		
		alarmPanelOuter = new JPanel(new BorderLayout());
		alarmPanel = new JPanel();
		alarmPanel.setLayout(new GridLayout(2, 1));

		JPanel p1 = new JPanel();
		p1.setLayout(new BoxLayout(p1, BoxLayout.LINE_AXIS));
		JPanel p2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JPanel p3 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		
		vitalStatNameLabel = new JLabel("  Patient Name:  ");
		
		vitalStatName = new JLabel(name);
		vitalStatName.setFont(new Font(UIManager.getDefaults().getFont("Label.font").getFontName(), 
				Font.BOLD, UIManager.getDefaults().getFont("Label.font").getSize()));
		p1.add(vitalStatNameLabel);
		p1.add(vitalStatName);
		
		alarmStatusLabel = new JLabel("Alarm:  ");
		alarmStatusLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
		p2.add(alarmStatusLabel);
		alarmPanel.add(p2);
		
		alarmStatus = new JLabel(alarmState);
		alarmStatus.setAlignmentX(Component.RIGHT_ALIGNMENT);
		p3.add(alarmStatus);
		alarmPanel.add(p3);
		
		alarmPanelOuter.add(alarmPanel, BorderLayout.LINE_END);
		
		alarmButtonPanel = new JPanel();
		alarmButtonPanel.setLayout(new BorderLayout());
		acknowlAlarmButton = new JButton("Turn Off");
		if (alarmState.equals(AlarmStatus.ACTIVE.name())) {
			acknowlAlarmButton.setEnabled(true);
			alarmStatus.setFont(new Font("Tahoma", Font.BOLD, 14));
			alarmStatus.setForeground(Color.RED);
		} else {
			acknowlAlarmButton.setEnabled(false);
			alarmStatus.setFont(new Font("Tahoma", Font.PLAIN, 14));
			alarmStatus.setForeground(Color.GRAY);
		}
		acknowlAlarmButton.addActionListener(new TurnOffAlarmListener());
		alarmButtonPanel.add(acknowlAlarmButton, BorderLayout.EAST);


		
		this.add(p1, BorderLayout.WEST);
		this.add(alarmPanelOuter, BorderLayout.CENTER);
		this.add(alarmButtonPanel, BorderLayout.EAST);
		
		this.setPreferredSize(new Dimension(700, 50));
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
	 * Inner class for listener on Turn Off Alarm button
	 */
	private class TurnOffAlarmListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			alarmStatus.setText(AlarmStatus.INACTIVE.name());
			alarmStatus.setFont(new Font("Tahoma", Font.PLAIN, 14));
			alarmStatus.setForeground(Color.GRAY);
			acknowlAlarmButton.setEnabled(false);
			//TODO logging, updating external sources, etc
		}
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

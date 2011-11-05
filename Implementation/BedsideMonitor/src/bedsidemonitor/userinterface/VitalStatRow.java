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
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;

import alarm.AlarmStatus;


/**
 * VitalStatRow - A panel to hold a single vital stat of a patient and it's alarm state/off button
 * 
 * @author Anthony Barone
 */

@SuppressWarnings("serial")
public class VitalStatRow extends JPanel {

	private JPanel infoPanel, alarmPanel, alarmPanelTop, alarmPanelBottom, alarmButtonPanel, alarmPanelOuter,
		minRangeValuePanel, maxRangeValuePanel, configureButtonPanel;
	private JButton configureButton, acknowlAlarmButton;
	private JLabel alarmStatusLabel, alarmStatus, vitalStatNameLabel, vitalStatName, 
		vitalValueLabel, vitalStatValue, minRangeLabel, maxRangeLabel;
	private JCheckBox enableBox;
	private JSpinner minRangeValue, maxRangeValue;
	private SpinnerNumberModel spinnerConfigMin, spinnerConfigMax;

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

		infoPanel = new JPanel();
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.LINE_AXIS));

		//TODO checkbox id should be tied to vital stat id
		enableBox = new JCheckBox();
		enableBox.setEnabled(true);
		enableBox.setSelected(true);
		enableBox.addActionListener(new EnableDisableVitalListener());

		//TODO configure button should be tied to vital stat id
		configureButtonPanel = new JPanel();
		configureButtonPanel.setLayout(new BoxLayout(configureButtonPanel, BoxLayout.LINE_AXIS));
		ImageIcon gearIcon = new ImageIcon("img/gear-icon.png");
		configureButton = new JButton(gearIcon);
		configureButton.setIcon(gearIcon);
		Dimension dim = new Dimension(gearIcon.getIconWidth()*2,
				gearIcon.getIconHeight());
		configureButton.setSize(dim);
		configureButton.setPreferredSize(dim);
		configureButtonPanel.setSize(dim);
		configureButtonPanel.setPreferredSize(dim);
		configureButton.setOpaque(false);
		configureButton.addActionListener(new ConfigureVitalListener());
		configureButtonPanel.add(configureButton);

		vitalStatNameLabel = new JLabel("  Vital Stat:  ");
		vitalStatName = new JLabel(name);
		vitalStatName.setFont(new Font(UIManager.getDefaults().getFont("Label.font").getFontName(), 
				Font.BOLD, UIManager.getDefaults().getFont("Label.font").getSize()));
		
		vitalValueLabel = new JLabel(" = ");
		vitalStatValue = new JLabel("");
		vitalStatValue.setFont(new Font(UIManager.getDefaults().getFont("Label.font").getFontName(), 
				Font.BOLD, 20));
		infoPanel.add(enableBox);
		infoPanel.add(configureButtonPanel);

		// JSpinner config model used for min and max
		spinnerConfigMin = new SpinnerNumberModel(0, 0, 100, 1);
		spinnerConfigMax = new SpinnerNumberModel(100, 0, 100, 1);
		
		minRangeValuePanel = new JPanel();
		minRangeValuePanel.setLayout(new BoxLayout(minRangeValuePanel, BoxLayout.LINE_AXIS));
		minRangeValue = new JSpinner(spinnerConfigMin);
		((DefaultEditor) minRangeValue.getEditor()).getTextField().setEditable(false);
		minRangeValue.setPreferredSize(new Dimension(60,minRangeValue.getPreferredSize().height));
		
		maxRangeValuePanel = new JPanel();
		maxRangeValuePanel.setLayout(new BoxLayout(maxRangeValuePanel, BoxLayout.LINE_AXIS));
		maxRangeValue = new JSpinner(spinnerConfigMax);
		((DefaultEditor) maxRangeValue.getEditor()).getTextField().setEditable(false);
		maxRangeValue.setPreferredSize(new Dimension(60,maxRangeValue.getPreferredSize().height));

		minRangeLabel = new JLabel(" Min: ");
		minRangeValuePanel.add(minRangeLabel);
		minRangeValuePanel.setVisible(false);
		minRangeValuePanel.add(minRangeValue);
		infoPanel.add(minRangeValuePanel);

		maxRangeLabel = new JLabel(" Max: ");
		maxRangeValuePanel.add(maxRangeLabel);
		maxRangeValuePanel.setVisible(false);
		maxRangeValuePanel.add(maxRangeValue);
		infoPanel.add(maxRangeValuePanel);
		
		infoPanel.add(vitalStatNameLabel);
		infoPanel.add(vitalStatName);
		infoPanel.add(vitalValueLabel);
		infoPanel.add(vitalStatValue);

		alarmPanelTop = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		alarmStatusLabel = new JLabel("Alarm:  ");
		alarmStatusLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
		alarmPanelTop.add(alarmStatusLabel);
		alarmPanel.add(alarmPanelTop);

		alarmPanelBottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		alarmStatus = new JLabel(alarmState);
		alarmStatus.setAlignmentX(Component.RIGHT_ALIGNMENT);
		alarmPanelBottom.add(alarmStatus);
		alarmPanel.add(alarmPanelBottom);

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

		this.add(infoPanel, BorderLayout.WEST);
		this.add(alarmPanelOuter, BorderLayout.CENTER);
		this.add(alarmButtonPanel, BorderLayout.EAST);

		this.setPreferredSize(new Dimension(700, 50));
	}

	/**
	 * Update display to show new sensor data for this stat
	 */
	public void updateValue(int value) {
		vitalStatValue.setText(value+"");
	}

	/**
	 * Update display to represent alarm being triggered for this stat
	 */
	public void triggerAlarm() {
		alarmStatus.setText(AlarmStatus.ACTIVE.name());
		alarmStatus.setFont(new Font("Tahoma", Font.BOLD, 14));
		alarmStatus.setForeground(Color.RED);
		acknowlAlarmButton.setEnabled(true);
	}

	/**
	 * Inner class for listener on Enable/Disable vital sign checkbox
	 */
	private class EnableDisableVitalListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JCheckBox box = (JCheckBox)e.getSource();
			if (box.isSelected()) {
				//TODO vital sign enabled
			} else {
				//TODO vital sign disabled
			}
		}
	}

	/**
	 * Inner class for listener on Configure Vital button
	 */
	private class ConfigureVitalListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (minRangeValuePanel.isVisible()) {
				//TODO save min/max values
				minRangeValuePanel.setVisible(false);
				maxRangeValuePanel.setVisible(false);
				refresh();
			} else {
				//TODO save min/max values
				minRangeValuePanel.setVisible(true);
				maxRangeValuePanel.setVisible(true);
				refresh();
			}
		}
	}

	/**
	 * Refresh the interface to ensure correct display
	 */
	public void refresh() {
		repaint();
		validate();
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
//	private class AcknowlAlarmListener implements ActionListener {
//		public void actionPerformed(ActionEvent e) {
//			alarmStatus.setText(AlarmStatus.ACKNOWLEDGED.name());
//			alarmStatus.setFont(new Font("Tahoma", Font.BOLD, 14));
//			alarmStatus.setForeground(Color.BLUE);
//			acknowlAlarmButton.setEnabled(false);
//			//TODO logging, updating external sources, etc
//		}
//	}
}

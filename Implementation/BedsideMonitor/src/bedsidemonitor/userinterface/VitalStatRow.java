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
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;

import alarm.AlarmStatus;
import bedsidemonitor.vitalsigncollection.VitalSignConfiguration;
import bedsidemonitor.vitalsigncollection.VitalSignController;
import bedsidemonitor.vitalsigncollection.VitalSignProcessing;


/**
 * VitalStatRow - A panel to hold a single vital stat of a patient and it's alarm state/off button
 * 
 * @author Anthony Barone
 */

@SuppressWarnings("serial")
public class VitalStatRow extends JPanel implements Observer {

    private VitalSignController vitalSign;
	private JPanel infoPanel, alarmPanel, alarmPanelTop, alarmPanelBottom, alarmButtonPanel, alarmPanelOuter,
		minRangeValuePanel, maxRangeValuePanel, minAndMaxComboPanel, convCollComboPanel, 
		configureButtonPanel, convFactorPanel, collRatePanel, configurationPanel, enableBoxPanel;
	private JButton configureButton, acknowlAlarmButton;
	private JLabel alarmStatusLabel, alarmStatus, vitalStatNameLabel, vitalStatName, 
		vitalValueLabel, vitalStatValue, minRangeLabel, maxRangeLabel, convFactorLabel, collRateLabel;
	private JCheckBox enableBox;
	private JSpinner minRangeValue, maxRangeValue, conversionFactor, collectionRate;
	private SpinnerNumberModel spinnerConfigMin, spinnerConfigMax, spinnerConfigConvFactor, spinnerConfigCollRate;

	/**
	 * Constructor
	 */
	public VitalStatRow(VitalSignController vitalSign) {

		// Call parent constructor
		super();

		this.vitalSign = vitalSign;
		this.vitalSign.addObserver(this);
		VitalSignConfiguration configuration = vitalSign.getConfiguration();
		
		// Instantiate values / construct panel
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createEtchedBorder());

		alarmPanelOuter = new JPanel(new BorderLayout());
		alarmPanel = new JPanel();
		alarmPanel.setLayout(new GridLayout(2, 1));

		infoPanel = new JPanel();
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.LINE_AXIS));

		//TODO checkbox id should be tied to vital stat id
		enableBoxPanel = new JPanel();
		enableBoxPanel.setLayout(new BoxLayout(enableBoxPanel, BoxLayout.LINE_AXIS));
		enableBox = new JCheckBox();
		enableBox.setEnabled(false);
		enableBox.setSelected(false);
		enableBox.addActionListener(new EnableDisableVitalListener());
		enableBoxPanel.add(enableBox);

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
		vitalStatName = new JLabel(configuration.getName());
		vitalStatName.setFont(new Font(UIManager.getDefaults().getFont("Label.font").getFontName(), 
				Font.BOLD, 16));
		
		vitalValueLabel = new JLabel();
		vitalStatValue = new JLabel("");
		vitalStatValue.setFont(new Font(UIManager.getDefaults().getFont("Label.font").getFontName(), 
				Font.BOLD, 20));
		infoPanel.add(enableBoxPanel);
		infoPanel.add(configureButtonPanel);

		// JSpinner config models
		spinnerConfigMin = new SpinnerNumberModel(configuration.getMinAllowedReading(), 0.0, 100.0, .1);
		spinnerConfigMax = new SpinnerNumberModel(configuration.getMaxAllowedReading(), 0.0, 100.0, .1);
		spinnerConfigConvFactor = new SpinnerNumberModel(configuration.getConversionFactor(), 0.0, 100.0, .1);
		spinnerConfigCollRate = new SpinnerNumberModel(configuration.getCollectionRate(), 0.0, 9999.0, 1.0);
		
		minRangeValuePanel = new JPanel();
		minRangeValuePanel.setLayout(new BoxLayout(minRangeValuePanel, BoxLayout.LINE_AXIS));
		minRangeValue = new JSpinner(spinnerConfigMin);
		
		maxRangeValuePanel = new JPanel();
		maxRangeValuePanel.setLayout(new BoxLayout(maxRangeValuePanel, BoxLayout.LINE_AXIS));
		maxRangeValue = new JSpinner(spinnerConfigMax);
		
		convFactorPanel = new JPanel(new GridLayout(0, 1));
		convFactorPanel.setLayout(new BoxLayout(convFactorPanel, BoxLayout.LINE_AXIS));
		conversionFactor = new JSpinner(spinnerConfigConvFactor);
		
		collRatePanel = new JPanel(new GridLayout(0, 1));
		collRatePanel.setLayout(new BoxLayout(collRatePanel, BoxLayout.LINE_AXIS));
		collectionRate = new JSpinner(spinnerConfigCollRate);
		// Remove commas
		JSpinner.NumberEditor editor = new JSpinner.NumberEditor(collectionRate, "#.#");
		collectionRate.setEditor(editor);

		minRangeLabel = new JLabel("   Min:  ");
		minRangeValuePanel.add(minRangeLabel);
		minRangeValuePanel.add(minRangeValue);

		maxRangeLabel = new JLabel("   Max: ");
		maxRangeValuePanel.add(maxRangeLabel);
		maxRangeValuePanel.add(maxRangeValue);

		minAndMaxComboPanel = new JPanel(new BorderLayout());
		minAndMaxComboPanel.add(minRangeValuePanel, BorderLayout.NORTH);
		minAndMaxComboPanel.add(maxRangeValuePanel, BorderLayout.SOUTH);

		convFactorLabel = new JLabel(" Factor: ");
		convFactorPanel.add(convFactorLabel);
		convFactorPanel.add(conversionFactor);
		
		collRateLabel = new JLabel(" Collect: ");
		collRatePanel.add(collRateLabel);
		collRatePanel.add(collectionRate);
		
		convCollComboPanel = new JPanel(new BorderLayout());
		convCollComboPanel.add(convFactorPanel, BorderLayout.NORTH);
		convCollComboPanel.add(collRatePanel, BorderLayout.SOUTH);
		
		configurationPanel = new JPanel(new FlowLayout());
		configurationPanel.add(minAndMaxComboPanel);
		configurationPanel.add(convCollComboPanel);
		configurationPanel.setVisible(true);
		configurationPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		infoPanel.add(configurationPanel);
		
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
		// TODO: Change this to reference the vital sign controller
		alarmStatus = new JLabel(AlarmStatus.INACTIVE.name());
		alarmStatus.setAlignmentX(Component.RIGHT_ALIGNMENT);
		alarmPanelBottom.add(alarmStatus);
		alarmPanel.add(alarmPanelBottom);

		alarmPanelOuter.add(alarmPanel, BorderLayout.LINE_END);

		alarmButtonPanel = new JPanel();
		alarmButtonPanel.setLayout(new BorderLayout());
		acknowlAlarmButton = new JButton("Turn Off");
		
		acknowlAlarmButton.setEnabled(false);
		alarmStatus.setFont(new Font("Tahoma", Font.PLAIN, 14));
		alarmStatus.setForeground(Color.GRAY);
		
		acknowlAlarmButton.addActionListener(new TurnOffAlarmListener());
		alarmButtonPanel.add(acknowlAlarmButton, BorderLayout.EAST);

		this.add(infoPanel, BorderLayout.WEST);
		this.add(alarmPanelOuter, BorderLayout.CENTER);
		this.add(alarmButtonPanel, BorderLayout.EAST);

		this.setPreferredSize(new Dimension(700, this.getPreferredSize().height));
	}

	/**
	 * Update display to show new sensor data for this stat
	 */
	public void updateVital(Double value) {
		vitalStatValue.setText(""+value);
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
				vitalSign.enableMeasurement();
				vitalStatName.setText(vitalStatName.getText() + " = ");
			} else {
				vitalSign.disableMeasurement();
				vitalStatValue.setText("");
				vitalStatName.setText(vitalSign.getConfiguration().getName());
			}
		}
	}

	/**
	 * Inner class for listener on Configure Vital button
	 */
	private class ConfigureVitalListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (!enableBox.isEnabled()) {
				// Initial configuration complete, allow enabling of vital stat collection
				enableBox.setEnabled(true);
			}
			VitalSignConfiguration tempConfig = vitalSign.getConfiguration();
			if (configurationPanel.isVisible()) {
				tempConfig = vitalSign.getConfiguration();
				tempConfig.setMinAllowedReading((Double)minRangeValue.getValue());
				tempConfig.setMaxAllowedReading((Double)maxRangeValue.getValue());
				tempConfig.setConversionFactor((Double)conversionFactor.getValue());
				
				long oldCollectionRate = tempConfig.getCollectionRate();
				long newCollectionRate = Math.round((Double)collectionRate.getValue());
				tempConfig.setCollectionRate(newCollectionRate);
				
				if(oldCollectionRate != newCollectionRate) {
				    vitalSign.restartCollectionTask();
				}
				
				configurationPanel.setVisible(false);
				refresh();
			} else {
				configurationPanel.setVisible(true);
				refresh();
			}
		}
	}

	/**
	 * Refresh the interface to ensure correct display
	 */
	public void refresh() {
		revalidate();
		repaint();
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
			vitalSign.resetAlarm();
			//TODO logging, updating external sources, etc
		}
	}


    public void update(Observable observable, Object pushedObject) {
        if(pushedObject instanceof VitalSignProcessing) {
            VitalSignProcessing processor = (VitalSignProcessing) pushedObject;
            updateVital(processor.getVitalSignValue());
            
            if(processor.getAlarmStatus() == AlarmStatus.ACTIVE) {
                triggerAlarm();
            }
        }
    }
}

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
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;

import nursestation.notificationservice.NotificationServiceTask;
import nursestation.notificationservice.VitalSignMessage;

import alarm.AlarmStatus;


/**
 * PatientPanel - A panel to hold a single patients' vitals and current state.
 * 
 * @author Anthony Barone
 */

@SuppressWarnings("serial")
public class PatientPanel extends JPanel implements Observer {

    private NotificationServiceTask notificationTask;
	private JPanel infoPanel, subInfoPatientPanel, subInfoAlarmPanel, alarmPanel, alarmButtonPanel, alarmPanelOuter;
	private JButton acknowlAlarmButton, acknowlAllAlarmButton;
	private JLabel alarmStatusLabel, alarmStatus, patientNameLabel, patientName;
	private DefaultListModel activeAlarmsModel, acknowlAlarmsModel;
	private JList activeAlarms, acknowlAlarms;
	private JScrollPane activeAlarmScroll, acknowlAlarmScroll;

	/**
	 * Constructor
	 */
	public PatientPanel(String name, NotificationServiceTask notificationTask) {

		// Call parent constructor
		super();

		this.notificationTask = notificationTask;
		this.notificationTask.addObserver(this);
		
		// Instantiate values / construct panel
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createEtchedBorder());

		alarmPanelOuter = new JPanel(new BorderLayout());
		alarmPanel = new JPanel();

		infoPanel = new JPanel(new GridLayout(2, 1));
		subInfoPatientPanel = new JPanel();
		subInfoPatientPanel.setLayout(new BoxLayout(subInfoPatientPanel, BoxLayout.LINE_AXIS));
		subInfoAlarmPanel = new JPanel();
		subInfoAlarmPanel.setLayout(new BoxLayout(subInfoAlarmPanel, BoxLayout.LINE_AXIS));

		patientNameLabel = new JLabel("  Patient:  ");
		patientName = new JLabel(name);
		patientName.setFont(new Font(UIManager.getDefaults().getFont("Label.font").getFontName(), 
				Font.BOLD, UIManager.getDefaults().getFont("Label.font").getSize()));
		subInfoPatientPanel.add(patientNameLabel);
		subInfoPatientPanel.add(patientName);
		infoPanel.add(subInfoPatientPanel);

		alarmStatusLabel = new JLabel("  Alarm State:  ");
		alarmStatus = new JLabel(AlarmStatus.INACTIVE.name());
		subInfoAlarmPanel.add(alarmStatusLabel);
		subInfoAlarmPanel.add(alarmStatus);
		infoPanel.add(subInfoAlarmPanel);

		activeAlarmsModel = new DefaultListModel();

		activeAlarms = new JList(activeAlarmsModel);
		activeAlarms.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		activeAlarms.setLayoutOrientation(JList.VERTICAL);
		activeAlarms.setVisibleRowCount(-1);
		activeAlarms.setFixedCellHeight(20);
		activeAlarmScroll = new JScrollPane(activeAlarms);
		activeAlarmScroll.setPreferredSize(new Dimension(200, 65));

		acknowlAlarmsModel = new DefaultListModel();

		acknowlAlarms = new JList(acknowlAlarmsModel);
		acknowlAlarms.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		acknowlAlarms.setLayoutOrientation(JList.VERTICAL);
		acknowlAlarms.setVisibleRowCount(-1);
		acknowlAlarms.setFixedCellHeight(20);
		acknowlAlarmScroll = new JScrollPane(acknowlAlarms);
		acknowlAlarmScroll.setPreferredSize(new Dimension(200, 65));

		alarmPanelOuter.add(alarmPanel, BorderLayout.LINE_END);

		alarmButtonPanel = new JPanel(new BorderLayout());
		alarmButtonPanel.setLayout(new BorderLayout());
		acknowlAlarmButton = new JButton("Respond");
		acknowlAllAlarmButton = new JButton("Respond All");
		acknowlAlarmButton.addActionListener(new AcknowlAlarmListener());
		acknowlAllAlarmButton.addActionListener(new AcknowlAllAlarmListener());

		alarmStatus.setFont(new Font("Tahoma", Font.PLAIN, 14));
		alarmStatus.setForeground(Color.GRAY);

		alarmButtonPanel.add(acknowlAlarmButton, BorderLayout.NORTH);
		alarmButtonPanel.add(acknowlAllAlarmButton, BorderLayout.SOUTH);

		alarmPanel.add(activeAlarmScroll);
		alarmPanel.add(alarmButtonPanel);
		alarmPanel.add(acknowlAlarmScroll);

		this.add(infoPanel, BorderLayout.WEST);
		this.add(alarmPanel, BorderLayout.EAST);

		this.setPreferredSize(new Dimension(800, 80));
	}

	/**
	 * Called by external sources to update this patient and show alarm has been triggered
	 */
	public void triggerAlarm() {
		alarmStatus.setText(AlarmStatus.ACTIVE.name());
		alarmStatus.setFont(new Font("Tahoma", Font.BOLD, 14));
		alarmStatus.setForeground(Color.RED);
		acknowlAlarmButton.setEnabled(true);
		acknowlAllAlarmButton.setEnabled(true);
	}

	/**
	 * Inner class for listener on Acknowledge All Alarms button
	 */
	private class AcknowlAllAlarmListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// Obtain all vital sign objects in active alarm box
			int numActiveAlarms = activeAlarmsModel.getSize();
			String[] tempStorage = new String[numActiveAlarms];
			activeAlarmsModel.copyInto(tempStorage);

			// Transfer all vital sign objects
			for (int i = 0; i < numActiveAlarms; i++) {
				String selectString = (String)tempStorage[i];
				acknowlAlarmsModel.addElement(selectString);
			}

			// Clear out active alarm list
			activeAlarmsModel.removeAllElements();

			// Update the alarm status to "Acknowledged" since all active alarms 
			// have been acknowledged.
			alarmStatus.setText(AlarmStatus.ACKNOWLEDGED.name());
			alarmStatus.setFont(new Font("Tahoma", Font.BOLD, 14));
			alarmStatus.setForeground(Color.BLUE);
			acknowlAlarmButton.setEnabled(false);
			acknowlAllAlarmButton.setEnabled(false);

			//TODO backend logic
		}
	}

	/**
	 * Inner class for listener on Acknowledge Alarm button
	 */
	private class AcknowlAlarmListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// Check for selection
			Object[] selections = activeAlarms.getSelectedValues();
			if (selections.length > 0) {
			    Collection<String> vitals = new ArrayList<String>();
			    
				for (Object selection : selections) {
				    
					if (selection instanceof String) {
						// Make the transfer
						String selectString = (String)selection;
						activeAlarmsModel.removeElement(selectString);
						acknowlAlarmsModel.addElement(selectString);
						vitals.add(selectString);
					}
				}
				
				if(vitals.size() > 0) {
				    notificationTask.acknowledgeAlarms(
				            patientName.getText(), vitals);
				}
			}

			// Determine if we need to change the alarm status
			// (All active alarms being acknowledged translates to a label change)
			if (activeAlarmsModel.isEmpty()) {
				alarmStatus.setText(AlarmStatus.ACKNOWLEDGED.name());
				alarmStatus.setFont(new Font("Tahoma", Font.BOLD, 14));
				alarmStatus.setForeground(Color.BLUE);
				acknowlAlarmButton.setEnabled(false);
				acknowlAllAlarmButton.setEnabled(false);
			}
			
			//TODO backend logic
		}
	}

    public void update(Observable observable, Object pushedObject) {
        if(pushedObject instanceof VitalSignMessage) {
            VitalSignMessage msg = (VitalSignMessage) pushedObject;
            
            if(msg.getPatientName().equals(patientName.getText())) {
                String vitalSignName = msg.getVitalSignName();
                
                switch(msg.getAlarmStatus()) {
                    case ACTIVE:
                        if(!activeAlarmsModel.contains(vitalSignName) &&
                           !acknowlAlarmsModel.contains(vitalSignName)){
                            activeAlarmsModel.addElement(vitalSignName);
                            triggerAlarm();
                        }
                        break;
                    case ACKNOWLEDGED:
                        if(activeAlarmsModel.contains(vitalSignName)){
                            activeAlarmsModel.removeElement(vitalSignName);
                        }
                        
                        if(!acknowlAlarmsModel.contains(vitalSignName)) {
                            acknowlAlarmsModel.addElement(vitalSignName);
                        }
                        break;
                    case INACTIVE:
                        if(activeAlarmsModel.contains(vitalSignName)){
                            activeAlarmsModel.removeElement(vitalSignName);
                        }
                        
                        if(acknowlAlarmsModel.contains(vitalSignName)) {
                            acknowlAlarmsModel.removeElement(vitalSignName);
                        }
                        break;
                }
            }
        }
    }
}

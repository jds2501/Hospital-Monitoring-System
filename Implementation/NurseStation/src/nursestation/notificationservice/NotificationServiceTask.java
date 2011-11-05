/*
 * Package: nursestation.notificationservice
 *
 * File: NotificationServiceTask.java
 *
 * Date: Nov 4, 2011
 * 
 */
package nursestation.notificationservice;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import alarm.AlarmStatus;


/**
 * Notification service task for collecting incoming patient data and
 * sending it to observers when found.
 * 
 * @author Jason Smith
 */
public class NotificationServiceTask extends Observable implements Runnable {

    /**
     * Notification service to pull incoming patient data from
     */
    private NotificationServiceImpl notificationService;
    
    /**
     * 3-D map containing patient ID --> Vital Sign ID --> Alarm Status
     */
    private Map<Long, Map<Long, AlarmStatus>> alarmStatuses;
    
    /**
     * Specifies whether this task is still alive or not
     */
    private boolean isAlive;
    
    /**
     * Constructs a NotificationServiceTask object with a notification service.
     * 
     * @param service the service to pull patient updates from
     */
    public NotificationServiceTask(NotificationServiceImpl service){
        this.notificationService = service;
        this.isAlive = true;
        this.alarmStatuses = new HashMap<Long, Map<Long, AlarmStatus>>();
    }
    
    /**
     * Adds a patient to the notification service.
     * 
     * @param patientID the patient ID to add
     */
    public void addPatient(Long patientID) {
        synchronized(alarmStatuses) {
            alarmStatuses.put(patientID, new HashMap<Long, AlarmStatus>());
        }
    }
    
    /**
     * Sets whether this notifcation task is active or not.
     * 
     * @param isAlive true keeps the thread alive, false turns it off
     */
    public void setAlive(boolean isAlive){
        this.isAlive = isAlive;
    }
    
    /**
     * Continuously pulls messages off the notification service and
     * updates the alarms respectively.
     */
    public void run() {
        while(isAlive){
            VitalSignMessage msg = notificationService.pullVitalSign();
            
            if(msg != null) {
                updateAlarm(msg.getPatientID(), msg.getVitalSignID(), 
                            msg.getAlarmStatus());
                notifyObservers(msg);
            }
        }
    }
    
    /**
     * Acknowledges the alarm for a specific patient and vital sign.
     * 
     * @param patientID the patient to acknowledge an alarm for
     * @param vitalSignID the vital sign to acknowledge an alarm for
     */
    public void acknowledgeAlarm(Long patientID, Long vitalSignID){
        updateAlarm(patientID, vitalSignID, AlarmStatus.ACKNOWLEDGED);
    }
    
    /**
     * Updates the alarm for the specific patient and vital sign to the
     * specified status.
     * 
     * @param patientID the ID of the patient
     * @param vitalSignID the ID of the vital sign
     * @param status the status of the alarm
     */
    private void updateAlarm(
            Long patientID, Long vitalSignID, AlarmStatus status) {
        boolean illegalArgs = true;
        
        synchronized(alarmStatuses) {            
            if(alarmStatuses.containsKey(patientID)) {
                Map<Long, AlarmStatus> patientAlarms = 
                        alarmStatuses.get(patientID);
                
                if(patientAlarms.containsKey(vitalSignID)) {
                    patientAlarms.put(vitalSignID, status);
                    illegalArgs = false;
                }
            }
        }
        
        if(illegalArgs) {
            throw new IllegalArgumentException("Patient ID " + patientID + 
                    " and vital sign ID " + vitalSignID + " does not exist");
        }
        
        setChanged();
        notifyObservers(alarmStatuses);
    }
    
} // NotificationServiceTask

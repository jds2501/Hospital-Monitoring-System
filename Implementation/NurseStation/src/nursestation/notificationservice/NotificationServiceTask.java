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
     * 3-D map containing patient Name --> Vital Sign Name --> Alarm Status
     */
    private Map<String, Map<String, AlarmStatus>> alarmStatuses;
    
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
        this.alarmStatuses = new HashMap<String, Map<String, AlarmStatus>>();
    }
    
    /**
     * Adds a patient to the notification service.
     * 
     * @param patientName the patient name to add
     */
    public void addPatient(String patientName) {
        synchronized(alarmStatuses) {
            alarmStatuses.put(patientName, new HashMap<String, AlarmStatus>());
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
                updateAlarm(msg.getPatientName(), msg.getVitalSignName(), 
                            msg.getAlarmStatus());
                notifyObservers(msg);
            }
        }
    }
    
    /**
     * Acknowledges the alarm for a specific patient and vital sign.
     * 
     * @param patientName the patient to acknowledge an alarm for
     * @param vitalSignName the vital sign to acknowledge an alarm for
     */
    public void acknowledgeAlarm(String patientName, String vitalSignName){
        updateAlarm(patientName, vitalSignName, AlarmStatus.ACKNOWLEDGED);
    }
    
    /**
     * Updates the alarm for the specific patient and vital sign to the
     * specified status.
     * 
     * @param patientName the name of the patient
     * @param vitalSignName the name of the vital sign
     * @param status the status of the alarm
     */
    private void updateAlarm(
            String patientName, String vitalSignName, AlarmStatus status) {
        boolean illegalArgs = true;
        
        synchronized(alarmStatuses) {
            System.out.println("PATIENT NAME: " + patientName);
            System.out.println("VITAL SIGN: " + vitalSignName);
            System.out.println(alarmStatuses);
            
            if(alarmStatuses.containsKey(patientName)) {
                Map<String, AlarmStatus> patientAlarms = 
                        alarmStatuses.get(patientName);                
                patientAlarms.put(vitalSignName, status);
                illegalArgs = false;
            }
        }
        
        if(illegalArgs) {
            throw new IllegalArgumentException("Patient name " + patientName + 
                    " and vital sign name " + vitalSignName + 
                    " does not exist");
        }
        
        setChanged();
        notifyObservers(alarmStatuses);
    }
    
} // NotificationServiceTask

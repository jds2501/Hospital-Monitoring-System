/*
 * Package: nursestation.notificationservice
 *
 * File: VitalSignMessage.java
 *
 * Date: Nov 2, 2011
 * 
 */
package nursestation.notificationservice;

/**
 * A message sent to the notification service with a new vital sign and
 * an alarm status for a particular patient.
 * 
 * @author Jason Smith
 */
public class VitalSignMessage {

    /**
     * The ID of the patient
     */
    private int patientID;
    
    /**
     * The name of the vital sign
     */
    private String name;
    
    /**
     * The vital sign updated value
     */
    private double vitalSign;
    
    /**
     * The status of the alarm for this vital sign
     */
    private boolean alarmStatus;
    
    /**
     * Constructs a VitalSignMessage object with a patient ID, vital sign
     * name, vital sign value, and an alarm status.
     * 
     * @param patientID the ID of the patient
     * @param name the name of the vital sign
     * @param vitalSign the value of the vital sign
     * @param alarmStatus the status of the alarm for that vital sign
     */
    public VitalSignMessage(int patientID, String name, double vitalSign, 
                            boolean alarmStatus) {
        this.patientID = patientID;
        this.name = name;
        this.vitalSign = vitalSign;
        this.alarmStatus = alarmStatus;
    }
    
    /**
     * @return the patientID
     */
    public int getPatientID() {
    
        return patientID;
    }
    
    /**
     * @return the name
     */
    public String getName() {
    
        return name;
    }
    
    /**
     * @return the vitalSign
     */
    public double getVitalSign() {
    
        return vitalSign;
    }
    
    /**
     * @return the alarmStatus
     */
    public boolean getAlarmStatus() {
    
        return alarmStatus;
    }
    
} // VitalSignMessage

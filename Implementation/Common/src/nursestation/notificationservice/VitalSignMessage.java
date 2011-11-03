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
     * The patient's name
     */
    private String patientName;
    
    /**
     * The name of the vital sign
     */
    private String vitalSignName;
    
    /**
     * The vital sign updated value
     */
    private double vitalSignValue;
    
    /**
     * The status of the alarm for this vital sign
     */
    private boolean alarmStatus;
    
    /**
     * Constructs a VitalSignMessage object with a patient name, vital sign
     * name, vital sign value, and an alarm status.
     * 
     * @param patientName the name of the patient
     * @param vitalSignName the name of the vital sign
     * @param vitalSign the value of the vital sign
     * @param alarmStatus the status of the alarm for that vital sign
     */
    public VitalSignMessage(String patientName, String vitalSignName, 
                            double vitalSign, boolean alarmStatus) {
        this.patientName = patientName;
        this.vitalSignName = vitalSignName;
        this.vitalSignValue = vitalSign;
        this.alarmStatus = alarmStatus;
    }
    
    /**
     * @return the patient's name
     */
    public String getPatientName() {
        return patientName;
    }
    
    /**
     * @return the name of the vital sign
     */
    public String getVitalSignName() {
        return vitalSignName;
    }
    
    /**
     * @return the value of the vital sign
     */
    public double getVitalSign() {
    
        return vitalSignValue;
    }
    
    /**
     * @return the alarmStatus
     */
    public boolean getAlarmStatus() {
    
        return alarmStatus;
    }
    
} // VitalSignMessage

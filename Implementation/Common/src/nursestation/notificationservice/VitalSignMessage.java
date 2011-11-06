/*
 * Package: nursestation.notificationservice
 *
 * File: VitalSignMessage.java
 *
 * Date: Nov 2, 2011
 * 
 */
package nursestation.notificationservice;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import alarm.AlarmStatus;

/**
 * A message sent to the notification service with a new vital sign and
 * an alarm status for a particular patient.
 * 
 * @author Jason Smith
 */
public class VitalSignMessage implements Externalizable {
    
    public static final long serialVersionUID = 1L;
    
    /**
     * The patient's ID
     */
    private long patientID;
    
    /**
     * The patient's name
     */
    private String patientName;
    
    /**
     * Vital sign ID
     */
    private long vitalSignID;
    
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
    private AlarmStatus alarmStatus;
    
    /**
     * Constructs a VitalSignMessage object.
     */
    public VitalSignMessage() {
        
    }
    
    /**
     * Constructs a VitalSignMessage object with a patient name, vital sign
     * name, vital sign value, and an alarm status.
     * 
     * @param patientID the ID of the patient
     * @param patientName the name of the patient
     * @param vitalSignName the name of the vital sign
     * @param vitalSign the value of the vital sign
     * @param alarmStatus the status of the alarm for that vital sign
     */
    public VitalSignMessage(long patientID, String patientName, 
            String vitalSignName, long vitalSignID, double vitalSign,
            AlarmStatus alarmStatus) {
        this.patientID = patientID;
        this.patientName = patientName;
        this.vitalSignID = vitalSignID;
        this.vitalSignName = vitalSignName;
        this.vitalSignValue = vitalSign;
        this.alarmStatus = alarmStatus;
    }

    /**
     * @return the patient's ID
     */
    public long getPatientID() {
        return patientID;
    }
    
    /**
     * @return the vital sign ID
     */
    public long getVitalSignID() {
        return vitalSignID;
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
    public AlarmStatus getAlarmStatus() {
    
        return alarmStatus;
    }

    /**
     * Writes this object into the output stream to be sent over the
     * network.
     * 
     * @param out the network output stream to write to
     */
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeLong(patientID);
        out.writeUTF(patientName);
        out.writeLong(vitalSignID);
        out.writeUTF(vitalSignName);
        out.writeDouble(vitalSignValue);
        out.writeObject(alarmStatus);
    }

    /**
     * Reads the object received from the input stream that was sent
     * over the network.
     * 
     * @param in the network input stream to read from
     */
    public void readExternal(ObjectInput in) throws IOException,
            ClassNotFoundException {
        patientID = in.readLong();
        patientName = in.readUTF();
        vitalSignID = in.readLong();
        vitalSignName = in.readUTF();
        vitalSignValue = in.readDouble();
        alarmStatus = (AlarmStatus) in.readObject();
    }
    
} // VitalSignMessage

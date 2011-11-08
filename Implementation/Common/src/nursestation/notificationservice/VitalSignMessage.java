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
     * The patient's name
     */
    private String patientName;
    
    /**
     * The name of the vital sign
     */
    private String vitalSignName;
    
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
     * Constructs a VitalSignMessage object with a patient name, vital
     * sign name, and an alarm status.
     * 
     * @param patientName the name of the patient
     * @param vitalSignName the name of the vital sign
     * @param alarmStatus the status of the alarm for that vital sign
     */
    public VitalSignMessage(String patientName, String vitalSignName,
            AlarmStatus alarmStatus) {
        this.patientName = patientName;
        this.vitalSignName = vitalSignName;
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
        out.writeUTF(patientName);
        out.writeUTF(vitalSignName);
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
        patientName = in.readUTF();
        vitalSignName = in.readUTF();
        alarmStatus = (AlarmStatus) in.readObject();
    }
    
    public String toString() {
        return patientName + ", " + vitalSignName + ", " + alarmStatus.name();
    }
    
} // VitalSignMessage

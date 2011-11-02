/*
 * Package: nursestation.notificationservice
 *
 * File: CallButtonMessage.java
 *
 * Date: Nov 2, 2011
 * 
 */
package nursestation.notificationservice;

/**
 * A message to be sent to the notification service with a call button status.
 * 
 * @author Jason Smith
 */
public class CallButtonMessage {

    /**
     * The ID of the patient
     */
    private int patientID;
    
    /**
     * The status of the call button
     */
    private boolean callButtonStatus;
    
    /**
     * Constructs a CallButtonMessage object.
     * 
     * @param patientID The ID of the patient
     * @param callButtonStatus The call button status
     */
    public CallButtonMessage(int patientID, boolean callButtonStatus){
        this.patientID = patientID;
        this.callButtonStatus = callButtonStatus;
    }
    
    /**
     * @return the patientID
     */
    public int getPatientID() {
    
        return patientID;
    }
    
    /**
     * @return the callButtonStatus
     */
    public boolean getCallButtonStatus() {
    
        return callButtonStatus;
    }
    
} // CallButtonMessage

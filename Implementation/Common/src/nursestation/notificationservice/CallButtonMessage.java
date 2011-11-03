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
     * The patient name
     */
    private String patientName;
    
    /**
     * The status of the call button
     */
    private boolean callButtonStatus;
    
    /**
     * Constructs a CallButtonMessage object.
     * 
     * @param patientName the patient name
     * @param callButtonStatus The call button status
     */
    public CallButtonMessage(String patientName, boolean callStatus){
        this.patientName = patientName;
        this.callButtonStatus = callStatus;
    }
    
    /**
     * @return the patient name
     */
    public String getPatientName() {
    
        return patientName;
    }
    
    /**
     * @return the callButtonStatus
     */
    public boolean getCallButtonStatus() {
    
        return callButtonStatus;
    }
    
} // CallButtonMessage

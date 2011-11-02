/*
 * Package: nursestation.notificationservice
 *
 * File: NotificationService.java
 *
 * Date: Nov 2, 2011
 * 
 */

package nursestation.notificationservice;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Remote interface to allow bedside monitors to push vital
 * signs and call button statuses to the nurses' station.
 * 
 * @author Jason Smith
 */
public interface NotificationService extends Remote {

    /**
     * Pushes a vital sign and its associated alarm status to the server
     * for a particular patient.
     * 
     * @param patientID The patient making the push request
     * @param vitalSign The vital sign changed
     * @param alarmStatus The status of the alarm
     */
    public void pushVitalSign(int patientID, double vitalSign,
                              boolean alarmStatus) throws RemoteException;
    
    /**
     * Pushes a call button status to the server for a particular patient.
     * 
     * @param patientID The patient making the push request
     * @param callButtonStatus The status of the call button
     */
    public void pushCallButton(int patientID, boolean callButtonStatus)
        throws RemoteException;
    
} // NotificationService

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
     * @param msg the VitalSignMessage to be pushed
     */
    public void pushVitalSign(VitalSignMessage msg) throws RemoteException;
    
    /**
     * Pushes a call button status to the server for a particular patient.
     * 
     * @param msg the CallButtonMessage to be pushed
     */
    public void pushCallButton(CallButtonMessage msg)
        throws RemoteException;
    
} // NotificationService

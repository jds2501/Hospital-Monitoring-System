/*
 * Package: nursestation.notificationservice
 *
 * File: NotificationServiceImpl.java
 *
 * Date: Nov 2, 2011
 * 
 */

package nursestation.notificationservice;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import nursestation.notificationservice.NotificationService;

/**
 * Implementation of the notification service interface to allow callers
 * to push vital sign and call button updates to the Nurses' Station.
 * 
 * @author Jason Smith
 */
public class NotificationServiceImpl 
    extends UnicastRemoteObject implements NotificationService {
    
    /**
     * The received vital sign message updates from clients
     */
    private Queue<VitalSignMessage> vitalSignMsgs;
    
    /**
     * The received call button message updates from clients
     */
    private Queue<CallButtonMessage> callButtonMsgs;
    
    /**
     * Constructs a NotificationServiceImpl object with a vital sign message
     * queue and call button message queue.
     * 
     * @throws RemoteException If this service fails to deploy
     */
    public NotificationServiceImpl() throws RemoteException {
        super();
        
        vitalSignMsgs = new LinkedBlockingQueue<VitalSignMessage>();
        callButtonMsgs = new LinkedBlockingQueue<CallButtonMessage>();
    }

    /**
     * Pushes a vital sign message onto the queue.
     * 
     * @param msg the vital sign message to push
     */
    public void pushVitalSign(VitalSignMessage msg) throws RemoteException {
        vitalSignMsgs.offer(msg);
    }

    /**
     * Pushes a call button message onto the queue.
     * 
     * @param msg the call button message to push
     */
    public void pushCallButton(CallButtonMessage msg) throws RemoteException {
        callButtonMsgs.offer(msg);
    }

    /**
     * Polls a vital sign message off of the vital sign message queue
     * 
     * @return The message at the top of vital sign queue if one exists,
     * null otherwise
     */
    public VitalSignMessage pullVitalSign() {
        return vitalSignMsgs.poll();
    }
    
    /**
     * Polls a call button message off of the call button message queue
     * 
     * @return The message at the top of call button queue if one exists,
     * null otherwise
     */
    public CallButtonMessage pullCallButton() {
        return callButtonMsgs.poll();
    }
    
} // NotificationServiceImpl

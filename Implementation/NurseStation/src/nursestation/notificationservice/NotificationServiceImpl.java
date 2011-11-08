/*
 * Package: nursestation.notificationservice
 *
 * File: NotificationServiceImpl.java
 *
 * Date: Nov 2, 2011
 * 
 */

package nursestation.notificationservice;

import historylogging.HistoryLogging;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

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
    private LinkedBlockingQueue<VitalSignMessage> vitalSignMsgs;
    
    /**
     * Constructs a NotificationServiceImpl object with a vital sign message
     * queue and call button message queue.
     * 
     * @throws RemoteException If this service fails to deploy
     */
    public NotificationServiceImpl() throws RemoteException {
        super();
        
        vitalSignMsgs = new LinkedBlockingQueue<VitalSignMessage>();
    }

    /**
     * Pushes a vital sign message onto the queue.
     * 
     * @param msg the vital sign message to push
     */
    public void pushVitalSign(VitalSignMessage msg) throws RemoteException {
        HistoryLogging.getInstance().logMessage("pushVitalSign, " + msg);
        vitalSignMsgs.offer(msg);
    }

    /**
     * Polls a vital sign message off of the vital sign message queue
     * 
     * @return The message at the top of vital sign queue if one exists,
     * null otherwise
     */
    public VitalSignMessage pullVitalSign() {
        VitalSignMessage msg = null;
        
        try {
            msg = vitalSignMsgs.poll(1, TimeUnit.MINUTES);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        
        return msg;
    }
    
} // NotificationServiceImpl

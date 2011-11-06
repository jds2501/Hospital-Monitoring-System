/*
 * Package: bedsidemonitor
 *
 * File: BedsideMonitorSubscribeImpl.java
 *
 * Date: Nov 4, 2011
 * 
 */
package bedsidemonitor;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import nursestation.notificationservice.NotificationService;
import nursestation.notificationservice.VitalSignMessage;


/**
 * This class acts as the subscribable implementation for the bedside
 * monitor to fire notifications to registered observers.
 * 
 * @author Jason Smith
 */
public class BedsideMonitorSubscribeImpl extends UnicastRemoteObject
    implements BedsideMonitorSubscribeInterface {

    /**
     * The list of notification services registered
     */
    private List<NotificationService> notificationServices;

    /**
     * Acknowledge messages for alarms for vital signs.
     */
    private Queue<String> acknowledgements;
    
    /**
     * Constructs a BedsideMonitorSubscribeImpl object.
     * 
     * @throws RemoteException If remote construction fails
     */
    public BedsideMonitorSubscribeImpl() throws RemoteException {
        super();
        notificationServices = new ArrayList<NotificationService>();
        acknowledgements = new LinkedBlockingQueue<String>();
    }
    
    /**
     * Subscribes the specified observer to watch the bedside monitor.
     * 
     * @param service the notification service subscribing to this monitor
     */
    public void subscribe(NotificationService service) throws RemoteException {
        synchronized(notificationServices) {
            notificationServices.add(service);
        }
    }

    /**
     * Unsubscribes the specified observer to not watch the bedside monitor
     * 
     * @param service the notification service unsubscribing from the monitor
     */
    public void unsubscribe(NotificationService service) throws RemoteException {
        synchronized(notificationServices) {
            notificationServices.remove(service);
        }
    }
    
    /**
     * Publishes the given vital sign message to all remote observers.
     * 
     * @param msg the vital sign message to publish
     */
    public void publishVitalSign(VitalSignMessage msg) {
        for(NotificationService service: notificationServices) {
            try {
                service.pushVitalSign(msg);
            } catch (RemoteException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Acknowledges the specified alarm at this bedside monitor.
     * 
     * @param vitalSignAlarm the vital sign alarm to acknowledge
     */
    public void acknowledgeAlarm(String vitalSignName) throws RemoteException {
        System.out.println("Acknowledge received: " + vitalSignName);
        acknowledgements.offer(vitalSignName);
    }
    
} // BedsideMonitorSubscribeImpl

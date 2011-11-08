/*
 * Package: bedsidemonitor
 *
 * File: BedsideMonitorInterface.java
 *
 * Date: Nov 3, 2011
 * 
 */
package bedsidemonitor;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collection;

import nursestation.notificationservice.NotificationService;

/**
 * The subscribable interface for observing patient updates remotely.
 * 
 * @author Jason Smith
 */
public interface BedsideMonitorSubscribeInterface extends Remote {

    /**
     * Subscribes this nurses' station to this patient for updates.
     * 
     * @param service the nurses' station observing patient updates
     * 
     * @throws RemoteException if the remote call fails
     */
    public void subscribe(NotificationService service) throws RemoteException;
    
    /**
     * Unsubscribes this nurses' station from this patient.
     * 
     * @param service the service to unsubscribe
     * 
     * @throws RemoteException if the remote call fails
     */
    public void unsubscribe(NotificationService service) throws RemoteException;
    
    /**
     * Acknowledges the specified alarm at the bedside monitor.
     * 
     * @param vitalSignName the vital sign alarm to acknowledge
     * 
     * @throws RemoteException if the remote call fails
     */
    public void acknowledgeAlarm(Collection<String> vitals) throws RemoteException;
    
} // BedsideMonitorSubscribeInterface

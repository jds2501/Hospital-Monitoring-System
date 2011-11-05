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

import nursestation.notificationservice.NotificationService;

/**
 * @author Jason
 *
 */
public interface BedsideMonitorSubscribeInterface extends Remote {

    public void subscribe(NotificationService service) throws RemoteException;
    
    public void unsubscribe(NotificationService service) throws RemoteException;
    
}

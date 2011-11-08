/*
 * Package: nursestation
 *
 * File: NurseStation.java
 *
 * Date: Nov 6, 2011
 * 
 */
package nursestation;

import historylogging.HistoryLogging;

import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import bedsidemonitor.BedsideMonitorSubscribeInterface;

import nursestation.notificationservice.NotificationServiceImpl;
import nursestation.notificationservice.NotificationServiceTask;


/**
 * @author Jason
 *
 */
public class NurseStation {

    private NotificationServiceImpl service;
    private NotificationServiceTask serviceRunnable;

    public NurseStation(String[] patientNames) throws RemoteException, NotBoundException{
        long start = System.currentTimeMillis();
        this.service = new NotificationServiceImpl();
        this.serviceRunnable = new NotificationServiceTask(service);
        
        Thread serviceTask = new Thread(serviceRunnable);
        serviceTask.start();
        
        this.subscribeToPatients(patientNames);
        long end = System.currentTimeMillis();
        
        HistoryLogging.getInstance().logMessage("NurseStation constructor, " 
                + (end - start));
    }
    
    /**
     * Subscribes this nurse station to the specified patients.
     * 
     * @param patientNames the names of the patients to subscribe to
     * 
     * @throws RemoteException If the remote connection fails
     * @throws NotBoundException If the remote object does not exist in the
     * RMI registry
     */
    private void subscribeToPatients(String[] patientNames) 
            throws RemoteException, NotBoundException {
        System.setSecurityManager(new RMISecurityManager());
        Registry registry = LocateRegistry.getRegistry();
        
        for(String patientName: patientNames){
            BedsideMonitorSubscribeInterface bedsideMonitor = 
                    (BedsideMonitorSubscribeInterface) registry.lookup(patientName);
            bedsideMonitor.subscribe(service);
            this.serviceRunnable.addPatient(patientName, bedsideMonitor);
        }
    }
    
    public NotificationServiceTask getServiceTask(){
        return serviceRunnable;
    }
    
}

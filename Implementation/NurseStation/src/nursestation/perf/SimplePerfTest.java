/*
 * Package: nursestation.perf
 *
 * File: SimplePerfTest.java
 *
 * Date: Nov 6, 2011
 * 
 */
package nursestation.perf;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import nursestation.NurseStation;
import nursestation.notificationservice.NotificationServiceTask;


/**
 * @author Jason
 *
 */
public class SimplePerfTest {

    public static void main(String[] args) {
        String[] patientNames = {"patient1"};
        
        try {
            NurseStation nurseStation = new NurseStation(patientNames);
            NotificationServiceTask task = nurseStation.getServiceTask();
            
            try {
                Thread.sleep(10000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            
            task.acknowledgeAlarmsByPatient("patient1");
        } catch (RemoteException ex) {
            ex.printStackTrace();
        } catch (NotBoundException ex) {
            ex.printStackTrace();
        }
        
    }
    
}

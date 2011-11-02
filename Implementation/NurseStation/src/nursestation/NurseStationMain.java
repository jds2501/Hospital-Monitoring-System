package nursestation;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;

import nursestation.notificationservice.NotificationServiceImpl;

/**
 * Main starting point to start up the nurse station
 * 
 * @author Chris Bentivenga
 */
public class NurseStationMain {
	
	/**
	 * Main method of execution to startup the nurse station.
	 * 
	 * @param args
	 */
	public static void main(String[] args){
		try {
            NotificationServiceImpl service = new NotificationServiceImpl();
            Naming.rebind("nurse-station", service);
        } catch (RemoteException ex) {
            ex.printStackTrace();
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }
	}
	
} // NurseStationMain

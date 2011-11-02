package nursestation;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;

import nursestation.notificationservice.NotificationServiceImpl;
import nursestation.userinterface.NurseStationView;

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
            NurseStationView view = new NurseStationView();
            view.setStationNameBox("Dummy Nurse Station");
            view.setPatientNumber(0);
        } catch (RemoteException ex) {
            ex.printStackTrace();
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }
	}
	
} // NurseStationMain
package bedsidemonitor.sensor;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;

/**
 * Main execution point for deploying the vital sign sensors.
 * 
 * @author Jason Smith
 */
public class SensorMain {
    
    /**
     * Main method that sets up the sensors specified.
     * 
     * @param args the names of the sensors to deploy
     */
	public static void main(String[] args) {
        try {
            for(String sensorName: args){
                SensorImpl sensor = new SensorImpl();
                Naming.rebind(sensorName, sensor);
            }
        } catch (RemoteException ex) {
            ex.printStackTrace();
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }
	}
	
} // SensorMain

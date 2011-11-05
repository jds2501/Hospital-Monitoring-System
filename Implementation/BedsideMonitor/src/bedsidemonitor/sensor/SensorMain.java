<<<<<<< HEAD
package bedsidemonitor.sensor;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.ServerException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class SensorMain {
	public static void main(String [] args) throws RemoteException, MalformedURLException{
	      // Create registry if not already running
	      try 
	      {
	    	  LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
	      } catch (java.rmi.server.ExportException ee) 
	      {
	         // Registry already exists
	      } catch (RemoteException e) 
	      {
	         throw new ServerException("Could not create registry", e);
	      }
	      
	      SensorInterface sensor = new SensorImpl();
	      Naming.rebind("sensor", sensor);
	}
}
=======
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
>>>>>>> f207d6ef488732aa079efa9dae51577840bacbfc

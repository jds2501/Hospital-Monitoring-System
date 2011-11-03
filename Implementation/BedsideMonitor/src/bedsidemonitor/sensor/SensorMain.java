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

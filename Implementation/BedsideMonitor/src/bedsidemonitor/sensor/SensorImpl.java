package bedsidemonitor.sensor;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;

public class SensorImpl extends UnicastRemoteObject implements SensorInterface {

    private Random generator;
    
	public SensorImpl() throws RemoteException {
		super();
	}

	public int getVitalSign() throws RemoteException {
		return generator.nextInt(101);
	}

}
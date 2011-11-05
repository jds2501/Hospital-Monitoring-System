<<<<<<< HEAD

package bedsidemonitor.sensor;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class SensorImpl extends UnicastRemoteObject implements SensorInterface{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected SensorImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getVitalSign() throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}

}
=======
package bedsidemonitor.sensor;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;

/**
 * A simple sensor implementation that returns a random integer for a vital
 * sign when prompted.
 * 
 * @author Jason Smith
 */
public class SensorImpl extends UnicastRemoteObject implements SensorInterface {

    /**
     * The random number generator for generating vital signs
     */
    private Random generator;
    
    /**
     * Constructs a SensorImpl object.
     * 
     * @throws RemoteException If the remote object construction fails
     */
	public SensorImpl() throws RemoteException {
		super();
		
		generator = new Random();
	}

	/**
	 * @return a random integer (0 - 100) for the vital sign for this sensor
	 */
	public int getVitalSign() throws RemoteException {
		return generator.nextInt(101);
	}
	
} // SensorImpl
>>>>>>> f207d6ef488732aa079efa9dae51577840bacbfc

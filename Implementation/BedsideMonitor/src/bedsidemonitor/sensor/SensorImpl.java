
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

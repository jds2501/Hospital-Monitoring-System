package bedsidemonitor.sensor;

import java.rmi.Remote;
import java.rmi.RemoteException;

public class SensorImpl_Stub 
extends java.rmi.server.RemoteStub
implements SensorInterface, Remote{

	@Override
	public int getVitalSign() throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}

}

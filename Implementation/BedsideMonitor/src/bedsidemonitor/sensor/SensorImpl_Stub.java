package bedsidemonitor.sensor;

import java.lang.reflect.Method;
import java.rmi.Remote;
import java.rmi.RemoteException;

public class SensorImpl_Stub 
extends java.rmi.server.RemoteStub
implements SensorInterface, Remote{

	private static final long serialVersionUID = 1L;
	private static Method $method_getVitalSign_0;

	static {
		try {
			$method_getVitalSign_0 = SensorInterface.class.getMethod("getVitalSign",
					new java.lang.Class[] {});
		} catch (java.lang.NoSuchMethodException e) {
			throw new java.lang.NoSuchMethodError(
					"stub class initialization failed");
		}
	}

	
    public SensorImpl_Stub(java.rmi.server.RemoteRef ref) {
    	super(ref);
    }
    
    
	@Override
	public int getVitalSign() throws RemoteException {
		  Object $result;
		try {
			$result = ref.invoke(this, $method_getVitalSign_0, null, $method_getVitalSign_0.hashCode());
			  return (Integer.parseInt($result.toString()));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return -100000;
	}

}

/*
 * Package: bedsidemonitor.perf
 *
 * File: BedsideMonitorPerfMain.java
 *
 * Date: Nov 6, 2011
 * 
 */
package bedsidemonitor.perf;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;


/**
 * @author Jason
 *
 */
public class BedsideMonitorPerfTest1 {

    public static void main(String[] args) {
        Map<String, VitalSignPerf> vitals = 
                new HashMap<String, VitalSignPerf>();
        VitalSignPerf vitalSign = new VitalSignPerf("vital1", 1.0, 100);
        vitals.put("vital1", vitalSign);
        
        try {
            BedsideMonitorPerf bedside = new BedsideMonitorPerf(vitals, 1);
            bedside.runCallsAndVitals(100);
        } catch (RemoteException ex) {
            ex.printStackTrace();
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }
        
        System.out.println("Test Finished");
    }
    
}

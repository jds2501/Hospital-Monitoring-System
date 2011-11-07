/*
 * Package: bedsidemonitor.perf
 *
 * File: BedsideMonitorPerfMain.java
 *
 * Date: Nov 6, 2011
 * 
 */
package bedsidemonitor.perf.testcases;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

import bedsidemonitor.perf.framework.BedsideMonitorPerf;
import bedsidemonitor.perf.framework.VitalSignPerf;


/**
 * @author Jason
 *
 */
public class OneVitalOneBedsideTest {

    public static void main(String[] args) {
        Map<String, VitalSignPerf> vitals = 
                new HashMap<String, VitalSignPerf>();
        VitalSignPerf vitalSign = new VitalSignPerf("vital1", 1.0, 100);
        vitals.put("vital1", vitalSign);
        
        try {
            BedsideMonitorPerf bedside = new BedsideMonitorPerf(vitals, 1);
            bedside.runCallsAndVitals(10000);
        } catch (RemoteException ex) {
            ex.printStackTrace();
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }
        
        System.out.println("Test Finished");
    }
    
}

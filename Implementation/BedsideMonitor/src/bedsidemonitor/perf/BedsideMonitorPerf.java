/*
 * Package: bedsidemonitor.test
 *
 * File: BedsideMonitorPerf.java
 *
 * Date: Nov 6, 2011
 * 
 */
package bedsidemonitor.perf;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

import bedsidemonitor.BedsideMonitor;
import bedsidemonitor.sensor.SensorInterface;
import bedsidemonitor.vitalsigncollection.VitalSignConfiguration;
import bedsidemonitor.vitalsigncollection.VitalSignController;


/**
 * @author Jason
 *
 */
public class BedsideMonitorPerf {

    private static int patientNumber = 1;
    private BedsideMonitor bedsideMonitor;
    private Map<String, VitalSignPerf> vitalSigns;
    private int numCallRequests;
    
    public BedsideMonitorPerf(Map<String, VitalSignPerf> vitalSigns, 
            int numCallRequests) throws RemoteException, MalformedURLException {
        this.numCallRequests = numCallRequests;
        this.vitalSigns = vitalSigns;
        
        String patientName = "patient" + patientNumber;
        patientNumber++;
        
        Map<String, SensorInterface> sensors = 
                new HashMap<String, SensorInterface>();
        
        for(String vitalSignName: vitalSigns.keySet()) {
            VitalSignPerf vitalSign = vitalSigns.get(vitalSignName);
            sensors.put(vitalSignName, vitalSign.getSensor());
        }
        
        this.bedsideMonitor = new BedsideMonitor(patientName, sensors);
        
        for(String vitalSignName: vitalSigns.keySet()) {
            VitalSignController controller = bedsideMonitor.getVitalSign(vitalSignName);
            VitalSignPerf vitalSign = vitalSigns.get(vitalSignName);
            VitalSignConfiguration config = vitalSign.getConfiguration();
            controller.setConfiguration(config);
        }
    }
    
    public void runCallsAndVitals(long delay) {
        roundTripCallExecution(delay);
        roundTripVitalSigns(delay);
        tearDown();
    }
    
    public void roundTripCallExecution(long delay) {
        long totalCallRequestTime = 0;
        long totalCallResetTime = 0;
        
        for(int i = 0; i < numCallRequests; i++) {
            long start = System.currentTimeMillis();
            bedsideMonitor.setCallButton(true);
            long end = System.currentTimeMillis();
            
            totalCallRequestTime += (end - start);
            
            System.out.println("Call Request On, " + (end - start));
            
            try {
                Thread.sleep(delay);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            
            start = System.currentTimeMillis();
            bedsideMonitor.setCallButton(false);
            end = System.currentTimeMillis();
            
            totalCallResetTime += (end - start);
            
            System.out.println("Call Request Off, " + (end - start));
            
            try {
                Thread.sleep(delay);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        
        double callRequestsPerSecond = 0;
        
        if(totalCallRequestTime > 0) {
            callRequestsPerSecond = 
                    ((double) numCallRequests * 1000) / totalCallRequestTime;
        }
        
        double callResetsPerSecond = 0;
        
        if(totalCallResetTime > 0) {
            callResetsPerSecond =
                    ((double) numCallRequests * 1000) / totalCallResetTime;
        }
        
        System.out.println("Call Requests per Second, " + callRequestsPerSecond);
        System.out.println("Call Resets per Second, " + callResetsPerSecond);
    }
    
    public void roundTripVitalSigns(long delay) {
        for(String vitalSignName: vitalSigns.keySet()) {
            VitalSignController vitalSign = bedsideMonitor.getVitalSign(vitalSignName);
            vitalSign.enableMeasurement();
        }
        
        try {
            Thread.sleep(delay);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        
        for(String vitalSignName: vitalSigns.keySet()) {
            VitalSignController vitalSign = bedsideMonitor.getVitalSign(vitalSignName);
            vitalSign.resetAlarm();
            vitalSign.disableMeasurement();
        }
    }
    
    public void tearDown() {
        bedsideMonitor.unpublish();
    }
    
}

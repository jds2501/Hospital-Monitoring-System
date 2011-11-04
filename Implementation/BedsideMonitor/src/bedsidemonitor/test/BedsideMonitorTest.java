/*
 * Package: 
 *
 * File: BedsideMonitorTest.java
 *
 * Date: Nov 4, 2011
 * 
 */

package bedsidemonitor.test;

import bedsidemonitor.BedsideMonitor;
import bedsidemonitor.sensor.FakeSensorLookup;
import bedsidemonitor.vitalsigncollection.VitalSignConfiguration;
import junit.framework.TestCase;

/**
 * @author Jason
 *
 */
public class BedsideMonitorTest extends TestCase {

    private BedsideMonitor bedsideMonitor;
    
    public void setUp(){
        this.bedsideMonitor = new BedsideMonitor(new FakeSensorLookup());
    }
    
    public void testAddNonExistentSensor(){
        boolean sensorFound = true;
        
        VitalSignConfiguration configuration = 
                new VitalSignConfiguration("not found", 0, 0, 0, 0);
        try{
            this.bedsideMonitor.addSensor(configuration);
        }catch(IllegalArgumentException ex){
            sensorFound = false;
        }
        
        assertFalse(sensorFound);
    }
    
}

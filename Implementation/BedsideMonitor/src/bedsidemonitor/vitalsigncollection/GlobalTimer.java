/*
 * Package: 
 *
 * File: GlobalTimer.java
 *
 * Date: Nov 6, 2011
 * 
 */

package bedsidemonitor.vitalsigncollection;

import java.util.Timer;

/**
 * @author Jason
 *
 */
public class GlobalTimer {

    private static GlobalTimer globalTimer;
    private Timer timer;
    
    private GlobalTimer() {
        timer = new Timer();
    }
    
    public static Timer getTimer() {
        if(globalTimer == null) {
            globalTimer = new GlobalTimer();
        }
        
        return globalTimer.timer;
    }
    
}

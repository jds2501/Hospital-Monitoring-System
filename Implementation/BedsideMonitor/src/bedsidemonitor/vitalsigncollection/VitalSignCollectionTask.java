/*
 * Package: bedsidemonitor.vitalsigncollection
 *
 * File: VitalSignCollectionTask.java
 *
 * Date: Nov 1, 2011
 * 
 */

package bedsidemonitor.vitalsigncollection;

import java.util.TimerTask;

/**
 * Schedulable task to collect sensor data from a specific
 * sensor and push it to a vital sign message queue.
 * 
 * @author Jason Smith
 */
public class VitalSignCollectionTask extends TimerTask {

    /**
     * The vital sign collection controller to poll sensor data off of
     */
    private VitalSignCollectionController controller;
    
    /**
     * Constructs a VitalSignColletionTask object with a message
     * queue and a vital sign collection controller.
     * 
     * @param vitalSignCollectionController The controller to call
     */
    public VitalSignCollectionTask(VitalSignCollectionController controller) {
        this.controller = controller;
    }

    /**
     * When scheduled, this task will call the vital sign controller
     * to poll sensor data.
     */
    public void run() {
        this.controller.pollSensorData();
    }

} // VitalSignCollectionTask
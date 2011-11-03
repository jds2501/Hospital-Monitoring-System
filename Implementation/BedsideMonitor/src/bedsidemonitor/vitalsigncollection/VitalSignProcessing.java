/*
 * Package: bedsidemonitor.vitalsigncollection
 *
 * File: VitalSignProcessing.java
 *
 * Date: Nov 3, 2011
 * 
 */
package bedsidemonitor.vitalsigncollection;

import historylogging.HistoryLogging;

import java.util.Observable;
import java.util.Queue;


/**
 * @author Jason
 *
 */
public class VitalSignProcessing extends Observable {

    /**
     * Vital sign message queue to containing gathered sensor data
     */
    private Queue<Integer> vitalSignMsgQueue;
    
    private Double vitalSignValue;
    
    private VitalSignConfiguration configuration;
    
    public VitalSignProcessing(Queue<Integer> vitalSignMsgQueue,
            VitalSignConfiguration converter){
        this.vitalSignMsgQueue = vitalSignMsgQueue;
        this.vitalSignValue = null;
        this.configuration = converter;
    }
    
    public void pullVitalSign(){
        int rawVitalSignReading = vitalSignMsgQueue.poll();
        vitalSignValue = configuration.convertRawVitalToActual(rawVitalSignReading);
        HistoryLogging.getInstance().logMessage("New Vital Reading: " + vitalSignValue);
        
        if(!configuration.isVitalSignInRange(vitalSignValue)){
            // TODO: Turn on alarm from controller
        }
        
        this.setChanged();
        this.notifyObservers();        
        // TODO: Push results to notification service
    }
    
}

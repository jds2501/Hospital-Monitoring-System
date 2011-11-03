/*
 * Package: bedsidemonitor.vitalsigncollection
 *
 * File: VitalSignProcessing.java
 *
 * Date: Nov 3, 2011
 * 
 */
package bedsidemonitor.vitalsigncollection;

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
    
    private VitalSignConfiguration converter;
    
    public VitalSignProcessing(Queue<Integer> vitalSignMsgQueue,
            VitalSignConfiguration converter){
        this.vitalSignMsgQueue = vitalSignMsgQueue;
        this.vitalSignValue = null;
        this.converter = converter;
    }
    
    public void pullVitalSign(){
        int rawVitalSignReading = vitalSignMsgQueue.poll();
        vitalSignValue = converter.convertRawVitalToActual(rawVitalSignReading);
        // TODO: Finish the processing post conversion
    }
    
}

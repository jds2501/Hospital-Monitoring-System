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
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import alarm.AlarmController;
import alarm.AlarmStatus;


/**
 * This class represents a vital sign processing class for pulling sensor
 * data from a message queue and processing the sensor data respectively.
 * 
 * @author Jason Smith
 */
public class VitalSignProcessing extends Observable implements Runnable {

    /**
     * Vital sign message queue to containing gathered sensor data
     */
    private LinkedBlockingQueue<Integer> vitalSignMsgQueue;
    
    /**
     * The current vital sign reading
     */
    private Double vitalSignValue;
    
    /**
     * The vital sign configuration values
     */
    private VitalSignConfiguration configuration;

    /**
     * The controller for managing the alarm for this vital sign
     */
    private AlarmController alarmController;
    
    /**
     * Specifies whether this task is still alive or not
     */
    private boolean isActive;
    
    /**
     * Constructs a VitalSignProcessing object with a message queue
     * and a vital sign configuration.
     * 
     * @param vitalSignMsgQueue the message queue to pull sensor data from
     * @param configuration the vital sign configuration
     */
    public VitalSignProcessing(LinkedBlockingQueue<Integer> vitalSignMsgQueue, 
           VitalSignConfiguration configuration){
        this.vitalSignMsgQueue = vitalSignMsgQueue;
        this.vitalSignValue = null;
        this.configuration = configuration;
        this.alarmController = new AlarmController();
    }
    
    /**
     * Pulls the vital sign from the message queue and converts it
     * to its actual value. Then, if the vital sign is out of range,
     * fire an alarm.
     */
    public void pullVitalSign(){
        Integer rawVitalSignReading = null;
        
        try {
            rawVitalSignReading = vitalSignMsgQueue.poll(
                    configuration.getCollectionRate(), TimeUnit.MILLISECONDS);
        } catch (InterruptedException ex) {
        }
        
        if(rawVitalSignReading != null) {
            vitalSignValue = configuration.convertRawVitalToActual(rawVitalSignReading);
            HistoryLogging.getInstance().logMessage("New Vital Reading: " + 
                    vitalSignValue);
            
            if(!configuration.isVitalSignInRange(vitalSignValue)){
                this.alarmController.setAlarmStatus(AlarmStatus.ACTIVE);
            }
            
            this.setChanged();
            this.notifyObservers(this);
        }
    }

    /**
     * @return the isActive
     */
    public boolean isActive() {
    
        return isActive;
    }

    
    /**
     * @param isActive the isActive to set
     */
    public void setActive(boolean isActive) {
    
        this.isActive = isActive;
    }
    
    /**
     * @return the vital sign configuration
     */
    public VitalSignConfiguration getConfiguration() {
        return configuration;
    }
    
    /**
     * @return vital sign value
     */
    public Double getVitalSignValue() {
        return vitalSignValue;
    }
    
    /**
     * @return the alarm status
     */
    public AlarmStatus getAlarmStatus() {
        return alarmController.getStatus();
    }
    
    /**
     * Sets the vital sign configuration to specified value.
     * 
     * @param configuration the new vital sign configuration
     */
    public void setConfiguration(VitalSignConfiguration configuration) {
        this.configuration = configuration;
    }
    
    /**
     * While this task is active, continue to pull vital signs until its
     * shut off.
     */
    public void run() {
        while(isActive) {
            System.out.println("Execute");
            pullVitalSign();
        }
    }

    public void acknowledgeAlarm() {
        alarmController.setAlarmStatus(AlarmStatus.ACKNOWLEDGED);
        setChanged();
        notifyObservers(this);
    }
    
    public void resetAlarm() {
        alarmController.setAlarmStatus(AlarmStatus.INACTIVE);
        setChanged();
        notifyObservers(this);
    }
    
} // VitalSignProcessing

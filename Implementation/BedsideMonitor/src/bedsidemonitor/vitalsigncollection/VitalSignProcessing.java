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

import java.util.List;
import java.util.Observable;
import java.util.Queue;

import nursestation.notificationservice.NotificationService;

import alarm.AlarmController;
import alarm.AlarmStatus;


/**
 * @author Jason
 *
 */
public class VitalSignProcessing extends Observable implements Runnable {

    /**
     * Vital sign message queue to containing gathered sensor data
     */
    private Queue<Integer> vitalSignMsgQueue;
    
    private Double vitalSignValue;
    
    private VitalSignConfiguration configuration;

    private AlarmController alarmController;
    
    private List<NotificationService> notificationServices;
    
    private boolean isActive;
    
    public VitalSignProcessing(Queue<Integer> vitalSignMsgQueue,
            VitalSignConfiguration converter, List<NotificationService> notificationServices){
        this.vitalSignMsgQueue = vitalSignMsgQueue;
        this.vitalSignValue = null;
        this.configuration = converter;
        this.alarmController = new AlarmController();
        this.notificationServices = notificationServices;
    }
    
    public void pullVitalSign(){
        int rawVitalSignReading = vitalSignMsgQueue.poll();
        vitalSignValue = configuration.convertRawVitalToActual(rawVitalSignReading);
        HistoryLogging.getInstance().logMessage("New Vital Reading: " + vitalSignValue);
        
        if(!configuration.isVitalSignInRange(vitalSignValue)){
            this.alarmController.setAlarmStatus(AlarmStatus.ACTIVE);
        }
        
        this.setChanged();
        this.notifyObservers();        
        // TODO: Push results to notification service
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
    
    public VitalSignConfiguration getConfiguration() {
        return configuration;
    }
    
    public void setConfiguration(VitalSignConfiguration configuration) {
        this.configuration = configuration;
    }
    
    public void run() {
        
    }
    
}

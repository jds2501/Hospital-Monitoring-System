/*
 * Package: alarm
 *
 * File: AlarmController.java
 *
 * Date: Nov 3, 2011
 * 
 */
package alarm;

import historylogging.HistoryLogging;

/**
 * A controller for managing an alarm for a vital sign.
 * 
 * @author Jason Smith
 */
public class AlarmController {

    /**
     * The status of the alarm
     */
    private AlarmStatus status;
    
    /**
     * The time when the alarm was triggered
     */
    private long alarmTriggeredTime;
    
    /**
     * Static counter of the number of alarms triggered
     */
    private static int alarmCounter = 0;
    
    /**
     * Constructs an AlarmController object.
     */
    public AlarmController(){
        status = AlarmStatus.INACTIVE;
    }
    
    /**
     * @return the alarm status for this vital sign
     */
    public AlarmStatus getStatus() {
        return status;
    }
    
    /**
     * Sets the alarm status for the vital sign this represents.
     * 
     * @param status the new status of the alarm
     */
    public void setAlarmStatus(AlarmStatus status){
        this.status = status;
        HistoryLogging.getInstance().logMessage("Alarm status changed: " + status);
        
        if(status == AlarmStatus.ACTIVE){
            alarmCounter++;
            HistoryLogging.getInstance().logMessage("Alarm counter update: " + alarmCounter);
            alarmTriggeredTime = System.currentTimeMillis();
            // TODO: Turn on diagnostic mode
        }else if(status == AlarmStatus.INACTIVE){
            long resetTime = System.currentTimeMillis();
            long roundTripTime = resetTime - alarmTriggeredTime;
            HistoryLogging.getInstance().logMessage("Alarm reset time: " + roundTripTime);
        }
    }
    
} // AlarmController

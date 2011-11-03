/*
 * Package: bedsidemonitor.callbutton
 *
 * File: CallButtonController.java
 *
 * Date: Nov 3, 2011
 * 
 */
package bedsidemonitor.callbutton;

import historylogging.HistoryLogging;

import java.util.List;
import java.util.Observable;

import nursestation.notificationservice.NotificationService;


/**
 * @author Jason
 *
 */
public class CallButtonController extends Observable {

    private boolean callStatus;
    private int callRequestCounter;
    private List<NotificationService> notificationServices;
    
    public CallButtonController(List<NotificationService> notificationServices) {
        this.callStatus = false;
        this.callRequestCounter = 0;
        this.notificationServices = notificationServices;
    }
    
    public boolean getCallStatus() {
        return callStatus;
    }
    
    public void setCallStatus(boolean callStatus){
        if(callStatus){
            callRequestCounter++;
        }
        
        this.callStatus = callStatus;
        HistoryLogging.getInstance().logMessage("Call Status Set: " + callStatus);
        
        setChanged();
        notifyObservers(this);
        
        // TODO: Fire message to all notification services
    }
    
}

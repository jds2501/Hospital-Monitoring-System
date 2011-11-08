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

import java.util.Observable;


/**
 * Controller for managing the call button status of a bedside monitor.
 * 
 * @author Jason Smith
 */
public class CallButtonController extends Observable {

    /**
     * The call status of a button currently
     */
    private boolean callStatus;
    
    /**
     * The call request counter
     */
    private int callRequestCounter;
    
    /**
     * Constructs a CallButtonController object.
     */
    public CallButtonController() {
        this.callStatus = false;
        this.callRequestCounter = 0;
    }
    
    /**
     * @return Gets the call status of the button
     */
    public boolean getCallStatus() {
        return callStatus;
    }
    
    /**
     * Sets the call status of the call button.
     * 
     * @param callStatus the status to set the call button to
     */
    public void setCallStatus(boolean callStatus){
        this.callStatus = callStatus;
        HistoryLogging.getInstance().logMessage("Call Status Set, " 
                + callStatus);
        
        if(callStatus){
            callRequestCounter++;
            HistoryLogging.getInstance().logMessage("Call Request Counter, " 
                    + callRequestCounter);
        }
        
        setChanged();
        notifyObservers(this);
    }
    
} // CallButtonController

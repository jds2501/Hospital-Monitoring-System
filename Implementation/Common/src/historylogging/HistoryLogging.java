/*
 * Package: historylogging
 *
 * File: HistoryLogging.java
 *
 * Date: Nov 3, 2011
 * 
 */
package historylogging;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;


/**
 * History logging class for logging actions and data for the bedside
 * monitor and nurses' station.
 * 
 * @author Jason Smith
 */
public class HistoryLogging {

    /**
     * Singleton instance of the history logger
     */
    private static HistoryLogging instance;
    
    /**
     * Log message queue for capturing log messages
     */
    private Queue<String> logMessageQueue;
    
    /**
     * Constructs a HistoryLogging object with an empty queue.
     */
    private HistoryLogging(){
        logMessageQueue = new LinkedBlockingQueue<String>();
    }
    
    /**
     * Logs a specified message by adding it the queue.
     * 
     * @param message the message to add
     */
    public void logMessage(String message){
        long time = System.currentTimeMillis();
        logMessageQueue.offer(time + " " + message);
    }
    
    /**
     * Gets the singleton instance of this class.
     * 
     * @return the singleton instance of this class
     */
    public static HistoryLogging getInstance() {
        if(instance == null){
            instance = new HistoryLogging();
        }
        
        return instance;
    }
    
} // HistoryLogging

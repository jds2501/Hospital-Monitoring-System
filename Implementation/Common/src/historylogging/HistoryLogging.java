/*
 * Package: historylogging
 *
 * File: HistoryLogging.java
 *
 * Date: Nov 3, 2011
 * 
 */
package historylogging;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;


/**
 * History logging class for logging actions and data for the bedside
 * monitor and nurses' station.
 * 
 * @author Jason Smith
 */
public class HistoryLogging extends Thread {

    /**
     * Log output filename
     */
    public static final String LOG_FILE_NAME = "history_log_startup_ " + 
            System.currentTimeMillis() + ".txt";
    
    /**
     * Declares whether the thread is alive or not
     */
    private boolean isAlive;
    
    /**
     * Singleton instance of the history logger
     */
    private static HistoryLogging instance;
    
    /**
     * Log message queue for capturing log messages
     */
    private LinkedBlockingQueue<String> logMessageQueue;
    
    /**
     * Output IO object to write log messages to
     */
    private BufferedWriter outputWriter;
    
    /**
     * Constructs a HistoryLogging object with an empty queue.
     */
    private HistoryLogging() {
        isAlive = true;
        logMessageQueue = new LinkedBlockingQueue<String>();
        
        try {
            this.outputWriter = new BufferedWriter(new FileWriter(LOG_FILE_NAME));
            this.start();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Specifies whether the logger is still alive.
     * 
     * @param alive whether the logger is still alive or not
     */
    public void setAlive(boolean alive){
        this.isAlive = alive;
    }
    
    /**
     * Logs a specified message by adding it the queue.
     * 
     * @param message the message to add
     */
    public void logMessage(String message){
        long time = System.currentTimeMillis();
        logMessageQueue.offer(time + ", " + message);
    }
    
    /**
     * Gets the singleton instance of this class.
     * 
     * @return the singleton instance of this class
     * @throws IOException if the logger instance cannot be created
     */
    public static HistoryLogging getInstance() {
        if(instance == null){
            instance = new HistoryLogging();
        }
        
        return instance;
    }
    
    /**
     * While this logger is active, it continuously logs all incoming
     * messages to the output file.
     */
    public void run() {
        while(isAlive){
            
            try {
                
                while(!logMessageQueue.isEmpty()) {
                    String message = null;
                    
                    try {
                        message = logMessageQueue.poll(1, TimeUnit.MINUTES);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    
                    if(message != null) {
                        outputWriter.write(message + "\n");
                        outputWriter.flush();
                    }
                }
                
            } catch(IOException ex){
                ex.printStackTrace();
            }
            
        }
        
        try {
            outputWriter.close();
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }
    
} // HistoryLogging

/*
 * Package: bedsidemonitor.vitalsigncollection
 *
 * File: VitalSignController.java
 *
 * Date: Nov 5, 2011
 * 
 */
package bedsidemonitor.vitalsigncollection;

import historylogging.HistoryLogging;

import java.util.Date;
import java.util.Observable;
import java.util.Observer;
import java.util.Queue;
import java.util.Timer;
import java.util.concurrent.LinkedBlockingQueue;

import alarm.AlarmStatus;
import bedsidemonitor.sensor.SensorInterface;


/**
 * The main controller for managing a vital sign, its associated configuration,
 * state, and alarms.
 * 
 * @author Jason Smith
 */
public class VitalSignController extends Observable {

    /**
     * The collection process for gathering sensor data
     */
    private VitalSignCollection collection;

    /**
     * The processing of collected sensor data
     */
    private VitalSignProcessing processor;
    
    /**
     * The schedule timer for vital sign processing tasks
     */
    private Timer timer;
    
    /**
     * Constructs a VitalSignController object with a vital sign name
     * and a respective sensor.
     * 
     * @param vitalSignName the name of the vital sign
     * @param sensor the sensor for polling vital sign data from
     */
    public VitalSignController(String vitalSignName, SensorInterface sensor){
        this.timer = GlobalTimer.getTimer();
        LinkedBlockingQueue<Integer> vitalSignMsgQueue = new LinkedBlockingQueue<Integer>();
        VitalSignConfiguration configuration = new VitalSignConfiguration(
                vitalSignName);
        this.collection = new VitalSignCollection(
                configuration, sensor, vitalSignMsgQueue);
        this.processor = new VitalSignProcessing(
                vitalSignMsgQueue, configuration);
    }
    
    /**
     * Adds an observer to this object and the processor.
     * 
     * @param observer the observer to add
     */
    public synchronized void addObserver(Observer observer) {
        super.addObserver(observer);
        this.processor.addObserver(observer);
    }
    
    /**
     * Enables the measurement by starting up the collection and processing
     * tasks for this vital sign.
     */
    public void enableMeasurement(){
        long start = System.currentTimeMillis();
        processor.setActive(true);
        Thread processorTask = new Thread(processor);
        processorTask.start();
        startCollectionTask();
        this.setChanged();
        this.notifyObservers(this);
        long end = System.currentTimeMillis();
        HistoryLogging.getInstance().logMessage("enableMeasurement, " + 
                processor.getConfiguration().getName() + ", " + (end - start));
    }
    
    /**
     * Disables the measurement by killing the collection and processing
     * tasks for this vital sign.
     */
    public void disableMeasurement() {
        long start = System.currentTimeMillis();
        killCollectionTask();
        processor.setActive(false);
        this.setChanged();
        this.notifyObservers(this);
        long end = System.currentTimeMillis();
        HistoryLogging.getInstance().logMessage("disableMeasurement, " + 
                processor.getConfiguration().getName() + ", " + (end - start));
    }
    
    public void restartCollectionTask() {
        killCollectionTask();
        startCollectionTask();
    }
    
    private void killCollectionTask() {
        VitalSignCollection oldCollection = collection;
        collection = new VitalSignCollection(oldCollection);
        oldCollection.cancel();
    }
    
    private void startCollectionTask() {
        timer.scheduleAtFixedRate(collection, new Date(),
                processor.getConfiguration().getCollectionRate());
    }
    
    /**
     * @return the vital sign configuration for this vital sign
     */
    public VitalSignConfiguration getConfiguration() {
        return processor.getConfiguration();
    }
    
    public void setConfiguration(VitalSignConfiguration configuration) {
        processor.setConfiguration(configuration);
    }
    
    /**
     * @return vital sign value
     */
    public Double getVitalSignValue() {
        return processor.getVitalSignValue();
    }
    
    public void acknowledgeAlarm() {
        processor.acknowledgeAlarm();
    }
    
    public void resetAlarm() {
        processor.resetAlarm();
    }
    
    /**
     * @return the alarm status
     */
    public AlarmStatus getAlarmStatus() {
        return processor.getAlarmStatus();
    }
    
} // VitalSignController

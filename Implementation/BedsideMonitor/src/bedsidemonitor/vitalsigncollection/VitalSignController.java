/*
 * Package: bedsidemonitor.vitalsigncollection
 *
 * File: VitalSignController.java
 *
 * Date: Nov 5, 2011
 * 
 */
package bedsidemonitor.vitalsigncollection;

import java.util.Date;
import java.util.Observable;
import java.util.Observer;
import java.util.Queue;
import java.util.Timer;
import java.util.concurrent.LinkedBlockingQueue;

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
    private VitalSignCollectionController collection;

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
        this.timer = new Timer();
        Queue<Integer> vitalSignMsgQueue = new LinkedBlockingQueue<Integer>();
        this.collection = new VitalSignCollectionController(
                sensor, vitalSignMsgQueue);
        VitalSignConfiguration configuration = new VitalSignConfiguration(
                vitalSignName);
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
        Thread processorTask = new Thread(processor);
        processorTask.start();
        timer.scheduleAtFixedRate(collection, new Date(),
                processor.getConfiguration().getCollectionRate());
        this.setChanged();
        this.notifyObservers(this);
    }
    
    /**
     * Disables the measurement by killing the collection and processing
     * tasks for this vital sign.
     */
    public void disableMeasurement() {
        collection.cancel();
        processor.setActive(false);
        this.setChanged();
        this.notifyObservers(this);
    }
    
    public VitalSignConfiguration getConfiguration() {
        return processor.getConfiguration();
    }
    
} // VitalSignController
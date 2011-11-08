/*
 * Package: bedsidemonitor.vitalsigncollection
 *
 * File: VitalSignCollectionController.java
 *
 * Date: Nov 1, 2011
 * 
 */
package bedsidemonitor.vitalsigncollection;

import java.rmi.RemoteException;
import java.util.Queue;
import java.util.TimerTask;

import bedsidemonitor.sensor.SensorInterface;


/**
 * Collection controller in charge of gathering data against a
 * sensor interface and pushing the results to a vital sign
 * message queue.
 * 
 * @author Jason Smith
 */
public class VitalSignCollection extends TimerTask {
    
    /**
     * Sensor interface to poll data from
     */
    private SensorInterface sensor;
    
    /**
     * Vital sign message queue to push sensor data to
     */
    private Queue<Integer> vitalSignMsgQueue;
    
    /**
     * Constructs a VitalSignCollectionController object with a
     * sensor interface and a vital sign message queue.
     * 
     * @param sensor the sensor interface to pull sensor data from
     * @param vitalSignMsgQueue the message queue to push sensor data to
     */
    public VitalSignCollection(SensorInterface sensor, 
            Queue<Integer> vitalSignMsgQueue){
        this.sensor = sensor;
        this.vitalSignMsgQueue = vitalSignMsgQueue;
    }
    
    /**
     * Constructs a VitalSignCollection object that copies the underlying
     * data for another vital sign collection object.
     * 
     * @param collection the vital sign collection object to copy data from
     */
    public VitalSignCollection(VitalSignCollection collection) {
        this.sensor = collection.sensor;
        this.vitalSignMsgQueue = collection.vitalSignMsgQueue;
    }
    
    /**
     * Polls the sensor data off the sensor interface and pushes
     * the results to the vital sign message queue.
     */
    public void pollSensorData(){
        try {
            // TODO: Log that vital has been collected
            int reading = this.sensor.getVitalSign();
            this.vitalSignMsgQueue.offer(reading);
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * When scheduled, this task will pull sensor data and place it into
     * a queue.
     */
    public void run() {
        pollSensorData();
    }
    
} // VitalSignCollectionController

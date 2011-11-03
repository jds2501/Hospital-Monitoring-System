package bedsidemonitor.vitalsigncollection;

/**
 *
 * Interface for the algorithms responsible for converting raw
 * SensorData to a usable form
 *
 * @author Ian hunt
 * @date 11/3/11
 */
public interface VitalSignConfiguration {

    /**
     * Convert a raw sensor value
     * 
     * @param value raw data in integer form
     * 
     * @return converted value
     */
    public double convert( int value );

}

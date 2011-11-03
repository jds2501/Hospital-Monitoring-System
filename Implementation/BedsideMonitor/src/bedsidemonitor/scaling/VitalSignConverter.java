package bedsidemonitor.scaling;

import nursestation.enums.Vitals;

/**
 *
 * Interface for the algorithms responsible for converting raw
 * SensorData to a usable form
 *
 * @author ian hunt
 * @date 11/3/11
 */
public interface VitalSignConverter {

    /**
     * Convert a raw sensor value
     * @param value raw data in integer form
     * @param vitalType vital sign type to convert
     * @return converted value
     */
    public double convert( int value, Vitals vitalType );



}

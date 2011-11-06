package bedsidemonitor.vitalsigncollection;

/**
 *
 * Interface for the algorithms responsible for converting raw
 * SensorData to a usable form
 *
 * @author Ian hunt
 * @date 11/3/11
 */
public class VitalSignConfiguration {

    /**
     * The name of the vital sign
     */
    private String name;
    
    /**
     * The conversion factor for converting a raw value to the actual value
     */
    private Double conversionFactor;
    
    /**
     * The minimum allowed value that a vital sign can be in
     */
    private Double minAllowedReading;
    
    /**
     * The maximum allowed value that a vital sign can be in
     */
    private Double maxAllowedReading;
    
    /**
     * The collection rate of the vital sign from the sensor
     */
    private Long collectionRate;

    /**
     * Constructs a basic vital sign configuration with hard-coded defaults.
     * 
     * @param name the name of the vital sign
     */
    public VitalSignConfiguration(String name) {
        this.name = name;
        this.conversionFactor = 1.0;
        this.minAllowedReading = 0.0;
        this.maxAllowedReading = 100.0;
        this.collectionRate = 1000L;
    }
    
    /**
     * Constructs a VitalSignConfiguration object with a name, conversion
     * factor, and minimum/maximum allowed readings.
     * 
     * @param name the name of the vital sign
     * @param conversionFactor the conversion factor to convert raw values
     * @param minAllowedReading the minimum allowed for a vital sign
     * @param maxAllowedReading the maximum allowed for a vital sign
     */
    public VitalSignConfiguration(
            String name, double conversionFactor, double minAllowedReading,
            double maxAllowedReading, long collectionRate) {
        this.name = name;
        this.conversionFactor = conversionFactor;
        this.minAllowedReading = minAllowedReading;
        this.maxAllowedReading = maxAllowedReading;
        this.collectionRate = collectionRate;
    }
    
    /**
     * Converts a raw vital sign to its actual value
     * 
     * @param rawVitalSignValue the raw vital sign to convert
     * 
     * @return The raw value multiplied by the conversion value
     */
    public double convertRawVitalToActual( int rawVitalSignValue ) {
        return rawVitalSignValue * conversionFactor;
    }
    
    /**
     * Checks if the given vital sign value is in range of the allowed
     * values for that vital sign.
     * 
     * @param vitalSignValue the value to check
     * @return true if the vital sign is is range, false otherwise
     */
    public boolean isVitalSignInRange( double vitalSignValue ) {
        return vitalSignValue >= minAllowedReading &&
               vitalSignValue <= maxAllowedReading;
    }
    
    /**
     * @return the name
     */
    public String getName() {
    
        return name;
    }
    
    /**
     * @return the conversionFactor
     */
    public double getConversionFactor() {
    
        return conversionFactor;
    }
    
    /**
     * @param conversionFactor the conversionFactor to set
     */
    public void setConversionFactor(double conversionFactor) {
    
        this.conversionFactor = conversionFactor;
    }
    
    /**
     * @return the minAllowedReading
     */
    public double getMinAllowedReading() {
    
        return minAllowedReading;
    }
    
    /**
     * @param minAllowedReading the minAllowedReading to set
     */
    public void setMinAllowedReading(double minAllowedReading) {
    
        this.minAllowedReading = minAllowedReading;
    }
    
    /**
     * @return the maxAllowedReading
     */
    public double getMaxAllowedReading() {
    
        return maxAllowedReading;
    }
    
    /**
     * @param maxAllowedReading the maxAllowedReading to set
     */
    public void setMaxAllowedReading(double maxAllowedReading) {
    
        this.maxAllowedReading = maxAllowedReading;
    }
    
    /**
     * @return the collectionRate
     */
    public long getCollectionRate() {
    
        return collectionRate;
    }
    
    /**
     * @param collectionRate the collectionRate to set
     */
    public void setCollectionRate(long collectionRate) {
    
        this.collectionRate = collectionRate;
    }
    
} // VitalSignConfiguration

package pl.edu.elka.ham.JRotorMap.Geography;

/**
 * Coordinate class.
 */
public class Coordinate {
    private int value_d;
    private double value_mm;

    /**
     * Default constructor, sets object to 0.
     */
    public Coordinate() {
        value_d = 0;
        value_mm = 0;
    }

    /**
     * Constructor initialised by double.
     * @param ddd degree in DDD format(no sign!)
     */
    public Coordinate(Double ddd) {
        value_d = ddd.intValue();
        value_mm = (ddd - ddd.intValue())*60;
    }

    /**
     * Constructor initialised in dmm format.
     * @param degrees degrees as integer
     * @param minutes minutes in double.
     */
    Coordinate(int degrees, double minutes) {
        value_d = degrees;
        value_mm = minutes;
    }

    /**
     * @return degrees, D part of DMM
     */
    public int getDegrees() {
        return value_d;
    }

    /**
     * @return minutes, MM part of DMM.
     */
    public double getMinutes() {
        return value_mm;
    }

    /**
     * @return degres in DDD format, one double only.
     */
    public double getDDD() {
        return value_d + value_mm/60;
    }
}

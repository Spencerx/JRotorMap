package pl.edu.elka.ham.JRotorMap.Geography;

/**
 * Created by erxyi on 09.01.17.
 */
public class Coordinate {
    private int value_d;
    private double value_mm;

    public Coordinate() {
        value_d = 0;
    }

    public Coordinate(Double ddd) {
        value_d = ddd.intValue();
        value_mm = (ddd - ddd.intValue())*60;
    }

    Coordinate(int degrees, double minutes) {
        value_d = degrees;
        value_mm = minutes;
    }

    public int getDegrees() {
        return value_d;
    }

    public double getMinutes() {
        return value_mm;
    }

    public double getDDD() {
        return value_d + value_mm/60;
    }
}

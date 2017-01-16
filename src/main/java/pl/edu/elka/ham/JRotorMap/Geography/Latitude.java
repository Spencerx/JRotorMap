package pl.edu.elka.ham.JRotorMap.Geography;

/**
 * Latitude class.
 */
public class Latitude extends Coordinate {
    public enum LatitudeDirections { North, South }

    private LatitudeDirections direction;

    public Latitude() {
        super();
        direction = LatitudeDirections.North;
    }

    public Latitude(double ddd, LatitudeDirections ld) {
        super(ddd);
        direction = ld;
    }

    public Latitude(int degrees, double mm, LatitudeDirections ld) {
        super(degrees, mm);
        direction = ld;
    }

    public LatitudeDirections getDirection() {
        return direction;
    }

    public String toString()
    {
        String d;
        if(direction == LatitudeDirections.North)
            d = "N";
        else
            d = "S";

        return Double.toString(super.getDDD()) + " " + d;
    }

    public double getSignDDD()
    {
        double d = super.getDDD();

        if(direction == Latitude.LatitudeDirections.South)
            d *= -1.0;

        return d;
    }
}

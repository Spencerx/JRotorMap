package pl.edu.elka.ham.JRotorMap.Geography;

/**
 * Latitude class.
 */
public class Latitude extends Coordinate {
    public enum LatitudeDirections { North, South }

    private LatitudeDirections direction;

    /**
     * Default constructor, sets latitude represented by object to 0.0N
     */
    public Latitude() {
        super();
        direction = LatitudeDirections.North;
    }

    /**
     * Constructor using DDD + direction formula.
     * @param ddd degrees in DDD format, not signDDD!
     * @param ld direction
     */
    public Latitude(double ddd, LatitudeDirections ld) {
        super(ddd);
        direction = ld;
    }

    /**
     * Constructor using DMM + direction formula.
     * @param degrees degrees
     * @param mm minutes
     * @param ld direction
     */
    public Latitude(int degrees, double mm, LatitudeDirections ld) {
        super(degrees, mm);
        direction = ld;
    }

    /**
     * @return direction from latitude object.
     */
    public LatitudeDirections getDirection() {
        return direction;
    }

    /**
     * @return stringified object.
     */
    public String toString()
    {
        String d;
        if(direction == LatitudeDirections.North)
            d = "N";
        else
            d = "S";

        return Double.toString(super.getDDD()) + " " + d;
    }

    /**
     * @return DDD with sign - south is represented by minus.
     */
    public double getSignDDD()
    {
        double d = super.getDDD();

        if(direction == Latitude.LatitudeDirections.South)
            d *= -1.0;

        return d;
    }
}

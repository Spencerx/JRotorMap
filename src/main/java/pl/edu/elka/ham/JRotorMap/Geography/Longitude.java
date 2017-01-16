package pl.edu.elka.ham.JRotorMap.Geography;

/**
 * Abstraction of Longitude.
 */
public class Longitude extends Coordinate {
    public enum LongitudeDirections {East, West }

    private LongitudeDirections direction;

    /**
     * Constructor - sets longitude to 0E
     */
    public Longitude() {
        super();
        direction = LongitudeDirections.East;
    }

    public Longitude(double ddd, LongitudeDirections ld) {
        super(ddd);
        direction = ld;
    }

    /**
     * Longitude constructor in DMM manner.
     * @param degrees Degrees, integer only.
     * @param mm Minutes
     * @param ld Direction
     */
    public Longitude(int degrees, double mm, LongitudeDirections ld) {
        super(degrees, mm);
        direction = ld;
    }

    /**
     * @return Longitude direction from instance.
     */
    public LongitudeDirections getDirection() {
        return direction;
    }

    /**
     * @return Longitude object as String.
     */
    public String toString() {
        String d;
        if (direction == LongitudeDirections.East)
            d = "E";
        else
            d = "W";

        return Double.toString(super.getDDD()) + " " + d;
    }

    /**
     * @return Longitude degrees in DDD format(one double), west is represented by negative numbers.
     */
    public double getSignDDD()
    {
        double d = super.getDDD();

        if(direction == LongitudeDirections.West)
            d *= -1.0;

        return d;
    }
}

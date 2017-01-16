package pl.edu.elka.ham.JRotorMap.Geography;

/**
 * Abstraction of result effect, passed between Model and View.
 */
public class Result {
    private double distance;
    private double azimuth;
    private Location destination;
    private String name;

    /**
     * Default constructor.
     * @param distance distance in km.
     * @param azimuth azimuth in degrees, clockwise from north.
     * @param destination location redundant info, other way of telling what point is described by another params.
     * @param name_ name, to be used in IOutput.
     */
    public Result(double distance, double azimuth, Location destination, String name_) {
        this.distance = distance;
        this.azimuth = azimuth;
        this.destination = destination;
        this.name = name_;
    }

    public Location getDestination() { return destination; }

    public double getAzimuth() {
        return azimuth;
    }

    public double getDistance() {
        return distance;
    }

    public String getName() { return name; }
}

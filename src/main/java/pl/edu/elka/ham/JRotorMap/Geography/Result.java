package pl.edu.elka.ham.JRotorMap.Geography;

/**
 * Created by erxyi on 11.01.17.
 */
public class Result {
    double distance;
    double azimuth;
    Location destination;
    String name;
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

package pl.edu.elka.ham.JRotorMap.Geography;

import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.WaypointRenderer;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * Location - pair of Longitude and latitude objects.
 */
public class Location {

    private Latitude    lat;
    private Longitude   lon;

    public Location() {
        lat = new Latitude();
        lon = new Longitude();
    }

    public Location(double latddd, Latitude.LatitudeDirections latd, double londdd, Longitude.LongitudeDirections lond) {
        lat = new Latitude(latddd, latd);
        lon = new Longitude(londdd, lond);
    }

    /**
     * Constructor which accepts string data.
     * @param input location in text form, to be parsed.
     * @throws RuntimeException Thrown if parsing fails.
     */
    public Location(String input) throws RuntimeException {
        double latitude, longitude;
        Latitude.LatitudeDirections latd;
        Longitude.LongitudeDirections lond;
        String cleaned = input.replaceAll("\\s", "");
        String[] locationElements = cleaned.split(",");
        if(locationElements.length != 2){
            throw new RuntimeException("Invalid argument, should be only one comma");
        }

        try
        {
            latitude = Double.parseDouble(locationElements[0]);
            longitude = Double.parseDouble(locationElements[1]);
        }
        catch(Exception e)
        {
            throw new RuntimeException("Cannot convert values to doubles");
        }

        if(latitude>=0)
            latd = Latitude.LatitudeDirections.North;
        else
            latd = Latitude.LatitudeDirections.South;

        if(longitude>=0)
            lond = Longitude.LongitudeDirections.East;
        else
            lond = Longitude.LongitudeDirections.West;

        latitude = Math.abs(latitude);
        longitude = Math.abs(longitude);

        lat = new Latitude(latitude, latd);
        lon = new Longitude(longitude, lond);

    }

    public Latitude getLatitude() {
        return lat;
    }

    public Longitude getLongitude() {
        return lon;
    }

    /**
     * @return Returns Location in format usable by jxmapviewer2.
     */
    public GeoPosition getGeoPosition() {

        double latitude = lat.getSignDDD();
        double longitude = lon.getSignDDD();
        return new GeoPosition(latitude, longitude);
    }

    public String exportString()
    {
        DecimalFormat df = new DecimalFormat("#.######", new DecimalFormatSymbols(Locale.US));
        StringBuilder sb = new StringBuilder(30);

        double latitude = lat.getSignDDD();
        double longitude = lon.getSignDDD();

        sb.append(latitude);
        sb.append(", ");
        sb.append(longitude);
        return sb.toString();
    }



}

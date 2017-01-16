package pl.edu.elka.ham.JRotorMap.Internal;

import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.GeoPosition;
import pl.edu.elka.ham.JRotorMap.Geography.Location;

import java.awt.*;

/**
 * Created by erxyi on 12.01.17.
 */
public class MapPoint extends DefaultWaypoint {
    private final String label;
    private final Color color;


    public MapPoint(String label, Color color, Location loc)
    {
        super(loc.getGeoPosition());
        this.label = label;
        this.color = color;
    }

    public String getLabel() {
        return label;
    }

    public Color getColor() {
        return color;
    }
}

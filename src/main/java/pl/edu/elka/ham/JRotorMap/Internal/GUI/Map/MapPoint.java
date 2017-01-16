package pl.edu.elka.ham.JRotorMap.Internal.GUI.Map;

import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.GeoPosition;
import pl.edu.elka.ham.JRotorMap.Geography.Location;

import java.awt.*;

/**
 * Extension of JXMapViewer normal point, in order to show colorful map points.
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

    String getLabel() {
        return label;
    }

    Color getColor() {
        return color;
    }
}

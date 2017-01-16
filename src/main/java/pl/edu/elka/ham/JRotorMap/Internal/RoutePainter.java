package pl.edu.elka.ham.JRotorMap.Internal;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.GeoPosition;
import pl.edu.elka.ham.JRotorMap.Geography.Location;
import org.jxmapviewer.painter.Painter;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.*;
import java.util.List;

/**
 * Created by erxyi on 12.01.17.
 */
public class RoutePainter implements Painter<JXMapViewer> {
    private Color color = Color.RED;
    private boolean antiAliasing = true;

    private Set<GeoPosition> track;

    public RoutePainter(Set<MapPoint> track)
    {
        this.track = new HashSet<GeoPosition>();
        updateTrackFromSet(track);

    }

    public void updateTrackFromSet(Set<MapPoint> track) {
        for(MapPoint t : track)
            this.track.add(t.getPosition());
    }

    public void paint(Graphics2D g, JXMapViewer map, int width, int height) {
        g = (Graphics2D) g.create();

        Rectangle rect = map.getViewportBounds();
        g.translate(-rect.x, -rect.y);

        if (antiAliasing)
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g.setColor(Color.black);
        g.setStroke(new BasicStroke(4));

        drawRoute(g, map);

        g.setColor(color);
        g.setStroke(new BasicStroke(2));

        drawRoute(g, map);

        g.dispose();
    }

    private void drawRoute(Graphics2D g, JXMapViewer map)
    {
        int lastX = 0;
        int lastY = 0;
        boolean first = true;

        for(GeoPosition gp : track)
        {
            Point2D point = map.getTileFactory().geoToPixel(gp, map.getZoom());
            if(first)
                first = false;
            else
                g.drawLine(lastX, lastY, (int)point.getX(), (int)point.getY());

            lastX = (int)point.getX();
            lastY = (int)point.getY();
        }
    }

    public void updateTrack(Set<MapPoint> track)
    {
        this.track.clear();
        updateTrackFromSet(track);
    }

}

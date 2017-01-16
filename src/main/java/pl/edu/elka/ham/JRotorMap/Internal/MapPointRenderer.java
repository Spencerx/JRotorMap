package pl.edu.elka.ham.JRotorMap.Internal;

import org.jxmapviewer.JXMapViewer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.nio.Buffer;
import java.nio.BufferOverflowException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by erxyi on 12.01.17.
 */
public class MapPointRenderer implements org.jxmapviewer.viewer.WaypointRenderer<MapPoint> {

    private final Map<Color, BufferedImage> map = new HashMap<Color, BufferedImage>();
    private BufferedImage originalImage;


    private BufferedImage convert(BufferedImage loadImg, Color newColor)
    {
        int w = loadImg.getWidth();
        int h = loadImg.getHeight();
        BufferedImage imgOut = new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB);
        BufferedImage imgColor = new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB);

        Graphics2D g = imgColor.createGraphics();
        g.setColor(newColor);
        g.fillRect(0,0,w+1,h+1);
        g.dispose();

        g = imgOut.createGraphics();
        g.drawImage(loadImg, 0,0, null);
        g.setComposite(MultiplyComposite.Default);
        g.drawImage(imgColor, 0,0, null);
        g.dispose();

        return imgOut;
    }

    public MapPointRenderer()
    {
        URL resource = getClass().getClassLoader().getResource("waypoint_white.png");
        try
        {
            originalImage = ImageIO.read(resource);
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }

    public void paintWaypoint(Graphics2D graphics2D, JXMapViewer jxMapViewer, MapPoint mapPoint) {


        graphics2D = (Graphics2D)graphics2D.create();

        if(originalImage == null)
            return;

        BufferedImage myImage = map.get(mapPoint.getColor());
        if(myImage == null)
        {
            myImage = convert(originalImage, mapPoint.getColor());
            map.put(mapPoint.getColor(), myImage);
        }

        Point2D point = jxMapViewer.getTileFactory().geoToPixel(mapPoint.getPosition(), jxMapViewer.getZoom());
        int x = (int) point.getX();
        int y = (int) point.getY();

        graphics2D.drawImage(myImage, x-myImage.getWidth()/2, y-myImage.getHeight()/2, null);

        String label = mapPoint.getLabel();
        FontMetrics fm = graphics2D.getFontMetrics();
        int tw = fm.stringWidth(label);
        int th = 1 + fm.getAscent();

        graphics2D.drawString(label, x - tw/2, y + th - myImage.getHeight());

        graphics2D.dispose();
    }

}

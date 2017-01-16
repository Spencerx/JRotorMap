package pl.edu.elka.ham.JRotorMap;

import net.sf.geographiclib.Geodesic;
import net.sf.geographiclib.GeodesicData;
import pl.edu.elka.ham.JRotorMap.Geography.Location;
import pl.edu.elka.ham.JRotorMap.Geography.Result;
import pl.edu.elka.ham.JRotorMap.Input.IQuery;
import pl.edu.elka.ham.JRotorMap.Internal.Settings;
import pl.edu.elka.ham.JRotorMap.Output.DumpToFile;
import pl.edu.elka.ham.JRotorMap.Output.IOutput;

/**
 * Model - manages information about last Result object, makes calculation and saves it if desired.
 */
public class Model extends java.util.Observable {

    private Location queriedLocation;
    private Settings settings;
    private Result lastResult;

    /**
     * Default constructor.
     * @param s Settings object.
     */
    public Model(Settings s)
    {
        settings = s;
    }

    /**
     * Process a query - gets latitude/longitude from param and notifies all observers about result.
     * @param inputQuery object which implements IQuery interface with data to process.
     */
    void processQuery(IQuery inputQuery)
    {
        queriedLocation = inputQuery.getFoundLocation();
       // System.out.println("Found:" + queriedLocation.exportString());
        double azimuth;

        GeodesicData g = Geodesic.WGS84.Inverse(settings.getUserLocation().getLatitude().getSignDDD(),
                settings.getUserLocation().getLongitude().getSignDDD(),
                queriedLocation.getLatitude().getSignDDD(),
                queriedLocation.getLongitude().getSignDDD());
        azimuth = g.azi1;
        if(azimuth < 0)
            azimuth = 360.0 + azimuth;
        Result result = new Result(g.s12/1000.0,azimuth, queriedLocation, inputQuery.getQueryString());
        setChanged();
        lastResult = result;
        notifyObservers(result);
    }

    /**
     * Sets last target as new home and updates the internal Settings object.
     * @see Settings
     */
    void setTargetAsHome()
    {
        settings.setUserLocation(queriedLocation);
        setChanged();
        notifyObservers(lastResult);
    }


    /**
     * Saves last Result(stored privately in object) into file.
     * @param path Filepath.
     */
    void saveToFile(String path)
    {
        IOutput io = new DumpToFile(path);
        io.saveOutput(lastResult);
    }
}

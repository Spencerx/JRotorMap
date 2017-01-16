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
 * Created by erxyi on 10.01.17.
 */
public class Model extends java.util.Observable {

    Location queriedLocation;
    Settings settings;
    Result lastResult;

    public Model(Settings s)
    {
        settings = s;
    }

    public void processQuery(IQuery inputQuery)
    {
        queriedLocation = inputQuery.getFoundLocation();
        System.out.println("Found:" + queriedLocation.exportString());
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

    public void setTargetAsHome()
    {
        settings.setUserLocation(queriedLocation);
        setChanged();
        notifyObservers(lastResult);
    }

    public void saveToFile(String path)
    {
        IOutput io = new DumpToFile(path);
        io.saveOutput(lastResult);
    }
}

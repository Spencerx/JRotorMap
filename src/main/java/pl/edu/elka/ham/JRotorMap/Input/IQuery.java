package pl.edu.elka.ham.JRotorMap.Input;

import pl.edu.elka.ham.JRotorMap.Geography.Location;

/**
 * Created by erxyi on 12.01.17.
 */
public interface IQuery {

    public Location getFoundLocation();
    public String getQueryString();
}

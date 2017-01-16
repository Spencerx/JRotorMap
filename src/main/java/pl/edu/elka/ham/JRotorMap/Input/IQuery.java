package pl.edu.elka.ham.JRotorMap.Input;

import pl.edu.elka.ham.JRotorMap.Geography.Location;

/**
 * Interface of query services.
 */
public interface IQuery {

    /**
     * @return Location returned by service.
     */
    Location getFoundLocation();

    /**
     * @return Input string - method is used later, i.e. in IOutput, to distinguish places in file by name.
     */
    String getQueryString();
}

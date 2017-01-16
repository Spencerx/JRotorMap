package pl.edu.elka.ham.JRotorMap.Input;

import pl.edu.elka.ham.JRotorMap.Geography.Location;

/**
 * Query service where user selects location by hand, inserting latitude and longitude.
 */
public class ManualQuery implements IQuery {
    private Location loc;
    private String query;

    /**
     * Default constructor.
     * @param input Latitude and longitude entered by user.
     * @throws RuntimeException If user was a bad guy and forgot about something(i.e. comma).
     */
    public ManualQuery(String input)
    {
        query = input;
     try {
         loc = new Location(input);
     }
     catch (Exception e)
     {
         throw new RuntimeException("There's problem with your manual input:" + e);
     }
    }

    /**
     * @return query string from constructor.
     */
    @Override
    public String getQueryString() {
        return query;
    }

    /**
     * @return location from input string.
     */
    @Override
    public Location getFoundLocation()
    {
        return loc;
    }
}

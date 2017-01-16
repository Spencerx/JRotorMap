package pl.edu.elka.ham.JRotorMap.Input;

import pl.edu.elka.ham.JRotorMap.Geography.Location;

/**
 * Created by erxyi on 12.01.17.
 */
public class ManualQuery implements IQuery {
    Location loc;
    String query;
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

    @Override
    public String getQueryString() {
        return query;
    }

    public Location getFoundLocation()
    {
        return loc;
    }
}

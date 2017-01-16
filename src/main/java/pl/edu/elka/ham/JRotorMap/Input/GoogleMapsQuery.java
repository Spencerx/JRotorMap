package pl.edu.elka.ham.JRotorMap.Input;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.QueryAutocompleteRequest;
import com.google.maps.errors.InvalidRequestException;
import com.google.maps.model.GeocodingResult;
import pl.edu.elka.ham.JRotorMap.Geography.Location;

/**
 * Adapter to GoogleMaps geocoding services.
 */
public class GoogleMapsQuery implements IQuery {
    private Location location;
    private String lastQuery;

    /**
     * Default constructor.
     * @param query query entered by user.
     * @param api_key api key from configuration file.
     * @throws RuntimeException thrown if there's something wrong(bad api key, location not found, etc.)
     */
    public GoogleMapsQuery(String query, String api_key) throws RuntimeException {
        GeoApiContext context = new GeoApiContext().setApiKey(api_key);
        lastQuery = query;
        try
        {
            GeocodingResult[] results = GeocodingApi.geocode(context, query).await();
            location = new Location(results[0].geometry.location.toString());

        }
        catch(InvalidRequestException ire)
        {
            throw new RuntimeException("Your API key for GoogleMaps is possibly wrong or there's other problem with Google Maps:"+ire);
        }
        catch(ArrayIndexOutOfBoundsException ex)
        {
            throw new RuntimeException("There's no place like your query - Google Maps haven't returned any results");
        }
        catch(Exception e)
        {
            throw new RuntimeException("GoogleMaps general exception:" + e);
        }
    }

    /**
     * @return Location from processed user query.
     */
    @Override
    public Location getFoundLocation() {
        return location;
    }

    /**
     * @return query entered by user.
     */
    @Override
    public String getQueryString() {
        return lastQuery;
    }
}

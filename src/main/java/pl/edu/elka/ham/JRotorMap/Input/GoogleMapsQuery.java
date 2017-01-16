package pl.edu.elka.ham.JRotorMap.Input;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.QueryAutocompleteRequest;
import com.google.maps.errors.InvalidRequestException;
import com.google.maps.model.GeocodingResult;
import pl.edu.elka.ham.JRotorMap.Geography.Location;

/**
 * Created by erxyi on 12.01.17.
 */
public class GoogleMapsQuery implements IQuery {
    Location location;
    String lastQuery;
    public GoogleMapsQuery(String query, String api_key) {
        GeoApiContext context = new GeoApiContext().setApiKey(api_key);
        lastQuery = query;
        double latitude;
        double longitude;
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
    public Location getFoundLocation() {
        return location;
    }

    public String getQueryString() {
        return lastQuery;
    }
}

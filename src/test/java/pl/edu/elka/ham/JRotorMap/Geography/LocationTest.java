package pl.edu.elka.ham.JRotorMap.Geography;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
/**
 * Created by erxyi on 11.01.17.
 */
class LocationTest {
    @Test
    void exportString() {
        Location positive = new Location(32.1,
                Latitude.LatitudeDirections.North,
                51.3,
                Longitude.LongitudeDirections.East);

        Location negative = new Location(32.1,
                Latitude.LatitudeDirections.South,
                51.3,
                Longitude.LongitudeDirections.West);

        assertEquals(positive.exportString(), "32.1, 51.3");
        assertEquals(negative.exportString(), "-32.1, -51.3");

    }

    @org.junit.jupiter.api.Test
    void getLatitude() {
        try {
            Location positive = new Location("12.421,12.151");
            Location negative = new Location("-12.31, -53.12");

            assertEquals(positive.getLatitude().getDirection(), Latitude.LatitudeDirections.North);
            assertEquals(positive.getLatitude().getDDD(), 12.421);
            assertEquals(negative.getLatitude().getDirection(), Latitude.LatitudeDirections.South);
            assertEquals(negative.getLatitude().getDDD(), 12.31);
        }
        catch(Exception e)
        {
            fail("Exception thrown");
        }
    }

    @org.junit.jupiter.api.Test
    void getLongitude() {
        try {
            Location positive = new Location("12.421,12.151");
            Location negative = new Location("-12.31, -53.12");

            assertEquals(positive.getLongitude().getDirection(), Longitude.LongitudeDirections.East);
            assertEquals(positive.getLongitude().getDDD(), 12.151);
            assertEquals(negative.getLongitude().getDirection(), Longitude.LongitudeDirections.West);
            assertEquals(negative.getLongitude().getDDD(), 53.12);
        }
        catch(Exception e)
        {
            fail("Exception thrown");
        }
    }

    @org.junit.jupiter.api.Test()
    void constructorException() {
        try {
            Location failed = new Location(",,,");
            fail("It should fail");
        }
        catch(Exception e)
        {
        }

    }


}
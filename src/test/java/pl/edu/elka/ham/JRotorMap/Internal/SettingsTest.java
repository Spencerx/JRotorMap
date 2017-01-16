package pl.edu.elka.ham.JRotorMap.Internal;

import org.junit.jupiter.api.Test;
import pl.edu.elka.ham.JRotorMap.Geography.Location;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by erxyi on 11.01.17.
 */
class SettingsTest {
    @Test
    void DoesSettingWorks() {

        Path p = FileSystems.getDefault().getPath("test.prop");
        System.out.println(p.toAbsolutePath().toString());
        try {
            Files.delete(p);
        }
        catch(NoSuchFileException e) {}
        catch(IOException e) {fail("Other problems");}


        try {
            Settings s = new Settings();
            s.setFilePath(p.toString());
            s.setGoogleMapsApiKey("gmapi");
            s.setQrzApiKey("qrzkey");
            s.setUserLocation(new Location("-32.12, -42.51"));
            s.close();

            Settings s2 = new Settings(p.toAbsolutePath().toString());
            assertEquals("gmapi", s2.getGoogleMapsApiKey());
            assertEquals("qrzkey", s2.getQrzApiKey());
            assertEquals("-32.12, -42.51", s.getUserLocation().exportString());
        }
        catch(FileNotFoundException e)
        {

        }
        catch(Exception e)
        {
            fail("Settings thrown exception");
        }
    }

}
package pl.edu.elka.ham.JRotorMap.Internal;

import pl.edu.elka.ham.JRotorMap.Geography.Location;

import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Properties;

/**
 * Created by erxyi on 11.01.17.
 */
public class Settings implements Runnable {
    Location userLocation;
    String googleMapsApiKey;
    String qrzApiKey;
    String filePath;
    Properties properties;

    public Settings()
    {
        properties = new Properties();
    }
    public Settings(String filepath) throws FileNotFoundException
    {
        properties = new Properties();
        loadFile(filepath);
    }

    public void loadFile(String filepath) throws FileNotFoundException {

        try
        {
            filePath = filepath;
            FileInputStream inputStream = new FileInputStream(filepath);
            properties.load(inputStream);
            inputStream.close();

            googleMapsApiKey = properties.getProperty("GoogleMapsAPIKey");
            qrzApiKey = properties.getProperty("QRZAPIKey");
            userLocation = new Location(properties.getProperty("userLocation"));
        }
        catch(FileNotFoundException e)
        {
                throw e;
        }
        catch(Exception e)
        {
            System.out.println("Exception:" + e);
        }
    }

    public void run()
    {
        close();
    }

    public void close()
    {
        try
        {
            FileOutputStream outputStream = new FileOutputStream(filePath);
            properties.store(outputStream, "--- jRotorMap config file - edit with caution! ---");
            outputStream.flush();
            outputStream.close();
        }
        catch(Exception e)
        {
            System.out.println("Exception:"+ e);
        }
    }

    public Location getUserLocation() {
        return userLocation;
    }

    public String getQrzApiKey() {
        return qrzApiKey;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getGoogleMapsApiKey() {
        return googleMapsApiKey;
    }

    public void setGoogleMapsApiKey(String googleMapsApiKey) {
        this.googleMapsApiKey = googleMapsApiKey;
        properties.setProperty("GoogleMapsAPIKey", googleMapsApiKey);
    }

    public void setQrzApiKey(String qrzApiKey) {
        this.qrzApiKey = qrzApiKey;
        properties.setProperty("QRZAPIKey", qrzApiKey);
    }

    public void setUserLocation(Location userLocation) {
        this.userLocation = userLocation;
        properties.setProperty("userLocation", userLocation.exportString());
    }


}

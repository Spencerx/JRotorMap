package pl.edu.elka.ham.JRotorMap.Internal;

import pl.edu.elka.ham.JRotorMap.Geography.Location;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Properties;

/**
 * Settings object - high-level interface of Properties,
 * implements runnable in order to gain ability to be an shutdown hook(last chance to update file on disk).
 * @see Properties
 */
public class Settings implements Runnable {
    private Location userLocation;
    private String googleMapsApiKey;
    private String qrzApiKey;
    private String filePath;
    private Properties properties;

    /**
     * Default constructor - used if there's no configuration file on disk.
     */
    public Settings()
    {
        properties = new Properties();
    }

    /**
     * @param filepath Path to file on disk.
     * @throws FileNotFoundException Thrown if file provided by param isn't available.
     */
    public Settings(String filepath) throws FileNotFoundException
    {
        properties = new Properties();
        loadFile(filepath);
    }

    private void loadFile(String filepath) throws FileNotFoundException {

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

    /**
     * Overrides run from Runnable, required for object to work as shutdown hook.
     */
    @Override
    public void run()
    {
        close();
    }

    /**
     *  Closes file - writes to disk and flushes it.
     */
    void close()
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

    /**
     * @return Returns user location from file.
     */
    public Location getUserLocation() {
        return userLocation;
    }

    /**
     * @return QRZ.com api key.
     * @deprecated
     */
    public String getQrzApiKey() {
        return qrzApiKey;
    }

    /**
     * @param filePath Place where Properties file will be stored.
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * @return Gets GoogleMaps api key from disk.
     */
    public String getGoogleMapsApiKey() {
        return googleMapsApiKey;
    }

    /**
     * @param googleMapsApiKey Updates GoogleMaps API key inside class to param.
     */
    public void setGoogleMapsApiKey(String googleMapsApiKey) {
        this.googleMapsApiKey = googleMapsApiKey;
        properties.setProperty("GoogleMapsAPIKey", googleMapsApiKey);
    }

    /**
     * @param qrzApiKey api Key to be stored.
     * @deprecated
     */
    public void setQrzApiKey(String qrzApiKey) {
        this.qrzApiKey = qrzApiKey;
        properties.setProperty("QRZAPIKey", qrzApiKey);
    }

    /**
     * @param userLocation set user location in settings class to param.
     */
    public void setUserLocation(Location userLocation) {
        this.userLocation = userLocation;
        properties.setProperty("userLocation", userLocation.exportString());
    }


}

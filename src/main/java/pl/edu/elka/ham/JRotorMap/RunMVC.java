package pl.edu.elka.ham.JRotorMap;

import pl.edu.elka.ham.JRotorMap.Geography.Location;
import pl.edu.elka.ham.JRotorMap.Internal.Settings;

import java.io.FileNotFoundException;
import static javax.swing.JOptionPane.showMessageDialog;
import java.nio.file.FileSystems;
import java.nio.file.Path;

/**
 * Created by erxyi on 10.01.17.
 */
public class RunMVC {
    public RunMVC() {
        String configFilepath = "jRotor.properties";
        Settings settings;
        try
        {
            settings = new Settings(configFilepath);
        }
        catch(FileNotFoundException e)
        {
            showMessageDialog(null, "Config file not found, so I will create one.");
            settings = new Settings();
            settings.setFilePath(configFilepath);
            settings.setQrzApiKey("CHANGEME");
            settings.setGoogleMapsApiKey("CHANGEME");
            settings.setUserLocation(new Location("0.0, 0.0"));
        }

        Runtime.getRuntime().addShutdownHook(new Thread(settings));

        Model model = new Model(settings);
        View view = new View(settings);

        model.addObserver(view);

        Controller controller = new Controller(settings);
        controller.addModel(model);
        controller.addView(view);
        view.addController(controller);
    }

}

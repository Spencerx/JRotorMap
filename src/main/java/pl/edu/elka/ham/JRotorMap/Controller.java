package pl.edu.elka.ham.JRotorMap;

import pl.edu.elka.ham.JRotorMap.Input.GoogleMapsQuery;
import pl.edu.elka.ham.JRotorMap.Input.HamQTHQuery;
import pl.edu.elka.ham.JRotorMap.Input.ManualQuery;
import pl.edu.elka.ham.JRotorMap.Internal.ButtonType;
import pl.edu.elka.ham.JRotorMap.Internal.Settings;

import javax.swing.*;

/**
 * Created by erxyi on 10.01.17.
 */
public class Controller implements java.awt.event.ActionListener {
    Model model;
    View view;
    Settings settings;

    Controller(Settings s) {
        settings = s;
    }

    public void actionPerformed(java.awt.event.ActionEvent e) {
        if(e.getSource() instanceof JButton)
        {
            try {
                JButton clicked = (JButton) e.getSource();
                if (clicked.getName().equals(ButtonType.RESET_MAP.getName()))
                    view.resetMap();
                else if (clicked.getName().equals(ButtonType.M_QUERY.getName()))
                    model.processQuery(new ManualQuery(view.getManualQueryString()));
                else if (clicked.getName().equals(ButtonType.GM_QUERY.getName()))
                    model.processQuery(new GoogleMapsQuery(view.getGoogleMapsQueryString(), settings.getGoogleMapsApiKey()));
                else if (clicked.getName().equals(ButtonType.QRZ_QUERY.getName()))
                    model.processQuery(new HamQTHQuery(view.getHamQTHQueryString()));
                else if(clicked.getName().equals(ButtonType.NEW_HOME.getName()))
                    model.setTargetAsHome();
                else if(clicked.getName().equals(ButtonType.SAVE_TO_FILE.getName()))
                    model.saveToFile(e.getActionCommand());
            }
            catch(Exception ex)
            {
                view.showError(ex.getMessage());
            }
        }
    }

    public void addModel(Model m) {
        this.model = m;
    }

    public void addView(View v) {
        this.view = v;
    }
}

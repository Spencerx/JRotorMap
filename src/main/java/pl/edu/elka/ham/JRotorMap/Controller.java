package pl.edu.elka.ham.JRotorMap;

import pl.edu.elka.ham.JRotorMap.Input.GoogleMapsQuery;
import pl.edu.elka.ham.JRotorMap.Input.HamQTHQuery;
import pl.edu.elka.ham.JRotorMap.Input.ManualQuery;
import pl.edu.elka.ham.JRotorMap.Internal.GUI.ButtonType;
import pl.edu.elka.ham.JRotorMap.Internal.Settings;

import javax.swing.*;

/**
 * Controller part of MVC pattern used in this project, responsible for maintaining ActionListener events.
 */
public class Controller implements java.awt.event.ActionListener {
    private Model model;
    private View view;
    private Settings settings;


    /**
     * A constructor.
     * @param s a Settings object, created in main thread.
     */
    public Controller(Settings s) {
        settings = s;
    }

    /**
     * Performs an action when occured - usually calls @Model.
     * @param e Action event from view to be processed.
     * @see View
     */
    public void actionPerformed(java.awt.event.ActionEvent e) {
        if(e.getSource() instanceof JButton)
        {
            try {
                JButton clicked = (JButton) e.getSource();
                if (clicked.getName().equals(ButtonType.RESET_MAP.getName()))
                    view.redrawMap();
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

    /**
     *  Adds reference to model.
     *  @param m Model object.
     *  @see Model
     *  @see RunMVC
     */
    void addModel(Model m) {
        this.model = m;
    }

    /**
     *  Adds reference to view.
     * @param v View object.
     * @see View
     * @see RunMVC
     */
    void addView(View v) {
        this.view = v;
    }
}

package pl.edu.elka.ham.JRotorMap;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.VirtualEarthTileFactoryInfo;
import org.jxmapviewer.input.*;
import org.jxmapviewer.painter.CompoundPainter;
import org.jxmapviewer.viewer.*;
import pl.edu.elka.ham.JRotorMap.Geography.Result;
import pl.edu.elka.ham.JRotorMap.Internal.*;

/**
 * Created by erxyi on 10.01.17.
 */
class View implements java.util.Observer, ItemListener, ActionListener {


    /*
        Input cards, qrz query card
     */
    final static String HAMQTHPANEL = "HamQTH.com query";
    final static String GMPANEL = "Google Maps query";
    final static String MPANEL = "Manual method";

    ActionListener fileActionListener;

    JTextField hamQTHQuery;
    JButton hamQTHQueryButton;

    JTextField gmQuery;
    JButton gmQueryButton;

    JTextField mQuery;
    JButton mQueryButton;
    JButton resetMapButton;

    JPanel inputCards;

    JXMapViewer mapViewer;
    JButton newHomeButton;

    JTextField answerAzimuth;
    JTextField answerDistance;
    JButton answerSaveToFileButton;

    JTextField answerLatitude;
    JTextField answerLongitude;

    Settings settings;

    Set<MapPoint> waypoints = new HashSet<MapPoint>();
    private final WaypointPainter<MapPoint> waypointPainter;
    private final RoutePainter routePainter;
    private final JFrame frame;

    View(Settings s) {
        settings = s;
        frame = new JFrame("JRotorMap");

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputCards = new JPanel(new CardLayout());
        JPanel mapPanel = new JPanel(new BorderLayout());
        JPanel outputPanel = new JPanel(new BorderLayout());

      //  mapPanel.add(new JLabel("mapPanel"));
        outputPanel.add(new JLabel("OutputPanel"));

        frame.add("West", inputPanel);
        frame.add("Center", mapPanel);
        frame.add("East", outputPanel);


        /*
            Cards at all
         */
        String[] comboBoxItems = {HAMQTHPANEL, GMPANEL, MPANEL };

        JComboBox cb = new JComboBox(comboBoxItems);
        cb.setName("QuerySelect");
        cb.setEditable(false);
        cb.addItemListener(this);

        /*
                Input cards
         */

        hamQTHQueryButton = new JButton();
        gmQueryButton = new JButton();
        mQueryButton = new JButton();
        hamQTHQuery = new JTextField();
        gmQuery = new JTextField();
        mQuery = new JTextField();
        JPanel QRZPanel = generateCard(hamQTHQuery, hamQTHQueryButton, "Query HamQTHQuery!", "Callsign:", "<html>Location will be <br> scraped from hamqth.com<br> and it's really inaccurate.</html>");
        JPanel GMPanel = generateCard(gmQuery, gmQueryButton, "Query GMaps!", "Location:", "<html>Google is clever enough to<br> find many places. Try!</html>");
        JPanel MPanel = generateCard(mQuery, mQueryButton, "Calculate", "Enter coordinates(lat, lon):","<html>Example: -34.12, +42.123<br>Directions are distinguished by sign<br>and separated by comma.</html>");

        inputCards.add(HAMQTHPANEL, QRZPanel);
        inputCards.add(GMPANEL, GMPanel);
        inputCards.add(MPANEL, MPanel);
        inputPanel.add(cb, BorderLayout.PAGE_START);
        inputPanel.add(inputCards, BorderLayout.CENTER);

        /*
            Map panel
         */
        JPanel mapContainer = new JPanel(new BorderLayout());
        JPanel mapButtons = new JPanel();

        newHomeButton = new JButton("Target -> home");
        resetMapButton = new JButton("Reset map");
        newHomeButton.setEnabled(false);

        mapViewer = new JXMapViewer();
            TileFactoryInfo info = new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.MAP);
        //TileFactoryInfo info = new OSMTileFactoryInfo();
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);

        File cacheDir = new File("jRotorMap.jxmapviewer2");
        LocalResponseCache.installResponseCache(info.getBaseURL(), cacheDir, false);
        MouseInputListener mia = new PanMouseInputListener(mapViewer);
        mapViewer.addMouseListener(mia);
        mapViewer.addMouseMotionListener(mia);
        mapViewer.addMouseListener(new CenterMapListener(mapViewer));
        mapViewer.addMouseWheelListener(new ZoomMouseWheelListenerCursor(mapViewer));
        mapViewer.addKeyListener(new PanKeyListener(mapViewer));

        mapViewer.setTileFactory(tileFactory);
        tileFactory.setThreadPoolSize(8);
        resetWaypoints();
        waypointPainter = new WaypointPainter<MapPoint>();
        waypointPainter.setWaypoints(waypoints);
        waypointPainter.setRenderer(new MapPointRenderer());

        routePainter = new RoutePainter(waypoints);

        CompoundPainter<JXMapViewer> painter = new CompoundPainter<JXMapViewer>();
        painter.addPainter(routePainter);
        painter.addPainter(waypointPainter);

        mapViewer.setOverlayPainter(painter);

        mapViewer.setAddressLocation(settings.getUserLocation().getGeoPosition());
        mapViewer.setZoom(7);
        //mapContainer.add(Box.createRigidArea(new Dimension(0, 5)));
        mapContainer.add(mapViewer);
        mapButtons.add(resetMapButton);
        mapButtons.add(newHomeButton);
        mapPanel.add(mapContainer, BorderLayout.CENTER);
        mapPanel.add(mapButtons, BorderLayout.SOUTH);

        /*
            Output Panel
         */
        JPanel resultPanel = new JPanel();
        JPanel logPanel = new JPanel();
        answerAzimuth = new JTextField();
        resizeTextField(answerAzimuth);
        answerDistance = new JTextField();
        resizeTextField(answerDistance);
        answerLatitude = new JTextField();
        resizeTextField(answerLatitude);
        answerLongitude = new JTextField();
        resizeTextField(answerLongitude);
        answerAzimuth.setEditable(false);
        answerDistance.setEditable(false);
        answerLatitude.setEditable(false);
        answerLongitude.setEditable(false);

        answerSaveToFileButton = new JButton("Save to file");
        answerSaveToFileButton.setEnabled(false);
        resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));

        resultPanel.add(new JLabel("Calculated distance:"));
        resultPanel.add(answerDistance);
        JLabel resultLabel = new JLabel("Calculated azimuth:");
        resultPanel.add(resultLabel);
        resultPanel.add(answerAzimuth);
        resultPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        resultPanel.add(answerSaveToFileButton);
        resultPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        resultPanel.add(new JLabel("Selected point latitude:"));
        resultPanel.add(answerLatitude);
        resultPanel.add(new JLabel("Selected point longitude:"));
        resultPanel.add(answerLongitude);

        outputPanel.add(new JLabel("Your results:"), BorderLayout.NORTH);
        outputPanel.add(resultPanel, BorderLayout.CENTER);

    //     And menubar!
    //    JMenuBar menuBar = new JMenuBar();
    //    JMenu menuOptions = new JMenu("Options");
    //    menuBar.add(menuOptions);
    //    menuOptions.add("Settings");
    //    menuOptions.addSeparator();
    //    menuOptions.add("Quit");
    //    frame.setJMenuBar(menuBar);
        // Setting names to easily
        hamQTHQueryButton.setName(ButtonType.QRZ_QUERY.getName());
        mQueryButton.setName(ButtonType.M_QUERY.getName());
        gmQueryButton.setName(ButtonType.GM_QUERY.getName());
        newHomeButton.setName(ButtonType.NEW_HOME.getName());
        answerSaveToFileButton.setName(ButtonType.SAVE_TO_FILE.getName());
        resetMapButton.setName(ButtonType.RESET_MAP.getName());

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 500);
        frame.setVisible(true);
        frame.setResizable(false);
    }

    private void resetWaypoints() {
        waypoints.clear();
        waypoints.add(new MapPoint("You", Color.GREEN, settings.getUserLocation()));
    }

    private JPanel generateCard(JTextField queryField, JButton queryFieldButton, String buttonText, String labelText, String commentText) {
        JPanel QRZPanel = new JPanel();
        QRZPanel.setLayout(new BoxLayout(QRZPanel, BoxLayout.Y_AXIS));
        //queryField = new JTextField(12);
        resizeTextField(queryField);
        //queryFieldButton = new JButton(buttonText);
        queryFieldButton.setText(buttonText);
        QRZPanel.add(new JLabel(labelText)) ;
        QRZPanel.add(Box.createRigidArea(new Dimension(0,5)));
        QRZPanel.add(queryField);
        QRZPanel.add(Box.createRigidArea(new Dimension(0,5)));
        QRZPanel.add(queryFieldButton);
        QRZPanel.add(Box.createRigidArea(new Dimension(0,5)));
        QRZPanel.add(new JLabel(commentText));
        return QRZPanel;
    }

    private void resizeTextField(JTextField queryField) {
        queryField.setMaximumSize(new Dimension(Integer.MAX_VALUE, queryField.getPreferredSize().height));
    }

    public void update(Observable obs, Object obj) {
        Result result = (Result)obj;
        DecimalFormat df = new DecimalFormat("#.###", new DecimalFormatSymbols(Locale.US));

        newHomeButton.setEnabled(true);
        answerSaveToFileButton.setEnabled(true);
        answerLongitude.setText(result.getDestination().getLongitude().toString());
        answerLatitude.setText(result.getDestination().getLatitude().toString());
        answerAzimuth.setText(df.format(result.getAzimuth()) + " degrees");
        answerDistance.setText(df.format(result.getDistance()) + " km");
        resetWaypoints();
        waypoints.add(new MapPoint("Target", Color.ORANGE, result.getDestination()));
        waypointPainter.setWaypoints(waypoints);
        routePainter.updateTrack(waypoints);
        resetMap();
    }

    public void addController(ActionListener c) {
        newHomeButton.addActionListener(c);
        resetMapButton.addActionListener(c);
        answerSaveToFileButton.addActionListener(this);

        hamQTHQueryButton.addActionListener(c);
        gmQueryButton.addActionListener(c);
        mQueryButton.addActionListener(c);

        fileActionListener = c;

    }

    public void itemStateChanged(ItemEvent evt) {
        if(evt.getSource() instanceof JComboBox) {
            JComboBox cb = (JComboBox)evt.getSource();
            if(cb.getName().equals("QuerySelect") ) {
                CardLayout cl = (CardLayout) (inputCards.getLayout());
                cl.show(inputCards, (String) evt.getItem());
            }
        }
    }



    public void resetMap() {
        HashSet<GeoPosition> gp = new HashSet<GeoPosition>();
        for (MapPoint mp : waypoints)
            gp.add(mp.getPosition());

        mapViewer.zoomToBestFit(gp, 0.7);
        SwingUtilities.updateComponentTreeUI(frame);
    }

    public String getManualQueryString()
    {
        return mQuery.getText();
    }
    public String getGoogleMapsQueryString()
    {
        return gmQuery.getText();
    }
    public String getHamQTHQueryString() { return hamQTHQuery.getText(); }


    public void showError(String message) {
        JOptionPane.showMessageDialog(frame, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource() instanceof JButton)
        {
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new File("."));
            int chooseResult = chooser.showSaveDialog(frame);
            if(chooseResult == JFileChooser.APPROVE_OPTION)
            {
                fileActionListener.actionPerformed(new ActionEvent(answerSaveToFileButton, 0x123, chooser.getSelectedFile().getAbsolutePath()));
            }
        }
    }
}

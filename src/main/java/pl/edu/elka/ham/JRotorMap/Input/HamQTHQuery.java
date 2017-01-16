package pl.edu.elka.ham.JRotorMap.Input;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;
import pl.edu.elka.ham.JRotorMap.Geography.Location;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;

/**
 * Interprets XML from HamQTH.com.
 */
public class HamQTHQuery implements IQuery {
    private Location loc;
    private String lastQuery;

    /**
     * Default constructor.
     * @param query query entered by user.
     * @throws Exception if XML is malformed on other cases.
     */
    public HamQTHQuery(String query) throws Exception {
        lastQuery = query;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        InputStream is = new URL("https://www.hamqth.com/dxcc.php?callsign=" + query).openStream();
        Document doc = db.parse(is);
        NodeList dxcc = doc.getElementsByTagName("dxcc");
        NodeList dxccChild = dxcc.item(0).getChildNodes();
        String latitude = "", longitude = "";
        for(int k = 0; k < dxccChild.getLength(); k++)
        {
            Node node = dxccChild.item(k);
            if(node.getNodeName().equals("lat"))
                latitude = node.getTextContent();
            else if(node.getNodeName().equals("lng"))
                longitude = node.getTextContent();
        }


        loc = new Location(latitude + ", " + longitude);
    }

    /**
     * @return query from user.
     */
    public String getQueryString() {
        return lastQuery;
    }

    /**
     * @return location from webservice.
     */
    @Override
    public Location getFoundLocation() {
        return loc;
    }

    private static void printDocument(Document doc, OutputStream out) throws IOException, TransformerException {
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

        transformer.transform(new DOMSource(doc),
                new StreamResult(new OutputStreamWriter(out, "UTF-8")));
    }
}

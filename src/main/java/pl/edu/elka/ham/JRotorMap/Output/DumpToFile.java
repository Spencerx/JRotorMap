package pl.edu.elka.ham.JRotorMap.Output;

import pl.edu.elka.ham.JRotorMap.Geography.Result;

import java.io.FileWriter;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * Created by erxyi on 12.01.17.
 */
public class DumpToFile implements IOutput {

    String path_;

    public DumpToFile(String path)
    {
        path_ = path;
    }
    public void saveOutput(Result r)
    {
        try(FileWriter fw = new FileWriter(path_, true))
        {
            DecimalFormat df = new DecimalFormat("#.###", new DecimalFormatSymbols(Locale.US));
            StringBuilder sb = new StringBuilder();
            sb.append(r.getName());
            sb.append(';');
            sb.append(df.format(r.getDestination().getLatitude().getSignDDD()));
            sb.append(';');
            sb.append(df.format(r.getDestination().getLongitude().getSignDDD()));
            sb.append(';');
            sb.append(df.format(r.getAzimuth()));
            sb.append(';');
            sb.append(df.format(r.getDistance()));
            sb.append("\n");
            fw.append(sb.toString());
        }
        catch(Exception e)
        {
            throw new RuntimeException("Can't dump result to file:" + e);
        }
    }
}

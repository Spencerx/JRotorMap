package pl.edu.elka.ham.JRotorMap.Output;

import pl.edu.elka.ham.JRotorMap.Geography.Result;

import java.io.FileWriter;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * First implementation of IOutput - saves Result object in CSV-related manner.
 */
public class DumpToFile implements IOutput {

    private String path_;

    /**
     * Constructor. Only saves a path into instance - whole process of writing to file is in saveOutput method.
     * @param path file path.
     */
    public DumpToFile(String path)
    {
        path_ = path;
    }

    /**
     * Saves param to file specifed in constructor - it's worth noticing that it will append data into existing file.
     * @param r Result object to be saved.
     * @throws RuntimeException Exception is thrown while it's impossible to save it, i.e. access right problem.
     */
    @Override
    public void saveOutput(Result r) throws RuntimeException
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

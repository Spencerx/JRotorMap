package pl.edu.elka.ham.JRotorMap.Output;

import pl.edu.elka.ham.JRotorMap.Geography.Result;

/**
 * Interface which generalise output - in future might be used to implements i.e. sending Result object over network.
 */
public interface IOutput {
    /**
     * @param result Result object to be saved.
     */
    void saveOutput(Result result);
}

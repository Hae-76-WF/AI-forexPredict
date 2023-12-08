package DataCollectPreprocess;

import java.io.FileReader;

/**
 *
 * Interface for data Assembling
 * @author WETAKA FRANCIS
 */
public interface DAssembler {
    /**
     * Parses a JSON string and extracts the OHLCV and time data.
     * @param jsonString The JSON string to parse.
     * @return A 2D array containing the OHLCV and time data.
     *
     * @Note The JSON data extracted in the code below is applicable the websocket API
     * being used in here.
     */
    public String[][] webSocketDataOrganizer(String jsonString);
    /**
     * Reads data from a CSV file and returns it as a 2D array.
     * @param reader Object of the fileReader CSV file.
     * @return A 2D array containing the data from the CSV file.
     */
    public String[][] organizeFromFile(FileReader reader);
}

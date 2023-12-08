package DataCollectPreprocess;
import org.json.JSONObject;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;


public class DataAssembler implements IDataAssembler {
    /**
     * Parses a JSON string and extracts the OHLCV and time data.
     * @param jsonString The JSON string to parse.
     * @return A 2D array containing the OHLCV and time data.
     *
     * @Note The JSON data extracted in the code below is applicable to the websocket API
     * being used in here.
     */
    public String[][] webSocketDataOrganizer(String jsonString) {
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONArray candles = jsonObject.getJSONArray("candles");

        String[][] data = new String[candles.length()][6];
        data[0][0] = "open";
        data[0][1] = "high";
        data[0][2] = "low";
        data[0][3] = "close";
        data[0][4] = "null";
        data[0][5] = "epoch";

        for (int i = 1; i < candles.length(); i++) {
            JSONObject candle = candles.getJSONObject(i);
            data[i][0] = Double.toString(candle.getDouble("open"));
            data[i][1] = Double.toString(candle.getDouble("high"));
            data[i][2] = Double.toString(candle.getDouble("low"));
            data[i][3] = Double.toString(candle.getDouble("close"));
            //data[i][4] = Double.toString(candle.getDouble("volume"));

            // Convert epoch to date/time
            long epoch = candle.getLong("epoch");
            Date date = new Date(epoch * 1000L);  // The epoch is in seconds, convert to milliseconds
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            data[i][5] = sdf.format(date);
        }

        return data;
    }

    /**
     * Reads data from a CSV file and returns it as a 2D array.
     * @param reader Object of the fileReader CSV file.
     * @return A 2D array containing the data from the CSV file.
     */
    public String[][] organizeFromFile(FileReader reader) {
        List<String[]> data = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(reader)) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                data.add(values);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data.toArray(new String[0][]);
    }
}

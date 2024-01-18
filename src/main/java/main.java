import DataCollectPreprocess.DataAssembler;
import DataCollectPreprocess.WSCDataCollector;
import models.LSTMModel;
import org.glassfish.tyrus.client.ClientManager;

import javax.websocket.CloseReason;
import java.util.Arrays;

public class main {
    public static void main(String[] args) {
        DataAssembler assembler = new DataAssembler();
        ClientManager clientManager = ClientManager.createClient();
        WSCDataCollector sampleExperiments = new WSCDataCollector(clientManager,"wss://ws.derivws.com/websockets/v3?app_id=1089");
        try {
            sampleExperiments.sendRequest(sampleExperiments.getSession(), "{\"ticks_history\": \"R_100\", \"count\": \"10\", \"end\": \"latest\", \"style\": \"candles\"}");

            String zz =sampleExperiments.getForexData();
            String [][] results = assembler.webSocketDataOrganizer(zz);

            for (String[] result : results) {
                for (int j = 0; j < result.length; j++) System.out.print(result[j] + " ");
                System.out.println();
            }

            sampleExperiments.getSession().close(new CloseReason(CloseReason.CloseCodes.GOING_AWAY, "Bye"));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        LSTMModel ff = new LSTMModel(12,2,0.2);
    }
}

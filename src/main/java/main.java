import DataCollectPreprocess.WSCDataCollector;
import org.glassfish.tyrus.client.ClientManager;

import javax.websocket.CloseReason;

public class main {
    public static void main(String[] args) {
        ClientManager clientManager = ClientManager.createClient();
        WSCDataCollector sampleExperiments = new WSCDataCollector(clientManager,"wss://ws.derivws.com/websockets/v3?app_id=1089");
        try {
            sampleExperiments.sendRequest(sampleExperiments.getSession(), "{\"ticks_history\": \"R_100\", \"count\": \"10\", \"end\": \"latest\", \"style\": \"candles\"}");

            String zz =sampleExperiments.getForexData();
            System.out.println(zz);

            sampleExperiments.getSession().close(new CloseReason(CloseReason.CloseCodes.GOING_AWAY, "Bye"));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

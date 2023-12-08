package DataCollectPreprocess;

import javax.websocket.*;
import java.util.concurrent.TimeUnit;

/**
 * This class is used to collect data from a WebSocket server.
 * It uses the Tyrus library, which is a reference implementation of the WebSocket API in Java.
 * @author WETAKA FRANCIS
 */
@ClientEndpoint
public interface IWSCDataCollector {

    /**
     * Returns the current WebSocket session.
     */
    public Session getSession();

    /**
     * Returns the latest forex data received from the WebSocket server.
     * It waits for up to 3 seconds for a message to be received before returning.
     */
    public String getForexData() throws InterruptedException;
    /**
     * Returns the latest forex data received from the WebSocket server.
     * It waits for a specified amount of time for a message to be received before returning.
     */
    public String getForexData(int count, TimeUnit timeUnit) throws InterruptedException;

    /**
     * Sends a request to the WebSocket server.
     */
    public void sendRequest(Session session, String requestMessage) ;
}

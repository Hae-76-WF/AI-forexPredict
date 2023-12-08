package DataCollectPreprocess;

import org.glassfish.tyrus.client.ClientManager;
import javax.websocket.*;
import java.net.URI;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * This class is used to collect data from a WebSocket server.
 * It uses the Tyrus library, which is a reference implementation of the WebSocket API in Java.
 * @author WETAKA FRANCIS
 */
@ClientEndpoint
public class WSCDataCollector implements IWSCDataCollector {

    private static CountDownLatch latch;
    private Session ss;
    private String forexData;
    private ClientManager client;

    /**
     *
     * Constructor that takes a ClientManager and a URL as parameters.
     * It establishes a connection to the WebSocket server at the given URL.
     * Example of clientManager to be passed;
     * ---------------------------------------------------------------------
     * ClientManager = ClientManager.createClient();
     */
    public WSCDataCollector(ClientManager manager, String url) {
        client = manager;
        try {
            client.connectToServer(this, new URI(url));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Constructor that takes a URL as a parameter.
     * It creates a new ClientManager and establishes a connection to the WebSocket server at the given URL.
     */
    public WSCDataCollector(String url) {
        client = ClientManager.createClient();
        try {
            client.connectToServer(this, new URI(url));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Callback method that is called when a connection is established with the WebSocket server.
     */
    @OnOpen
    public void onOpen(Session session) throws InterruptedException {
        ss = session;
    }

    /**
     * Callback method that is called when a message is received from the WebSocket server.
     */
    @OnMessage
    public void onMessage(String message) {
        this.forexData = message;
    }

    /**
     * Returns the current WebSocket session.
     */
    public Session getSession(){ return this.ss;}

    /**
     * Returns the latest forex data received from the WebSocket server.
     * It waits for up to 3 seconds for a message to be received before returning.
     */
    public String getForexData() throws InterruptedException {
        latch = new CountDownLatch(1);
        latch.await(3, TimeUnit.SECONDS);
        return this.forexData;
    }

    /**
     * Returns the latest forex data received from the WebSocket server.
     * It waits for a specified amount of time for a message to be received before returning.
     */
    public String getForexData(int count, TimeUnit timeUnit) throws InterruptedException {
        latch = new CountDownLatch(1);
        latch.await(count, timeUnit);
        return this.forexData;
    }

    /**
     * Sends a request to the WebSocket server.
     */
    public void sendRequest(Session session, String requestMessage){
        try {
            // Send a request for OHLC data every minute
            session.getBasicRemote().sendText(requestMessage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

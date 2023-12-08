package DataCollectPreprocess;

import org.glassfish.tyrus.client.ClientManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WSCDataCollectorTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testConstructorWithClientManager() {
        ClientManager manager = ClientManager.createClient();
        WSCDataCollector collector = new WSCDataCollector(manager, "ws://example.com");
        assertNotNull(collector);
    }

    @Test
    void testConstructorWithURL() {
        WSCDataCollector collector = new WSCDataCollector("ws://example.com");
        assertNotNull(collector);
    }

    @Test
    void onOpen() {

    }

    @Test
    void onMessage() {
    }

    @Test
    void getSession() {
    }

    @Test
    void getForexData() {
    }

    @Test
    void testGetForexData() {
    }

    @Test
    void sendRequest() {
    }
}
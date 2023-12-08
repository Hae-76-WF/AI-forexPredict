package DataCollectPreprocess;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;

import static org.junit.jupiter.api.Assertions.*;

class DataAssemblerTest {
    private DataAssembler organizer;

    @BeforeEach
    public void setUp() {
        organizer = new DataAssembler();
    }

    @Test
    public void webSocketDataOrganizer() {
        String[][] data = organizer.webSocketDataOrganizer("");
        assertNotNull(data);
    }

    @Test
    public void organizeFromFile() throws FileNotFoundException {
        String[][] data = organizer.organizeFromFile(new FileReader(""));
        assertNotNull(data);

    }


    @AfterEach
    public void tearDown() {
        organizer = null;
    }
}
package DataCollectPreprocess;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import java.util.*;

public class DataFrameTest {
    private DataFrame df;

    @BeforeEach
    public void setUp() {
        df = new DataFrame();
    }

    @Test
    public void testAddColumn() {
        df.addColumn("Column1", new ArrayList<>(Arrays.asList(1.1, 2.2, 3.3, null, 5.5)));
        Assertions.assertEquals(1, df.getColumn("Column1").size());
    }

    @Test
    public void testGetColumn() {
        df.addColumn("Column1", new ArrayList<>(Arrays.asList(1.1, 2.2, 3.3, null, 5.5)));
        Assertions.assertEquals(1.1, df.getColumn("Column1").get(0));
    }

    @Test
    public void testAddRowWithMap() {
        df.addColumn("Column1", new ArrayList<>(Arrays.asList(1.1)));
        df.addRow(new HashMap<String, Double>() {{
            put("Column1", 2.2);
        }});
        Assertions.assertEquals(2, df.getColumn("Column1").size());
    }

    @Test
    public void testAddRowWithList() {
        df.addColumn("Column1", new ArrayList<>(Arrays.asList(1.1)));
        df.addRow(new ArrayList<>(Arrays.asList(2.2)));
        Assertions.assertEquals(2, df.getColumn("Column1").size());
    }

    @Test
    public void testHeadWithN() {
        df.addColumn("Column1", new ArrayList<>(Arrays.asList(1.1, 2.2, 3.3, 4.4, 5.5)));
        DataFrame head = df.head(3);
        Assertions.assertEquals(3, head.getColumn("Column1").size());
    }

    @Test
    public void testHead() {
        df.addColumn("Column1", new ArrayList<>(Arrays.asList(1.1, 2.2, 3.3, 4.4, 5.5)));
        DataFrame head = df.head();
        Assertions.assertEquals(5, head.getColumn("Column1").size());
    }

    @Test
    public void testFillna() {
        df.addColumn("Column1", new ArrayList<>(Arrays.asList(1.1, null, 3.3, null, 5.5)));
        DataFrame filled = df.fillna(0.0);
        Assertions.assertEquals(0.0, filled.getColumn("Column1").get(1));
    }

    @Test
    public void testAddRowWithMapNewColumn() {
        df.addRow(new HashMap<String, Double>() {{
            put("Column1", 1.1);
        }});
        Assertions.assertEquals(1, df.getColumn("Column1").size());
    }

    @Test
    public void testAddRowWithListNewColumn() {
        df.addRow(new ArrayList<>(Arrays.asList(1.1)));
        Assertions.assertEquals(1, df.getColumn("Column1").size());
    }

    @Test
    public void testHeadWithNNewColumn() {
        df.addColumn("Column1", new ArrayList<>(Arrays.asList(1.1, 2.2, 3.3, 4.4, 5.5)));
        DataFrame head = df.head(6);
        Assertions.assertEquals(5, head.getColumn("Column1").size());
    }

    @Test
    public void testFillnaNewColumn() {
        df.addColumn("Column1", new ArrayList<>(Arrays.asList(1.1, null, 3.3, null, 5.5)));
        DataFrame filled = df.fillna(0.0);
        Assertions.assertEquals(0.0, filled.getColumn("Column1").get(5));
    }
}

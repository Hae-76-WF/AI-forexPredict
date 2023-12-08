package DataCollectPreprocess;

import java.util.*;

/**
 * A DataFrame-like structure in Java, capable of storing string column labels and rows of double data.
 */
public class DataFrame implements IDataFrame {
    private Map<String, List<Double>> data;

    /**
     * Default constructor which initializes an empty DataFrame.
     */
    public DataFrame() {
        this.data = new LinkedHashMap<>();
    }

    /**
     * Constructor that initializes a DataFrame with the provided map of data.
     * @param data A map where keys are column labels and values are lists of doubles.
     */
    public DataFrame(Map<String, List<Double>> data) {
        this.data = data;
    }

    /**
     * Constructor that initializes a DataFrame with the provided 2D array of data.
     * @param data A 2D array where the first element of each row is the column label and the rest are data.
     */
    public DataFrame(String[][] data) {
        this.data = new LinkedHashMap<>();
        String[] headers = data[0];

        for (int j = 0; j < headers.length; j++) {
            this.data.put(headers[j], new ArrayList<>());
        }

        for (int i = 1; i < data.length; i++) {
            for (int j = 0; j < headers.length; j++) {
                if (j < data[i].length) {
                    try {
                        double value = Double.parseDouble(data[i][j]);
                        this.data.get(headers[j]).add(value);
                    } catch (NumberFormatException e) {
                        this.data.get(headers[j]).add(null);
                    }
                } else {
                    this.data.get(headers[j]).add(null);
                }
            }
        }

    }

    /**
     * Adds a new column to the DataFrame with the given label and list of values.
     * @param label The label of the column.
     * @param values The data of the column.
     */
    public void addColumn(String label, List<Double> values) {
        this.data.put(label, values);
    }

    /**
     * Returns the column with the given label.
     * @param label The label of the column.
     * @return The data of the column.
     */
    public List<Double> getColumn(String label) {
        return this.data.get(label);
    }

    /**
     * Adds a new row to the DataFrame. The row is represented as a map where the keys are column labels and the values are the corresponding data.
     * @param values A map where keys are column labels and values are data.
     */
    public void addRow(Map<String, Double> values) {
        for (Map.Entry<String, Double> entry : values.entrySet()) {
            String label = entry.getKey();
            if (!this.data.containsKey(label)) {
                this.data.put(label, new ArrayList<>());
            }
            this.data.get(label).add(entry.getValue());
        }
    }

    /**
     * Adds a new row to the DataFrame. The row is represented as a list of values. The order of values in the list should correspond to the order of columns in the DataFrame.
     * @param values A list of values.
     */
    public void addRow(List<Double> values) {
        int i = 0;
        for (Map.Entry<String, List<Double>> entry : this.data.entrySet()) {
            entry.getValue().add(values.get(i));
            i++;
        }
    }

    /**
     * Prints the DataFrame to the console.
     */
    public void print() {
        for (Map.Entry<String, List<Double>> entry : this.data.entrySet()) {
            System.out.print(entry.getKey() + "\t");
        }
        System.out.println();
        int numRows = this.data.entrySet().iterator().next().getValue().size();
        for (int i = 0; i < numRows; i++) {
            for (Map.Entry<String, List<Double>> entry : this.data.entrySet()) {
                System.out.print(entry.getValue().get(i) + "\t");
            }
            System.out.println();
        }
    }

    /**
     * Returns a new DataFrame that contains only the first n rows of the original DataFrame.
     * @param n The number of rows.
     * @return A new DataFrame.
     */
    public DataFrame head(int n) {
        DataFrame df = new DataFrame();
        for (Map.Entry<String, List<Double>> entry : this.data.entrySet()) {
            String label = entry.getKey();
            List<Double> values = entry.getValue().subList(0, Math.min(n, entry.getValue().size()));
            df.addColumn(label, values);
        }
        return df;
    }

    /**
     * Returns a new DataFrame that contains only the first 5 rows of the original DataFrame.
     * @return A new DataFrame.
     */
    public DataFrame head() {
        return head(5);
    }

    /**
     * Returns a new DataFrame where all null values are replaced with the provided value.
     * @param value The value to replace nulls with.
     * @return A new DataFrame.
     */
    public DataFrame fillna(double value) {
        DataFrame df = new DataFrame();
        for (Map.Entry<String, List<Double>> entry : this.data.entrySet()) {
            String label = entry.getKey();
            List<Double> values = new ArrayList<>();
            for (Double v : entry.getValue()) {
                if (v == null) {
                    values.add(value);
                } else {
                    values.add(v);
                }
            }
            df.addColumn(label, values);
        }
        return df;
    }

//    /**
//     * The main method which is the entry point for any Java program. In this method, a new DataFrame is created and some operations are performed to demonstrate the functionality of the DataFrame class.
//     * @param args Command line arguments.
//
//    public static void main(String[] args) {
//        DataFrame df = new DataFrame();
//        df.addColumn("Column1", new ArrayList<>(Arrays.asList(1.1, 2.2, 3.3, null, 5.5)));
//        df.addColumn("Column2", new ArrayList<>(Arrays.asList(4.4, null, 6.6, 7.7, 8.8)));
//        df.print();
//
//        DataFrame head = df.head();
//        head.print();
//
//        DataFrame filled = df.fillna(0.0);
//        filled.print();
//    }
//     * /
}


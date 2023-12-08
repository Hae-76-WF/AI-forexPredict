package DataCollectPreprocess;

import java.util.*;

/**
 * An interface for DataFrame-like structures in Java.
 * @author WETAKA FRANCIS S.
 */
public interface IDataFrame {
    /**
     * Adds a new column to the DataFrame with the given label and list of values.
     * @param label The label of the column.
     * @param values The data of the column.
     */
    void addColumn(String label, List<Double> values);

    /**
     * Returns the column with the given label.
     * @param label The label of the column.
     * @return The data of the column.
     */
    List<Double> getColumn(String label);

    /**
     * Adds a new row to the DataFrame. The row is represented as a map where the keys are column labels and the values are the corresponding data.
     * @param values A map where keys are column labels and values are data.
     */
    void addRow(Map<String, Double> values);

    /**
     * Adds a new row to the DataFrame. The row is represented as a list of values. The order of values in the list should correspond to the order of columns in the DataFrame.
     * @param values A list of values.
     */
    void addRow(List<Double> values);

    /**
     * Prints the DataFrame to the console.
     */
    void print();

    /**
     * Returns a new DataFrame that contains only the first n rows of the original DataFrame.
     * @param n The number of rows.
     * @return A new DataFrame.
     */
    IDataFrame head(int n);

    /**
     * Returns a new DataFrame that contains only the first 5 rows of the original DataFrame.
     * @return A new DataFrame.
     */
    IDataFrame head();

    /**
     * Returns a new DataFrame where all null values are replaced with the provided value.
     * @param value The value to replace nulls with.
     * @return A new DataFrame.
     */
    IDataFrame fillna(double value);
}

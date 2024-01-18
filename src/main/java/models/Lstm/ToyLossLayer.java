package models.Lstm;

/**
 * This class represents a toy loss layer for an LSTM model.
 */
public class ToyLossLayer{

    /**
     * This method computes the square loss with the first element of the hidden layer array.
     *
     * @param pred  The predicted values, represented as an array of doubles.
     * @param label The actual label, represented as a double.
     * @return      The computed square loss as a double.
     */
    public double loss(double[] pred, double label) {
        return (pred[0] - label) * (pred[0] - label);
    }

    /**
     * This method computes the bottom difference for backpropagation in the LSTM model.
     *
     * @param pred  The predicted values, represented as an array of doubles.
     * @param label The actual label, represented as a double.
     * @return      The computed bottom difference as an array of doubles.
     */
    public double[] bottom_diff(double[] pred, double label) {
        double[] diff = new double[pred.length];
        diff[0] = 2 * (pred[0] - label);
        return diff;
    }

}

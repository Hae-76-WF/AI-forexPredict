package models;

/**
 * ISVM is an interface for Support Vector Machines (SVMs).
 */
public interface ISVM {
    /**
     * Train the SVM with a fixed learning rate.
     *
     * @param inputs 2D array of input data
     * @param targets Array of target data
     */
    void train(double[][] inputs, int[] targets);

    /**
     * Train the SVM with a variable learning rate.
     *
     * @param inputs 2D array of input data
     * @param targets Array of target data
     * @param initialLearningRate The initial learning rate
     */
    void train(double[][] inputs, int[] targets, double initialLearningRate);

    /**
     * Predict the class of a new input.
     *
     * @param x The new input data
     * @return The predicted class
     */
    int predict(double[] x);

    /**
     * Validate the SVM with a new set of data.
     *
     * @param inputs 2D array of input data
     * @param targets Array of target data
     * @return The accuracy of the model on the validation data
     */
    double validate(double[][] inputs, int[] targets);
}

package models;

import java.util.Random;

/**
 * SVM is a class for Support Vector Machines (SVMs) with a sigmoid kernel.
 */
public class SVM {
    private double[][] inputs;
    private int[] targets;
    private double[] alphas;
    private double b = 0;
    private double learningRate = 0.001;
    private double EPOCHS = 1000;

    // Constructor
    public SVM(double learningRate, double EPOCHS) {
        this.learningRate = learningRate;
        this.EPOCHS = EPOCHS;
    }

    public SVM(){

    }

    // Sigmoid kernel function
    private double kernel(double[] x, double[] y) {
        double sum = 0;
        for (int i = 0; i < x.length; i++) {
            sum += x[i] * y[i];
        }
        return Math.tanh(sum);
    }

    /**
     * Train the SVM with a fixed learning rate.
     *
     * @param inputs  2D array of input data
     * @param targets Array of target data
     */
    public void train(double[][] inputs, int[] targets) {
        train(inputs, targets, learningRate);
    }

    /**
     * Train the SVM with a variable learning rate.
     *
     * @param inputs              2D array of input data
     * @param targets             Array of target data
     * @param initialLearningRate The initial learning rate
     */
    public void train(double[][] inputs, int[] targets, double initialLearningRate) {
        if (inputs.length != targets.length) {
            throw new IllegalArgumentException("Inputs and targets must have the same length");
        }

        this.inputs = inputs;
        this.targets = targets;
        this.alphas = new double[inputs.length];

        double learningRate = initialLearningRate;

        for (int n = 0; n < EPOCHS; n++) {
            for (int i = 0; i < inputs.length; i++) {
                double delta = 1 - targets[i] * f(inputs[i]);
                alphas[i] += learningRate * delta;
                b += learningRate * delta;
            }

            // Decrease the learning rate over time
            learningRate *= 0.99;
        }

    }

    /**
     * Calculate the output of the SVM for a given input.
     *
     * @param x The input data
     * @return The output of the SVM
     */
    private double f(double[] x) {
        double sum = 0;
        for (int i = 0; i < inputs.length; i++) {
            sum += alphas[i] * targets[i] * kernel(inputs[i], x);
        }
        return sum + b;
    }

    /**
     * Predict the class of a new input.
     *
     * @param x The new input data
     * @return The predicted class
     */
    public int predict(double[] x) {
        double f = f(x);
        return f > 0 ? 1 : -1;
    }

    /**
     * Validate the SVM with a new set of data.
     *
     * @param inputs  2D array of input data
     * @param targets Array of target data
     * @return The accuracy of the model on the validation data
     */
    public double validate(double[][] inputs, int[] targets) {
        if (inputs.length != targets.length) {
            throw new IllegalArgumentException("Inputs and targets must have the same length");
        }

        int correct = 0;
        for (int i = 0; i < inputs.length; i++) {
            int prediction = predict(inputs[i]);
            if (prediction == targets[i]) {
                correct++;
            }
        }
        return correct / (double) inputs.length;
    }

}

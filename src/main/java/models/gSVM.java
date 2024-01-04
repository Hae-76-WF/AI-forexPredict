package models;

import com.aparapi.Kernel;
import com.aparapi.Range;
import com.aparapi.device.Device;
import com.aparapi.device.OpenCLDevice;
import com.aparapi.internal.opencl.OpenCLPlatform;

import java.io.*;

/**
 * SVM is a class for Support Vector Machines (SVMs) that runs on a GPU.
 */
public class gSVM {
    private double[][] inputs;
    private int[] targets;
    private double[] alphas;
    private double b = 0;
    private double learningRate = 0.001;
    private double EPOCHS = 1000;

    // Constructor
    public gSVM(double learningRate, double EPOCHS) {
        this.learningRate = learningRate;
        this.EPOCHS = EPOCHS;
    }

    public gSVM(){

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

        double finalLearningRate = learningRate;
        Kernel kernel = new Kernel(){
            @Override
            public void run() {
                int i = getGlobalId();
                double delta = 1 - targets[i] * f(inputs[i]);
                alphas[i] += finalLearningRate * delta;
                b += finalLearningRate * delta;
            }
        };

        for (int n = 0; n < EPOCHS; n++) {
            Range range = Range.create(inputs.length);
            kernel.execute(range);

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
        final double[] sum = new double[1];
        Kernel kernel = new Kernel(){
            @Override
            public void run() {
                int i = getGlobalId();
                sum[0] += alphas[i] * targets[i] * kernel(inputs[i], x);
            }
        };
        kernel.execute(Range.create(inputs.length));
        return sum[0] + b;
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

    /**
     * Save the trained model to a file.
     *
     * @param filename The name of the file
     */
    public void saveModel(String filename) {
        try {
            FileOutputStream fileOut = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    /**
     * Load a trained model from a file.
     *
     * @param filename The name of the file
     * @return The loaded SVM
     */
    public static SVM loadModel(String filename) {
        SVM svm = null;
        try {
            FileInputStream fileIn = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            svm = (SVM) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
            return null;
        } catch (ClassNotFoundException c) {
            System.out.println("SVM class not found");
            c.printStackTrace();
            return null;
        }
        return svm;
    }

}


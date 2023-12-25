package models;

public class ModelSample {
    public static void main(String[] args) {
        // Assuming forex data of OHLCV
        double[][] inputs = {
                // Add your data here
        };
        int[] targets = {
                // Add your data here
        };

        SVM svm = new SVM(0.005, 10);

        svm.train(inputs,targets);  // Use variable learning rate

        // Validate the model
        double accuracy = svm.validate(inputs, targets);
        System.out.println("Model accuracy: "+accuracy);

        // Predict a new sample
        double[] newSample = {
                // Add your data here
        };
        int prediction = svm.predict(newSample);
        System.out.println("Prediction: "+prediction);
    }

}

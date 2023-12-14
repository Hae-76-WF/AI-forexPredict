package FeatureEngineeringSelection.Engineer;

import java.util.ArrayList;
import java.util.List;

/**
 * The FeatureTransformer class is responsible for transforming the original features with the calculated technical indicators.
 */
public class FeatureTransform {
    /**
     * Transforms the original features with the calculated technical indicators.
     * @param originalFeatures The original features.
     * @param technicalIndicators The calculated technical indicators.
     * @return A list of transformed features.
     */
    public List<String> transformFeatures(List<String> originalFeatures, List<String> technicalIndicators) {
        List<String> transformedFeatures = new ArrayList<>();
        transformedFeatures.addAll(originalFeatures);
        transformedFeatures.addAll(technicalIndicators);
        return transformedFeatures;
    }

//    /**
//     * Encodes categorical features using one-hot encoding.
//     * @param categoricalFeatures The categorical features to be encoded.
//     * @return The encoded features.
//     */
//    public List<String> oneHotEncode(List<String> categoricalFeatures) {
//        // One-hot encoding converts categorical features into numerical values
//        // by creating a binary vector for each category
//        List<String> oneHotEncodedFeatures = new ArrayList<>();
//        for (String category : categoricalFeatures) {
//            // Create a binary vector with a length equal to the number of categories
//            int[] oneHotVector = new int[categoricalFeatures.size()];
//            // Set the ith element of the vector to 1
//            oneHotVector[categoricalFeatures.indexOf(category)] = 1;
//            // Convert the binary vector to a string
//            oneHotEncodedFeatures.add(String.join(",", oneHotVector));
//        }
//        return oneHotEncodedFeatures;
//    }

    /**
     * Scales the features to a specified range.
     * @param features The features to be scaled.
     * @param min The minimum value of the range.
     * @param max The maximum value of the range.
     * @return The scaled features.
     */
    public List<String> scaleFeatures(List<String> features, double min, double max) {
        // Scaling the features to a specified range
        // Subtract the minimum value from each feature and divide by the range
        List<String> scaledFeatures = new ArrayList<>();
        for (String feature : features) {
            double scaledValue = (Double.parseDouble(feature) - min) / (max - min);
            scaledFeatures.add(String.valueOf(scaledValue));
        }
        return scaledFeatures;
    }
}

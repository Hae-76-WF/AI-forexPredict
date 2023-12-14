package FeatureEngineeringSelection.Engineer;

import java.util.ArrayList;
import java.util.List;

public class General implements IGeneral{
    /**
     * Creates lagged features from the original features.
     * Lagged features are created by shifting the data points backward or forward in time by a certain number of time periods.
     * @param features The original features.
     * @param lag The number of time periods to shift the data points.
     * @return A list of lagged features.
     */
    public List<String> laggedFeatures(List<String> features, int lag) {
        List<String> laggedFeatures = new ArrayList<>();
        for (String feature : features) {
            for (int i = 0; i < lag; i++) {
                String laggedFeature = feature + "_lag" + i;
                laggedFeatures.add(laggedFeature);
            }
        }
        return laggedFeatures;
    }

    /**
     * Creates differenced features from the original features.
     * Differenced features are created by calculating the difference between consecutive data points in the time series.
     * @param features The original features.
     * @return A list of differenced features.
     */
    public List<String> differencedFeatures(List<String> features) {
        List<String> differencedFeatures = new ArrayList<>();
        for (int i = 1; i < features.size(); i++) {
            String difference = String.format("%s_diff%d", features.get(i - 1), i);
            differencedFeatures.add(difference);
        }
        return differencedFeatures;
    }

    @Override
    public double[] Custom(Object... arg) {
        return new double[0];
    }
}

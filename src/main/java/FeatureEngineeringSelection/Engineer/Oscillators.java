package FeatureEngineeringSelection.Engineer;

public class Oscillators {
    // Class Trend contains methods for calculating technical indicators

    /**
     * Calculates the Relative Strength Index (RSI)
     * @param begin the starting index for the calculation
     * @param length the length of the price array
     * @param price the price array
     * @return the Relative Strength Index (RSI) value
     */
    public double RSI(int begin, int length, double[] price) {
        // Calculate the average gain and average loss
        double avgGain = 0.0, avgLoss = 0.0;

        for (int i = begin; i < price.length - 1; i++) {
            avgGain += Math.max(price[i], price[i + 1]);
            avgLoss += Math.min(price[i], price[i + 1]);
        }

        // Calculate the RSI value
        return 100.0 - (avgGain / avgLoss);
    }

    /**
     * Calculates the Chande Momentum Convergence Divergence (CCI)
     * @param begin the starting index for the calculation
     * @param length the length of the price array
     * @param price the price array
     * @return the Chande Momentum Convergence Divergence (CCI) value
     */
    public double CCI(int begin, int length, double[] price) {
        // Calculate the typical price, average price, and momentum
        double typicalPrice = 0.0;
        double averagePrice = 0.0;
        double momentum = 0.0;

        for (int i = begin; i < price.length - 1; i++) {
            typicalPrice = (price[i] + price[i + 1] + price[i + 2]) / 3.0;
            averagePrice += price[i];
            momentum = typicalPrice - averagePrice;
        }

        averagePrice /= (price.length - begin);
        momentum /= 3.0;

        // Calculate the CCI value
        return (100.0 * momentum) / (Math.abs(averagePrice) + Math.abs(typicalPrice) + Math.abs(momentum));
    }

    /**
     * Calculates the Average True Range (ATR)
     * @param begin the starting index for the calculation
     * @param length the length of the price array
     * @param price the price array
     * @return the Average True Range (ATR) value
     */
    public double ATR(int begin, int length, double[] price) {
        // Calculate the true range
        double trueRange = 0.0;

        for (int i = begin; i < price.length - 1; i++) {
            trueRange = Math.max(Math.abs(price[i] - price[i + 1]), trueRange);
        }

        // Calculate the ATR value
        return trueRange != 0.0 ? trueRange : 0.0;
    }

    /**
     * Calculates the Standard Deviation (SD)
     * @param begin the starting index for the calculation
     * @param length the length of the price array
     * @param price the price array
     * @return the Standard Deviation (SD) value
     */
    public double SD(int begin, int length, double[] price) {
        // Calculate the mean
        double sum = 0.0, mean = 0.0;

        for (int i = begin; i < price.length; i++) {
            sum += price[i];
        }

        mean = sum / (price.length - begin);

        // Calculate the deviation from the mean
        double deviation = 0.0;

        for (int i = begin; i < price.length; i++) {
            deviation += Math.pow(price[i] - mean, 2);
        }

        // Calculate the Standard Deviation
        return Math.sqrt(deviation / (price.length - begin));
    }

    /**
     * Calculates the Stochastic Indicator
     * @param begin the starting index for the calculation
     * @param length the length of the price array
     * @param high the high price array
     * @param low the low price array
     * @param close the close price array
     * @param kPeriod the K period
     * @param dPeriod the D period
     * @return the Stochastic Indicator value
     */
    public double[] Stochastic(int begin, int length, double[] high, double[] low, double[] close, int kPeriod, int dPeriod) {
        double[] kValues = new double[length];
        double[] dValues = new double[length];

        for (int i = begin; i < length; i++) {
            double highestHigh = high[i];
            double lowestLow = low[i];

            for (int j = i - kPeriod + 1; j <= i; j++) {
                if (high[j] > highestHigh) {
                    highestHigh = high[j];
                }

                if (low[j] < lowestLow) {
                    lowestLow = low[j];
                }
            }

            kValues[i] = 100.0 * ((close[i] - lowestLow) / (highestHigh - lowestLow));
        }

        for (int i = begin + dPeriod - 1; i < length; i++) {
            double sum = 0.0;

            for (int j = i - dPeriod + 1; j <= i; j++) {
                sum += kValues[j];
            }

            dValues[i] = sum / dPeriod;
        }

        return dValues;
    }
}


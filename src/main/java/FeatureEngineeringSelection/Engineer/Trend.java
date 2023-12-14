package FeatureEngineeringSelection.Engineer;

import java.util.ArrayList;
import java.util.List;

/**
 * Trend Class contains properties for generating trend indicator features
 * @author Wetaka Francis S.
 */
public class Trend {
    static MA ma;
    public Trend(){
        ma = new MA();

    }

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
    static class MA {


        /**
         * Calculates the Linear Weighted Moving Average on a price array
         *
         * @param rates_total     the total number of rates
         * @param prev_calculated the number of rates previously calculated
         * @param begin           the beginning index of the price array
         * @param period          the period of the moving average
         * @param price           the price array
         * @param buffer          the buffer array to store the Linear Weighted Moving Average
         * @return the total number of rates
         */
        int LinearWeightedMAOnBuffer(int rates_total, int prev_calculated, int begin, int period, double[] price, double[] buffer) {
            //--- check period
            if (period <= 1 || period > (rates_total - begin)) return (0);

            //--- calculate start position
            int i, start_position;

            if (prev_calculated <= period + begin + 2)  // first calculation or number of bars was changed
            {
                //--- set empty value for first bars
                start_position = period + begin;

                for (i = 0; i < start_position; i++)
                    buffer[i] = 0.0;
            } else start_position = prev_calculated - 2;
            //--- calculate first visible value
            double sum = 0.0, lsum = 0.0;
            int l, weight = 0;

            for (i = start_position - period, l = 1; i < start_position; i++, l++) {
                sum += price[i] * l;
                lsum += price[i];
                weight += l;
            }
            buffer[start_position - 1] = sum / weight;
            //--- main loop
            for (i = start_position; i < rates_total; i++) {
                sum = sum - lsum + price[i] * period;
                lsum = lsum - price[i - period] + price[i];
                buffer[i] = sum / weight;
            }

            return (rates_total);
        }

        /**
         * Calculates the Linear Weighted Moving Average on a price array (fast)
         *
         * @param rates_total     the total number of rates
         * @param prev_calculated the number of rates previously calculated
         * @param begin           the beginning index of the price array
         * @param period          the period of the moving average
         * @param price           the price array
         * @param buffer          the buffer array to store the Linear Weighted Moving Average
         * @param weight_sum      the sum of weights
         * @return the total number of rates
         */
        int LinearWeightedMAOnBuffer(int rates_total, int prev_calculated, int begin, int period, double[] price, double[] buffer, int weight_sum) {
            //--- check period
            if (period <= 1 || period > (rates_total - begin)) return (0);

            //--- calculate start position
            int start_position;

            if (prev_calculated == 0)  // first calculation or number of bars was changed
            {
                //--- set empty value for first bars
                start_position = period + begin;

                for (int i = 0; i < start_position; i++)
                    buffer[i] = 0.0;
                //--- calculate first visible value
                double first_value = 0;
                int wsum = 0;

                for (int i = begin, k = 1; i < start_position; i++, k++) {
                    first_value += k * price[i];
                    wsum += k;
                }

                buffer[start_position - 1] = first_value / wsum;
                weight_sum = wsum;
            } else start_position = prev_calculated - 1;
            //--- main loop
            for (int i = start_position; i < rates_total; i++) {
                double sum = 0;

                for (int j = 0; j < period; j++)
                    sum += (period - j) * price[i - j];

                buffer[i] = sum / weight_sum;
            }

            //---
            return (rates_total);
        }

        /**
         * Calculates the Smoothed Moving Average on a price array
         *
         * @param rates_total     the total number of rates
         * @param prev_calculated the number of rates previously calculated
         * @param begin           the beginning index of the price array
         * @param period          the period of the moving average
         * @param price           the price array
         * @param buffer          the buffer array to store the Smoothed Moving Average
         * @return the total number of rates
         */
        int SmoothedMAOnBuffer(int rates_total, int prev_calculated, int begin, int period, double[] price, double[] buffer) {
            //--- check period
            if (period <= 1 || period > (rates_total - begin)) return (0);

            //--- calculate start position
            int start_position;

            if (prev_calculated == 0)  // first calculation or number of bars was changed
            {
                //--- set empty value for first bars
                start_position = period + begin;

                for (int i = 0; i < start_position - 1; i++)
                    buffer[i] = 0.0;
                //--- calculate first visible value
                double first_value = 0;

                for (int i = begin; i < start_position; i++)
                    first_value += price[i];

                buffer[start_position - 1] = first_value / period;
            } else start_position = prev_calculated - 1;

            for (int i = start_position; i < rates_total; i++)
                buffer[i] = (buffer[i - 1] * (period - 1) + price[i]) / period;

            return (rates_total);
        }


        /**
         * Calculates the Simple Moving Average
         *
         * @param position the current position in the price array
         * @param period   the period of the moving average
         * @param price    the price array
         * @return the Simple Moving Average
         */
        public double SimpleMA(int position, int period, double[] price) {
            double result = 0.0;

            // Check if the period is valid
            if (period > 0 && period <= (position + 1)) {
                for (int i = 0; i < period; i++) {
                    result += price[position - i];
                }
                result /= period;
            }

            return result;
        }

        /**
         * Calculates the Exponential Moving Average
         *
         * @param position   the current position in the price array
         * @param period     the period of the moving average
         * @param prev_value the previous value of the Exponential Moving Average
         * @param price      the price array
         * @return the Exponential Moving Average
         */
        public double ExponentialMA(int position, int period, double prev_value, double[] price) {
            double result = 0.0;

            // Check if the period is valid
            if (period > 0) {
                double pr = 2.0 / (period + 1.0);
                result = price[position] * pr + prev_value * (1 - pr);
            }

            return result;
        }

        /**
         * Calculates the Smoothed Moving Average
         *
         * @param position   the current position in the price array
         * @param period     the period of the moving average
         * @param prev_value the previous value of the Smoothed Moving Average
         * @param price      the price array
         * @return the Smoothed Moving Average
         */
        public double SmoothedMA(int position, int period, double prev_value, double[] price) {
            double result = 0.0;

            // Check if the period is valid
            if (period > 0 && period <= (position + 1)) {
                if (position == period - 1) {
                    for (int i = 0; i < period; i++) {
                        result += price[position - i];
                    }
                    result /= period;
                } else {
                    result = (prev_value * (period - 1) + price[position]) / period;
                }
            }

            return result;
        }

        /**
         * Calculates the Linear Weighted Moving Average
         *
         * @param position the current position in the price array
         * @param period   the period of the moving average
         * @param price    the price array
         * @return the Linear Weighted Moving Average
         */
        public double LinearWeightedMA(int position, int period, double[] price) {
            double result = 0.0;

            // Check if the period is valid
            if (period > 0 && period <= (position + 1)) {
                double sum = 0.0;
                int wsum = 0;

                for (int i = period; i > 0; i--) {
                    wsum += i;
                    sum += price[position - i + 1] * (period - i + 1);
                }

                result = sum / wsum;
            }

            return result;
        }

        /**
         * Calculates the Simple Moving Average on a price array
         *
         * @param rates_total     the total number of rates
         * @param prev_calculated the number of rates previously calculated
         * @param begin           the beginning index of the price array
         * @param period          the period of the moving average
         * @param price           the price array
         * @param buffer          the buffer array to store the Simple Moving Average
         * @return the total number of rates
         */
        public int SimpleMAOnBuffer(int rates_total, int prev_calculated, int begin, int period, double[] price, double[] buffer) {
            // Check if the period is valid
            if (period <= 1 || period > (rates_total - begin)) {
                return 0;
            }

            // Check if the previous calculation is 0 or the number of bars has changed
            int start_position;
            if (prev_calculated == 0) {
                // Set empty value for first bars
                start_position = period + begin;
                for (int i = 0; i < start_position - 1; i++) {
                    buffer[i] = 0.0;
                }

                // Calculate first visible value
                double first_value = 0;
                for (int i = begin; i < start_position; i++) {
                    first_value += price[i];
                }
                buffer[start_position - 1] = first_value / period;
            } else {
                start_position = prev_calculated - 1;
            }

            // Main loop
            for (int i = start_position; i < rates_total; i++) {
                buffer[i] = buffer[i - 1] + (price[i] - price[i - period]) / period;
            }

            return rates_total;
        }

        /**
         * Calculates the Exponential Moving Average on a price array
         *
         * @param rates_total     the total number of rates
         * @param prev_calculated the number of rates previously calculated
         * @param begin           the beginning index of the price array
         * @param period          the period of the moving average
         * @param price           the price array
         * @param buffer          the buffer array to store the Exponential Moving Average
         * @return the total number of rates
         */
        public double[] ExponentialMAOnBuffer(int rates_total, int prev_calculated, int begin, int period, double[] price, double[] buffer) {
            // Check if the period is valid
            if (period <= 1 || period > (rates_total - begin)) {
                return null;
            }

            // Calculate start position
            int start_position;
            double smooth_factor = 2.0 / (1.0 + period);
            if (prev_calculated == 0) {
                // Set empty value for first bars
                for (int i = 0; i < begin; i++) {
                    buffer[i] = 0.0;
                }

                // Calculate first visible value
                double first_value = price[begin];
                start_position = period + begin;
                for (int i = begin + 1; i < start_position; i++) {
                    buffer[i] = price[i] * smooth_factor + buffer[i - 1] * (1.0 - smooth_factor);
                }
                buffer[begin] = first_value;
            } else {
                start_position = prev_calculated - 1;
            }

            // Main loop
            for (int i = start_position; i < rates_total; i++) {
                buffer[i] = price[i] * smooth_factor + buffer[i - 1] * (1.0 - smooth_factor);
            }
            return buffer;
        }
    }

}



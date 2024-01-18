package models.Lstm;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import com.aparapi.Kernel;
import com.aparapi.Range;

/**
 * This class represents an LSTM (Long Short-Term Memory) model.
 */
public class gLSTM {

    int mem_cell_cnt;
    int x_dim;
    double lr;

    LSTMParam param;
    List<LSTMNode> lstm_node_list;
    List<double[]> x_list;

    /**
     * This constructor initializes the LSTM model with the given memory cell count, dimension, and learning rate.
     *
     * @param mem_cell_cnt The number of memory cells in the LSTM model.
     * @param x_dim        The dimension of the input to the LSTM model.
     * @param lr           The learning rate.
     */
    public gLSTM(int mem_cell_cnt, int x_dim, double lr) {
        this.mem_cell_cnt = mem_cell_cnt;
        this.x_dim = x_dim;
        this.lr = lr;

        param = new LSTMParam(mem_cell_cnt, x_dim);

        this.lstm_node_list = new ArrayList<>();
        this.x_list = new ArrayList<>();
    }

    /**
     * This method clears the input list of the LSTM model.
     */
    public void clear() {
        x_list.clear();
    }


    /**
     * This method calculates the loss of the LSTM model given the target output and a loss layer.
     *
     * @param y         The target output.
     * @param lossLayer The loss layer.
     * @return          The calculated loss.
     */
    public double y_list_is(double[] y, ToyLossLayer lossLayer) {
        assert y.length == x_list.size();
        int idx = this.x_list.size() - 1;

        double loss = lossLayer.loss(this.lstm_node_list.get(idx).state.h, y[idx]);
        double[] diff_h = lossLayer.bottom_diff(this.lstm_node_list.get(idx).state.h, y[idx]);
        double[] diff_s = new double[this.mem_cell_cnt];
        this.lstm_node_list.get(idx).top_diff_is(diff_h, diff_s);
        idx -= 1;

        while (idx >= 0) {
            loss += lossLayer.loss(this.lstm_node_list.get(idx).state.h, y[idx]);
            diff_h = lossLayer.bottom_diff(this.lstm_node_list.get(idx).state.h, y[idx]);
            diff_h = add(diff_h, this.lstm_node_list.get(idx + 1).state.bottom_diff_h);
            diff_s = this.lstm_node_list.get(idx + 1).state.bottom_diff_s;
            this.lstm_node_list.get(idx).top_diff_is(diff_h, diff_s);
            idx -= 1;
        }

        return loss;
    }

    /**
     * This method adds an input to the LSTM model.
     *
     * @param x The input to be added.
     */
    public void x_list_add(double[] x) {
        x_list.add(x);
        if (x_list.size() > lstm_node_list.size()) {
            LSTMState state = new LSTMState(this.mem_cell_cnt, this.x_dim);
            lstm_node_list.add(new LSTMNode(state, this.param));
        }

        int idx = x_list.size() - 1;
        if (idx == 0) {
            this.lstm_node_list.get(idx).bottom_data_is(x, null, null);
        }
        else {
            double[] s_prev = this.lstm_node_list.get(idx - 1).state.s;
            double[] h_prev = this.lstm_node_list.get(idx - 1).state.h;
            this.lstm_node_list.get(idx).bottom_data_is(x, s_prev, h_prev);
        }
    }

    public static double[] hstack(double[] a, double[] b) {
        double[] ret = new double[a.length + b.length];
        int k = 0;
        for (int i = 0; i < a.length; ++i) ret[k++] = a[i];
        for (int i = 0; i < b.length; ++i) ret[k++] = b[i];
        return ret;
    }

    public static double[][] hstack(double[][] a, double[][] b) {
        double[][] ret = new double[a.length][];
        for (int i = 0; i < a.length; ++i) ret[i] = hstack(a[i], b[i]);
        return ret;
    }

    public static double sigmoid(double x) {
        return 1 / (1 + Math.exp(-x));
    }

    public static double[] sigmoid(double[] a) {
        double[] b = new double[a.length];
        for (int i = 0; i < a.length; ++i) b[i] = sigmoid(a[i]);
        return b;
    }

    public static double sigmoid_derivative(double v) {
        return v * (1 - v);
    }

    public static double[] sigmoid_derivative(double[] v) {
        double[] ret = new double[v.length];
        for (int i = 0; i < v.length; ++i) ret[i] = sigmoid_derivative(v[i]);
        return ret;
    }

    public static double tanh_derivative(double v) {
        return 1 - v * v;
    }

    public static double[] tanh_derivative(double[] v) {
        double[] ret = new double[v.length];
        for (int i = 0; i < v.length; ++i) ret[i] = tanh_derivative(v[i]);
        return ret;
    }

    /**
     * This method generates a 2D array with random double values within a specified range.
     *
     * @param a The lower bound of the random double values.
     * @param b The upper bound of the random double values.
     * @param x The number of rows in the 2D array.
     * @param y The number of columns in the 2D array.
     * @return  The generated 2D array.
     */
    public static double[][] rand_arr(double a, double b, int x, int y){
        double[][] ret = new double[x][y];
        Random random = new Random(2016666);
        for (int i = 0; i < x; ++i) {
            for (int j = 0; j < y; ++j) {
                ret[i][j] = random.nextDouble() * (b - a) + a;
            }
        }
        return ret;
    }

    /**
     * This method generates an array with random double values within a specified range.
     *
     * @param a The lower bound of the random double values.
     * @param b The upper bound of the random double values.
     * @param x The length of the array.
     * @return  The generated array.
     */
    public static double[] rand_vec(double a, double b, int x){
        double[] ret = new double[x];
        Random random = new Random(2016666);
        for (int i = 0; i < x; ++i) {
            ret[i] = random.nextDouble() * (b - a) + a;
        }
        return ret;
    }

    /**
     * This method generates an array of zeros with the same length as the input array.
     *
     * @param a The input array.
     * @return  The generated array of zeros.
     */
    public static double[] zero_like(double[] a) {
        double[] b = new double[a.length];
        return b;
    }

    /**
     * This method generates a 2D array of zeros with the same dimensions as the input 2D array.
     *
     * @param a The input 2D array.
     * @return  The generated 2D array of zeros.
     */
    public static double[][] zero_like(double[][] a) {
        double[][] b = new double[a.length][a[0].length];
        return b;
    }

    /**
     * This method calculates the dot product of two arrays.
     *
     * @param a The first array.
     * @param b The second array.
     * @return  The dot product of the two arrays.
     */
    public static double dot(double[] a, double[] b) {
        final double[] result = new double[1];
        final double[] arrayA = a;
        final double[] arrayB = b;

        Kernel kernel = new Kernel(){
            @Override
            public void run() {
                int i = getGlobalId();
                result[0] += arrayA[i] * arrayB[i];
            }
        };

        Range range = Range.create(a.length);
        kernel.execute(range);

        return result[0];
    }

    /**
     * This method calculates the dot product of a 2D array and an array.
     *
     * @param a The 2D array.
     * @param b The array.
     * @return  The dot product of the 2D array and the array.
     */
    public static double[] dot(double[][] a, double[] b) {
        double[] ret = new double[a.length];
        for (int i = 0; i < a.length; ++i) {
            ret[i] = dot(a[i], b);
        }
        return ret;
    }

    /**
     * This method calculates the element-wise multiplication of two arrays.
     *
     * @param a The first array.
     * @param b The second array.
     * @return  The element-wise multiplication of the two arrays.
     */
    public static double[] mat(double[] a, double[] b) {
        double[] ret = new double[a.length];
        for (int i = 0; i < a.length; ++i) ret[i] = a[i] * b[i];
        return ret;
    }

    /**
     * This method transposes a 2D array.
     *
     * @param a The 2D array to be transposed.
     * @return  The transposed 2D array.
     */
    public static double[][] transpose(double[][] a){
        int n = a.length;
        int m = a[0].length;
        double[][] ret = new double[m][n];
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                ret[i][j] = a[j][i];
            }
        }
        return ret;
    }

    /**
     * This method calculates the element-wise addition of two arrays.
     *
     * @param a The first array.
     * @param b The second array.
     * @return  The element-wise addition of the two arrays.
     */
    public static double[] add(double[] a, double[] b) {
        double[] ret = new double[a.length];
        for (int i = 0; i < a.length; ++i) ret[i] = a[i] + b[i];
        return ret;
    }

    /**
     * This method calculates the element-wise addition of two 2D arrays.
     *
     * @param a The first 2D array.
     * @param b The second 2D array.
     * @return  The element-wise addition of the two 2D arrays.
     */
    public static double[][] add(double[][] a, double[][] b){
        double[][] ret = new double[a.length][a[0].length];
        for (int i = 0; i < a.length; ++i) {
            for (int j = 0; j < a[0].length; ++j) {
                ret[i][j] = a[i][j] + b[i][j];
            }
        }
        return ret;
    }

    /**
     * This method calculates the outer product of two arrays.
     *
     * @param a The first array.
     * @param b The second array.
     * @return  The outer product of the two arrays.
     */
    public static double[][] outer(double[] a, double[] b){
        int n = a.length;
        int m = b.length;
        double[][] ret = new double[n][m];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                ret[i][j] = a[i] * b[j];
            }
        }
        return ret;
    }

    /**
     * This method extracts a subarray from an array.
     *
     * @param a The input array.
     * @param l The start index of the subarray.
     * @param r The end index of the subarray.
     * @return  The extracted subarray.
     */
    public static double[] dim(double[] a, int l, int r) {
        int len = r - l;
        double[] ret = new double[len];
        for (int i = l; i < r; ++i) {
            ret[i - l] = a[i];
        }
        return ret;
    }

    /**
     * This method calculates the weighted sum of an array and a 2D array, and adds a bias.
     *
     * @param w The 2D array of weights.
     * @param x The array.
     * @param b The bias.
     * @return  The calculated result.
     */
    public static double[] WtxPlusBias(double[][] w, double[] x, double[] b) {
        int n = w.length;
        double[] ans = new double[n];
        for (int i = 0; i < n; ++i) {
            double wtx = dot(w[i], x);
            ans[i] = wtx + b[i];
        }
        return ans;
    }

    /**
     * This method applies the hyperbolic tangent function element-wise to an array.
     *
     * @param a The input array.
     * @return  The array after applying the hyperbolic tangent function.
     */
    public static double[] tanh(double[] a) {
        double[] b = new double[a.length];
        for (int i = 0; i < a.length; ++i) b[i] = Math.tanh(a[i]);
        return b;
    }

//    public static void main(String[] args) {
//        Random random = new Random(2016666);
//        int mem_cell_cnt = 100;
//        int x_dim = 50;
//        gLSTM lstm = new gLSTM(mem_cell_cnt, x_dim, 0.1);
//
//        // input
//        double[] y = {-0.5, 0.6, 0.3, -0.5};
//        double[][] X = new double[y.length][x_dim]; // x_1, x_2, x_3, x_4, ... , x_50
//
//        for (int i = 0; i < X.length; ++i) {
//            for (int j = 0; j < X[0].length; ++j) {
//                X[i][j] = random.nextDouble();
//            }
//        }
//
//        for (int cut_iter = 0; cut_iter < 1000; ++cut_iter) {
//            System.out.print("iter: " + cut_iter + ": ");
//            for (int i = 0; i < y.length; ++i) {
//                lstm.x_list_add(X[i]);
//            }
//            String[] predict = new String[y.length];
//            for (int i = 0; i < y.length; ++i) {
//                predict[i] = lstm.lstm_node_list.get(i).state.h[0] + "";
//            }
//            System.out.print("y_pred = [" + String.join(",", predict) + "], ");
//            double loss = lstm.y_list_is(y, new ToyLossLayer());
//            lstm.param.apply_diff(0.1);
//            System.out.println("loss: " + loss);
//            lstm.clear();
//        }
//
//    }
}



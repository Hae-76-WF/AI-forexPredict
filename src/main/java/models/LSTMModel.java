package models;

import models.Lstm.LSTM;
import models.Lstm.ToyLossLayer;

import java.util.Random;

public class LSTMModel extends LSTM {
    public LSTMModel(int mem_cell_cnt, int x_dim, double lr) {
        super(mem_cell_cnt, x_dim, lr);
    }

    public static void main(String[] args) {
        Random random = new Random(2016666);
        int mem_cell_cnt = 100;
        int x_dim = 50;
        LSTM lstm = new LSTMModel(mem_cell_cnt, x_dim, 0.1);

        // input
        double[] y = {-0.5, 0.6, 0.3, -0.5};
        double[][] X = new double[y.length][x_dim]; // x_1, x_2, x_3, x_4, ... , x_50

        for (int i = 0; i < X.length; ++i) {
            for (int j = 0; j < X[0].length; ++j) {
                X[i][j] = random.nextDouble();
            }
        }

        for (int cut_iter = 0; cut_iter < 1000; ++cut_iter) {
            System.out.print("iter: " + cut_iter + ": ");
            for (int i = 0; i < y.length; ++i) {
                lstm.x_list_add(X[i]);
            }
            String[] predict = new String[y.length];
            for (int i = 0; i < y.length; ++i) {
                predict[i] = lstm_node_list.get(i).state.h[0] + "";
            }
            System.out.print("y_pred = [" + String.join(",", predict) + "], ");
            double loss = lstm.y_list_is(y, new ToyLossLayer());
            lstm.param.apply_diff(0.1);
            System.out.println("loss: " + loss);
            lstm.clear();
        }

    }

}
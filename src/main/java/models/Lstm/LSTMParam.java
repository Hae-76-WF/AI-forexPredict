package models.Lstm;

/**
 * This class represents the parameters of an LSTM (Long Short-Term Memory) model.
 */
public class LSTMParam{

    int mem_cell_cnt;
    int x_dim;
    int concat_len;

    double[][] wg, wi, wf, wo;
    double[][] wg_diff, wi_diff, wf_diff, wo_diff;
    double[] bg, bi, bf, bo;
    double[] bg_diff, bi_diff, bf_diff, bo_diff;

    /**
     * This constructor initializes the LSTM parameters with the given memory cell count and dimension.
     *
     * @param mem_cell_cnt The number of memory cells in the LSTM model.
     * @param x_dim        The dimension of the input to the LSTM model.
     */
    public LSTMParam(int mem_cell_cnt, int x_dim) {
        this.mem_cell_cnt = mem_cell_cnt;
        this.x_dim = x_dim;
        this.concat_len = mem_cell_cnt + x_dim;

        this.wg = LSTM.rand_arr(-0.1, 0.1, mem_cell_cnt, concat_len);
        this.wf = LSTM.rand_arr(-0.1, 0.1, mem_cell_cnt, concat_len);
        this.wi = LSTM.rand_arr(-0.1, 0.1, mem_cell_cnt, concat_len);
        this.wo = LSTM.rand_arr(-0.1, 0.1, mem_cell_cnt, concat_len);

        this.bg = LSTM.rand_vec(-0.1, 0.1, mem_cell_cnt);
        this.bf = LSTM.rand_vec(-0.1, 0.1, mem_cell_cnt);
        this.bo = LSTM.rand_vec(-0.1, 0.1, mem_cell_cnt);
        this.bi = LSTM.rand_vec(-0.1, 0.1, mem_cell_cnt);

        this.wg_diff = new double[mem_cell_cnt][concat_len];
        this.wo_diff = new double[mem_cell_cnt][concat_len];
        this.wi_diff = new double[mem_cell_cnt][concat_len];
        this.wf_diff = new double[mem_cell_cnt][concat_len];

        this.bg_diff = new double[mem_cell_cnt];
        this.bo_diff = new double[mem_cell_cnt];
        this.bi_diff = new double[mem_cell_cnt];
        this.bf_diff = new double[mem_cell_cnt];
    }

    /**
     * This method applies the difference to the weights and biases using the given learning rate.
     *
     * @param lr The learning rate.
     */
    public void apply_diff(double lr) {
        reduce(wg, wg_diff, lr);
        reduce(wf, wf_diff, lr);
        reduce(wo, wo_diff, lr);
        reduce(wi, wi_diff, lr);

        reduce(bf, bf_diff, lr);
        reduce(bg, bg_diff, lr);
        reduce(bo, bo_diff, lr);
        reduce(bi, bi_diff, lr);

        this.wg_diff = new double[mem_cell_cnt][concat_len];
        this.wo_diff = new double[mem_cell_cnt][concat_len];
        this.wi_diff = new double[mem_cell_cnt][concat_len];
        this.wf_diff = new double[mem_cell_cnt][concat_len];

        this.bg_diff = new double[mem_cell_cnt];
        this.bo_diff = new double[mem_cell_cnt];
        this.bi_diff = new double[mem_cell_cnt];
        this.bf_diff = new double[mem_cell_cnt];
    }

    /**
     * This private method reduces the weights by the product of the learning rate and the weight difference.
     *
     * @param w      The weights.
     * @param w_diff The weight difference.
     * @param lr     The learning rate.
     */
    private void reduce(double[][] w, double[][] w_diff, double lr) {
        int n = w.length;
        int m = w[0].length;
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                w[i][j] -= lr * w_diff[i][j];
            }
        }
    }

    /**
     * This private method reduces the biases by the product of the learning rate and the bias difference.
     *
     * @param b      The biases.
     * @param b_diff The bias difference.
     * @param lr     The learning rate.
     */
    private void reduce(double[] b, double[] b_diff, double lr) {
        int n = b.length;
        for (int i = 0; i < n; ++i) {
            b[i] -= lr * b_diff[i];
        }
    }
}

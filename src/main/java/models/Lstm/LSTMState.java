package models.Lstm;

/**
 * This class represents the state of an LSTM (Long Short-Term Memory) model.
 */
public class LSTMState{

    double[] g;
    double[] i;
    double[] f;
    double[] o;
    double[] s;
    public double[] h;
    double[] bottom_diff_h;
    double[] bottom_diff_s;

    /**
     * This constructor initializes the LSTM state with the given memory cell count and dimension.
     *
     * @param mem_cell_cnt The number of memory cells in the LSTM model.
     * @param x_dim        The dimension of the input to the LSTM model.
     */
    public LSTMState(int mem_cell_cnt, int x_dim) {
        this.g = new double[mem_cell_cnt];
        this.i = new double[mem_cell_cnt];
        this.f = new double[mem_cell_cnt];
        this.o = new double[mem_cell_cnt];
        this.s = new double[mem_cell_cnt];
        this.h = new double[mem_cell_cnt];
        this.bottom_diff_h = new double[mem_cell_cnt];
        this.bottom_diff_s = new double[mem_cell_cnt];
    }
}

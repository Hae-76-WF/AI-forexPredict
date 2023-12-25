package models;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases for the SVM class.
 */
class SVMTest {
    SVM svm;
    @BeforeEach
    void setUp() {
        svm = new SVM(0.001, 1000);
    }

    @AfterEach
    void tearDown() {
    }

    /**
     * Test case for the train method with a fixed learning rate.
     */
    @Test
    public void testTrainFixedLearningRate() {
        double[][] inputs = {{1, 2}, {2, 3}, {3, 4}};
        int[] targets = {1, -1, 1};
        svm.train(inputs, targets);
        
        assertNotNull(svm);
    }

    /**
     * Test case for the train method with a variable learning rate.
     */
    @Test
    public void testTrainVariableLearningRate() {
        double[][] inputs = {{1, 2}, {2, 3}, {3, 4}};
        int[] targets = {1, -1, 1};
        svm.train(inputs, targets, 0.01);
        
        assertNotNull(svm);
    }

    /**
     * Test case for the predict method.
     */
    @Test
    public void testPredict() {
        double[][] inputs = {{1, 2}, {2, 3}, {3, 4}};
        int[] targets = {1, -1, 1};
        svm.train(inputs, targets);
        int prediction = svm.predict(new double[]{4, 5});
        
        assertTrue(prediction == 1 || prediction == -1);
    }

    /**
     * Test case for the validate method.
     */
    @Test
    public void testValidate() {
        double[][] inputs = {{1, 2}, {2, 3}, {3, 4}};
        int[] targets = {1, -1, 1};
        svm.train(inputs, targets);
        double accuracy = svm.validate(inputs, targets);
        
        assertTrue(accuracy >= 0 && accuracy <= 1);
    }

    /**
     * Test case for the constructor.
     */
    @Test
    public void testConstructor() {
        assertNotNull(svm);
    }

}
package coursera_stanford_2013.week3;

import coursera_stanford_2013.util.RandomArrayGenerator;
import coursera_stanford_2013.week2.util.PartitioningAssertion;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RandomizedSelectionTest {

    @Test
    public void test_strait_order() {
        int[] input = {1, 2, 3, 4, 5, 6};

        assertEquals(1, RandomizedSelection.select(input, 1));
        assertEquals(2, RandomizedSelection.select(input, 2));
        assertEquals(3, RandomizedSelection.select(input, 3));
        assertEquals(4, RandomizedSelection.select(input, 4));
        assertEquals(5, RandomizedSelection.select(input, 5));
        assertEquals(6, RandomizedSelection.select(input, 6));
    }

    @Test
    public void test_reverse_order() {
        int[] input = {6, 5, 4, 3, 2, 1};

        assertEquals(1, RandomizedSelection.select(input, 1));
        assertEquals(2, RandomizedSelection.select(input, 2));
        assertEquals(3, RandomizedSelection.select(input, 3));
        assertEquals(4, RandomizedSelection.select(input, 4));
        assertEquals(5, RandomizedSelection.select(input, 5));
        assertEquals(6, RandomizedSelection.select(input, 6));
    }

    @Test
    public void test_with_random_values() {
        int[] input = RandomArrayGenerator.generateArray(10, 10);
        int oderStatistics = 1;

        int selectValue = RandomizedSelection.select(input, 1);

        Arrays.sort(input);

        assertEquals(input[oderStatistics - 1], selectValue);
    }


    @Test
    public void partitioningTest() {
        // given
        int[] inputArray = {7, 6, 5, 10, 4, 15};
        int pivotValue = 7;

        // when

        RandomizedSelection.partition(inputArray, 0, inputArray.length);

        // then
        PartitioningAssertion.assertNotLessThanAndNotGreaterThanPivotValue(inputArray, pivotValue);
    }

    @Test
    public void partitioningTest_Bounds_eq_one() {
        // given
        int[] inputArray = {1, 2, 3, 4, 5};
        int[] expectedArray = {1, 2, 3, 4, 5};

        // when
        RandomizedSelection.partition(inputArray, 0, 1);

        // then
        assertTrue(Arrays.equals(inputArray, expectedArray));
    }
}

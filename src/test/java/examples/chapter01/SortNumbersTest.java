package examples.chapter01;

import static examples.chapter01.SortNumbers.sortInPlace;
import static org.junit.Assert.assertArrayEquals;
import org.junit.Test;

public class SortNumbersTest {

  @Test
  public void sortInPlaceShouldSortInPlace() {
    testCase(new double[0], new double[0]);

    double[] input1 = {1.0};
    double[] expected1 = {1.0};
    testCase(input1, expected1);

    double[] input2 = {2.0, 1.0};
    double[] expected2 = {1.0, 2.0};
    testCase(input2, expected2);

    double[] input3 = {1.0, 2.0};
    double[] expected3 = {1.0, 2.0};
    testCase(input3, expected3);

    double[] input4 = {2.0, 1.0, 3.0, 5.0, 4.0};
    double[] expected4 = {1.0, 2.0, 3.0, 4.0, 5.0};
    testCase(input4, expected4);
  }

  private static void testCase(double[] input, double[] expected) {
    sortInPlace(input);
    assertArrayEquals(expected, input, 1.0e-10);
  }
}

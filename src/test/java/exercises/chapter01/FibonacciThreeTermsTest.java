package exercises.chapter01;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static exercises.chapter01.FibonacciThreeTerms.fibonacci;
import static org.junit.Assert.assertArrayEquals;

public class FibonacciThreeTermsTest {

  @Test
  public void fibonacciShouldComputeFibonacciSequences() {
    assertArrayEquals(new int[0], fibonacci(0));
    assertArrayEquals(array(Collections.singletonList(1)), fibonacci(1));
    assertArrayEquals(array(Arrays.asList(1, 1)), fibonacci(2));
    assertArrayEquals(array(Arrays.asList(1, 1, 2)), fibonacci(3));
    assertArrayEquals(array(Arrays.asList(1, 1, 2, 4)), fibonacci(4));
    assertArrayEquals(array(Arrays.asList(1, 1, 2, 4, 7)), fibonacci(5));
    assertArrayEquals(array(Arrays.asList(1, 1, 2, 4, 7, 13)), fibonacci(6));
  }

  @Test(expected = IllegalArgumentException.class)
  public void fibonacciShouldThrowBelowZero() {
    fibonacci(-1);
  }

  private static <T> int[] array(List<Integer> xs) {
    int[] out = new int[xs.size()];

    for(int idx = 0; idx < out.length; idx++) {
      out[idx] = xs.get(idx);
    }

    return out;
  }
}

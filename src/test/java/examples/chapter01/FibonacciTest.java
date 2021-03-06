package examples.chapter01;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static examples.chapter01.Fibonacci.fibonacci;
import static org.junit.Assert.assertEquals;

public class FibonacciTest {

  @Test
  public void fibonacciShouldComputeFibonacciSequences() {
    assertEquals("fibonacci(0)", Collections.emptyList(), fibonacci(0));
    assertEquals("fibonacci(1)", Collections.singletonList(1), fibonacci(1));
    assertEquals("fibonacci(2)", Arrays.asList(1, 1), fibonacci(2));
    assertEquals("fibonacci(3)", Arrays.asList(1, 1, 2), fibonacci(3));
    assertEquals("fibonacci(4)", Arrays.asList(1, 1, 2, 3), fibonacci(4));
    assertEquals("fibonacci(5)", Arrays.asList(1, 1, 2, 3, 5), fibonacci(5));
  }

  @Test(expected = IllegalArgumentException.class)
  public void fibonacciShouldThrowBelowZero() {
    fibonacci(-1);
  }
}

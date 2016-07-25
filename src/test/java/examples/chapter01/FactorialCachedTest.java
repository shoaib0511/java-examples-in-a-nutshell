package examples.chapter01;

import static examples.chapter01.FactorialCached.factorial;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import org.junit.Test;

public class FactorialCachedTest {

  @Test
  public void factorialShouldComputeFactorials() {
    assertEquals("factorial(0)", 1, factorial(0));
    assertEquals("factorial(1)", 1, factorial(1));
    assertEquals("factorial(2)", 2, factorial(2));
    assertEquals("factorial(3)", 6, factorial(3));
    assertEquals("factorial(4)", 24, factorial(4));
    assertEquals("factorial(20)", 2432902008176640000L, factorial(20));
  }

  @Test
  public void factorialShouldCacheValues() {
    FactorialCached fact = spy(new FactorialCached());

    assertEquals("factorial(4)", 24, fact.factorial(4));
    assertEquals("factorial(4)", 24, fact.factorial(4));

    verify(fact, times(1)).computeAndCache(4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void factorialShouldThrowBelowZero() {
    factorial(-1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void factorialShouldThrowAbove20() {
    factorial(21);
  }

}

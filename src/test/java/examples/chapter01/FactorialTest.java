package examples.chapter01;

import static examples.chapter01.Factorial.factorial;
import static examples.chapter01.Factorial.factorialRec;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class FactorialTest {

  @Test
  public void factorialShouldComputeFactorials() {
    assertEquals("factorial(0)", 1, factorial(0));
    assertEquals("factorial(1)", 1, factorial(1));
    assertEquals("factorial(2)", 2, factorial(2));
    assertEquals("factorial(3)", 6, factorial(3));
    assertEquals("factorial(4)", 24, factorial(4));
  }

  @Test(expected = IllegalArgumentException.class)
  public void factorialShouldThrowBelowZero() {
    Factorial.factorial(-1);
  }

  @Test
  public void factorialRecShouldComputeFactorials() {
    assertEquals("factorialRec(0)", 1, factorialRec(0));
    assertEquals("factorialRec(1)", 1, factorialRec(1));
    assertEquals("factorialRec(2)", 2, factorialRec(2));
    assertEquals("factorialRec(3)", 6, factorialRec(3));
    assertEquals("factorialRec(4)", 24, factorialRec(4));
  }

  @Test(expected=IllegalArgumentException.class)
  public void factorialRecShouldThrowBelowZero() {
    Factorial.factorialRec(-1);
  }

}

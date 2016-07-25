package examples.chapter01;

import static examples.chapter01.FactorialBig.factorial;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

import java.math.BigInteger;

public class FactorialBigTest {

  @Test
  public void factorialShouldComputeFactorials() {
    assertEquals("factorial(0)", BigInteger.valueOf(1), factorial(0));
    assertEquals("factorial(1)", BigInteger.valueOf(1), factorial(1));
    assertEquals("factorial(2)", BigInteger.valueOf(2), factorial(2));
    assertEquals("factorial(3)", BigInteger.valueOf(6), factorial(3));
    assertEquals("factorial(4)", BigInteger.valueOf(24), factorial(4));
    assertEquals("factorial(20)", BigInteger.valueOf(2432902008176640000L), factorial(20));

    assertEquals(
      "factorial(50)",
      new BigInteger("30414093201713378043612608166064768844377641568960512000000000000"),
      factorial(50)
    );
  }

  @Test(expected = IllegalArgumentException.class)
  public void factorialShouldThrowBelowZero() {
    factorial(-1);
  }

}

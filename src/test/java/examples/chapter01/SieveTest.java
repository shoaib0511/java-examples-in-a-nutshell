package examples.chapter01;

import static examples.chapter01.Sieve.largestPrimeUpTo;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class SieveTest {

  @Test
  public void largestPrimeUpToShouldFindTheLargestPrime() {
    assertEquals("largestPrimeUpTo(2)", 2, largestPrimeUpTo(2));
    assertEquals("largestPrimeUpTo(3)", 3, largestPrimeUpTo(3));
    assertEquals("largestPrimeUpTo(4)", 3, largestPrimeUpTo(4));
    assertEquals("largestPrimeUpTo(5)", 5, largestPrimeUpTo(5));
    assertEquals("largestPrimeUpTo(6)", 5, largestPrimeUpTo(6));
    assertEquals("largestPrimeUpTo(7)", 7, largestPrimeUpTo(7));
    assertEquals("largestPrimeUpTo(8)", 7, largestPrimeUpTo(8));
    assertEquals("largestPrimeUpTo(347)", 347, largestPrimeUpTo(347));
    assertEquals("largestPrimeUpTo(348)", 347, largestPrimeUpTo(348));
  }

  @Test(expected = IllegalArgumentException.class)
  public void largestPrimeUpToShouldThrowBelowTwo() {
    largestPrimeUpTo(1);
  }
}

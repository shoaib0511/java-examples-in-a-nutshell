package examples.chapter01;

import static examples.chapter01.Functional.lastIdx;
import static java.util.Arrays.fill;

public class Sieve {

  /**
   * Computes the largest prime up to or equal to some int
   *
   * @param max the computed prime will be this large or smaller
   * @return the computed prime
   */
  public static int largestPrimeUpTo(int max) {
    if (max < 2) throw new IllegalArgumentException("max must be >= 2");

    // stores if prime
    Boolean[] isPrime = new Boolean[max + 1];

    // assume prime until we prove not prime
    fill(isPrime, true);
    isPrime[0] = isPrime[1] = false;

    int checkUpTo = (int) Math.ceil(Math.sqrt(max));

    for(int i = 0; i <= checkUpTo; i++) {

      // if item is prime, mark all of its multiples as not prime
      if (isPrime[i]) {
        for(int j = i*2; j <= max; j += i) {
          isPrime[j] = false;
        }
      }
    }

    return lastIdx(isPrime, i -> i);
  }
}

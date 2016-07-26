package exercises.chapter01;

public class FibonacciThreeTerms {

  /**
   * Calculates a Fibonacci-esque sequence, except each term is the sum of the previous 3 terms, instead of the sum of
   * the previous 2.
   *
   * @param n length of sequence to return
   * @return Fibonacci-esque sequence
   */
  public static int[] fibonacci(int n) {
    if (n < 0) throw new IllegalArgumentException("n must not be negative");

    int[] out = new int[n];
    if (n >= 1) out[0] = 1;
    if (n >= 2) out[1] = 1;
    if (n >= 3) out[2] = 2;

    for(int idx = 3; idx < out.length; idx++) {
      out[idx] = out[idx - 1] + out[idx - 2] + out[idx - 3];
    }

    return out;
  }
}

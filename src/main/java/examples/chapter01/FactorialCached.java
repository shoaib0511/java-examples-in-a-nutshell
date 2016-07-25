package examples.chapter01;

/**
 * This class computes factorials and caches the results in a table. It maxes out at 20!, because that's the highest
 * factorial we can compute with a long. It throws an IllegalArgumentException if the factorial you ask for is too big
 * or too small
 */
public class FactorialCached {
  // For caching values 0! through 20!
  static long[] table = new long[21];

  // A "static initializer" - we know that the factorial of 0 is 1
  static { table[0] = 1; }

  // Remember the highest initialized value in the array
  static int last = 0;

  static void computeAndCache(int x) {
    while (last < x) {
      table[last + 1] = table[last] * (last + 1);  // so if we'd previously stored 18!, now store 19!
      last++;
    }
  }

  public static long factorial(int x) throws IllegalArgumentException {
    // Check that x is in bounds
    if (x >= table.length)
      throw new IllegalArgumentException("Overflow; x is too large, must be < " + table.length + ", was " + x);
    if (x < 0)
      throw new IllegalArgumentException("x must be non-negative, was " + x);

    // Compute and cache any values that are not yet cached. Skips entirely if already cached
    computeAndCache(x);

    // Return the cached factorial
    return table[x];
  }
}

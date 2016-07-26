package examples.chapter01;

import java.math.BigInteger;
import java.util.ArrayList;

public class FactorialBig {
  protected static ArrayList<BigInteger> table = new ArrayList<>();
  static {
    table.add(BigInteger.valueOf(1));
  }

  protected static void computeAndCache(int upTo) {
    for (int size = table.size(); size <= upTo; size++) {
      BigInteger lastFactorial = table.get(size - 1);
      BigInteger nextFactorial = lastFactorial.multiply(BigInteger.valueOf(size));
      table.add(nextFactorial);
    }
  }

  public static synchronized BigInteger factorial(int x) {
    if (x < 0) throw new IllegalArgumentException("x must be non-negative.");

    computeAndCache(x);
    return table.get(x);
  }
}

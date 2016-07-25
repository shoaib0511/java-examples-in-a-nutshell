package examples.chapter01;

import java.util.ArrayList;
import java.util.List;

public class Fibonacci {
  public static List<Integer> fibonacci(int x) {
    if (x < 0) throw new IllegalArgumentException("x must be >= 0");

    int n1 = 1, n2 = 1, placeholder;
    List<Integer> fib = new ArrayList<>();

    if (x >= 1) fib.add(n1);
    if (x >= 2) fib.add(n2);

    for(int i = 2; i < x; i++) {
      placeholder = n2 + n1;
      n1 = n2;
      n2 = placeholder;

      fib.add(n2);
    }

    return fib;
  }
}

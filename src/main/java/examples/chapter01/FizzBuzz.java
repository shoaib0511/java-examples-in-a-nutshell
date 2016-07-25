package examples.chapter01;

import java.util.ArrayList;
import java.util.List;

public class FizzBuzz {

  public static List<String> fizzBuzz(int n) {
    if (n < 0) throw new IllegalArgumentException("n must be >= 0");

    List<String> out = new ArrayList<>();

    for (int i = 1; i <= n; i++) {
      if (i % 5 == 0 && i % 7 == 0) out.add("fizbuzz");
      else if (i % 5 == 0)          out.add("fizz");
      else if (i % 7 == 0)          out.add("buzz");
      else                          out.add(String.valueOf(i));
    }

    return out;
  }

  public static List<String> fizzBuzzSwitch(int n) throws IllegalArgumentException {
    if (n < 0 || n > 35) throw new IllegalArgumentException("n must be between 0 and 35");

    List<String> out = new ArrayList<>();

    for (int i = 1; i <= n; i++) {
      switch (i % 35) {
        case 0:
          out.add("fizbuzz");
          break;
        case 5:case 10:case 15:case 20:case 25:case 30:
          out.add("fizz");
          break;
        case 7:case 14:case 21:case 28:
          out.add("buzz");
          break;
        default:
          out.add(String.valueOf(i));
          break;
      }
    }

    return out;
  }
}

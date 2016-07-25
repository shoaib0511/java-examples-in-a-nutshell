package examples.chapter01;

import java.util.ArrayList;
import java.util.List;

public class Reverse {

  public static List<String> reverse(List<String> xs) {
    List<String> out = new ArrayList<>();

    // loop backwards through list
    for(int i = xs.size()-1; i >= 0; i--) {
      String x = xs.get(i);
      StringBuilder reversed = new StringBuilder();

      // reverse the string
      for(int j = x.length()-1; j >= 0; j--) {
        reversed.append(x.charAt(j));
      }

      out.add(reversed.toString());
    }

    return out;
  }
}

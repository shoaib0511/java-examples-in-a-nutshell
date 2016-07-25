package examples.chapter01;

import java.io.*;

/**
 * To build and run:
 *   $ javac -sourcepath src/main/java -d build/manual src/main/java/examples/chapter01/Rot13Input.java
 *   $ java -cp build/manual examples.chapter01.Rot13Input
 */
public class Rot13Input {

  public static void main(String[] args) {
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

    for(;;) {
      System.out.print("> ");

      try {
        String line = in.readLine();
        if (line == null || line.equals("quit")) break;

        StringBuilder sb = new StringBuilder(line);

        for (int i = 0; i < sb.length(); i++) {
          sb.setCharAt(i, rot13(sb.charAt(i)));
        }

        System.out.println(sb);
      }

      catch (Exception e) {
        System.out.println("Invalid input");
      }
    }
  }

  /**
   * Performs Rot13 substitution cipher. Returns a new char that is 13 chars ahead of the input. Both encodes and
   * decodes, since c == rot13(rot13(c))
   *
   * @param c char to encode
   * @return encoded char
   */
  static char rot13(char c) {
    if (c >= 'A' && c <= 'Z') {  // uppercase
      c += 13;
      if (c > 'Z') c -= 26;      // don't overflow
    }

    if (c >= 'a' && c <= 'z') {  // lowercase
      c += 13;
      if (c > 'z') c -= 26;
    }

    return c;
  }

}

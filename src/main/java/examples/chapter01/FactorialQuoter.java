package examples.chapter01;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import static examples.chapter01.FactorialBig.factorial;

/**
 * To build and run:
 *   $ javac -sourcepath src/main/java -d build/manual src/main/java/examples/chapter01/FactorialQuoter.java
 *   $ java -cp build/manual examples.chapter01.FactorialQuoter
 */
public class FactorialQuoter {

  public static void main(String[] args) {
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

    while (true) {
      // Display prompt
      System.out.print("<FactorialQuoter> ");

      // Read a line from the user
      try {
        String line = in.readLine();

        // Exit if we reach EOF, or user types "quit"
        if (line == null || line.equals("quit")) break;

        int x = Integer.parseInt(line);
        System.out.println(x + "! = " + factorial(x));
      }

      catch (Exception e) {
        System.out.println("Invalid input");
      }
    }
  }

}

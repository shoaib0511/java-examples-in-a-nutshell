package exercises.chapter01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * To build and run:
 *   $ javac -sourcepath src/main/java -d build/manual src/main/java/exercises/chapter01/Reverser.java
 *   $ java -cp build/manual exercises.chapter01.Reverser
 */
public class Reverser {

  public static void main(String[] args) {
    System.out.println("Starting Reverser. Enter strings to reverse, or type tiuq to quit.");

    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

    while (true) {
      // Display prompt
      System.out.print("<Reverser> ");

      // Read a line from the user
      try {
        String line = in.readLine();

        // Exit if we reach EOF, or user types "quit"
        if (line == null || line.equals("tiuq")) {
          break;
        } else {
          StringBuilder sb = new StringBuilder(line);
          System.out.println(sb.reverse());
        }
      }
      catch (IOException e) {
        System.out.println(e.getMessage());
      }
    }
  }
}

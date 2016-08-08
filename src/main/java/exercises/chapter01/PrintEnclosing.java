package exercises.chapter01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

import static examples.chapter01.SortNumbers.sortInPlace;

/**
 * To build and run:
 *   $ javac -sourcepath src/main/java -d build/manual src/main/java/exercises/chapter01/PrintEnclosing.java
 *   $ java -cp build/manual exercises.chapter01.PrintEnclosing
 */
public class PrintEnclosing {

  public static void main(String[] args) {
    int size = 100;
    double[] nums = sorted(getNums(size));
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

    System.out.println("Choose a number between " + nums[0] + " and " + nums[nums.length - 1]);

    while (true) {
      // Display prompt
      System.out.print("<PrintEnclosing> ");

      // Read a line from the user
      try {
        String input = in.readLine();

        // Exit if we reach EOF, or user types "quit"
        if (input == null || input.equals("quit")) {
          break;
        } else {
          double num = Double.parseDouble(input);
          int elemsBelow = elemsBelowTarget(num, nums);

          if (elemsBelow == 0) {
            System.out.println(num + " is below all other elements");
          } else if (elemsBelow == nums.length) {
            System.out.println(num + " is above all other elements");
          } else {
            // TODO: buggy
            System.out.println(nums[elemsBelow - 1] + " < " + num + " < " + nums[elemsBelow]);
          }
        }
      }
      catch (IOException e) {
        System.out.println(e.getMessage());
      }
    }
  }

  private static double[] getNums(int n) {
    double[] nums = new double[n];
    Random rng = new Random();

    for (int i = 0; i < nums.length; i++) {
      nums[i] = rng.nextDouble() * n;
    }

    return nums;
  }

  // TODO: buggy
  private static int elemsBelowTarget(double target, double[] searchSpace) {
    int left = 0;
    int right = searchSpace.length - 1;
    int middle;

    while (left < right) {
      middle = middle(left, right);
      double middleVal = searchSpace[middle];

      if (target < middleVal) {
        right = middle - 1;
      } else {
        left = middle + 1;
      }
    }

    return left;
  }

  private static double[] sorted(double[] ds) {
    double[] out = new double[ds.length];
    System.arraycopy(ds, 0, out, 0, ds.length);
    sortInPlace(out);

    return out;
  }

  private static int middle(int left, int right) {
    return (int) Math.floor((right + left) / 2.0);
  }
}

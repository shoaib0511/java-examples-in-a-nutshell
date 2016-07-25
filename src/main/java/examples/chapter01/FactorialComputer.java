package examples.chapter01;

import static examples.chapter01.FactorialBig.factorial;

public class FactorialComputer {

  public static void main(String[] args) {

    try {
      int x = Integer.parseInt(args[0]);
      System.out.println(x + "! = " + factorial(x));
    }

    catch (ArrayIndexOutOfBoundsException e) {
      System.out.println(
        "You must specify an argument.\n" +
        "Usage: java FactorialComputer <number>"
      );
    }

    catch (NumberFormatException e) {
      System.out.println("The argument you specify must be an integer");
    }

    catch (IllegalArgumentException e) {
      System.out.println("Bad argument: " + e.getMessage());
    }
  }

}

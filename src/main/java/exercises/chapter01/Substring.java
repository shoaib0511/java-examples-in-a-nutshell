package exercises.chapter01;

public class Substring {

  public static void main(String[] args) {
    String defaultErrorMessage = "You must provide 1 string and two integers.\n" +
      "Usage: java Substring <String string> <int start> <int end>";

    try {
      String string = args[0];
      int start = Integer.parseInt(args[1]);
      int end = Integer.parseInt(args[2]);

      System.out.println(string.substring(start, end));
    }
    catch (ArrayIndexOutOfBoundsException|NumberFormatException e) {
      System.out.println(defaultErrorMessage);
    }
    catch (StringIndexOutOfBoundsException e) {
      System.out.println("Improper indexes for substring");
    }
  }
}

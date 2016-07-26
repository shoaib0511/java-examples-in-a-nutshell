package exercises.chapter01;

public class CountPrinter {
  public static void main(String[] args) {
    int start = 1;
    int end = 15;

    // count up by 1s
    for(int i = start; i < end; i++) {
      System.out.print(i + " ");
    }

    // count down by 2s
    for(int i = end; i >= start; i -= 2) {
      System.out.print(i + " ");
    }
  }
}

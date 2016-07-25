package examples.chapter01;

public class Factorial {

  public static long factorial(long n) {
    if (n < 0) throw new IllegalArgumentException("n must be >= 0 to compute it's factorial");

    long fact = 1;

    while(n >= 2) {  // >= 2 is an optimization. There's never a need to multiply by 1, and the fact of 0 is 1
      fact *= n;
      n--;
    }

    return fact;
  }

  public static long factorialRec(long n) {
    if (n < 0) throw new IllegalArgumentException("n must be >= 0 to compute it's factorial");

    if (n == 0 || n == 1) return 1;   // stop recursion
    else return n * factorialRec(n - 1); // recurse
  }
}

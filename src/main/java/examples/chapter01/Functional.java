package examples.chapter01;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Contains methods you'd expect in a functional programming language
 */
public class Functional {

  /**
   * Return the index of the last item in a list that satisfies a predicate.
   *
   * @param xs the list to check
   * @param predicate the check to run on each item
   * @param <T> the type of items in the list
   * @return the index of the last item in the list that satisfies the predicate
   */
  public static <T> int firstIdx(List<T> xs, Function<T, Boolean> predicate) {
    int minIdx = -1;

    for(int i = 0; i < xs.size(); i++) {
      if (predicate.apply(xs.get(i))) {
        minIdx = i;
        break;
      }
    }

    return minIdx;
  }

  /**
   * Return the index of the last item in an array that satisfies a predicate.
   *
   * @param xs the array to check
   * @param predicate the check to run on each item
   * @param <T> the type of items in the array
   * @return the index of the last item in the array that satisfies the predicate
   */
  public static <T> int firstIdx(T[] xs, Function<T, Boolean> predicate) {
    int minIdx = -1;

    for(int i = 0; i < xs.length; i++) {
      if (predicate.apply(xs[i])) {
        minIdx = i;
        break;
      }
    }

    return minIdx;
  }

  /**
   * Return the index of the last item in a list that satisfies a predicate.
   *
   * @param xs the list to check
   * @param predicate the check to run on each item
   * @param <T> the type of items in the list
   * @return the index of the first item in the list that satisfies the predicate
   */
  public static <T> int lastIdx(List<T> xs, Function<T, Boolean> predicate) {
    int maxIdx = -1;

    for(int i = 0; i < xs.size(); i++) {
      if (predicate.apply(xs.get(i))) maxIdx = i;
    }

    return maxIdx;
  }

  /**
   * Return the index of the last item in an array that satisfies a predicate.
   *
   * @param xs the array to check
   * @param predicate the check to run on each item
   * @param <T> the type of items in the array
   * @return the index of the first item in the array that satisfies the predicate
   */
  public static <T> int lastIdx(T[] xs, Function<T, Boolean> predicate) {
    int maxIdx = -1;

    for(int i = 0; i < xs.length; i++) {
      if (predicate.apply(xs[i])) maxIdx = i;
    }

    return maxIdx;
  }

  /**
   * Functional foldLeft on a list.
   *
   * @param as the list to fold
   * @param zero a zero value for the first compaction
   * @param f the function to combine two items
   * @param <A> the type of items in the list
   * @param <B> the return type, and the type of the zero value
   * @return the folded value
   */
  public static <A,B> B foldLeft(List<A> as, B zero, BiFunction<B,A,B> f) {
    B accum = zero;

    for(A a: as) {
      accum = f.apply(accum, a);
    }

    return accum;
  }

  /**
   * Functional foldLeft on an array.
   *
   * @param as the array to fold
   * @param zero a zero value for the first compaction
   * @param f the function to combine two items
   * @param <A> the type of items in the array
   * @param <B> the return type, and the type of the zero value
   * @return the folded value
   */
  public static <A,B> B foldLeft(A[] as, B zero, BiFunction<B,A,B> f) {
    B accum = zero;

    for(A a: as) {
      accum = f.apply(accum, a);
    }

    return accum;
  }
}

package examples.chapter01;

public class SortNumbers {

  /**
   * Sorts array of doubles. Sorts in place, as a side effect. Note: inefficient with large arrays
   *
   * @param nums array to sort
   */
  public static void sortInPlace(double[] nums) {

    // Loop through each element in the array. Each time through, find the smallest remaining element,
    // move to the first unsorted position
    for(int i = 0; i < nums.length; i++) {
      int min = i;  // holds the index of the smallest element

      // Search array from current position to end, setting min to the smallest element in the remaining array
      for(int j = i; j < nums.length; j++) {
        if (nums[j] < nums[min]) min = j;
      }

      // Swap element at min with element at i
      double tmp = nums[i];
      nums[i] = nums[min];  // i now holds the smallest remaining element
      nums[min] = tmp;      // what was previously at i get rotated back to where the min element was
    }
  }
}

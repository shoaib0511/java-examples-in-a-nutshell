package examples.chapter01;

import static examples.chapter01.Reverse.reverse;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ReverseTest {

  @Test
  public void reverseShouldReverseStringsInAList() {
    testCase(Collections.emptyList(), Collections.emptyList());
    testCase(Collections.singletonList(""), Collections.singletonList(""));
    testCase(Collections.singletonList("a"), Collections.singletonList("a"));
    testCase(Collections.singletonList("ab"), Collections.singletonList("ba"));
    testCase(Collections.singletonList("abc"), Collections.singletonList("cba"));
  }

  @Test
  public void reverseShouldReverseLists() {
    testCase(Arrays.asList("a", "b"), Arrays.asList("b", "a"));
    testCase(Arrays.asList("a", "b", "c"), Arrays.asList("c", "b", "a"));
  }

  @Test
  public void reverseShouldReverseStringsAndLists() {
    testCase(Arrays.asList("ab", "cd"), Arrays.asList("dc", "ba"));
    testCase(Arrays.asList("ab", "cde", "fghi"), Arrays.asList("ihgf", "edc", "ba"));
  }

  private static void testCase(List<String> input, List<String> expected) {
    assertEquals("reverse(" + input + ") should be " + expected, expected, reverse(input));
  }
}

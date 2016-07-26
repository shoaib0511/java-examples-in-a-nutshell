package examples.chapter01;

import static examples.chapter01.FizzBuzz.fizzBuzz;
import static examples.chapter01.FizzBuzz.fizzBuzzSwitch;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FizzBuzzTest {
  private final List<String> FIZZ_BUZZ_35 = Arrays.asList(
    "1", "2", "3", "4", "fizz", "6", "buzz", "8", "9", "fizz", "11", "12", "13", "buzz", "fizz", "16", "17", "18", "19",
    "fizz", "buzz", "22", "23", "24", "fizz", "26", "27", "buzz", "29", "fizz", "31", "32", "33", "34", "fizbuzz"
  );

  @Test
  public void fizzBuzzShouldGenerateFizzBuzzSequences() {
    assertEquals("fizzBuzz(0)", Collections.emptyList(), fizzBuzz(0));
    assertEquals("fizzBuzz(1)", Collections.singletonList("1"), fizzBuzz(1));
    assertEquals("fizzBuzz(2)", Arrays.asList("1", "2"), fizzBuzz(2));
    assertEquals("fizzBuzz(35)", FIZZ_BUZZ_35, fizzBuzz(35));
  }

  @Test(expected = IllegalArgumentException.class)
  public void fizzBuzzShouldThrowBelowZero() {
    fizzBuzz(-1);
  }

  @Test
  public void fizzBuzzSwitchShouldGenerateFizzBuzzSequences() {
    assertEquals("fizzBuzzSwitch(0)", Collections.emptyList(), fizzBuzzSwitch(0));
    assertEquals("fizzBuzzSwitch(1)", Collections.singletonList("1"), fizzBuzzSwitch(1));
    assertEquals("fizzBuzzSwitch(2)", Arrays.asList("1", "2"), fizzBuzzSwitch(2));
    assertEquals("fizzBuzzSwitch(35)", FIZZ_BUZZ_35, fizzBuzzSwitch(35));
  }

  @Test(expected = IllegalArgumentException.class)
  public void fizzBuzzSwitchShouldThrowBelowZero() {
    fizzBuzzSwitch(-1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void fizzBuzzSwitchShouldThrowAbove35() {
    fizzBuzzSwitch(36);
  }
}

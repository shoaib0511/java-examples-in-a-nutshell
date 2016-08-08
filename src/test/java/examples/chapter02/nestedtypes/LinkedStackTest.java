package examples.chapter02.nestedtypes;

import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;

public class LinkedStackTest {

  LinkedStack stack;
  LinkableInteger one;
  LinkableInteger two;

  @Before
  public void setUp() {
    stack = new LinkedStack();
    one = new LinkableInteger(1);
    two = new LinkableInteger(2);
  }

  @Test
  public void pushShouldSetANewTop() {
    stack.push(one);
    assertEquals("{1}.peek()", ((LinkableInteger) stack.peek()).getValue(), 1);

    stack.push(two);
    assertEquals("{1,2}.peek()", ((LinkableInteger) stack.peek()).getValue(), 2);
  }

  @Test
  public void popShouldRemoveAndReturnTop() {
    stack.push(one);
    stack.push(two);

    assertEquals("{1,2}.pop()", ((LinkableInteger) stack.pop()).getValue(), 2);
    assertEquals("{1}.pop()", ((LinkableInteger) stack.pop()).getValue(), 1);
  }

  @Test
  public void iteratorShouldReturnAnIteratorOfTheCurrentStack() {
    stack.push(one);
    stack.push(two);
    Iterator<LinkedStack.Linkable> iterator = stack.iterator();

    assertEquals("{1,2}.next()", ((LinkableInteger) iterator.next()).getValue(), 2);
    assertEquals("{1}.next()", ((LinkableInteger) iterator.next()).getValue(), 1);
  }

  @Test
  public void usingIteratorShouldNotAffectTheLinkedStack() {
    stack.push(one);
    stack.push(two);
    Iterator<LinkedStack.Linkable> iterator = stack.iterator();

    iterator.next();
    iterator.next();
    assertEquals("{1,2}.peek()", ((LinkableInteger) stack.peek()).getValue(), 2);
  }

  @Test(expected = NoSuchElementException.class)
  public void peekOnEmptyStackShouldThrow() {
    stack.peek();
  }

  @Test(expected = NoSuchElementException.class)
  public void popOnEmptyStackShouldThrow() {
    stack.pop();
  }

  @Test(expected = NoSuchElementException.class)
  public void nextOnEmptyIteratorShouldThrow() {
    stack.iterator().next();
  }
}

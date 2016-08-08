package examples.chapter02.nestedtypes;

import java.util.NoSuchElementException;
import java.util.Iterator;

public class LinkedStack {

  /**
   * >>> EXAMPLE OF A STATIC MEMBER TYPE <<<
   *
   * Note that it's a static interface, and the methods are public, but the keywords are not needed
   */
  interface Linkable {
    Linkable getNext();
    void setNext(Linkable node);
  }

  private Linkable top;

  public LinkedStack() {
    top = null;
  }

  public boolean isEmpty() {
    return top == null;
  }

  /**
   * Add an item to the top of the stack
   *
   * @param node the item to add
   */
  public void push(Linkable node) {
    node.setNext(top);
    top = node;
  }

  /**
   * Removes and returns the item at the top of the stack
   *
   * @return the node at the top of the stack
   * @throws NoSuchElementException if the stack is empty
   */
  public Linkable pop() {
    if(isEmpty()) throw new NoSuchElementException("pop on empty stack");
    Linkable oldTop = top;
    top = oldTop.getNext();
    return oldTop;
  }

  public Linkable peek() {
    if(isEmpty()) throw new NoSuchElementException("pop on empty stack");
    return top;
  }

  /**
   * Get an iterable version of this LinkedStack. Note that we have it return an Iterator<Linkable>, not a
   * LinkedIterator (a subclass of Iterator<Linkable>), because LinkedIterator is protected
   *
   * @return an iterable version of this LinkedStack
   */
  public Iterator<Linkable> iterator() {
    return new LinkedIterator();
  }

  /**
   * >>> EXAMPLE OF A NON-STATIC MEMBER TYPE <<<
   *
   * Returns an iterator over the current stack
   */
  protected class LinkedIterator implements Iterator<Linkable> {
    private Linkable current;

    /**
     * Note how it has access to `top` from the containing class (a private field)!
     */
    public LinkedIterator() {
      current = top;

      /**
       * Could also write this very explicitly:
       *
       * this.current = LinkedStack.this.top;
       *
       * Note: could use classname.this to refer to this for a nested class at any depth
       * Can use this syntax for any "hidden" member (i.e. works if they have a field or method with the same name)
       */
    }

    public boolean hasNext() {
      return current != null;
    }

    public Linkable next() {
      if (!hasNext()) throw new NoSuchElementException("no next element");

      Linkable value = current;
      current = current.getNext();
      return value;
    }
  }
}

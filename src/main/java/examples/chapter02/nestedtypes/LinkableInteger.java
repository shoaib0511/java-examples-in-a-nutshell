package examples.chapter02.nestedtypes;

public class LinkableInteger implements LinkedStack.Linkable {
  private int value;
  private LinkedStack.Linkable next;

  public LinkableInteger(int value) {
    this.value = value;
  }

  public int getValue() {
    return value;
  }

  public LinkedStack.Linkable getNext() {
    return next;
  }

  public void setNext(LinkedStack.Linkable node) {
    next = node;
  }
}

package examples.chapter02.shapes;

/**
 * Run with:
 *  $ gradle run -PmainClass=examples.chapter02.shapes.PassByValueExample
 */
public class PassByValueExample {
  public static void main(String[] args) {
    Circle c = new Circle(2);
    System.out.println("Before all: radius of c is " + c.getRadius());

    doesntMutate(c);
    System.out.println("After doesntMutate(c): radius of c is " + c.getRadius());

    doesMutate(c);
    System.out.println("After doesMutate(c): radius of c is " + c.getRadius());
  }

  // if Java were pass by reference, this would alter the arg that got passed in
  static void doesntMutate(Circle circle) {
    circle = new Circle(3);
  }

  static void doesMutate(Circle circle) {
    circle.setRadius(4);
  }
}

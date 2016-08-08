package examples.chapter02.shapes;


/**
 * Note: from the book `java-in-a-nutshell`, not from the examples book.
 */
public class Circle extends Shape {
  public static final double PI = 3.14159;

  public static double radiansToDegrees(double rads) {
    return rads * 180 / PI;
  }

  // No public access! Though allow subclasses, and classes in package, to access
  protected double r;

  // Enforce restrictions on radius
  protected void checkRadius(double radius) {
    if (radius < 0.0) throw new IllegalArgumentException("radius may not be negative");
  }

  // Restrictions enforced
  public Circle(double r) {
    checkRadius(r);
    this.r = r;
  }

  public double getRadius() {
    return r;
  }

  // Restrictions enforced
  public void setRadius(double r) {
    checkRadius(r);
    this.r = r;
  }

  @Override
  public double area() {
    return PI * r * r;
  }

  @Override
  public double circumference() {
    return 2 * PI * r;
  }
}

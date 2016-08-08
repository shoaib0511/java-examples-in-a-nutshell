package examples.chapter02.shapes;

public class PlaneCircle extends Circle {
  private final double cx, cy;

  public PlaneCircle(double r, double cx, double cy) {
    super(r);
    this.cx = cx;
    this.cy = cy;
  }

  public double getCentreX() {
    return cx;
  }

  public double getCentreY() {
    return cy;
  }

  // Note: still has area() and circumference() methods from Circle
  // also still has instance field r from Circle, which we'll use here:
  public boolean isInside(double x, double y) {
    // distances from centre
    double dx = x - cx;
    double dy = y - cy;

    // pythagorean theorem
    double distance = Math.sqrt(dx*dx + dy*dy);

    return (distance < r);
  }
}

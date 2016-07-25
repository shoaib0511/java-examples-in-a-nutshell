package examples.chapter01;

public class Point {
  public double x, y;

  public Point(double x, double y) {
    this.x = x;
    this.y = y;
  }

  @Override
  public String toString() {
    return "Point{x=" + x + ", y=" + y + '}';
  }
}

package examples.chapter02.shapes;

/**
 * We can draw the outline of a circle using trigonometry. Trig is slow, though, so we do some pre-computing
 */
public class TrigCircle {
  // Class field initialized with an expression
  private static final int NUM_PTS = 500;

  // Class fields that will get initialized later
  private static double sines[] = new double[NUM_PTS];
  private static double cosines[] = new double[NUM_PTS];

  // Initialize
  static {
    double x = 0.0;
    double deltaX = (Circle.PI / 2) / (NUM_PTS - 1);

    for (int i = 0; i < NUM_PTS; i++, x += deltaX) {
      sines[i] = Math.sin(x);
      cosines[i] = Math.cos(x);
    }
  }

  // ... rest of class, with drawing functions and whatnot ...
}

# Introduction to Object-Oriented Design in Java

## Java Values
Java has two types of values: **primitives and references**. These are the **only things** that can be put into
variables, or passed to methods (for example, that's why lambdas must be wrapped in objects, a.k.a. references).

Primitive values cannot be altered (i.e. the value 2 is always the same). The objects referred to be references are
generally mutable.

Java is pass-by-value:
* **pass-by-reference:** methods are never passed values, they're always passed references to values, even primitive
types. So if you modify the params, you always modify what's passed in
* Java doesn't do this. If it did, when you called a method with a reference, you'd get a ref to a ref. In Java you
don't

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

## java.lang.Object
Here's a class that overrides the important methods of `java.lang.Object`:

    // this class represents a circle with immutable position and radius
    public class Circle implements Comparable<Circle> {
        private final int x, y, r;
            
        // basic constructor
        public Circle(int x, int y, int r) {
            if (r < 0) throw new IllegalArgumentException("negative radius");
            this.x = x;
            this.y = y;
            this.r = r;
        }
        
        // "copy constructor", a useful alternative to clone()
        public Circle(Circle original) {
            x = original.x;
            y = original.y;
            r = original.r;
        }
        
        @Override public String toString() {
            return String.format("Circle{%d,%d,%d}", x, y, r);
        }
        
        // test equality with another object
        @Override public boolean equals(Object o) {
            if (o == this) return true;                 // identical references?
            if (!(o instanceof Circle)) return false;   // is it not a Circle? Not that null will also return false
            
            Circle that = (Circle) o;  // safe cast because of above check
            return this.x == that.x && this.y == that.y && this.r == that.r;
        }
        
        // hashCode allows object to be used in a hash table. Equal objects must have equal hashCodes. For performance, it's
        // better if different objects have few hashCode collisions. We need to override this because we also override
        // equals. The below algorithm is from "Effective Java"
        @Override public int hashCode() {
            int result = 17;
            result = 37 * result + x;
            result = 37 * result + y;
            result = 37 * result + r;
            return result;
        }
        
        // this method defined by the Comparable interface. Compare this circle to that circle
        // return < 0 if this < that. return 0 if this == that. return > 0 if this > that
        // Circles ordered top to bottom, then left to right, then by radius
        public int compareTo(Circle that) {
            // compare y first, if equal then x, if equal then r
            // cast to long to avoid overflow when comparing very positive/negative ints
            long result = (long)that.y - this.y;               // I'm greater if I have a lesser y
            if (result == 0) result = (long)this.x - that.x;   // I'm greater if I have a greater x
            if (result == 0) result = (long)this.r - that.r;   // I'm greater if I have a greater r
            
            // can't return the long, just return its sign as an int
            return Long.signum(result);
        }
    }

#### toString()
By default, classname + hexadecimal representation of the hashCode()
Better to override with something more human readable

#### equals()
The `==` operator tests if two references refer to the same object
If you want to test for logical equality (i.e. same values), use `obj.equals(otherObj)`
In the above sample code, note how we make sure the `equals()` doesn't throw an exception

#### hashCode()
Whenever you override `equals()`, you must also override `hashCode()`. Returns an int for use by hash table data
structures. Criterion:
* if two objects are equal according to `equals()`, they must have the same hash code
* if two objects are not equal, they should at least be very unlikely to have the same hash code (often done through
somewhat tricky arithmetic or bit manipulation)

Note how in the example implementation, if we had two circles that were identical except they had reversed x and ys,
they'd still have different hashCodes, which is what we want.  This is because the order of the reduction matters, the
repeated multiplication and addition "spread out" the range of hash codes.

#### Comparable::compareTo()
Defined by `java.lang.Comparable` interface, not by `Object`. Still a very common method, allows instances of class to
be compared to with `<, <=, >=, >` logic. **Thus also allows sorting.**

In the above example, we compared circles as if they were words on a page: top left (high y, low x) is least, while
bottom right (low y, high x) is most. If coords are the same, then compare by radius (higher radius is more).

Not required, but highly advised that the ordering defined by `compareTo()` has the same equality as `equals()`.

`compareTo()` returns an int:
* positive int if this > that
* 0 if this.equals(that)
* negative int if this < that

#### Clone
LEFT OFF HERE:              182 (200)
* current chapter goes to:  196 (214)

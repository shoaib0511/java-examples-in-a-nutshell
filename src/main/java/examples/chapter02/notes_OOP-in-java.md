# Object Oriented Programming in Java

## Modifiers
Modifiers for classes:
* public, protected, private (no modifier = package private?)
* abstract (can't be instantiated, must be extended)
* final (can't be extended)
* strictfp (rare, all methods behave as if defined strictfp)

## Class members
Four types of class members
* class fields: associated with class (static)
* instance fields: associated with instance of class
* class methods
* instance methods

Example:

    public class Circle {
        // class field
        public static final double PI = 3.14159;
        
        // class method
        public static double radiansToDegree(double radians) {
            return radians * 180 / PI;
        }
        
        // instance field ==> probably a bad idea to make this public, though!
        public double r;
        
        // instance methods - operate on instance fields
        public double area() {
            return PI * r * r;
        }
        
        public double circumference() {
            return 2 * PI * r;
        }
    }

## Field Declaration Syntax
Like declaring a local variable, but can also include modifiers:

    int x = 1;
    private String name;
    public static final int DAYS_PER_WEEK = 7;
    String[] daynames = new String[DAYS_PER_WEEK];
    private int a = 17, b = 37, c = 53;

Can use the following modifiers:
* public, protected, private
* static
* final (i.e. val in Scala, can be used to create immutable classes)
* transient (not part of persistent state of object, doesn't need to be included in serialization)
* volatile (must always be read from and flushed to main memory. Must not be cached by a thread or CPU cache)

## Class Fields
AKA static fields. Example:

    public class Circle {
        public static final double PI = 3.14159;
    }

Final is optional, just makes it immutable. Is essentially a global val/var, just one copy.

## Class Methods
AKA static methods. Example:

    public class Circle {
        public static double radiansToDegrees(double rads) {
            return rads * 180 / PI;
        }
    }

## Instance Fields
Non-static. Example:

    public class Circle {
        public double r;   // value set elsewhere
    }
    
    Circle smallCircle = new Circle();
    smallCircle.r = 2.0;
    Circle bigCircle = new Circle();
    bigCircle.r = smallCircle.r * 2;

## Instance Methods
Non-static. Example:

    Circle smallCircle = new Circle();
    smallCircle.r = 2.0;
    smallCircle.area();   // calling an instance method

## `this` Reference
Often not required, but can add clarity. For example, the following two are the same (inside our `Circle` class):

    public double area() { return Circle.PI * this.r * this.r; }
    public double area() { return PI * r * r; }

However, it IS required with name conflicts, e.g.:

    public void setRadius(double r) {
        this.r = r;
    }

In the above code, it distinguishes the instance variable `this.r` from the method argument `r`.

## Constructors
Even if you don't provide one, javac will add the no-arg default constructor:

    Circle c = new Circle();

However, you can also define constructors.

    public class Circle {
        public static final double PI = 3.14159;
        protected double r;  // instance field that must be set for the class to be useful
        
        // let's set it in a constructor!
        public Circle(double r) { this.r = r; }
        
        public double circumference() { return 2 * PI * r; }
        public double area() { return PI * r*r; }
        public double radius() { return r; }
    }
    
    Circle c = new Circle(0.25);

Constructor facts:
* Name is always the same as the class
* Always declared without a return type
* The body initializes the objects (sets up "this")
* Constructor may not return this or any other value

Multiple constructors
* Can define multiple constructors as long as the have different signatures
* Constructors can invoke one another using `this(args)`. `this` must be the first statement, but can be followed by
other initialization code

    public class Circle {
        public Circle(double r) {
            this.r = r;
        }
        
        public Circle() {
            this(1.0);
            // could have more here, if you want
        }
    }

Field defaults and initializers
* if field not initialized, auto-assigned default falsey value (false, '\u0000', 0, 0.0 or null)
* or, can just give it a default, like `public double r = 1.0;`
* with defaults, the compiler really just re-writes constructors. So this:

    public class SampleClass {
        public int len = 10;
        public int[] table = new int[len];
        
        public SampleClass() {
            for(int i = 0; i < len; i++) table[i] = i;
        }
    }

Gets re-written to:

    public class SampleClass {
        public int len;
        public int[] table;
        
        public SampleClass() {
            len = 10;
            table = new int[len];
            for(int i = 0; i < len; i++) table[i] = i;
        }
    }

Note that this only applies to constructors that don't begin with a `this(args)` call. Secondary constructors like these
have their initialization performed by the "primary" constructor.

Static/class initialization fields get put into a special method that is invoked exactly once, before a class is used.

## Initializer blocks
What if we need to do complex initialization of static fields? Can do so with a `static initializer`:

    // We can draw the outline of a circle using trigonometry
    // trig is slow, though, so we do some pre-computing
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
    
        // ... rest of class ...
    }

Note that a class can have any number of static initializers. Like class methods, can't use `this`.

Can also have instance initializers, but they're rare (normally all done in constructors): 

    private static final int NUM_PTS = 100;
    private int[] data = new int[NUM_PTS];
    { for(int i = 0; i < NUM_PTS; i++) data[i] = i; }

## Subclasses and Inheritance
Our past circle only had radii. What if we want a circle with not just size, but also a position (on a Cartesian plane)?
Can make a subclass, `PlaneCircle`

    public class PlaneCircle extends Circle {
        private final double cx, cy;
        
        // Invokes Circle constructor with super(args)
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

Note that all `PlaneCircle`s are `Circle`s, but the opposite is not true:

    PlaneCircle pc = new PlaneCircle(2.5, 0.0, 0.0);
    Circle c = pc;                                              // no cast required
    PlaneCircle pc2 = (PlaneCircle) c;                          // requires cast, may not be safe
    boolean isInside = ((PlaneCircle) c).isInside(0.0, 0.5);    // requires cast, may not be safe

## Final Classes
Prevents extension. i.e. if I want obj.method() to always have the same implementation, can use final class.

## Class Hierarchy
Subclass vs. superclass:

    class SubClass extends SuperClass { ... }

If you don't specify a superclass, the superclass is `java.lang.Object`

Example Heirarchy:

    Object --|-- Circle ----- PlaneCircle
             |-- Math
             |-- System
             |-- Reader --|-- InputStreamReader ----- FileReader
                          |-- FileReader
                          |-- StringReader

## Subclass Constructors

    public class PlaneCircle extends Circle {
        public PlaneCircle(double r, double cx, double cy) {
            super(r);
            this.cx = cx;
            this.cy = cy;
        }
    }

Explicitly initializes cx and cy, but relies on `Circle` constructor to init r. Uses the `super` keyword:
* `super(args)` invokes superclass constructor, just like `this(args)` invokes own constructor
* call to `super(args)` must be first statement in the constructor
* if multiple constructors, `super(args)` can invoke any of them (just like `this(args)`)

## Constructor Chaining
If you don't explicitly call `this` or `super` at the start of a constructor, javac inserts it
 
i.e. this:

    public class PlaneCircle extends Circle {
        public PlaneCircle(double r, double cx, double cy) {
            this.cx = cx;
            this.cy = cy;
        }
    }

Becomes this: (note the no-arg super call)

    public class PlaneCircle extends Circle {
        public PlaneCircle(double r, double cx, double cy) {
            super();      // <<<<<< no args!
            this.cx = cx;
            this.cy = cy;
        }
    }

If there's no no-arg constructor, a compilation error is thrown.

When we instantiate a new `PlaneCircle`, this is what happens:
* First, `PlaneCircle` constructor is invoked
* Explicitly calls `super(r)`, which invokes `Circle` constructor
* `Circle` constructor implicitly calls `super()` to invoke `Object` constructor
* Body of `Object` constructor runs
* Body of `Circle` constructor runs
* Remainder of `PlaneCircle` body runs

## The Default Constructor
If you don't write a constructor, javac automatically adds one:

    public class PlaneCircle() {}
    // becomes ...
    public class PlaneCircle() { public PlaneCircle() { super(); } }

Which could throw a compilation error, if `super()` lacks a no-arg constructor.

The default constructor will be public if the class is public, else it will have no modifier. If declaring classes that
should never be instantiated (like `Math`), then you should always explicitly write a private constructor, to prevent
javac from adding a public one, even if you never use that constructor.

## Hiding Superclass Fields (bad idea, generally)
Generally, it's code small/a bad idea to "hide" members of the superclass with a member of the same name.

It is possible, though:

    public class PlaneCircle extends Circle {
        // "hides" Circle's r
        // instead of circle radius, this will mean distance from circle centre and origin
        public double r;
    
        public PlaneCircle(double r, double cx, double cy) {
            super(r);
            this.r = Math.sqrt(cx*cx + cy*cy);
            this.cx = cx;
            this.cy = cy;
        }
    }

But what if we want to refer to the `Circle` r inside the class?  Can use `super.r` instead of `this.r`.

Can also cast `this` to a `Circle`:

    ((Circle) this).r  // refers to r of the Circle class

Casting is especially useful if you need to refer to a hidden field up more than one level.

## Overriding Superclass Methods
If subclass defines an instance method with:
* same name
* same return type
* same parameters

As a method in the superclass, it overrides that method. The overriding return type can also be a subclass of the
initial return type.

Overriding is only done for instance methods, not class methods (though you can HIDE class methods, in a somewhat
ineffective way).

Overriding is not hiding:
* Basically, with hiding you can still refer to the superclass field, with overriding you can't

## Virtual method lookup
When you call a method, JVM first looks up runtime type of the object (i.e. is it a `Circle` or an `Ellipse`), then
calls the appropriate method. So if you have a `Circle[]` full of both `Circle` and `Ellipse`, it'll call the
appropriate .area method at runtime. This is called **virtual method loopup**.

## Invoking an overridden method

    class A {
        int i = 1;
        int f() { return i; }
    }
    
    class B extends A {
        int i;                      // hides A.i
        int f() {                   // overrides A.f()
            i = super.i + 1;        // can retrieve hidden field using super
            return super.f() + i;   // can also use overridden method using super
        }
    }

Normally, when you invoked `f()`, the interpreter looks for f in:
* current class
* if not there, superclass
* if not there, superclass
* etc.

If you invoke `super.f()`, the same search happens, but begins at the first superclass
* invokes the most immediately overridden version of a method
* so if B extends A, and C extends B, C can directly invoke B.f(), but not A.f()

## Data Hiding and Encapsulation
Encapsulation:
* Hide the data itself
* Expose it only through methods
* Lets you hide the internal implementation details of your class, so they can be changed in the future without
affecting public APIs
* Also, sometimes different fields must be in certain interdependent states. You can only enforce this if you hide the
fields, and prevent users of the class from messing with them directly
* Also improves testability (if only your methods are exposed, you just have to test your methods)
* Especially important with multi-threaded code (discussed more later)

Other reasons:
* Internal fields/methods left visible creates clutter/mess
* You should document all external methods - save effort by hiding if possible

## Access Control
Access to classes
* By default, top level (not nested) classes are accessible in the **package** they're defined in
* However, if declared public, can be accessed anywhere

Access to members
* members of a class always accessible in the body of the class
* by default, also accessible in the package (this is **package access**, the default)
* **public, protected and private** are the other levels
* public = accessible anywhere the containing class is
* private = not accessible anywhere but within the class itself
* protected = package access, AND accessible to subclasses (LESS restrictive than default)

Note that if class `B` extends `A`, it can use `A` protected members. But, it doesn't get access to these fields in a
more general sense - if `B` has a method that takes an `A` as an arg, it cannot access the protected members of that `A`
argument.

Note that packages don't "nest":
* the package `top.mid` is totally different from `top.mid.bottom` in terms of access control 

Rules of thumb:
* use **public** only for **methods** and **constants** that form the public API of the class
* avoid **public** fields - only for constants or immutable objects, should be declared final
* use **protected** if most users don't need it, but subclasses might (protected is still part of the "public" API)
* use default if it's an implementation detail, but still needs to be seen by something else in the package
* use private if the field can truly be hidden outside the class

Better to start too restrictive, and open up access only if necessary.

## Data Accessor Methods
Say we want to enforce that circles can't have negative radii. Can't do this if we give raw field access, but can with
getters and setters:

    // See examples.chapter02.shapes.Circle for getter and setter example

## Abstract Classes
Rules:
* Any class with an abstract method must be abstract itself
* An abstract class can't be instantiated
* A subclass can only be concrete if it implements all abstract methods (by overriding)
* A subclass that doesn't implement all abstract methods must itself be abstract
* static, private and final methods can't be abstract
* final classes can't be abstract
* A class **can** be declared abstract even if all methods are implemented (i.e. the `Classloader` class)

    // See examples.chapter02.shapes.{Shape, Circle, Rectangle} for examples

## Reference Type Conversions
Rules:
* Reference type can be widened without a cast (i.e. `String` can be an `Object`)
* Narrowing requires a cast (i.e. must cast `Object` to `String`). May cause runtime errors
* Can't cast unrelated types (i.e. `String` to `Point` not legal, even with a cast)

Example:

    String s1 = "I'm a string";
    Object o = someString;       // no cast needed
    String s2 = (String) o;      // requires cast
    CharSequence cs = s2;        // no cast needed

Array example, showing covariance:

    String[] strings = new String[] {"hello", "world"};
    CharSequence[] sequences = strings;
    String[] strings2 = (String[]) sequences;

This would be illegal, though:

    Integer[] ints = (Integer[]) strings2;  

Because `String` and `Integer` are unrelated types.

## Modifier Summary
Table:

    abstract
      - Class: can't be instantiated, may contain unimplemented methods
      - Interface: modifier optional, interfaces are always abstract
      - Method: no body provided, signature followed by ;
    default
      - Method: implementation of this interface method is optional
    final
      - Class: can't be subclassed
      - Method: can't be overridden
      - Field: can't have its value changed
      - Variable: a local variable, method param or exception param that can't have its value changed
    native
      - Method: implemented in platform-dependent way (often in C). No body provided
    <None>
      - Class: accessible only in its package
      - Interface: accessible only in its package
      - Member: accessible only in its package
    private
      - Member: accessible only in defining class
    protected
      - Member: accessible only in its package, and subclasses
    public
      - Class: accessible anywhere its package is
      - Interface: accessible anywhere its package is
      - Member: accessible anywhere its class is
    strictfp
      - **rare**, has to do with how floating points are calculated
    static
      - Class: an inner class declared static is a top-level class, not associated with a member of the containing class
      - Method: class method, not passed an implicit this object reference
      - Field: class field. Only one instance of this field ever
      - Initializer: run when class is first loaded, not when instance created
    synchronized
      - Method: makes nonatomic modifications to class or instance
          - for static methods, acquires lock on class before executing body
          - for non-static methods, acquires lock on instance before executing body
    transient
      - Field: doesn't get serialized when you serialize object
    volatile
      - Field: can be accessed by unsynchronized threads. Sometimes used as an alternative to synchronized

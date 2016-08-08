# The Java Type System

## Interfaces
Like a class, an interface defines a new reference type
* however, intended to just represent the API, not functionality
* though, in Java 8+, can also have a default implementation of methods

A class can implement multiple interfaces!

## Defining an Interface

    interface Centered {
        void setCenter(double x, double y);
        double getCenterX();
        double getCenterY();
    }

Restrictions applying to members of an interface:
* All mandatory interface methods are implicitly `abstract` (but generally leave off the keyword)
* An interface defines a public API - all methods implicitly `public` (generally leave off keyword)
* Interface can't define instance fields - these are an implementation detail
* Interface can declare `static final` fields
* Interface can't be instantiated, so no constructor
* Can contain nested types, that are implicitly `public static`
* In Java 8+, an interface can contain static methods

## Extending Interfaces
Interfaces can extend 1+ other interfaces

    interface Positionable extends Centered {
        void setUpperRightCorner(double x, double y);
        double getUpperRightX();
        double getUpperRightY();
    }
    
    interface Transformable extends Scalable, Translatable, Rotatable {}
    interface SuperShape extends Positionable, Transformable {}

## Implementing Interfaces
A class can implement 1+ interfaces (separated by commas)

    public class CenteredRectangle extends Rectangle implements Centered {
        // new instance fields
        private double cx, cy;
        
        // a constructor
        private CenteredRectangle(double cx, double cy, double w, double h) {
            super(w, h);
            this.cx = cx;
            this.cy = cy;
        }
        
        // inherit all Rectangle methods, but must implement Centered methods
        public void setCenter(double cx, double cy) {
            this.cx = cx;
            this.cy = cy;
        }
        public double getCenterX() {
            return cx;
        }
        public double getCenterY() {
            return cy;
        }        
    }

Then, say we also implement `CenteredCircle` and `CenteredSquare`, and all implement `Centered`, and extend `Shape`.
We know that all can be treated as a `Shape` **and** a `Centered`. However, the compiler doesn't necessarily know this,
if we have a collection of them it must be a collected of `Shape` or `Centered`, and accessing the other interface/class
requires a cast.

    Shape[] shapes = new Shape[3];
    shapes[0] = new CenteredCircle(1.0, 1.0, 1.0);
    shapes[0] = new CenteredSquare(2.5, 2, 3);
    shapes[0] = new CenteredRectangle(2.3, 4.5, 3, 4);

    // Compute avg area of the shapes, and avg distance from origin
    double totalArea = 0;
    double totalDistance = 0;
    
    for(Shape shape: shapes) {
        totalArea += shapes.area();  // from Shape
        
        // using instanceof like this is generally a sign of bad design
        if(shape instanceof Centered) {
            // note the cast needed
            Centered c = (Centered) shape;
            double cs = c.getCenterX();
            double cy = c.getCenterY();
            totalDistance += Math.sqrt(cx*cx + cy*cy);
        }
    }

    System.out.println("Average area: " + totalArea/shapes.length);
    System.out.println("Average distance: " + totalDistance/shapes.length);

## Default Methods
Say we start with this interface in a library:

    public interface Positionable extends Centered {
        void setUpperRightCorner(double x, double y);
        double getUpperRightX();
        double getUpperRightY();
    }

But want to extend it to this:

    public interface Positionable extends Centered {
        void setUpperRightCorner(double x, double y);
        double getUpperRightX();
        double getUpperRightY();
        
        void setUpperLeftCorner(double x, double y);
        double getUpperLeftX();
        double getUpperLeftY();    
    }

This would break all code that implements it. Really, need defaults to be able to compile old implementation with the
new interface.

    public interface Positionable extends Centered {
        void setUpperRightCorner(double x, double y);
        double getUpperRightX();
        double getUpperRightY();
        
        default void setUpperLeftCorner(double x, double y);
        default double getUpperLeftX();
        default double getUpperLeftY();    
    }

## Marker Interfaces
Interface with no body, just meant to carry some meaning. For example:
* `java.util.ArrayList` (an implementation of `java.util.List`) implements `java.util.RandomAccess`
* `java.util.Linked` (also an implementation of `java.util.List`) does not
* you can use this for smart algos, like:

    // Have some List, not sure whether or not it has efficient random access
    List l = ...;
    
    // If it doesn't implement RandomAccess, then turn it into an ArrayList
    if (l.size() > 2 && !(l instanceof RandomAccess)) l = new ArrayList(l);
    
    // Then sort in place - efficient only with efficient random access
    sortListInPlace(l);

## Java Generics
Without generics:

    List shapes = new ArrayList();
    shapes.add(new CenteredCircle(1.0, 1.0, 1.0));
    shapes.add(new CenteredSquare(2.5, 2, 3));          // legal, but bad idea
    
    // List::get() returns Object, so must cast to get a CenteredCircle
    CenteredCircle c = (CenteredCircle) shapes.get(0);
    
    // This is a runtime error!
    CenteredCircle c = (CenteredCircle) shapes.get(1);

Ugly code because of casts, and also runtime errors! Could be solved with generics, so the list knows what it contains:

    List<CenteredCircle> shapes = new ArrayList<>(CenteredCircle);
    shapes.add(new CenteredCircle(1.0, 1.0, 1.0));
    //  shapes.add(new CenteredSquare(2.5, 2, 3));    // illegal! Compile error
    
    // No cast needed :)
    CenteredCircle c = shapes.get(0);

Basically, all sorts of errors that used to be runtime errors can be compile time errors with generics.

Example generic type - a `Box` that can hold any sort of type T:

    interface Box<T> {
        void box(T t);
        T unbox();
    }

## Type Parameters
`Map<A, B>` has two type parameters, `A` and `B`.
`Map<String, Integer>` has assigned concrete values to the type parameters.

Note, can use the type params anywhere a normal type can go, like:

interface List<E> extends Collection<E> {
    boolean add(E e);
    E get(int index);
}

## Type Inference
Don't necessarily have to specify the type of the generic:

    // Compiler can infer type on the right hand side
    List<Integer> ints = new ArrayList<>();

## Type Erasure
To help backwards compatibility, when generics were introduced, these types of casts were allowed:

    List someThings = ...;
    List<String> myStrings = (List<String>) someThings;

So, at some level, `List` and `List<String>` are compatible types. Java achieves this by *type erasure* - generic type
params are visible only at compile time, they're stripped out by javac, and not visible in the bytecode.

Type erasure causes some issues. For example, say we want to count orders in two slightly different data structures:

    interface OrderCounter {
        int totalOrders(Map<String, List<String>> orders);
        int totalOrders(Map<String, Integer> orders);
    }

Seems fine, but won't compile! Because in bytecode, both methods look like:

    int totalOrders(Map orders);

So they're indistinguishable at runtime, not overloaded, and thus the compiler has been built to make this illegal.

## Wildcards
What if we don't know the type of a generic at compile time? Can use `<?>`:

    ArrayList<?> mysteryList = unknownList();
    Object o = mysteryList.get(0);

Note that at runtime, mysteryList will **actually** be something concrete, i.e. an `ArrayList<String>`,
`ArrayList<Integer>`, etc. This means we can't do:

    mysteryList.add(new Object());

Because it may not be an `ArrayList<Object>`. We can only add `null`, as `null` is a possible value for any reference
type:

    mysteryList.add(null);

Also note that we can't instantiate an object with `<?>` as the payload, i.e.:

    // won't compile!
    List<?> mysteryList = new ArrayList<?>();

### Wildcards and Subtyping
Is this legal?

    List<Object> objects = new ArrayList<String>();
    
Seems like it should be, since this is legal:

    Object o = "Hello World!";

However, not so simple with lists:

    objects.add(new Object());

The actual list holds `String`s, so trying to add an `Object` to it would fail at runtime. Thus, javac does not consider
`List<String>` to be a subtype of `List<Object>`.

**===> Key Point: Java containers are not covariant!**

If we want that subtyping relationship for containers, we need to use the unknown type:

    List<?> objects = new ArrayList<String>();

So `List<String>` IS A SUBTYPE OF `List<?>`. However, if you call .get on objects, you'll get an Object back.

### Bounded Wildcards
We can also restrict the types that can be used as the value of a type parameter, though **bounded wildcards**, a.k.a.
**type parameter constraints**.  For example:

    List<? extends Shape> = getCirclesOrSquares();
    
    // Is this valid? Not sure - TODO!
    interface Blargh<T extends List> {
        ...
    }

In the first example, this tells us `?` is some type of `Shape`.

Some terms:
* **Type covariance:** the container types have the same relationship to each other as the payload types do. This is
expressed using the `extends` keyword
* **Type contravariance:** this means that the container types have the inverse relationship to each other as the
payload types. This is expressed using the `super` keyword.

For example, if `Cat` extends `Pet`, then `List<Cat>` is a subtype of `List<? extends Pet>`.

FOR MORE DETAILS, LOOK AT THE **Producer Extends, Consumer Super (PECS)** PRINCIPLE FROM JOSHUA BLOCH!

## Array Covariance
In the early days of Java, the decision was made to let arrays be covariant, to make it possible to write methods like
sort. However, this was probably a bad idea, because it allows for runtime errors like this:

    String[] words = {"hello", "world};
    Object[] objects = words;   // the compiler allows this
    
    objects[0] = 42;            // runtime error!

Basically, array covariance is there, but **don't use it!** Bad decision that is only left in for backwards
compatibility.

## Generic Methods
Methods can be generic, regardless of whether or not the class is:

    public class Foo {
        public static <T> T bar(T a, T b) {
            return a;
        }
    }

## Compile and Runtime Typing
What is the type of `xs` in the below code?

    List<String> xs = new ArrayList<>();
    System.out.println(xs);

Depends on whether we consider `xs` at compile time (i.e. as seen by javac) or at runtime!
* at compile time, it's a `List` of `String`
* this type information can check for errors, like trying to add an `Integer` to the list
* at runtime, the JVM sees it as an `ArrayList`. So the `List` is made concrete, but there *of String* bit is lost due
to type erasure

## Enums and Annotations
Enums are **enumerated types**, annotations are **annotation types**.

### Enums
Enums are a variation of classes with limited functionality, that cal only have a limited number of values.

    public enum PrimaryColor {
        RED, GREEN, BLUE
    }

The we can refer to them like:

    PrimaryColor.RED

What if we want enums to take params (i.e. number of sides of a shape), and have some methods?

    public enum RegularPolygon {
        // these parameters get passed to the RegularPolygon constructor
        TRIANGLE(3), SQUARE(4), PENTAGON(5), HEXAGON(6);
        
        private Shape shape;
        
        public Shape getShape() {
            return shape;
        }
        
        // the RegularPolygon constructor. This runs when you get a specific enum, and can init values of the class like
        // a normal constructor
        private RegularPolygon(int sides) {
            switch (sides) {
                case 3:
                    // assume we have these classes elsewhere
                    shape = new Rectangle(1,1,1,60,60,60);
                    break;
                case 4:
                    shape = new Rectangle(1,1);
                    break;
                case 5:
                    shape = new Pentagon(1,1,1,1,1,108,108,108,108,108);
                    break;
                case 6:
                    shape = new Hexagon(1,1,1,1,1,1,120,120,120,120,120,120);
                    break;
            }
        }
    }

-->>> NOTE: not fully clear on how this works, do some more reading! Do you need to explicitly call getShape?
 
Enums special properties:
* all implicitly extend `java.lang.Enum`
* may not be generic
* may implement interfaces
* cannot be extended
* may only have abstract methods if all enum values provide an implementation
* may only have a private (or default access) constructor

### Annotations
A special kind of interface, that annotate some part of a Java program. Example: `@Override`
* has no direct effect
* instead, provides additional information about the method that it annotates
* acts as a useful hint to compilers and IDEs (i.e. if you mis-spell the method, compiler knows that you haven't
actually overridden)

Annotations don't alter program semantics, they just provide optional metadata.

Examples:
* `@Override`
* `@Deprecated` (indicates that method is deprecated)
* `@SuppressWarnings` (suppress compiler warnings)
* `@SafeVarargs` (extended warning suppression for varags methods)
* `@FunctionalInterface` (indicates that an interface can be used as a target for a lambda expression)

Properties of annotations (over normal interfaces):
* all implicitly extend `java.lang.annotation.Annotation`
* may not be generic
* may not extend any other interface
* may only define zero-arg methods
* may not define methods that throw exceptions
* have restrictions on the return types of methods
* can have a default return value for methods

### Defining Custom Annotations
There are two meta-annotations to know about:
* `@Target`, which takes an `ElementType` enum. Can say that the target is a `METHOD`, `FIELD`, `CONSTRUCTOR`, etc.
* `@Retention`, which indicates how javac should process the custom annotation type:
  * `RetentionPolicy.SOURCE` - discarded by javac during compilation
  * `RetentionPolicy.CLASS` - kept in class file, but not necessarily accessible at runtime. **Rare.**
  * `RetentionPolicy.RUNTIME` - accessible by user code at runtime, via reflection

Example: `@Nickname`, which lets us define a nickname for a method, which can be used to find the method via reflection
at runtime:

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Nickname {
        String[] value() default {};
    }

In addition to the two primary meta-annotations, there's also:
* `@Inherited`
* `@Documented`

But they're both pretty rare.

-->>> NOTE: unclear on details, read more

### Type Annotations
Java 8 introduced two new values for `ElementType` - `TYPE_PARAMETER` and `TYPE_USE`. This lets us annotate types, and
enables stuff like:

    @NotNull String safeString = getSomeString();

Java 8 ships with some basic pluggable type checkers, and also lets us create our own.

## Nested Types (a.k.a. "inner class")
Why nest types?
* is a member of the outer type, so can access private variables and whatnot (sometimes needed)
* or, it maybe be tightly coupled to the outer type, and is really an implementation detail, so should be encapsulated
away from the rest of the system

Types can be nested in 4 ways:
* **Static Member Types:** defined as a static member of another type. Nested interfaces, enums and annotations are
always static, even without the keyword
* **Non-Static Member Classes:** not declared static. Only classes can be non-static member types
* **Local Classes:** defined and only visible within a code block. Only classes can be local
* **Anonymous Classes:** a class with no meaningful name. Interfaces, enums and annotations can be declared anonymously 

### Static Member Types
Much like a regular top level type, but nested in another namespace for convenience.

Basic properties:
* a static member type is like the other static members of a class (static fields and methods)
* not associated with any one instance of the containing class (i.e. no `this`)
* can access **only** the `static` members of the containing class
* can access **all** the `static` members of the containing class (including other static member types)
* nested interfaces, enums and annotations are always static, even without the keyword
* any type nested within an interface or annotation is also implicitly static
* can be nested at any level of depth **of other static member types**
* can't be nested in **any other nested type** (just static member types)

**See `examples.chapter02.nestedtypes.LinkedStack.Linkable` for an example of static member types**

Note that top level types can be declared `private` or `protected`, but this makes little sense:
* a `private` top level type cannot be used anywhere
* a `protected` top level type is the same as package private

However, for a static member type, `private` and `protected` classes make a lot of sense.

### Non-Static Member Classes
A class declared as a member of a containing class or enum, WITHOUT the static keyword.

Only classes can be non-static member types. Example:

import java.util.Iterator;

**See `examples.chapter02.nestedtypes.LinkedStack.LinkedIterator` for an example of non-static member types**

Restrictions on non-static member classes:
* can't have the same name as any containing class or package
* can't contain `static` fields, methods or types EXCEPT `static final` constant fields

#### Scope versus inheritance
We've noticed that a top level class can extend a member class. With the introduction of non-static member classes, we
now need to considered two separate hierarchies for any class:
* the **inheritance hierarchy**, from superclass to subclass. Defines fields and methods that a member class inherits
* the **containment hierarchy**, from containing to contained class. Defines fields and methods that are in scope
(accessible) to the contained (member) class 

Properties and rules of thumb around these hierarchies:
* the two hierarchies are completely distinct from one another
* try to avoid naming conflicts. i.e. a member class shouldn't inherit a field/method of the same name as one in scope
from a containing class
* if conflicts to arise, inherited field/method takes precedence over containing field/method
* try to avoid deep containment hierarchies (1-2 levels deep is OK, beyond that confusing)
* can always fall back to defining top level classes instead of deep non-static member classes
 
### Local Classes
Defined within a block (method, static initializer, instance initializer). For example, here's the iterator method from
before, but defined using a local class:

    public class LinkedStack {
        ... most of body here ...
        
        public Iterator<Linkable> iterator() {
            
            // our local class
            class LinkedIterator implements Iterator<Linkable> {
                Linkable current;
                
                // constructor
                public LinkedIterator() { current = head };
                
                // next 3 methods implement the iterator interface
                public boolean hasNext() { return current != null };
                
                public Linkable next() {
                    if (!hasNext()) throw new NoSuchElementException("next on empty iterator");
                    Linkable value = current;
                    current = current.getNext();
                    return value;
                }
                
                public void remove() { throw new UnsupportedOperationException(); }
            }
            
            return new LinkedIterator();
        }
    }

Code is pretty similar to what it was with a non-static member class.

Features:
* can access any members of containing type, including private members
* can also access any local variable, method params or exception params that are in scope of the local method, **and
declared final**

Why the final restriction? A local class has a private, internal copy of the vars it uses (auto-generated by javac).
However, the lifetime of the instance of the local class can be longer than the method lifetime. The only way to ensure
that the local variable and private copy are the same is to insist that it's declared final.

Restrictions:
* name of local class cannot be accessed outside the block where it's defined
* can't be declared `public`, `protected`, `private` or `static`
* can't declare `static` fields, methods or classes. Only exception is `static final` constants
* only classes can be defined locally, not interfaces/enums/annotations

Fields and vars accessible to local classes:

    class A { protected char a = 'a'; }
    class B { protected char b = 'b'; }
    
    public class C extends A {
        private char c = 'c';               // private, but visible to local class
        public static char d = 'd';
        
        public void createLocalObject(final char e) {
            final char f = 'f';
            int i = 0;                      // not final, thus not accessible to local class
            
            class Local extends B {
                char g = 'g';
                
                public void printVars() {
                    // i cannot be accessed
                    System.out.println(g);  // (this.g) g is a field of this class
                    System.out.println(f);  // f is a final local variable
                    System.out.println(e);  // e is a final local param
                    System.out.println(d);  // (C.d) d is a static field of containing class
                    System.out.println(c);  // (C.this.c) c is a private instance field of containing class
                    System.out.println(b);  // b is inherited by this class 
                    System.out.println(a);  // a is inherited from the containing class
                }
            }
            
            new Local().printVars();
        }
    }

#### Lexical Scoping and Local Variables
Lexical scoping:
* {} ==> block of code ==> variables defined within are scoped to that block, not accessible outside

However, instances of local classes can be returned out of blocks, and they can access copies of variables within. 

    public class Weird {
        public static interface IntHolder { public int getValue(); }
    
        public static void main(String[] args) {
            IntHolder[] holders = new IntHolder[10];
    
            for(int i = 0; i < 10; i++) {
                final int fi = i;
        
                // a local class ===> LOCAL TO THIS FOR LOOP
                class MyIntHolder implements IntHolder {
                    public int getValue() { return fi; }   // the local fi
                }
                
                holders[i] = new MyIntHolder();
            }
        
            // the `fi`s are out of scope, but can still access copies of them here 
            for(int i = 0; i < 10; i++) {
                System.out.println(holders[i].getValue());
            }
        }
    }

Basically, local classes can be used as a type of closure. Other closure-like behaviour in Java can be found in
**anonymous classes** and **lambda expressions**. 

### Anonymous Classes
Can create a new instance of a local class without ever naming it:
 
    public class LinkedStack {
        ... most of body here ...
        
        public Iterator<Linkable> iterator() {
            
            // return an anonymous class
            // either extends class, or extends Object and implements interface (as here)
            // to pass args, just do so in the (), though it must be extending a class to do so, not Object/interface
            return new Iterator<Linkable>() {
                Linkable current;
                
                // constructor
                public LinkedIterator() { current = head };
                
                // next 3 methods implement the iterator interface
                public boolean hasNext() { return current != null };
                
                public Linkable next() {
                    if (!hasNext()) throw new NoSuchElementException("next on empty iterator");
                    Linkable value = current;
                    current = current.getNext();
                    return value;
                }
                
                public void remove() { throw new UnsupportedOperationException(); }
            };  // note the required semicolon - terminates the return statement
        }
    }

Restrictions are the same as on local classes. Also, cannot define a constructor, as the class has no name. Can often
use an instance initializer to achieve similar goals, though.

## How Nested Types Work
JVM and Java class file format didn't change when nested types were introduced. As far as they're concerned, there are
only top level types.

To make nested types behave as expected, javac inserts hidden fields, methods and arguments into the classes it
generates. The nested types are compiled into their own, top level class files, but with special names. In our example
with `LinkedStack` and the nested type `Linkable`, javac generates:
* `LinkedStack.class`
* `LinkedStack$Linkable.class`

Then, to let `Linkable` access private members of `LinkedStack`, javac generates non-private accessor methods, and has
`Linkable` call those whenever it access a private member.

If using an anonymous class, the classfile will look more like:
* `LinkedStack$1.class`

And local classes combine the two schemes, i.e.:
* `LinkedStack$1Linkable.class`

With non-static member classes, a field name `this$0` is generated, which holds a reference to the enclosing instance.
Constructors for the non-static member are given an extra param the initializes this field.

For closure-like behaviour, it only works on static final fields. What the compiler does is it generates a private field
in the local class with the same value as the static final field in the enclosing type.

## Lambda Expressions
Nested types (esp. anonymous classes) could often give similar functionality, but in a much more verbose way.

Syntax:

    (param1, param2) -> { /* method body */ }

Consider the `list()` method of `java.io.File`. It lists the files in a directory. Before it returns the list, though,
it first filters the files by name using a `FilenameFilter` object you supply. For example, if we want only files ending
in `.java`:

    File dir = new File("/src");
    
    // Now use anonymous implementation of FilenameFilter
    String[] files = dir.list(new FilenameFilter() {
        public boolean accept(File f, String s) {
            return s.endsWith(".java");
        }
    });

With lambdas, though, much more clean/concise:

    File dir = new File("/src");
    
    String[] files = dir.list((f, s) -> { return s.endsWith(".java"); })

### Lambda Expression Conventions
When javac encounters a lambda, it interprets it as the body of a method with a specific signature. Which method,
though? To resolve this, javac looks at the surrounding code. Restrictions:
* the lambda must appear where an **instance of an interface type is expected**
* the expected interface type should have **exactly one mandatory method**
* the mandatory method should have a signature that **exactly matches that of the lambda expression**

Then an instance implementing the interface is created, with the lambda body implementing the mandatory method.

### Method References
Say we have this lambda:

    // in real code, could often infer the type SomeObject 
    (SomeObject obj) -> obj.toString()

If all we do is call a method on the object like this, the lambda can instead be written as:

    SomeObject::toString

## Functional Programming
map()
* used with lists, and list-like containers
* returns new collection, with lambda applied to each element of the original collection (possibly changing the inner
type)

filter()
* returns a new collection, with items filtered out based on a predicate

reduce()
* an aggregation operation
* will see variations called `fold`, `accumulate`, `aggregate` or `reduce`
* take an collection, an initial value, and a function, apply the function to initial value and head of collection, then
result and second item of collection, etc.

Things that make Java not so functional:
* Java has no structural types, so no "true" function types. Lambdas are auto-converted to nominal (named) types
* type erasure can cause issues - type safety can be lost for higher order functions
* Java is inherently mutable

## Conclusion
Features of Java's type system:
* **Nominal:** names of types very important. No structural types
* **Static:** all types are known at compile time
* **Object/imperative:** all code lives in classes. Everything is an object except primitive types
* **Slightly functional:** limited support, but some functional features
* **Modestly type-inferred:** only a little, tend to prefer verbosity if it makes it easier to read
* **Strongly backwards compatible:** given huge emphasis
* **Type erased:** parameterized types allowed, but info lost after compile time



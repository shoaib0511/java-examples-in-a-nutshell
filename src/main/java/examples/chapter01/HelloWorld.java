package examples.chapter01;

public class HelloWorld {
  public static void main(String[] args) {
    // To manually build and run:
    //  javac -sourcepath src/main/java -d build/manual src/main/java/examples/chapter01/HelloWorld.java
    //  java -cp build/manual examples.chapter01.HelloWorld

    System.out.println("Hello World!");
    System.out.println("Here is a point: " + new Point(1.0, 2.0));
  }
}

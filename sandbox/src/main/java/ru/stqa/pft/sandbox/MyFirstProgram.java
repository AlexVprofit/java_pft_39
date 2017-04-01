package ru.stqa.pft.sandbox;

public class MyFirstProgram {

 public static void main(String[] args) {
   hello("World");
   hello("user");
   hello("Alexey");

   Square s = new Square(8);
   System.out.println("Площадь квадрата со стороной " + s.l + " = " + s.area());

   Rectangle r = new Rectangle(4, 6);
   System.out.println("Площадь прямоугольника со сторонами " + r.a + " и " + r.b +  " = " + r.area());
 }
	public static void hello(String somebody) {
    //String somebody = "World";
    System.out.println("Hello, " +somebody+ " !");
  }

}
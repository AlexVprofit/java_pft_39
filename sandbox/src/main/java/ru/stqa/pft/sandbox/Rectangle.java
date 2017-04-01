package ru.stqa.pft.sandbox;

/**
 * Created by LEN on 11.03.2017.
 */
public class Rectangle {

  public double a;
  public double b;

  public Rectangle(double a, double b) {

    this.a = a;
    this.b = b;
  }

  // метод
  public double area() {
    return this.a * this.b;
  }
}

package ru.stqa.pft.sandbox;

/**
 * Created by LEN on 11.03.2017.
 */
public class Square {

  public double l;
  public Square(double l) {

    this.l=l;
  }

  //public static double area(Square s) {
  //  return s.l * s.l;
  //}

  // метод
  public double area() {
    return this.l * this.l;
  }

}

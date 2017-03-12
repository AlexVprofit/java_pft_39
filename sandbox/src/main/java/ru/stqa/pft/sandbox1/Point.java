package ru.stqa.pft.sandbox1;

public class Point {
// атрибуты У Х , это координаты точки на плоскости
  public double y, x;
// public double x;
// Конструктор
  public Point (double y, double x) {
    this.y = y;
    this.x = x;
  }

  // Метод
  double Distance(double y, double x) {

    double yy = this.y - y;
    double xx = this.x - x;
    return  Math.sqrt(yy*yy +xx*xx);
  }

  double Distance(Point p) {

    return Distance(p.y, p.x);
  }

}

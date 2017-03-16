package ru.stqa.pft.sandbox1;

public class MyTaskPartTwo {

  public static void main(String[] args) {

    System.out.println("Решение задачи: Нахождение расстояния между двумя точками на двумерной плоскости");

    Point p1 = new Point(0, 5);
    Point p2 = new Point(0, 15);

    System.out.println("Координаты точек, 1-й У=[" +p1.y + "] Х=[" + p1.x + "] 2-й У=[" + p2.y + "] Х=[" + p2.x+
            "] Расстояние: = " + p1.distance(p2));
   }

   // функция вычислеия расстояния

}

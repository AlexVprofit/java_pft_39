package ru.stqa.pft.sandbox1;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by LEN on 16.03.2017.
 */
public class PointTests {

  @Test
  public void testDistance() {
    Point p1 = new Point(0,5);
    Point p2 = new Point(0,15);
    Assert.assertEquals(p1.distance(p2),10.0) ;
  }
}

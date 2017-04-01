package ru.stqa.pft.sandbox;

/**
 * Created by LEN on 23.03.2017.
 */
public class Equality {

  public static void main (String[] args) {

    String s1 = "Firefox 2.0";
    String s2 = "Firefox " + Math.sqrt(4.0);

    System.out.println(s1 == s2);
    System.out.println(s1.equals(s2));

    String s3 = new String(s1);

    System.out.println(s1 == s3);
    System.out.println(s1.equals(s3));

  }
}

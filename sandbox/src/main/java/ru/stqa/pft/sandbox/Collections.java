package ru.stqa.pft.sandbox;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Collections {
  public static void main(String[] args) {
//    String[] langs = new String[4];
    String[] langs = { "Java", "C#", "Pyton", "PHP"};
    for (int i = 1; i < langs.length; i++) {
      System.out.println("Яхочу выучить " + langs[i]);
    }
    for (String l : langs) {
      System.out.println("Яхочу выучить " + l);
    }

    List<String> languages = new ArrayList<String>();
    languages.add("Java");
    languages.add("C#");
    languages.add("Pyton");
    languages.add("PHP");

    for (String l : languages) {
      System.out.println("Яхочу выучить " + l);
    }

    List<String> languagess = Arrays.asList("Java", "C#", "Pyton", "PHP");

    for (String l : languagess) {
      System.out.println("Яхочу выучить " + l);
    }

    for (int i = 1; i < languagess.size();  i++) {
      System.out.println("Яхочу выучить " + languagess.get(i));
    }

    List languagesss = Arrays.asList("Java", "C#", "Pyton", "PHP");
    for (Object l : languagesss) {
      System.out.println("Яхочу выучить " + l);
    }



  }
}


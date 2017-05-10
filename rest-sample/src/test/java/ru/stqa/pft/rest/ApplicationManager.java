package ru.stqa.pft.rest;


import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ApplicationManager {

  private final Properties properties;

  public ApplicationManager() {
    // создали обект и сохранили в поле этого класса class ApplicationManager
    properties = new Properties();
  }

  public void init() throws IOException {
    String target = System.getProperty("target", "local");
    // загружаем файл
    properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", target))));
  }

  public void stop() {
   quit();
  }

  private void quit() {
  }

  public String getProperty(String key) {
    return properties.getProperty(key);
  } // извлекаем св-во properties

}

/*
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;


public class ApplicationManager {
  private final Properties properties;

  public ApplicationManager() {


    properties = new Properties();
  }

  public void init() throws IOException {
    String target = System.getProperty("target", "local");
    properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", target))));


  }

  public String getProperty(String key) {
    return properties.getProperty(key);
  }

}
*/
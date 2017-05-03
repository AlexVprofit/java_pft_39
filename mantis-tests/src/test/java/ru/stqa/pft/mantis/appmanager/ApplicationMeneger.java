package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;

import javax.swing.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class ApplicationMeneger {

  private final Properties properties;
  private WebDriver wd;

  private String browser;
  private RegistrationHelper registrationHelper;

  public ApplicationMeneger(String browser) {
    this.browser = browser;
    // создали обект и сохранили в поле этого класса class ApplicationMeneger
    properties = new Properties();
  }

  public void init() throws IOException {
    String target = System.getProperty("target", "local");
    // загружаем файл
    properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", target))));
  }

  public void stop() {
   if (wd != null) {
     wd.quit();
   }
  }

  public HttpSession newSession() {
    return new HttpSession(this);
  }

  public String getProperty(String key) {
    return properties.getProperty(key);
  }

  public RegistrationHelper registration() {
    // если не было инициализации, т.е. только один раз инициализируем при обращении к методу registration()
    if (registrationHelper == null) {
       registrationHelper = new RegistrationHelper(this); // менеджер нанимает помшника и передаёт ссылку на самого себя
    }
    return registrationHelper;
  }

  public WebDriver getDriver() { // драйвер браузера инициализируется если к нему кто-то обратиться через getDriver() {
    if (wd == null) {
      if (Objects.equals(browser, BrowserType.FIREFOX)) {
        // потому что зачудил !!!
        FirefoxBinary binary = new FirefoxBinary(new File("C:\\Program Files\\Mozilla Firefox\\firefox.exe"));
        wd = new FirefoxDriver(binary, new FirefoxProfile());
//      wd = new FirefoxDriver();
        //  это памятка как пример завел

      } else if (Objects.equals(browser, BrowserType.CHROME)) {
        wd = new ChromeDriver();
      } else if (Objects.equals(browser, BrowserType.IE)) {
        wd = new InternetExplorerDriver();
      }

      wd.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
      wd.get(properties.getProperty("web.baseUrl"));

    }
    return wd;
  }
}

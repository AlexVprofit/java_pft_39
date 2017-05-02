package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class ApplicationMeneger {

  private final Properties properties;
  WebDriver wd;

  private String browser;

  public ApplicationMeneger(String browser) {
    this.browser = browser;
    // создали обект и сохранили в поле этого класса class ApplicationMeneger
    properties = new Properties();
  }

  public void init() throws IOException {
    String target = System.getProperty("target", "local");
    // загружаем файл
    properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", target))));

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

  public void stop() {
    wd.quit();
  }

}

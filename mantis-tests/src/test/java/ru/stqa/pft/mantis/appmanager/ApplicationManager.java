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

public class ApplicationManager {

  private final Properties properties;
  private WebDriver wd;

  private String browser;
  private RegistrationHelper registrationHelper;
  private FtpHelper ftp;
  private MailHelper mailHelper;
  private DbHelper dbHelper;
  private ChangePasswordHelper changePasswordHelper;
  private ChangePassHelper changeHelper;

  public ApplicationManager(String browser) {
    this.browser = browser;
    // создали обект и сохранили в поле этого класса class ApplicationManager
    properties = new Properties();
  }

  public void init() throws IOException {
    String target = System.getProperty("target", "local");
    // загружаем файл
    properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", target))));
//    dbHelper = new DbHelper(); // инициализация помощника

  }

  public void stop() {
   if (wd != null) {
     wd.quit();
   }
  }

  public HttpSession newSession() {
    return new HttpSession(this);
  } // новая сессия создается

  public String getProperty(String key) {
    return properties.getProperty(key);
  } // извлекаем св-во properties

  public RegistrationHelper registration() {
    // если не было инициализации, т.е. только один раз инициализируем при обращении к методу registration()
    if (registrationHelper == null) {
       registrationHelper = new RegistrationHelper(this); // менеджер нанимает помшника и передаёт ссылку на самого себя
    }
    return registrationHelper;
  }

  public FtpHelper ftp() {
    // инициализируем метод один раз
    if(ftp == null) {
      ftp = new FtpHelper(this);
    }
    return ftp;
  }

  // механизм реализации ленивой инициализации  для MailHelper
  public MailHelper mail() {
    if (mailHelper == null) {
      mailHelper = new MailHelper(this);
    }
    return mailHelper;
  }
  // создаем метод кот. возвращает этого помощника
  public DbHelper db() {
    if (dbHelper == null) {
      dbHelper = new DbHelper(this);
    }
    return dbHelper;
  }

  // механизм реализации ленивой инициализации  для ChangePasswordHelper
  public ChangePasswordHelper changePassword(){
    if (changePasswordHelper == null) {
      changePasswordHelper = new ChangePasswordHelper(this);
    }
    return changePasswordHelper;
  }
  public ChangePassHelper change() {
    if (changeHelper == null) {
      changeHelper = new ChangePassHelper(this);
    }
    return changeHelper;
  }

  public WebDriver getDriver() { // драйвер браузера инициализируется если к нему кто-то обратиться через getDriver() {
    if (wd == null) { // инициализируем драйвер если он раньше небыл инициализирован
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

//      wd.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
      wd.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
      wd.get(properties.getProperty("web.baseUrl"));

    }
    return wd;
  }

}

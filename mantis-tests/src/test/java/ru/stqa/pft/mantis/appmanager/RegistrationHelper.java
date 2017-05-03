package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.WebDriver;

public class RegistrationHelper {
  private final ApplicationMeneger app;
  private WebDriver wd;

  public RegistrationHelper(ApplicationMeneger app) {
    this.app = app;
//    wd = app.wd; // взяли ссылку на драйвер у менеджера, но это сразу инициализирует драйвер
    wd = app.getDriver(); // инициализирует драйвер в момент 1-го обращения (ленивая инициализация)
  }

  public void start(String username, String email) {
    wd.get(app.getProperty("web.baseUrl") + "/signup_page.php"); // открываем страницу через драйвер браузера

  }
}

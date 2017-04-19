package ru.stqa.pft.addressbook.tests;

import org.openqa.selenium.remote.BrowserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import ru.stqa.pft.addressbook.appmeneger.ApplicationMeneger;

import java.lang.reflect.Method;
import java.util.Arrays;


public class TestBase {
  // для создания файла протокола тестов
  Logger logger = LoggerFactory.getLogger(TestBase.class);  // привязка к классу TestBase.class чтобы видеть откуда вызов идет

//  protected static final ApplicationMeneger app = new ApplicationMeneger(BrowserType.FIREFOX);
  protected static final ApplicationMeneger app
        = new ApplicationMeneger(System.getProperty("browser", BrowserType.FIREFOX));

  @BeforeSuite
  public void setUp() throws Exception {
    app.init();
  }

  @AfterSuite(alwaysRun = true) // alwaysRun = true нужно чтобы метод всё-равно сработал (чтобы браузер гарантировано остановился)
  public void tearDown() {
    app.stop();
  }


  // методы logTestStart и logTestStop создаем для получения протоколирования
  @BeforeMethod
  public void logTestStart(Method mm, Object[] p) { // (Method mm)позволяет узнать название метода в котором запускается
                                        // содер-т инф-ю о тестовом методе кот. запускается. Object[] p объявлен объект р
    logger.info("Start test " + mm.getName()
            + " with parameters " + Arrays.asList(p));  // выводим медот где инициализ-ся, метод в кот. запуск и параметры
  }

  @AfterMethod(alwaysRun = true) // alwaysRun = true нужно если тест упадет, чтобы метод всё-равно сработал
  public void logTestStop(Method mm) {  // (Method mm)позволяет узнать название метода в котором запускается
                                        // содержит инф-ю о тестовом методе кот. запускается
    logger.info("Stop test " + mm.getName());
  }


}

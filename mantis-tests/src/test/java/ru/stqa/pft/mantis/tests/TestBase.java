package ru.stqa.pft.mantis.tests;

import org.openqa.selenium.remote.BrowserType;
import org.testng.annotations.AfterSuite;
  import org.testng.annotations.BeforeSuite;
import ru.stqa.pft.mantis.appmanager.ApplicationMeneger;

public class TestBase {

//  protected static final ApplicationMeneger app = new ApplicationMeneger(BrowserType.FIREFOX);
  protected static final ApplicationMeneger app
        = new ApplicationMeneger(System.getProperty("browser", BrowserType.FIREFOX));

  @BeforeSuite  // инициализация метода
  public void setUp() throws Exception {
    app.init();
  }

  @AfterSuite(alwaysRun = true) // alwaysRun = true нужно чтобы метод всё-равно сработал
                               // (чтобы браузер гарантировано остановился)
  public void tearDown() {
    app.stop();
  }

}

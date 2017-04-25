package ru.stqa.pft.addressbook.tests;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.openqa.selenium.remote.BrowserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import ru.stqa.pft.addressbook.appmeneger.ApplicationMeneger;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;


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

  public void verifyGroupListInUI() {
    // С помощью ф-и Boolean.getBoolean получаем системное св-во с заданным именем и преобразует в Boolean тип
    if (Boolean.getBoolean("verifyUI")) {
      Groups dbGroups = app.db().groups(); // загрузка списка из бд
      Groups uiGroups = app.group().all(); // загрузка списка из интерфейса UI
      // теперь сравниваем списки  / применили статический импорт сократив команду
      // упрощаем список полученный из бд применив функ-е программирование (получаем stream и применяем функ-ю мар
      // к элементам коллеции  анонимная ф-я (g) принимает  -> на вход группу а на выходе новый объект типа GroupData
      // с идентификатором withId таким же как у преобразуемого объекта g.getId() с именем withName как у преобразуемого
      // объекта g.getName() , а header and footer уже не указываем т.к. их нет в интерфейсе UI) теперь когда ко всем элементам
      // применена эта функция все собираем collect коллектором Collectors.toSet() т.е. сравниваем два множества кот. содержат
      // информацию об идентификаторах и именах (кот. загружена из UI  и из бд( но приэтом из бд еще и очищена от лишней инфы) )
      assertThat(uiGroups, equalTo(dbGroups.stream()
              .map((g) -> new GroupData().withId(g.getId()).withName(g.getName()))
              .collect(Collectors.toSet())));
    }
  }

  public void verifyContactListInUI() {
    // С помощью ф-и Boolean.getBoolean получаем системное св-во с заданным именем и преобразует в Boolean тип
    if (Boolean.getBoolean("verifyUI")) {
      Contacts dbContacts = app.db().contacts(); // загрузка списка из бд
      Contacts uiContacts = app.contact().all(); // загрузка списка из интерфейса UI
      // теперь сравниваем списки  / применили статический импорт сократив команду
      // упрощаем список полученный из бд применив функ-е программирование (получаем stream и применяем функ-ю мар
      // к элементам коллеции  анонимная ф-я (g) принимает  -> на вход группу а на выходе новый объект типа GroupData
      // с идентификатором withId таким же как у преобразуемого объекта g.getId() с именем withName как у преобразуемого
      // объекта g.getName() , а header and footer уже не указываем т.к. их нет в интерфейсе UI) теперь когда ко всем элементам
      // применена эта функция все собираем collect коллектором Collectors.toSet() т.е. сравниваем два множества кот. содержат
      // информацию об идентификаторах и именах (кот. загружена из UI  и из бд( но приэтом из бд еще и очищена от лишней инфы) )
      assertThat(uiContacts, equalTo(dbContacts.stream()
              .map((g) -> new ContactData().withId(g.getId()).withFirstname(g.getFirstname()).withLastname(g.getLastname())
                      .withTitle(g.getTitle()).withCompany(g.getCompany())
                      .withNew_adress(g.getNew_adress()))
                      .collect(Collectors.toSet())));
    }
  }

}

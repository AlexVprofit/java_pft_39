package ru.stqa.pft.mantis.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.mantis.model.UserData;

import javax.mail.MessagingException;
import javax.xml.rpc.ServiceException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.sql.SQLException;

import static org.testng.Assert.assertTrue;


public class ChangePasswordTests extends TestBase {

  @BeforeMethod
  public void startMailSrver() throws RemoteException, ServiceException, MalformedURLException {
    app.mail().start();
    // получение из багтрекера (mantis) информации о баг-репорте( исправленный -  trueб нет - false)с заданным идентификатором
    // и если баг-репорт не исправен выполнение теста пропускается
    skipIfNotFixed(Integer.valueOf(app.getProperty("web.bugIdNotFiuxed").toString()));
  }

  @Test
  public void testChangePassword() throws IOException, SQLException, MessagingException, InterruptedException {
    UserData user;
    String newPassword = app.getProperty("web.adminPassword");

    app.change().goManagePage();
    user = app.change().findUser();
    app.change().resetPassword();
    app.change().changePassword(newPassword);
    // проверка что пользователь с новым (измененным) паролем зашел на сайт
//    HttpSession session = app.newSession();
    assertTrue(app.newSession().login(user.getLogin(), newPassword));
  }

  @AfterMethod(alwaysRun = true)
  public void stopMailServer() {
    app.mail().stop();
  }
}

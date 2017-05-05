package ru.stqa.pft.mantis.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.mantis.appmanager.HttpSession;
import ru.stqa.pft.mantis.model.UserData;

import javax.mail.MessagingException;
import java.io.IOException;
import java.sql.SQLException;

import static org.testng.Assert.assertTrue;


public class ChangePassword extends TestBase {

  @BeforeMethod
  public void startMailSrver()  {
    app.mail().start();

  }

  @Test
  public void testChangePassword() throws IOException, SQLException, MessagingException {
    UserData user;
    String newPassword = app.getProperty("web.adminPassword");

    app.change().goToManagePage();
    user = app.change().findUserForChange();
    app.change().resetPassword();
    app.change().changePassword(newPassword);

    HttpSession session = app.newSession();
    assertTrue(session.login(user.getLogin(), newPassword));

  }

  @AfterMethod
  public void stopMailServer() {
    app.mail().stop();
  }
}

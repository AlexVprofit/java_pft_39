package ru.stqa.pft.mantis.tests;
/*
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.pft.mantis.appmanager.DbHelper;
import ru.stqa.pft.mantis.model.MailMessage;
import ru.stqa.pft.mantis.model.UserData;
import ru.stqa.pft.mantis.model.Users;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

import static org.testng.Assert.assertTrue;


public class ChangePasswordTest extends TestBase {

  @BeforeMethod
  public void startMailServer() {
    app.mail().start();
  }

  @Test
  public void changePasswordTest() throws IOException, MessagingException {
    app.getDriver();
    app.changePassword().login("administrator", "root"); //изменение пароля
    DbHelper getofdbUsers1 = app.db();
    Users getofdbUsers  =  getofdbUsers1.users();  //app.db().users(); //получение пользователя
    UserData selectedUser = getofdbUsers.iterator().next();
    String username = selectedUser.getUsername();
    String email = username + "@localhost.localdomain";
    app.changePassword().resetPassword(username);
    List<MailMessage> mailMessages = app.mail().waitForMail(1, 15000);
    String confirmationLink = findConfirmationLink(mailMessages, email);
    String newpassword = "newpassword";
    app.changePassword().finish(confirmationLink, newpassword);
    assertTrue(app.newSession().login(username,newpassword));
  }

  private String findConfirmationLink(List<MailMessage> mailMessages, String email) {
    MailMessage mailMessage = mailMessages.stream().filter((m) -> m.to.equals(email)).findFirst().get();
    VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
    return regex.getText(mailMessage.text);
  }

  @AfterMethod(alwaysRun = true)
  public void stopMailServer() {
    app.mail().stop();
  }
}
*/
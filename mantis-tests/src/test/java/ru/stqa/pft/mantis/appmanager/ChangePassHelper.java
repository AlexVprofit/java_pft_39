package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.pft.mantis.model.MailMessage;
import ru.stqa.pft.mantis.model.UserData;

import javax.mail.MessagingException;
import java.io.IOException;
import java.sql.*;
import java.util.List;

public class ChangePassHelper extends HelperBase {
  public UserData user = new UserData().withId('0').withLogin("");

  public ChangePassHelper(ApplicationManager app) {
    super(app);
  }

  public void goManagePage() {
    wd.get(app.getProperty("web.baseUrl") + "/login_page.php");
    type(By.name("username"), app.getProperty("web.adminLogin"));
//    click(By.cssSelector("input[value='Войти']"));
    type(By.name("password"), app.getProperty("web.adminPassword"));
//    click(By.cssSelector("input[value='Войти']"));
    click(By.cssSelector("input[value='Login']"));
    wd.get(app.getProperty("web.baseUrl") + "/manage_user_page.php");
//    wd.get(app.getProperty("web.baseUrl") + "/account_page.php");
//    wd.get(app.getProperty("web.baseUrl") + "/manage_user_create_page.php");
  }

  public UserData findUser() throws SQLException {

    Connection conn = null;
    try {
      conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bugtracker?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC"
              + "&user=root&password=");
      Statement st = conn.createStatement();
      ResultSet rs = st.executeQuery("select id, username from mantis_user_table where username!='administrator' LIMIT 1 ");

      while (rs.next()) {

        user = new UserData().withId(rs.getInt("id"))
                .withLogin(rs.getString("username"));
      }

      rs.close();
      st.close();
      conn.close();

    } catch (SQLException ex) {
      System.out.println("SQLException: " + ex.getMessage());
      System.out.println("SQLState: " + ex.getSQLState());
      System.out.println("VendorError: " + ex.getErrorCode());
    }

    return user;
  }

  public void resetPassword() throws SQLException, InterruptedException {
    wd.get(app.getProperty("web.baseUrl") + "manage_user_edit_page.php?user_id=" + user.getId());
//    click(By.cssSelector("input[value='Сбросить пароль']"));
//    wd.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);
//    Thread.sleep(1000);

//    WebElement webElem = wd.findElement(By.cssSelector("input[value='Сбросить пароль']"));

//    Actions action = new Actions(wd);
//    action.moveToElement(webElem).click().perform();
//    WebDriverWait wait = new WebDriverWait(wd, 20);
//    final WebElement kload= wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[value='Сбросить пароль']")));
//    wait.until(ExpectedConditions.stalenessOf(kload));

//    click(By.cssSelector("input[value='Сбросить пароль']"));
//    click(By.xpath("//form[@id='manage-user-reset-form']/fieldset/span/input"));
    click(By.cssSelector("input[value='Reset Password']"));
//    wd.findElement(By.cssSelector("input[value='Сбросить пароль']")).click();
  }


  public void changePassword(String newPassword) throws IOException, MessagingException, SQLException {
    List<MailMessage> mailMessages = app.mail().waitForMail(1, 10000);
    String confirmationLink = findConfirmationLink(mailMessages);
    wd.get(confirmationLink);
    type(By.name("password"), newPassword);
    type(By.name("password_confirm"), newPassword);
    //click(By.cssSelector(".width-100.width-40.pull-right.btn.btn-success.btn-inverse.bigger-110"));
    click(By.cssSelector("input[value='Update User']"));
//    type(By.name("username"), user.getLogin());
//    type(By.name("password"), newPassword);
//   click(By.cssSelector("input[value='Login']"));
//    wd.get(app.getProperty("web.baseUrl") + "manage_user_edit_page.php?user_id=" + user.getId());
    // http://joxi.ru/gmvljeeCpeWWAa
  }

  private String findConfirmationLink(List<MailMessage> mailMessages) throws SQLException {
    MailMessage mailMessage = mailMessages.get(0);
    VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
    return regex.getText(mailMessage.text);
  }

}
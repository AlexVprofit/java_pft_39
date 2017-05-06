package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;

public class ChangePasswordHelper extends HelperBase {

  private String user;
  private String password;
  private String username;

  public ChangePasswordHelper(ApplicationManager app) {
    super(app);
  }

  public void login(String user, String password){
    this.user = user;
    this.password = password;
    type(By.name("username"), user);
    click(By.cssSelector("input[value='Войти']"));
    type(By.name("password"), password);
    click(By.cssSelector("input[value='Войти']"));
//    click(By.cssSelector("input[value='Зарегистрироваться']"));
  }

  public void resetPassword(String user) {
    this.username = user;
    click(By.linkText("Управление пользователями"));
    click(By.linkText(username));
//    click(By.linkText(user));
    click(By.cssSelector("input[value='Сбросить пароль']"));
  }

  public void finish(String confirmationLink, String newpassword) {
    wd.get(confirmationLink);
    type(By.name("password"),newpassword);
    type(By.name("password_confirm"),newpassword);
    click(By.cssSelector("button[class='width-100 width-40 pull-right btn btn-success btn-inverse bigger-110']"));
  }
}

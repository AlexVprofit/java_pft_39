package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;

public class RegistrationHelper extends HelperBase {

  public RegistrationHelper(ApplicationManager app) {
    // вызываем конструктор базового класса  туда передаем ссылку на ApplicationManager и конструктор делает всё остальное
    super(app);
//    wd = app.wd; // взяли ссылку на драйвер у менеджера, но это сразу инициализирует драйвер
//    wd = app.getDriver(); // инициализирует драйвер в момент 1-го обращения (ленивая инициализация)
  }

  public void start(String username, String email) {
    wd.get(app.getProperty("web.baseUrl") + "/signup_page.php"); // открываем страницу через драйвер браузера
    // заполнение поля пользователь (login)
//    wd.findElement(By.name("username")).sendKeys(username);
    type(By.name("username"), username); // передали локатор элемента и текст кот. нужно ввести в поле найденное по локатору
    type(By.name("email"), email);
    click(By.cssSelector("input[value='Зарегистрироваться']"));
  }

  public void finish(String confirmationLink, String password) {
    wd.get(confirmationLink);  // идем по ссылке
    type(By.name("password"), password);// заполняем поля
    type(By.name("password_confirm"), password);// заполняем поля
  //  click(By.cssSelector("input[value='Update User']"));
    click(By.cssSelector("button[class='width-100 width-40 pull-right btn btn-success btn-inverse bigger-110']"));
  }
}

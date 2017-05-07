package ru.stqa.pft.mantis.tests;

import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.pft.mantis.model.MailMessage;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class RegistrationTests extends TestBase {

 //@BeforeMethod
  public void startMailServer(){
    app.mail().start();
  }

  @Test
  public  void  testRegistration() throws IOException, MessagingException {
    // создаем уникальные идентификаторы чтобы каждый раз былиновые пользователи
    long now = System.currentTimeMillis();
    String user = String.format("user%s", now);
    String password = "password";
//    String email = String.format("user%s@localhost.localdomain", now);
    String email = String.format("user%s@localhost", now);
    app.james().createUser(user, password);// создание пользователя на почтовом сервере внешнем james()
    app.registration().start(user, email); //первая часть регистрации
    // ожидание почты 2 письма 　15 сек
    //List<MailMessage> mailMessages = app.mail().waitForMail(2, 15000); // получение почты с внутреннего сервера
    List<MailMessage> mailMessages = app.james().waitForMail(user, password, 60000); // получение почты с james()
    // метод извлечения ссылки на письмо из списка писем отправленных на адрес email
    String confirmationLink = findConfirmationLink(mailMessages, email);
    app.registration().finish(confirmationLink, password); // завершаем регистрацию
    // проверка входа при помощи помощника по протоколу HTTP
    assertTrue(app.newSession().login(user, password));
  }



  private String findConfirmationLink(List<MailMessage> mailMessages, String email) {
    // ищем письмо кот-е отправленно на нужный адрес используя filter кот. передается предикат
    // (ф-я возв-щающая или ложь или истину) (m) -> m.to.equals(email) остануться только те сообщения в потоке кот. отправленны
    // по адресу email и берем первое сообщение
    MailMessage mailMessage = mailMessages.stream().filter((m) -> m.to.equals(email)).findFirst().get();
    // с помощью библиотеки регулярных выражений ищем
    VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
    // применили регулярное выражение к тексту письма и вернули
    // т.е. возвращается тот кусок текста кот. соответствует построенному regex
    // регулярному выражению
    return regex.getText(mailMessage.text);
  }

  //@AfterMethod(alwaysRun = true)
  public void stopMailServer(){
    app.mail().stop();
  }

}

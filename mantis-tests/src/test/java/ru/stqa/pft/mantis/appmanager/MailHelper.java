package ru.stqa.pft.mantis.appmanager;

        import org.subethamail.wiser.Wiser;
import org.subethamail.wiser.WiserMessage;
import ru.stqa.pft.mantis.model.MailMessage;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


public class MailHelper {
  private ApplicationManager app;
  private final Wiser wiser;

  public MailHelper(ApplicationManager app) {
    this.app = app;
    wiser = new Wiser(); // почтовый сервер
  }

  public List<MailMessage> waitForMail(int count, long timeout) throws MessagingException, IOException {
    long start = System.currentTimeMillis();
    while (System.currentTimeMillis() < start + timeout) {
      if (wiser.getMessages().size() >= count) {
        // преобразование реальных объектов почты в наши модельные объекты формата представления почты
        // (у нас это два поля: кому пришло письмо и текст этого письма)
        // берется список wiser.getMessages() превращается в поток stream() и ко всем элементам потока
        // применяется ф-я (m)->toModeMail(m) и получившиеся новые объекты собираем снова в список collect(Collectors.toList())
        return wiser.getMessages().stream().map((m)->toModeMail(m)).collect(Collectors.toList());

      }
      try {
        Thread.sleep(1000);

      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    throw new Error("No mail");
  }

  public static MailMessage toModeMail(WiserMessage m) { // преобразование реальных почтовых сообщений в модельные

    try {
      MimeMessage nm = m.getMimeMessage();
      // берется реальный объект nm.getAllRecipients() список получателей  и берем первого из них [0].toString()
      // а т.к. письмо mantis посылает текстовое, то объект контент есть обычная строка
      // преобразуем его в строку (String) nm.getContent() и полученное значение попадает в модельный объект MailMessage
      return new MailMessage(nm.getAllRecipients()[0].toString(), (String) nm.getContent());
    } catch (MessagingException e) {
      e.printStackTrace();
      return null;
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  public void start () {wiser.start();} // запуск почтового сервера
  public void stop () {wiser.stop();}   // остановка почтового сервера

}
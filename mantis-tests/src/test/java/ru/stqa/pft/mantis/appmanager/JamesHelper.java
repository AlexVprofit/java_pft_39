package ru.stqa.pft.mantis.appmanager;

import org.apache.commons.net.telnet.TelnetClient;
import ru.stqa.pft.mantis.model.MailMessage;

import javax.mail.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class JamesHelper {

  private ApplicationManager app;

  private TelnetClient telnet;
  private InputStream in;
  private PrintStream out;

  private Session mailSession;
  private Store store;
  private String mailServer;

  public  JamesHelper(ApplicationManager app) {
    this.app = app;
    telnet = new TelnetClient(); //создаётся telnet client при инициализации модуля JamesHelper
    mailSession = Session.getDefaultInstance(System.getProperties()); //создаётся почтовая сессия для начала чтения почты
  }

  public boolean doesUserExist(String name) {
    initTelnetSession();
    write("verify " + name);
    String result = readUntil("exist");
    closeTelnetSession();
    return  result.trim().equals("User " + name + " exist");
  }

  //создаётся новый пользователь
  public void createUser(String name, String password) {
    initTelnetSession(); //устанавливается соед по протоколу telnet
    write("adduser " + name + " " + password); //пишем команду
    String result = readUntil("User " + name + " added"); //ждем пока на консоли появится этот текст
    closeTelnetSession(); //разрываем соед
  }

  public void deleteUser(String name) {
    initTelnetSession();
    write("deluser " + name);
    String result = readUntil("User " + name + " added");
    closeTelnetSession();
  }

  private void initTelnetSession() { //устанавливается соед по протоколу telnet
    //получаем параметры из local.properties
    mailServer = app.getProperty("mailserver.host");
    int port = Integer.parseInt(app.getProperty("mailserver.port"));
    String login = app.getProperty("mailserver.adminlogin");
    String password = app.getProperty("mailserver.adminpassword");

    try {
      telnet.connect(mailServer,port); //устанавливаем соединение с почтовым сервером
      in = telnet.getInputStream(); //входной поток, данные которые telnet отправляем нам
      out = new PrintStream(telnet.getOutputStream()); //выходноц поток, отправляются команды ему
    } catch (Exception e) {
      e.printStackTrace();
    }

    readUntil("Login id:"); // метод чтения это пишет сервер
    write("");                // это мыему отправляем
    readUntil("Password:");
    write("");

    readUntil("Login id:");
    write(login);
    readUntil("Password:");
    write(password);

    readUntil("Welcome " + login + ". HELP for a list of commands");
  }

  private void closeTelnetSession() {
    write("quit");
  }

  /*  посимвольно читаются данные из входного потока, данные которые вовит на консоль сервер
    и сравнивается с заданным шаблоном, как только совпадет с шаблоном ожидание завершается*/
  private String readUntil(String pattern) {
    try {
      char lastChar = pattern.charAt(pattern.length() - 1);
      StringBuffer sb = new StringBuffer();
      char ch = (char) in.read();
      while (true) {
        System.out.print(ch);
        sb.append(ch);
        if (ch == lastChar){
          if(sb.toString().endsWith(pattern)) {
            return sb.toString();
          }
        }
        ch = (char) in.read();
      }
    } catch (Exception e){
      e.printStackTrace();
    }
    return null;
  }

  private void write(String value) {
    try {
      out.println(value);
      out.flush();
      System.out.println(value);
    } catch (Exception e){
      e.printStackTrace();
    }
  }

  public List<MailMessage> waitForMail(String username, String password, long timeout) throws MessagingException, IOException {
    long now = System.currentTimeMillis(); //запоминаем текущее время

    //цикл выполняется пока время старта < чем время старта плюс время таймаут
    while (System.currentTimeMillis() < now + timeout) {
      List<MailMessage> allMail = getAllMail(username, password);
      //если почты пришло нужное кол-во, то ожидание можно прекращать
      if (allMail.size() > 0) {
        return allMail;
      }
      //если почты всё еще мало то ждём 1000мс - 1 сек
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    throw new Error("No mail"); // почты нет прошло время ожидания и  allMail.size() = 0
  }

  private List<MailMessage> getAllMail(String username, String password) throws MessagingException {
    // извлекается почта из почтового ящика и превращается в модельные объекты
    Folder inbox = openInbox(username,password);  // открытие почтового ящика
    List<MailMessage> messages = Arrays.asList(inbox.getMessages()).stream()
            .map((m) -> toModelMail(m)).collect(Collectors.toList());
    closeFolder(inbox);  // закрытие почтового ящика
    return messages; // полученная почта возвращается
  }

  // удаляет все письма кот. полученны каким-то пользователем ( очиста почтового ящика) если нужно использовать оди и то же ящик
  public void drainEmail(String username, String password) throws MessagingException {
    Folder inbox = openInbox(username, password);
    for (Message message : inbox.getMessages()) {
      message.setFlag(Flags.Flag.DELETED, true); // пометка каждого сообщения флагом DELETED
    }
    closeFolder(inbox); // при закрытии папки все удаляется
  }

  private void closeFolder(Folder folder) throws MessagingException { // закрытие почтового ящика
    folder.close(true); // true сообщает что пред закрытием удалить все письма помеченные на удаление
    store.close(); // закрытие соединения с почтовым сервером
  }

  private Folder openInbox(String username, String password) throws MessagingException { // открытие почтового ящика
    //берем почтовую сессию которая была созадна в конструкторе при создании помощника JamesHelper
    store = mailSession.getStore("pop3"); // сообщаем используемый протокол POP3
    store.connect(mailServer,username,password); //устанавливаем соединение по данному протоколу, адрес почтового сервера mailServer и т.д.
    Folder folder = store.getDefaultFolder().getFolder("INBOX"); //получаем доступ к папке INBOX
    folder.open(Folder.READ_WRITE); //открываем ее на чтение и запись
    return folder; // возвращается в метод getAllMail
  }

  public static MailMessage toModelMail(Message m) { // преобразование писем в модельные
    try {
      return new MailMessage(m.getAllRecipients()[0].toString(), (String) m.getContent());
    } catch (MessagingException e) {
      e.printStackTrace();
      return null;
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }
}
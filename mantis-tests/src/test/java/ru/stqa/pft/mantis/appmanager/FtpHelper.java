package ru.stqa.pft.mantis.appmanager;

import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FtpHelper {

  private final ApplicationMeneger app;
  private FTPClient ftp;

  public FtpHelper (ApplicationMeneger app) {
    this.app = app;
    ftp = new FTPClient(); // инициализация ftp при вызове конструктора
  }

// загрузка нового файла и временно переименовывает старый
// file локалный файл котю. д.б. загружен на удалённую машину, target имя уделённого файла,
// backup имя резервной копии если удаленный файл уже существует
  public  void  upload(File file, String target, String backup) throws IOException {
    ftp.connect(app.getProperty("ftp.host")); // соединение с сервером ftp.host
    ftp.login(app.getProperty("ftp.login"), app.getProperty("ftp.password")); // вход - указание login и password
    ftp.deleteFile(backup); // удаление предыдущей локальной копии
    ftp.rename(target, backup); // делаем резервную копию путем переименования уделенного файла
    ftp.enterLocalPassiveMode(); // пассивный режим передачи данных
    ftp.storeFile(target, new FileInputStream(file)); // передача файла в удаленный файл
    ftp.disconnect();
  }
// восстановление старого файла
  public  void  restore(String target, String backup) throws IOException {
    ftp.connect(app.getProperty("ftp.host"));
    ftp.login(app.getProperty("ftp.login"), app.getProperty("ftp.password"));
    ftp.deleteFile(target);
    ftp.rename(backup, target);
    ftp.disconnect();
  }

}

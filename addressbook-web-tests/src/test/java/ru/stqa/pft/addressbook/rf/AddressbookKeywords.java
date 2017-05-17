package ru.stqa.pft.addressbook.rf;


import org.openqa.selenium.remote.BrowserType;
import ru.stqa.pft.addressbook.appmeneger.ApplicationMeneger;
import ru.stqa.pft.addressbook.model.GroupData;

import java.io.IOException;

public class AddressbookKeywords {

  private ApplicationMeneger app;

   // описываем ключевые слова Keywords: Init Application Meneger, Stop Application Meneger, Get Group Count, Create Group

  public void initApplicationMeneger() throws IOException {
    app = new ApplicationMeneger(System.getProperty("browser", BrowserType.CHROME));
    app.init();
  }

  public void stopApplicationMeneger () {
    app.stop();
    app =null; // навсякий случай чтобы объект уничтожить
  }

  public int getGroupCount () {
    app.goTo().groupPage();
    return app.group().count();
  }

  public void createGroup(String name, String header, String footer) {
    app.goTo().groupPage();
    app.group().create(new GroupData().withName(name).withHeader(header).withFooter(footer));
  }
}

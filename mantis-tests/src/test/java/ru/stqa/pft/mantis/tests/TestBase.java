package ru.stqa.pft.mantis.tests;

import biz.futureware.mantis.rpc.soap.client.IssueData;
import biz.futureware.mantis.rpc.soap.client.MantisConnectLocator;
import biz.futureware.mantis.rpc.soap.client.MantisConnectPortType;
import biz.futureware.mantis.rpc.soap.client.ObjectRef;
import org.openqa.selenium.remote.BrowserType;
import org.testng.SkipException;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import ru.stqa.pft.mantis.appmanager.ApplicationManager;

import javax.xml.rpc.ServiceException;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;

public class TestBase {

//  protected static final ApplicationManager app = new ApplicationManager(BrowserType.FIREFOX);
  public static final ApplicationManager app
        = new ApplicationManager(System.getProperty("browser", BrowserType.FIREFOX));

  @BeforeSuite  // инициализация метода
  public void setUp() throws Exception {
    app.init();
    app.ftp().upload(new File("src/test/resources/config_inc.php"),
            "config_inc.php", "config_inc.php.bak");
  }

  @AfterSuite(alwaysRun = true) // alwaysRun = true нужно чтобы метод всё-равно сработал
                               // (чтобы браузер гарантировано остановился)
  public void tearDown() throws IOException {
    app.ftp().restore( "config_inc.php.bak", "config_inc.php");
    app.stop();
  }

  public  boolean isIssueOpen(int issueId) throws MalformedURLException, ServiceException, RemoteException {
   String post = app.getProperty("web.apiSoapMantisConnect");
    MantisConnectPortType mc= new MantisConnectLocator()
//            .getMantisConnectPort(new URL("http://localhost/mantisbt-1.2.19/api/soap/mantisconnect.php"));
            .getMantisConnectPort(new URL(post));
    IssueData issueMarker = mc.mc_issue_get("administrator", "root", BigInteger.valueOf(issueId));
    ObjectRef status = issueMarker.getStatus();
    if (status.getName().equals("resolved"))
      return false;
    else return true;
  }

  public void skipIfNotFixed(int issueId) throws MalformedURLException, ServiceException, RemoteException {
    if (isIssueOpen(issueId)) {
      throw new SkipException("Ignored because of issue " + issueId);
    }
  }

}

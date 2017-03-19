package ru.stqa.pft.addressbook.appmeneger;

import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * Created by LEN on 19.03.2017.
 */
public class NavigationHelper extends HelperBase {

  public NavigationHelper(FirefoxDriver wd) {
    super(wd);
  }

  public void gotoGroupPage() {
    click(By.linkText("groups"));
  }

  public void menuHome() {
    click(By.linkText("home"));
  }
  public void submitPublicModification() {
    click(By.name("update"));
  }
}

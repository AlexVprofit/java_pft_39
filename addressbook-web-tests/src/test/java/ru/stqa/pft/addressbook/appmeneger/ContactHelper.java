package ru.stqa.pft.addressbook.appmeneger;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.concurrent.TimeUnit;

/**
 * Created by LEN on 19.03.2017.
 */
public class ContactHelper extends HelperBase {

  public ContactHelper(WebDriver wd) {
    super(wd);
  }

  public void refreshHomePage() {
    type(By.name("searchstring"), "");
  }

  public void returnHomePage() {
    click(By.linkText("home page"));
  }

  public void inputAddNewFormContact() {
    click(By.xpath("//div[@id='content']/form/input[21]"));
  }

  public void fillAddNewFormContact(ContactData contactData, boolean creation) {
    type(By.name("firstname"), contactData.getFirstname());
    type(By.name("lastname"), contactData.getLastname());
    type(By.name("title"), contactData.getTitle());
    type(By.name("company"), contactData.getCompany());
    type(By.name("address"), contactData.getNew_adress());
    type(By.name("home"), contactData.getTelhome());

    if (creation) {
      new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
    } else {
      Assert.assertFalse(isElementPresent(By.name("new_group")));
    }
  }

  public void gotoAddNew() {
    click(By.linkText("add new"));
  }

  public void deleteStringContact() {
    click(By.xpath("//div[@id='content']/form[2]/div[2]/input"));
  }

  public void selectStringContact() {
    if (!wd.findElement(By.name("selected[]")).isSelected()) {
      click(By.name("selected[]"));
    }
  }

  public void initContactModification() {
    click(By.xpath("//table[@id='maintable']/tbody/tr[2]/td[8]/a/img"));
  }

  public void confirmationDeleteContact() {
//    wd.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);
//    Alert alert = (new WebDriverWait(wd, 45)).until(ExpectedConditions.alertIsPresent());
//    wd.switchTo().alert().accept();
//    wd.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

    int tries = 0;
    int maxTries = 30;
    while (tries < maxTries) {
      tries++;

      try {
        disableImplicityWait();
        Alert alert = wd.switchTo().alert();
        if (alert != null && alert.getText().length() > 1) {
          alert.accept();
          enableImplicityWait();
          return;
        }

      } catch (Exception e) {
        e.getSuppressed();
      }
    }

  }
//

  private void disableImplicityWait() {
    wd.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
  }

  private void enableImplicityWait() {
    wd.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
  }

//


}

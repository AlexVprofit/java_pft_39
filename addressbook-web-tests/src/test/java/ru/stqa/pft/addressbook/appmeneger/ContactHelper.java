package ru.stqa.pft.addressbook.appmeneger;

import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;
import ru.stqa.pft.addressbook.model.ContactData;

/**
 * Created by LEN on 19.03.2017.
 */
public class ContactHelper extends HelperBase {

  public ContactHelper(FirefoxDriver wd) {
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

  public void fillAddNewFormContact(ContactData contactData) {
    type(By.name("firstname"), contactData.getFirstname());
    type(By.name("lastname"), contactData.getLastname());
    type(By.name("title"), contactData.getTitle());
    type(By.name("company"), contactData.getCompany());
    type(By.name("address"), contactData.getNew_adress());
    type(By.name("home"), contactData.getTelhome());
  }

  public void gotoAddNew() {
    click(By.linkText("add new"));
  }

  public void confirmationDeleteContact() {
    wd.switchTo().alert().accept();
  }

  public void deleteStringContact() {
    click(By.xpath("//div[@id='content']/form[2]/div[2]/input"));
  }

  public void selectStringContact() {
    if (!wd.findElement(By.name("selected[]")).isSelected()) {
      click(By.name("selected[]"));
    }
  }
}

package ru.stqa.pft.addressbook.appmeneger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LEN on 19.03.2017.
 */
public class ContactHelper extends HelperBase {

  private WebElement element;

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

  public void selectStringContact(int index) {
    element = wd.findElements(By.name("selected[]")).get(index);
    element.isSelected();
    if (!element.isSelected()) {
      element.click();
    }
/*
    if ( !wd.findElements(By.name("selected[]")).get(index).isSelected() ) {
      wd.findElements(By.name("selected[]")).get(index).click();
    }*/
/*
    if (!wd.findElement(By.name("selected[]")).isSelected()) {
      click(By.name("selected[]"));
    } */
  }

  public void initContactModification(int index) {
    element = wd.findElements(By.cssSelector("img[title*='Edit']")).get(index);
    element.isSelected();
    if (!element.isSelected()) {
      element.click();

    }
  }

  public void confirmationDeleteContact() {
    wd.switchTo().alert().accept();
  }

  public boolean isThereAContact() {
    return isElementPresent(By.name("selected[]"));
  }

  public void createContact(ContactData contact, boolean typ) {
    fillAddNewFormContact(contact, typ);
    inputAddNewFormContact();
    returnHomePage();
    refreshHomePage();
  }

  public int getContactCount() {
    return wd.findElements(By.name("selected[]")).size();
  }

  public List<ContactData> getContactList() {
    List<ContactData> contacts = new ArrayList<ContactData>();
    List<WebElement> elements = wd.findElements(By.name("entry"));
    for (WebElement element : elements) {
      // Преобразование строчного значения в число
      int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
      List<WebElement> cells = element.findElements(By.tagName("td"));
      String lastname = cells.get(1).getText();
      String firstname = cells.get(2).getText();
      String new_adress = cells.get(3).getText();
      String telhome = cells.get(5).getText();
      ContactData contact = new ContactData(id, firstname, lastname, null, null, new_adress,
              telhome, null);
      contacts.add(contact);
    }
    return contacts;
  }
}

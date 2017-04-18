package ru.stqa.pft.addressbook.appmeneger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.util.List;

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
    type(By.name("home"), contactData.getTelHome());

    if (creation) {
//      new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
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
  }

  public void selectStringContactById(int id) {
    element = wd.findElement(By.cssSelector("input[value='" + id + "']"));
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

  public void create(ContactData contact, boolean typ) {
    fillAddNewFormContact(contact, typ);
    inputAddNewFormContact();
    groupCache = null;
    returnHomePage();
    refreshHomePage();
  }

  public void modify(ContactData contact) {
    // Процедура выбора адреса и его модификация
    selectStringContactById(contact.getId());
    initContactModificationById(contact.getId(), 7, 0);
    fillAddNewFormContact(contact, false);
    submitPublicModification();
    groupCache = null;
    goHome();
  }

  public void delete(ContactData contact) {
    selectStringContactById(contact.getId());
    deleteStringContact();
    confirmationDeleteContact();
    groupCache = null;
    goHome();
  }


  public int сount() {
    return wd.findElements(By.name("selected[]")).size();
  }

  // КЭШ реализация
  private Contacts groupCache = null;

  public Contacts all() {
    if (groupCache != null) {
      return new Contacts(groupCache);
    }
    groupCache = new Contacts();
    List<WebElement> elements = wd.findElements(By.name("entry"));
    for (WebElement element : elements) {
      // Преобразование строчного значения в число
      int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
      List<WebElement> cells = element.findElements(By.tagName("td"));
      String lastname = cells.get(1).getText();
      String firstname = cells.get(2).getText();
      String new_adress = cells.get(3).getText();
//    ОСТАВЛЕНО для ПОВТОРЕНИЯ
//    Формируем массив строк с разделением на элементы посредством split("\n")
      /*
      String[] emails = cells.get(4).getText().split("\n");
      String[] phones = cells.get(5).getText().split("\n");
     */
      String allEmails = cells.get(4).getText();
      String allPhones = cells.get(5).getText();
      groupCache.add(new ContactData().withId(id).withFirstname(firstname).withLastname(lastname)
              .withNew_adress(new_adress).withAllEmails(allEmails)
              .withAllPhones(allPhones).withTelHome(allPhones));
    }
    return new Contacts(groupCache);
  }


  public ContactData allDetails(ContactData contact, int index) {
    //  выбираем страницу с подробной информацией о контакте Details
   initContactModificationById(contact.getId(),index,0);
   String alldetails =wd.findElement(By.xpath("//*[@id='content']")).getText();
    // возвращаем предварительно заполненную подробную информацию о Контакте
    return new ContactData().withId(contact.getId()).withAllDetails(alldetails);
  }

  public ContactData infoFromEditForm(ContactData contact, int v) {
    initContactModificationById(contact.getId(), 7, v);
    String firstname = wd.findElement(By.name("firstname")).getAttribute("value");
    String lastname = wd.findElement(By.name("lastname")).getAttribute("value");
    String address = wd.findElement(By.name("address")).getText();
    String email1 = wd.findElement(By.name("email")).getAttribute("value");
    String email2 = wd.findElement(By.name("email2")).getAttribute("value");
    String email3 = wd.findElement(By.name("email3")).getAttribute("value");
    String telhome = wd.findElement(By.name("home")).getAttribute("value");
    String mobilPhone = wd.findElement(By.name("mobile")).getAttribute("value");
    String workPhone = wd.findElement(By.name("work")).getAttribute("value");
    String phone2Phone = wd.findElement(By.name("phone2")).getAttribute("value");
    // Выходим из формы редактирования Контакта
    wd.navigate().back();
      // возвращаем предварительно заполненный список полей Контакта (множество значений) в таблицу контактов
      return new ContactData().withId(contact.getId()).withFirstname(firstname).withLastname(lastname)
              .withNew_adress(address).withEmail1(email1).withEmail2(email2).withEmail3(email3).withTelHome(telhome)
              .withMobilePhone(mobilPhone).withWorkPhone(workPhone).withPhone2Phone(phone2Phone);
  }
// ЭТО ВАРИАНТ ПРЕДЫДУЩЕГО ЗАДАНИЯ 11
  /*
  public void initContactModificationById(int id) {
    element = wd.findElement(By.cssSelector("a[href='edit.php?id=" +  id + "']"));
    element.isSelected();
    if (!element.isSelected()) {
      element.click();
    }
  }
  */

  // ЭТО ВАРИАНТЫ ВЫБОРА КОНТАКТА по ЗАДАННОМУ ИДЕНТИФИКАТОРУ
  public void initContactModificationById(int id, int index, int v) {
    if (v == 0) {
    // МЕТОД ПОСЛЕДВАТЕЛЬНЫХ ПРИБЛИЖЕНИЙ
    // ищем checkbox
    WebElement checkbox = wd.findElement(By.cssSelector(String.format("input[value='%s']", id)));
    // Вверх на 2 уровня чтобы посчитать колонки td т.е. идём  к родителю tr
    WebElement row = checkbox.findElement(By.xpath("./../.."));
    // Получаем список (множество) td
    List<WebElement> cells = row.findElements(By.tagName("td"));
    // Выбираем нужную колонку (8 номер счет идет от 0)
    cells.get(index).findElement(By.tagName("a")).click();
  } else if (v == 1){
      wd.findElement(By.cssSelector("input[value='Modify']")).click();
    }

// И для примерчиков  ( в xpath нумерация с 1 начинается)
//  wd.findElement(By.xpath(String.format("//input[@value='%s']/../../td[8]/a",id))).click();
//  wd.findElement(By.xpath(String.format("//tr[.//input[@value='%s']]/td[8]/a",id))).click();
//  wd.findElement(By.cssSelector(String.format("a[href='edit.php?id=%s']",id))).click();

  }

}

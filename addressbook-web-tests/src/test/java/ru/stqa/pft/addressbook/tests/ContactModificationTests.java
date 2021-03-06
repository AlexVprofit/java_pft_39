package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;


public class ContactModificationTests extends TestBase {

  // инициализация локальная - подготовка состояния
  @BeforeMethod
  public void ensurePreconditions() {
    // Проверка наличия хоть одной группы
    app.group().check(new GroupData().withName("test1"));
    // Проверка наличия хоть  одного адреса
    app.goTo().goHome();
    if (app.db().contacts().size() == 0) {
      app.contact().gotoAddNew();
      app.contact().create(new ContactData().withFirstname("Alex1").withLastname("Alexbond").withTitle("Title")
              .withCompany("Education").withNew_adress("new adress").withTelHome("12345")/*.withGroup("test1")*/, true);
    }
  }

  //  @Test(enabled = false)
  @Test
  public void testContactModification() {
    Contacts before = app.db().contacts();
    //  получаем какой-нибудь элемент множества (т.е случайный)
    ContactData modifyContact = before.iterator().next();
    ContactData contact = new ContactData().withId(modifyContact.getId()).withFirstname("Alex").withLastname("Alexbond")
            .withTitle("Title").withCompany("Education").withNew_adress("new adress FOR VERIFICATION 1");
    //.withTelHome("12345");
//    app.contact().gotoAddNew();
    app.contact().modify(contact);
    // хэширование по размеру контактов , если падает то дальше тест не выполняется
    assertThat(app.contact().сount(), equalTo(before.size()));
    Contacts after = app.db().contacts();
    ContactData contactnew = app.db().contactsidmodi(modifyContact.getId());
    assertThat(after, equalTo(before.without(modifyContact).withAdded(contactnew)));
    // проверка контактов считанных с UI с контактами считаными по запросу с бд
    verifyContactListInUI();
  }

}
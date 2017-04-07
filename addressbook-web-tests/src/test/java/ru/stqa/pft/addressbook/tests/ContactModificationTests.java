package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;


public class ContactModificationTests extends TestBase {

  // Инициализация локальная - Подготовка состояния
  @BeforeMethod
  public void ensurePreconditions() {
    // Проверка наличия хоть одной группы
    app.group().check(new GroupData().withName("test1"));
    // Проверка наличия хоть  одного адреса
    app.goTo().goHome();
    if (app.contact().all().size() == 0) {
      app.contact().gotoAddNew();
      app.contact().create(new ContactData().withFirstname("Alex1").withLastname("Alexbond").withTitle("Title")
              .withCompany("Education").withNew_adress("new adress").withTelhome("12345").withGroup("test1"), true);
    }
  }

  //  @Test(enabled = false)
  @Test
  public void testContactModification() {
    Contacts before = app.contact().all();
    //  получаем какой-нибудь элемент множества (т.е случайный)
    ContactData modifyContact = before.iterator().next();
    ContactData contact = new ContactData().withId(modifyContact.getId()).withFirstname("Alex").withLastname("Alexbond").withTitle("Title")
            .withCompany("Education").withNew_adress("new adress FOR VERIFICATION 1").withTelhome("12345");
    app.contact().modify(contact);
    Contacts after = app.contact().all();
    assertEquals(after.size(), before.size());

    assertThat(after, equalTo(before.without(modifyContact).withAdded(contact)));
  }

}

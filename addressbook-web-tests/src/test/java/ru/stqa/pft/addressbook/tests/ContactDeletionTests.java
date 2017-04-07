package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class ContactDeletionTests extends TestBase {

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
              .withCompany("Education").withNew_adress("new adress").withTelhome("12345")
              .withGroup("test1"), true);
    }
  }

  //  @Test(enabled = false)
  @Test
  public void testContactDeletion() {
    Contacts before = app.contact().all();
    //  получаем какой-нибудь элемент множества (т.е случайный)
    ContactData deletedContact = before.iterator().next();
    // Процедура выбора адреса и его удаление
    app.contact().delete(deletedContact);
    Contacts after = app.contact().all();
    // Сравнение размеров списков
    assertEquals(after.size(), before.size() - 1);

    // Сравнение списков  до и после удаления
    assertThat(after, equalTo(before.without(deletedContact)));
  }

}

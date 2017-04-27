package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.appmeneger.NavigationHelper;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;


public class ContactAddGroupTests extends TestBase {

  // предусловие, инициализация локальная - подготовка состояния
  @BeforeMethod
  public void ensurePreconditions() {
    // Проверка наличия хоть одной группы и её добавление  если таковой нет
    if (app.db().groups().size() == 0) {
      app.goTo().groupPage();
      app.group().create(new GroupData().withName("test1"));
    }
    // Проверка наличия хоть  одного адреса
    app.goTo().goHome();
    if (app.contact().all().size() == 0) {
      app.contact().gotoAddNew();
      app.contact().create(new ContactData().withFirstname("Alex1").withLastname("Alexbond").withTitle("Title")
                      .withCompany("Education").withNew_adress("new adress").withTelHome("12345")
              /*.withGroup("test1")*/, true);
    }
  }


  @Test
  public void testContactAddGroup() {
    Contacts beforecontact = app.db().contacts(); //список контактов из бд
    Groups group = app.db().groups();       //список групп из бд
    ContactData contactForGroup = beforecontact.iterator().next(); // выбор произвольного контакта
    GroupData groupForContact = group.iterator().next();       // выбор произвольной группы
    Groups beforeInGroups = app.db().contactAddGroups(); // до добавления контакта в группы
    app.goTo().goHome();
    app.contact().ContactAddToGroup(contactForGroup.getId(), groupForContact.getName());
    app.goTo().goHome();
     Contacts aftercontact = app.db().contacts();
    assertThat(aftercontact.size(), equalTo(beforecontact.size())); // проверка на совпадение колич-ва контактов
    Groups afterInGrous = app.db().contactAddGroups(); // после добавления контакта в группы
    // проверка на соответствие
    assertThat((afterInGrous), equalTo(new Groups(beforeInGroups.withAdded(groupForContact))));

  }


}

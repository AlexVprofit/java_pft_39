package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactDelGroupTests extends TestBase {

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
    // проверка наличия контакта  в группе
    Contacts beforecontact = app.db().contacts(); //список контактов из бд
    ContactData contactForGroup = beforecontact.iterator().next(); // выбор произвольного контакта
    if (contactForGroup.getGroups().size() == 0) { // контакт не входит ни в одну из  групп, добавлеем в любую группу
      Groups group = app.db().groups();       //список групп из бд
      GroupData groupForContact = group.iterator().next();       // выбор произвольной группы
      app.contact().ContactAddToGroup(contactForGroup.getId(), groupForContact.getName());
      app.goTo().goHome();
    }
  }

  @Test
  public void testContactDelGroup() {
    Contacts beforecontact = app.db().contacts(); //список контактов из бд
    ContactData contactForGroup = beforecontact.iterator().next(); // выбор произвольного контакта
    Groups beforeInGroups = app.db().contactAllCountGroups(); // до удаления контакта в группы
    String name = contactForGroup.getGroups().iterator().next().getName();
    app.goTo().goHome();
    app.contact().ContactDelToGroup(contactForGroup.getId(), name); // удаление контакта из группы

    // группа из которой удалили контакт
    GroupData groupForContact = contactForGroup.getGroups()
            .stream().filter(g -> g.getName().equals(name)).findFirst().get();

    Contacts aftercontact = app.db().contacts();
    // хэширование по размеру контактов , если падает то дальше тест не выполняется
    assertThat(aftercontact.size(), equalTo(beforecontact.size())); // проверка на совпадение колич-ва контактов

    Groups afterInGrous = app.db().contactAllCountGroups(); // после удаление контакта из группы
    // проверка на соответствие
    assertThat((afterInGrous), equalTo(new Groups(beforeInGroups.without(groupForContact))));
  }

}

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

  }

  @Test
  public void testContactDelGroup() {
    Contacts beforecontact = app.db().contacts(); //список контактов из бд
    Groups group = app.db().groups();       //список групп из бд
    ContactData contactForGroup = beforecontact.iterator().next(); // выбор произвольного контакта
    Groups beforeInGroups = app.db().contactAllCountGroups(); // до удаления контакта в группы
    app.goTo().goHome();
    if (contactForGroup.getGroups().size() > 0) {
      String name = contactForGroup.getGroups().iterator().next().getName();
      app.contact().ContactDelToGroup(contactForGroup.getId(), name); // удаление контакта из группы

      // группа из которой удалили контакт
      GroupData groupForContact = contactForGroup.getGroups()
              .stream().filter(g -> g.getName().equals(name)).findFirst().get();

      Contacts aftercontact = app.db().contacts();
      // хэширование по размеру контактов , если падает то дальше тест не выполняется
      assertThat(aftercontact.size(), equalTo(beforecontact.size() - 1)); // проверка на совпадение колич-ва контактов

      Groups afterInGrous = app.db().contactAllCountGroups(); // после удаление контакта из группы
      // проверка на соответствие
      assertThat((afterInGrous), equalTo(new Groups(beforeInGroups.without(groupForContact))));
    }

  }

  }

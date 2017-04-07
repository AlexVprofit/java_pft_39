package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.List;


public class ContactModificationTests extends TestBase {

  // Инициализация локальная - Подготовка состояния
  @BeforeMethod
public  void ensurePreconditions() {
    // Проверка наличия хоть одной группы
    app.group().check(new GroupData().withName("test1"));
    // Проверка наличия хоть  одного адреса
    app.goTo().goHome();
    if (app.contact().list().size() == 0) {
      app.contact().gotoAddNew();
      app.contact().create(new ContactData().withFirstname("Alex1").withLastname("Alexbond").withTitle("Title")
              .withCompany("Education").withNew_adress("new adress").withTelhome("12345").withGroup("test1"), true);
    }
  }

//  @Test(enabled = false)
  @Test
  public void testContactModification() {
    List<ContactData> before = app.contact().list();
    int index = before.size() - 1;
    ContactData contact = new ContactData().withFirstname("Alex").withLastname("Alexbond").withTitle("Title")
            .withCompany("Education").withNew_adress("new adress FOR VERIFICATION 1").withTelhome("12345");
    app.contact().modify(index, contact);
    List<ContactData> after = app.contact().list();
    Assert.assertEquals(after.size(), before.size());

    // Для логики/красоты удаляем предыдущий список и записываем модифицированный список
    before.remove(index);
    before.add(contact);

    // Сортировка спискков ( до и после создания )
    // Лямбда выражение: анонимная функция -> на входе два параметра, это две группы, а сравниваем 2 идентификатора
    Comparator<? super ContactData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
    before.sort(byId);
    after.sort(byId);

    // Преобразование списков в множества (что бы сравнивать не задумываясь о порядке средования строк при сравнении)
    /*Assert.assertEquals(new HashSet<Object>(before), new HashSet<Object>(after));*/

    // Теперь в преобразовании нет необходимости
    Assert.assertEquals(before, after);

  }

}

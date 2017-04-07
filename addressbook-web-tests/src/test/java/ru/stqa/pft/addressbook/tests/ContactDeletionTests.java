package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.List;

public class ContactDeletionTests extends TestBase {

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
              .withCompany("Education").withNew_adress("new adress").withTelhome("12345")
              .withGroup("test1"), true);
    }
  }

  //  @Test(enabled = false)
  @Test
  public void testContactDeletion() {
    List<ContactData> before = app.contact().list();
    int index = before.size() - 1;
    // Процедура выбора адреса и его удаление
    app.contact().delete(index);
    List<ContactData> after = app.contact().list();

    // Сравнение размеров списков
    Assert.assertEquals(after.size(), before.size() - 1);

    before.remove(index);
    // Сравнение списков  до и после удаления
    Assert.assertEquals(before, after);
  }

}

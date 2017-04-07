package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

public class NewContactCreationTest extends TestBase {

//  @Test(enabled = false)
  @Test
  public void testNewContactCreation() {
    app.goTo().goHome();
    List<ContactData> before = app.contact().list();
    ContactData contact = new ContactData().withFirstname("Alex1").withLastname("Alexbond").withTitle("Title")
            .withCompany("Education").withNew_adress("new adress").withTelhome("12345").withGroup("test1");
    app.contact().gotoAddNew();
    app.contact().create(contact, true);
    List<ContactData> after = app.contact().list();
    Assert.assertEquals(after.size(), before.size() + 1);

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

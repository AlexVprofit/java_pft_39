package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.HashSet;
import java.util.List;

public class NewContactCreationTest extends TestBase {

  @Test
  public void testNewContactCreation() {
    app.getNavigationHelper().menuHome();
    List<ContactData> before = app.getContactHelper().getContactList();
    app.getContactHelper().gotoAddNew();
    ContactData contact = new ContactData("Alex", "Alexbond", "Title",
            "Education", "new adress", "12345", "test1");
    app.getContactHelper().createContact(contact, true);
    List<ContactData> after = app.getContactHelper().getContactList();
    Assert.assertEquals(after.size(), before.size() + 1);

    // После создания нового строки ищем максимальный id для последующей записи его в ней
    int max = 0;
    for (ContactData g : after) {
      if (g.getId() > max ) {
        max = g.getId();
      }
    }

    contact.setId(max);
    before.add(contact);
    // Преобразование списков в множества (что бы сравнивать не задумываясь о порядке средования строк при сравнении)
    Assert.assertEquals(new HashSet<Object>(before), new HashSet<Object>(after));

  }

}

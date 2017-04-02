package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.List;

public class NewContactCreationTest extends TestBase {

  @Test
  public void testNewContactCreation() {
    app.getNavigationHelper().menuHome();
    List<ContactData> before = app.getContactHelper().getContactList();
    app.getContactHelper().gotoAddNew();
    app.getContactHelper().createContact(new ContactData("Alex", "Alexbond", "Title",
            "Education", "new adress", "12345", "test1"), true);
    List<ContactData> after = app.getContactHelper().getContactList();
    Assert.assertEquals(after.size(), before.size() + 1);
  }

}

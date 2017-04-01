package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class NewContactCreationTest extends TestBase {

  @Test
  public void testNewContactCreation() {
    app.getNavigationHelper().menuHome();
    int before = app.getContactHelper().getContactCount();
    app.getContactHelper().gotoAddNew();
    app.getContactHelper().createContact(new ContactData("Alex", "Alexbond", "Title",
            "Education", "new adress", "12345", "test1"), true);
    int after = app.getContactHelper().getContactCount();
    Assert.assertEquals(after, before + 1);
  }

}

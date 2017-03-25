package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class NewContactCreationTest extends TestBase {

  @Test
  public void testNewContactCreation() {
    app.getContactHelper().gotoAddNew();
    app.getContactHelper().fillAddNewFormContact(new ContactData("Alex", "Alexbond", "Title",
            "Education", "new adress", "12345", "test1"), true);
    app.getContactHelper().inputAddNewFormContact();
    app.getContactHelper().returnHomePage();
    app.getContactHelper().refreshHomePage();
  }

}

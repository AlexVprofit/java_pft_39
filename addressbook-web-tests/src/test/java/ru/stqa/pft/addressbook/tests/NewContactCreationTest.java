package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class NewContactCreationTest extends TestBase {

  @Test
  public void testNewContactCreation() {
    app.getGroupHelper().gotoAddNew();
    app.getGroupHelper().fillAddNewFormContact(new ContactData("Alex", "Alexbond", "Title", "Education", "new adress", "12345"));
    app.getGroupHelper().inputAddNewFormContact();
    app.getGroupHelper().returnHomePage();
    app.getGroupHelper().refreshHomePage();
  }

}

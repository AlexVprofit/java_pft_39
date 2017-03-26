package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactDeletionTests extends TestBase {

  @Test
  public void testContactDeletion() {
    app.getNavigationHelper().menuHome();
     if(! app.getContactHelper().isThereAContact() ) {
       app.getContactHelper().gotoAddNew();
       app.getContactHelper().createContact(new ContactData("Alex", "Alexbond", "Title",
               "Education", "new adress", "12345", "test1"), true);
     }
    app.getContactHelper().selectStringContact();
    app.getContactHelper().deleteStringContact();
    app.getContactHelper().confirmationDeleteContact();
    app.getNavigationHelper().menuHome();
  }
}

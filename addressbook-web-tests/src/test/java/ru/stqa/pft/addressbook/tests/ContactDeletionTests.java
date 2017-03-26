package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;

public class ContactDeletionTests extends TestBase {

  @Test
  public void testContactDeletion() {
    app.getNavigationHelper().menuHome();

    app.getContactHelper().selectStringContact();
    app.getContactHelper().deleteStringContact();
    app.getContactHelper().confirmationDeleteContact();
    app.getNavigationHelper().menuHome();
  }
}

package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;

public class ContactDeletionTests extends TestBase {

  @Test
  public void testContactDeletion() {
    app.getNavigationHelper().menuHome();
    app.getGroupHelper().selectStringContact();
    app.getGroupHelper().deleteStringContact();
    app.getGroupHelper().confirmationDeleteContact();
  }
}

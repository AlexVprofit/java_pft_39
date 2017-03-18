package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;

public class ContactDeletionTests extends TestBase {

  @Test
  public void testContactDeletion() {
    app.menuHome();
    app.selectStringContact();
    app.deleteStringContact();
    app.confirmationDeleteContact();
  }
}

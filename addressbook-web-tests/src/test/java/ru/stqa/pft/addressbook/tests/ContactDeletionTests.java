package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

public class ContactDeletionTests extends TestBase {

  @Test
  public void testContactDeletion() {
    // Проверка наличия хоть одной группы
    app.getGroupHelper().checkGroup(new GroupData("test1", null, null));
    // Проверка наличия хоть  одного адреса
    app.getNavigationHelper().menuHome();
    int before = app.getContactHelper().getContactCount();
     if(! app.getContactHelper().isThereAContact() ) {
       app.getContactHelper().gotoAddNew();
       app.getContactHelper().createContact(new ContactData("Alex", "Alexbond", "Title",
               "Education", "new adress", "12345", "test1"), true);
       before++;      // При проверке выяснилось отсутствие контакта, его создали и  before++
     }
     // Процедура выбора адреса и его удаление
    app.getContactHelper().selectStringContact(before -1);
    app.getContactHelper().deleteStringContact();
    app.getContactHelper().confirmationDeleteContact();
    app.getNavigationHelper().menuHome();
    int after = app.getContactHelper().getContactCount();
    Assert.assertEquals(after, before - 1);

  }
}

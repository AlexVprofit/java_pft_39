package ru.stqa.pft.addressbook.tests;

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
     if(! app.getContactHelper().isThereAContact() ) {
       app.getContactHelper().gotoAddNew();
       app.getContactHelper().createContact(new ContactData("Alex", "Alexbond", "Title",
               "Education", "new adress", "12345", "test1"), true);
     }
     // Процедура выбора адреса и его удаление
    app.getContactHelper().selectStringContact();
    app.getContactHelper().deleteStringContact();
    app.getContactHelper().confirmationDeleteContact();
    app.getNavigationHelper().menuHome();
  }
}

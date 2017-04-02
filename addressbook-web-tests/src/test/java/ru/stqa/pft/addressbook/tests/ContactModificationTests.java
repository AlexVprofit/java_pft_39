package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.List;

/**
 * Created by LEN on 19.03.2017.
 */
public class ContactModificationTests extends TestBase {

  @Test
  public void testContactModification() {
    /**  Добавлен  вспомогательный метод [ initContactModification ] для [Edit contact] в классе getContactHelper()
     *  Примечание:
     * Для  [Update] метод [ submitPublicModification ] в класс getNavigationHelper() был добавлен ранее
     */
    // Проверка наличия хоть одной группы
    app.getGroupHelper().checkGroup(new GroupData("test1", null, null));
    // Проверка наличия хоть  одного адреса
    app.getNavigationHelper().menuHome();
    if(! app.getContactHelper().isThereAContact() ) {
      app.getContactHelper().gotoAddNew();
      app.getContactHelper().createContact(new ContactData("Alex", "Alexbond", "Title",
              "Education", "new adress", "12345", "test1"), true);
    }
    List<ContactData> before = app.getContactHelper().getContactList();
    app.getContactHelper().selectStringContact(before.size() -1);
    app.getContactHelper().initContactModification();
    app.getContactHelper().fillAddNewFormContact(new ContactData("Alex", "Alexbond", "Title",
            "Education", "new adress FOR VERIFICATION 1",
            "12345", null), false);
    app.getNavigationHelper().submitPublicModification();
    app.getNavigationHelper().menuHome();
    List<ContactData> after = app.getContactHelper().getContactList();
    Assert.assertEquals(after.size(), before.size());
  }
}

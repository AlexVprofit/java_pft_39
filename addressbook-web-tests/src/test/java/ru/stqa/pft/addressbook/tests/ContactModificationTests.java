package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

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
    app.getNavigationHelper().menuHome();
    app.getContactHelper().selectStringContact();
    app.getContactHelper().initContactModification();
    app.getContactHelper().fillAddNewFormContact(new ContactData("Alex", "Alexbond", "Title",
            "Education", "new adress FOR VERIFICATION",
            "12345", null), false
    );
    app.getNavigationHelper().submitPublicModification();
  }
}

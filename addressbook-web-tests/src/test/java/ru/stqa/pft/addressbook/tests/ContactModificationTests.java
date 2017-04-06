package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.List;


public class ContactModificationTests extends TestBase {

  @Test(enabled = false)
  //@Test
  public void testContactModification() {
    /**  Добавлен  вспомогательный метод [ initContactModification ] для [Edit contact] в классе getContactHelper()
     *  Примечание:
     * Для  [Update] метод [ submitPublicModification ] в класс getNavigationHelper() был добавлен ранее
     */
    // Проверка наличия хоть одной группы
    app.getGroupHelper().checkGroup(new GroupData("test1", null, null));
    // Проверка наличия хоть  одного адреса
    app.getNavigationHelper().menuHome();
    if (!app.getContactHelper().isThereAContact()) {
      app.getContactHelper().gotoAddNew();
      app.getContactHelper().createContact(new ContactData("Alex", "Alexbond", "Title",
              "Education", "new adress", "12345", "test1"), true);
    }
    List<ContactData> before = app.getContactHelper().getContactList();
    app.getContactHelper().selectStringContact(before.size() - 1);
    app.getContactHelper().initContactModification(before.size() - 1);
    ContactData contact = new ContactData(before.get(before.size() - 1).getId(), "Alex", "Alexbond", "Title",
            "Education", "new adress FOR VERIFICATION 1",
            "12345", null);
    app.getContactHelper().fillAddNewFormContact(contact, false);
    app.getGroupHelper().submitPublicModification();
    app.getNavigationHelper().menuHome();
    List<ContactData> after = app.getContactHelper().getContactList();
    Assert.assertEquals(after.size(), before.size());

    // Для логики/красоты удаляем предыдущий список и записываем модифицированный список
    before.remove(before.size() - 1);
    before.add(contact);

    // Сортировка спискков ( до и после создания )
    // Лямбда выражение: анонимная функция -> на входе два параметра, это две группы, а сравниваем 2 идентификатора
    Comparator<? super ContactData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
    before.sort(byId);
    after.sort(byId);

    // Преобразование списков в множества (что бы сравнивать не задумываясь о порядке средования строк при сравнении)
    /*Assert.assertEquals(new HashSet<Object>(before), new HashSet<Object>(after));*/

    // Теперь в преобразовании нет необходимости
    Assert.assertEquals(before, after);

  }
}

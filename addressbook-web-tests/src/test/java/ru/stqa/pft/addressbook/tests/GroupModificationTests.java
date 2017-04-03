package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.HashSet;
import java.util.List;

/**
 * Created by LEN on 19.03.2017.
 */
public class GroupModificationTests extends TestBase {

  @Test
  public void testGroupModification() {
    /**  Добавлены два вспомогательных метода для [Edit group] и [Update]
     * [ initGroupModification ] &  [ submitPublicModification ] в классах getGroupHelper(),  getNavigationHelper()
     */
    app.getNavigationHelper().gotoGroupPage();
     // Если при проверке выясниться, что групп нет, то будет созданна группа
    app.getGroupHelper().checkGroup(new GroupData("test1", null, null));
    List<GroupData> before = app.getGroupHelper().getGroupList();
    app.getGroupHelper().selectGroup(before.size() - 1);
    app.getGroupHelper().initGroupModification();
    GroupData group = new GroupData(before.get(before.size() - 1).getId(), "test1",
            "test2 FOR VERIFICATION", "test3");
    app.getGroupHelper().fillGroupForm(group);
    app.getNavigationHelper().submitPublicModification();
    app.getGroupHelper().returnToGroupPage();
    List<GroupData> after = app.getGroupHelper().getGroupList();
    Assert.assertEquals(after.size(),before.size());

    // Для логики/красоты удаляем предыдущий список и записываем модифицированный список
    before.remove(before.size() - 1);
    before.add(group);

    // Преобразование списков в множества (что бы сравнивать не задумываясь о порядке средования строк при сравнении)
    Assert.assertEquals(new HashSet<Object>(before), new HashSet<Object>(after));
  }
}

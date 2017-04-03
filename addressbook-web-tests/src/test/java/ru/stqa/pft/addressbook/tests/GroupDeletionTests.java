package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.List;

public class GroupDeletionTests extends TestBase {

  @Test
  public void testGroupDeletion() {
    app.getNavigationHelper().gotoGroupPage();
    // Предусловие Проверка наличия хоть одной группы
    // Если при проверке выясниться, что групп нет, то будет созданна группа
    app.getGroupHelper().checkGroup(new GroupData("test1", null, null));
    List<GroupData> before = app.getGroupHelper().getGroupList();
    app.getGroupHelper().selectGroup(before.size() - 1);
    app.getGroupHelper().deleteSelectedGroups();
    app.getGroupHelper().returnToGroupPage();
    List<GroupData> after = app.getGroupHelper().getGroupList();

    // Сравнение размеров списков
    Assert.assertEquals(after.size(),before.size() - 1);

    before.remove(before.size() - 1);
    // Сравнение списков  до и после удаления
      Assert.assertEquals(before, after);

  }

}

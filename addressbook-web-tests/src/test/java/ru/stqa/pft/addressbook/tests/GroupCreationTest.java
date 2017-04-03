package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.HashSet;
import java.util.List;

public class GroupCreationTest extends TestBase {

  @Test
  public void testGroupCreation() {
    app.getNavigationHelper().gotoGroupPage();
    List<GroupData> before = app.getGroupHelper().getGroupList();
    GroupData group = new GroupData("test1", null, null);
    app.getGroupHelper().createGroup(group);
    List<GroupData> after = app.getGroupHelper().getGroupList();
    Assert.assertEquals(after.size(),before.size() + 1);

    // После создания нового строки ищем максимальный id для последующей записи его в ней
    int max = 0;
    for (GroupData g : after) {
      if (g.getId() > max ) {
        max = g.getId();
      }
    }
    group.setId(max);
    before.add(group);
    // Преобразование списков в множества (что бы сравнивать не задумываясь о порядке средования строк при сравнении)
    Assert.assertEquals(new HashSet<Object>(before), new HashSet<Object>(after));
  }

}

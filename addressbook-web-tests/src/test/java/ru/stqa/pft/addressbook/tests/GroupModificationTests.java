package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

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
    int before = app.getGroupHelper().getGroupCount();
     // Если при проверке выясниться, что групп нет, то будет созданна группа и  before++
    if( app.getGroupHelper().checkGroup(new GroupData("test1", null, null)) ) before++;
    app.getGroupHelper().selectGroup();
    app.getGroupHelper().initGroupModification();
    app.getGroupHelper().fillGroupForm(new GroupData("test1", "test2 FOR VERIFICATION", "test3"));
    app.getNavigationHelper().submitPublicModification();
    app.getGroupHelper().returnToGroupPage();
    int after = app.getGroupHelper().getGroupCount();
    Assert.assertEquals(after,before);

  }
}

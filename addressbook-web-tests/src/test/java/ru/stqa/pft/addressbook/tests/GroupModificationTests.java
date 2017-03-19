package ru.stqa.pft.addressbook.tests;

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
    app.getGroupHelper().selectGroup();
    app.getGroupHelper().initGroupModification();
    app.getGroupHelper().fillGroupForm(new GroupData("test1", "test2 FOR VERIFICATION", "test3"));
    app.getNavigationHelper().submitPublicModification();
    app.getGroupHelper().returnToGroupPage();
  }
}

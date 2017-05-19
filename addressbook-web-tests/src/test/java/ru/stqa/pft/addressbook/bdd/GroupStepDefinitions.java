package ru.stqa.pft.addressbook.bdd;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.remote.BrowserType;
import ru.stqa.pft.addressbook.appmeneger.ApplicationMeneger;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

//реализация шагов
public class GroupStepDefinitions {

  private ApplicationMeneger app;
  private Groups groups;
  private GroupData newGroup;

  @Before
  public void init() throws IOException {
    app = new ApplicationMeneger(System.getProperty("browser", BrowserType.CHROME));
//    app = new ApplicationMeneger(System.getProperty("browser", BrowserType.FIREFOX));
    app.init();
  }

  @After
  public void stop() {
    app.stop();
    app =null; // навсякий случай чтобы объект уничтожить
  }

  @Given("^a set of groups$")
  // ^...$ регулярные выражения позволяют привязать текст к началу и концу и проверять точное соответствие
  public void loadGroups() {
    groups = app.db().groups(); // этот шаг через бд выполняем
  }

  @When("^I create a new group with name (.+), header (.+) and footer (.+)$")
  // (.+) регулярные выражения позволяют подставить параметры
  public void createGroup(String name, String header, String footer) {
    // этот шаг через пользовательский интерфейс
    newGroup = new GroupData().withName(name).withHeader(header).withFooter(footer);
    app.goTo().groupPage(); // переход на нужную страницу сайта в браузере
    app.group().create(newGroup);
  }

  @Then("^the new set of groups is equal to the old set with the added group$")
  public void verifyGroupCreated() {
    Groups newGroups = app.db().groups();
    assertThat(newGroups, equalTo(
            groups.withAdded(newGroup.withId(newGroups.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));
  }
}

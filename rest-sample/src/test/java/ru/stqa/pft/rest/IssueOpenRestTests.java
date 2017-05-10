package ru.stqa.pft.rest;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Set;

import static org.testng.Assert.assertEquals;

public class IssueOpenRestTests extends TestBase {

  @BeforeMethod
  public void startMailSrver() throws IOException {
    skipIfNotFixed(Integer.valueOf(app.getProperty("web.bugIdNotFiuxed").toString())); //проверка на resolved
  }

  @Test
  public void testCreateIssue () throws IOException { //создание новогобаг-репрота в багтрекере Bugify
    Set<Issue> oldIssues = getIssues(); // получаем множество объектов типа Issue
    String si = "Test is set because test is id: " + String.valueOf(app.getProperty("web.bugIdNotFiuxed").toString()) +" resolved";
    System.out.println(si); // сообщение
    Issue newIssue = new Issue().withSubject(si).withDescription("New test issue");// новый объект
    int issueId = createIssue(newIssue);
    Set<Issue> newIssues = getIssues(); // получаем множество объектов типа Issue после добавления
    oldIssues.add(newIssue.withId(issueId));// к старому множ-ву добавл. объект
    assertEquals(newIssues, oldIssues);// сравниваем
  }

  @AfterMethod(alwaysRun = true)
  public void stopMailServer() {
    app.stop();
  }

}

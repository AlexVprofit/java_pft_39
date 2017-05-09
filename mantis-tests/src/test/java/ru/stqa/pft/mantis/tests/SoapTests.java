package ru.stqa.pft.mantis.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.mantis.model.Issue;
import ru.stqa.pft.mantis.model.Project;

import javax.xml.rpc.ServiceException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.Set;

import static org.testng.Assert.assertEquals;

public class SoapTests extends TestBase{

  @Test
  public void testGetProjects() throws MalformedURLException, ServiceException, RemoteException { // Получаем список проектов
    Set<Project> projects = app.soap().getProjects();
    System.out.println(projects.size()); // выводим количество проектов
    for (Project project : projects) { // выводим имена проектов
      System.out.println(project.getName());
    }
  }

  // создание баг репортов
  @Test
  public void testCreateIssue() throws MalformedURLException, ServiceException, RemoteException {
    Set<Project> projects = app.soap().getProjects();
    Issue issue = new Issue().withSummary("Test issue").withDescription("Test issue description")
            .withProject(projects.iterator().next()); // выбираем какой-то проект
    Issue created = app.soap().addIssue(issue);// создаем проект
    assertEquals(issue.getSummary(),created.getSummary());// сравнивание с существующим ( например, сравниваем Summary)
  }
}

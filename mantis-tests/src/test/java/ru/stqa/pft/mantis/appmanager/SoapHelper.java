package ru.stqa.pft.mantis.appmanager;

import biz.futureware.mantis.rpc.soap.client.*;
import ru.stqa.pft.mantis.model.Issue;
import ru.stqa.pft.mantis.model.Project;

import javax.xml.rpc.ServiceException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class SoapHelper {
  private ApplicationManager app;
  public SoapHelper(ApplicationManager app) {
    this.app = app;
  }

  public Set<Project> getProjects() throws MalformedURLException, ServiceException, RemoteException {
    MantisConnectPortType mc = getMantisConnect();
    // получить список проектов к которым пользователь имеет доступ
    ProjectData[] projects = mc.mc_projects_get_user_accessible("administrator", "root"); // массив проектов
    // преобразуем полученные объекты в модельные данные превращаем в поток stream() ко всем элементам потока применяем ф-ю map
    // кот. из объекта типа ProjectData строить новый объект типа Project, затем получившийся поток из новых объектов собирается
    // и возвращается получившееся множество
    return Arrays.asList(projects).stream()
            .map((p) -> new Project().withId(p.getId().intValue()).withName(p.getName())).collect(Collectors.toSet());
  }

  private MantisConnectPortType getMantisConnect() throws ServiceException, MalformedURLException { // вспомогательный метод
    return new MantisConnectLocator()
              .getMantisConnectPort(new URL("http://localhost/mantisbt-1.2.19/api/soap/mantisconnect.php"));
  }

  public Issue addIssue(Issue issue) throws MalformedURLException, ServiceException, RemoteException {
    MantisConnectPortType mc = getMantisConnect(); // открываем соединение
    String[] categories = mc.mc_project_get_categories("administrator", "root"
            , BigInteger.valueOf(issue.getProject().getId())); // выбираем категории для последующего заполнения нового объекта
    IssueData issueDate = new IssueData(); //  из своего модельного объекта строим такой кот. имеет нужную структуру
                                          // для передачи его в метод удаленного интерфейса
    // заполняем объект типа IssueData
    issueDate.setSummary(issue.getSummary());
    issueDate.setDescription(issue.getDescription());
    issueDate.setProject(new ObjectRef(BigInteger.valueOf(issue.getProject().getId())
            , issue.getProject().getName())); // ObjectRef ссылка на проект
    issueDate.setCategory(categories[0]); // любая категория, например первую берем
    BigInteger issueId = mc.mc_issue_add("administrator", "root", issueDate); // обратное преобразование
    IssueData createdIssueData = mc.mc_issue_get("administrator", "root", issueId);
    // преобразуем в нужный модельный объект
    return new Issue().withId(createdIssueData.getId().intValue())
            .withSummary(createdIssueData.getSummary()).withDescription(createdIssueData.getDescription())
            .withProject(new Project().withId(createdIssueData.getProject().getId().intValue())
                                      .withName(createdIssueData.getProject().getName()));
  }
}

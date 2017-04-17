package ru.stqa.pft.addressbook.generators;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thoughtworks.xstream.XStream;
import ru.stqa.pft.addressbook.model.GroupData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class GroupDataGenerator {

  @Parameter(names = "-c", description = "Group count")
  public int count;

  @Parameter(names = "-f", description = "Target file")
  public String file;

  @Parameter(names = "-d", description = "Data file")
  public String format;

  public static void main(String[] args) throws IOException {
    // Создаём новые объекты
    GroupDataGenerator generator = new GroupDataGenerator();
    // Здесь generator заполняются атрибуты ( count и file), а здесь args опции "-c" "-f"
    // И описываем подробнее сообщение исключения
    //создаем новый объект JCommander
    JCommander JCommander = new JCommander(generator);
    try {
      JCommander.parse(args); // передаются аргументы args
    } catch (ParameterException ex) {
      // Т.к. возникло исключение, то методом JCommander.usage() вывод на консоль сообщения о доступных опциях
      JCommander.usage();
      return;
    }
    generator.run(); //Запуск generator http://joxi.ru/12M5VQQHVR8w2J
  }

  private void run() throws IOException {
    // генерация данных
    List<GroupData> groups = generateGroups(count);
    // Сохранение в файл
    if (format.equals("csv")) {
      saveAsCsv(groups, new File(file)); //Т.к.тип был String fileв(@Parameter(names = "-f"..)преобразуем в тип File csv
    } else if (format.equals("xml")) {
      saveAsXml(groups, new File(file)); //Т.к.тип был String fileв(@Parameter(names = "-f"..)преобразуем в тип File xml
    } else if (format.equals("json")) {
      saveAsJson(groups, new File(file)); //Т.к.тип был String fileв(@Parameter(names = "-f"..)преобразуем в тип File xml
    } else {
      System.out.println("Unrecognized format " +format);
    }
  }

  private void saveAsJson(List<GroupData> groups, File file) throws IOException {
    // Создаем объект типа Gson и вызываем метод сиреализующий объект
    Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
    // Делаем более читабельное форматирование GsonBuilder().setPrettyPrinting().create()
    // Это excludeFieldsWithoutExposeAnnotation() указывает - пропускать все поля кот. не помеченны аннотацией @Expose

    String json = gson.toJson(groups);
    Writer writer = new FileWriter(file);
    writer.write(json);
    writer.close();
  }

  private void saveAsXml(List<GroupData> groups, File file) throws IOException {
    // Создание объекта типа new XStream
    XStream xstream = new XStream();
    // Создаем своё имя tag-га вместо проставляемого по умолчанию т.ею прочитать подсказку @XStreamAlias("group")
    xstream.processAnnotations(GroupData.class); // обработка аннотации в классе GroupData
    String xml = xstream.toXML(groups); //сиреализация т.е. превращение объекта в строчку xml
    Writer writer = new FileWriter(file);
    writer.write(xml);
    writer.close();
  }


  private void saveAsCsv(List<GroupData> groups, File file) throws IOException {
    System.out.println(new File(".").getAbsoluteFile());
    // Открываем файл на запись , а это throws IOException обработка исключения
    Writer writer = new FileWriter(file);
    for (GroupData group: groups) {
      // запись в кэш
      writer.write(String.format("%s;%s;%s\n",group.getHeader(), group.getHeader(), group.getFooter()));
    }
    // запись в файл из кэша
    writer.close();
  }

  private List<GroupData> generateGroups(int count) {
    // Создаем новый список объектов типа GroupData
    List<GroupData> groups = new ArrayList<GroupData>();
    for (int i = 0; i < count; i++ ) {
      groups.add(new GroupData().withName(String.format("test %s",i))
              .withHeader(String.format("header %s",i)).withFooter(String.format("footer %s",i)));
    }
    return groups;
  }
}

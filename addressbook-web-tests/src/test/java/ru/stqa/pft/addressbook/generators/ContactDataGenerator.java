package ru.stqa.pft.addressbook.generators;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thoughtworks.xstream.XStream;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class ContactDataGenerator {

  @Parameter(names = "-c", description = "Contact count")
  public int count;

  @Parameter(names = "-f", description = "Target file")
  public String file;

  @Parameter(names = "-d", description = "Data file")
  public String format;

  public static void main(String[] args) throws IOException {
    // Создаём новые объекты
    ContactDataGenerator generator = new ContactDataGenerator();
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
      List<ContactData> contacts = generateContact(count);
      if (format.equals("csv")) {
        saveAsCsv(contacts, new File(file));
      } else if (format.equals("xml")) {
        saveAsXml(contacts, new File(file));
      } else if (format.equals("json")) {
        saveAsJson(contacts, new File(file));
      } else {
        System.out.println("Unrecognized format " + format);
      }
  }
  private void saveAsJson(List<ContactData> contacts, File file) throws IOException {
    // Создаем объект типа Gson и вызываем метод сиреализующий объект
    Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
    // Делаем более читабельное форматирование GsonBuilder().setPrettyPrinting().create()
    // Это excludeFieldsWithoutExposeAnnotation() указывает - пропускать все поля кот. не помеченны аннотацией @Expose
    String json = gson.toJson(contacts);
    Writer writer = new FileWriter(file);
    writer.write(json);
    writer.close();
  }

  private void saveAsXml(List<ContactData> contacts, File file) throws IOException {
    // Создание объекта типа new XStream
    XStream xstream = new XStream();
    // Создаем своё имя tag-га вместо проставляемого по умолчанию т.ею прочитать подсказку @XStreamAlias("group")
    xstream.processAnnotations(ContactData.class); // обработка аннотации в классе GroupData
    String xml = xstream.toXML(contacts); //сиреализация т.е. превращение объекта в строчку xml
    Writer writer = new FileWriter(file);
    writer.write(xml);
    writer.close();
  }


  private void saveAsCsv(List<ContactData> contacts, File file) throws IOException {
    System.out.println(new File(".").getAbsoluteFile());
    // Открываем файл на запись , а это throws IOException обработка исключения
    Writer writer = new FileWriter(file);
    for (ContactData contact : contacts) {
      writer.write(String.format("%s;%s;%s;%s;%s\n", contact.getLastname(), contact.getFirstname(),
                                    contact.getNew_adress(), contact.getEmail1(), contact.getTelHome()));
    }
    writer.close();
  }
  private List<ContactData> generateContact(int count) {
    List<ContactData> contacts =new ArrayList<ContactData>();
    for (int i = 0; i < count; i++) {
      contacts.add(new ContactData().withLastname(String.format("Alex %s", i))
              .withFirstname(String.format("AlexBond %s", i)).withNew_adress(String.format("Street %s", i))
              .withEmail1(String.format("11%s"+"@qqq", i)).withTelHome(String.format("+7 495 %s"+"77-777", i)));
    }
    return contacts;
  }

}





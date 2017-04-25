package ru.stqa.pft.addressbook.appmeneger;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.List;

public class DbHelper {
  private final SessionFactory sessionFactory;

  // создаём конструктор в котором инициализируется фабрика сессии
  public DbHelper() {
      // A SessionFactory is set up once for an application!
      final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
              .configure() // configures settings from hibernate.cfg.xml
              .build();
      sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
  }

  public Groups groups() {
    //  код для извлечения инфы из бд
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    // здесь вместо sql исп-ся oql(язык запроса объектов)
    List result = session.createQuery( "from GroupData" ).list();
    session.getTransaction().commit();
    session.close();
    return new Groups(result);
  }

  public Contacts contacts() {
    //  код для извлечения инфы из бд
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    // здесь вместо sql исп-ся oql(язык запроса объектов)
    List result = session.createQuery( "from ContactData  where deprecated ='0000-00-00'" ).list();
    session.getTransaction().commit();
    session.close();
    return new Contacts(result);
  }

  public ContactData contactsid(int id) {
    //  код для извлечения инфы из бд
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    // здесь вместо sql исп-ся oql(язык запроса объектов)
    // выбираем один объект и затем преобразуем в тип ContactData
    List resultid = session.createQuery( "from ContactData  where deprecated ='0000-00-00'").list();
    session.getTransaction().commit();
    session.close();
    int index = resultid.size() - 1;
    return (ContactData) resultid.get(index);
  }

  public ContactData contactsidmodi(int id) {
    //  код для извлечения инфы из бд
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    // здесь вместо sql исп-ся oql(язык запроса объектов)
    // выбираем один объект и затем преобразуем в тип ContactData
    Object resultid = session.createQuery( "from ContactData  where id ='" + id + "'").getSingleResult();
    session.getTransaction().commit();
    session.close();
    return (ContactData) resultid;
  }


}

package ru.stqa.pft.addressbook.tests;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.List;

public class HbConnectionContTest {

  private SessionFactory sessionFactory;

  @BeforeClass  // инициализация бд соединения таблиц (маппинги)
  protected void setUp() throws Exception {
    // A SessionFactory is set up once for an application!
    final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure() // configures settings from hibernate.cfg.xml
            .build();
    try {
      sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
    }
    catch (Exception e) {
      e.printStackTrace(); // вывести сообщение о ошибке на консоль
      // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
      // so destroy it manually.
      StandardServiceRegistryBuilder.destroy( registry );
    }
  }
  @Test
  public void testHbConnectionContTest() {

    //  код для извлечения инфы из бд
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    // здесь вместо sql исп-ся oql(язык запроса объектов)
    // выводятся записи у кот. в поле deprecated ='0000-00-00' т.ею не удалённые
    List result = session.createQuery( "from ContactData where deprecated ='0000-00-00'" ).list();
    for (ContactData contact  : (List<ContactData>) result ) {
      System.out.println( contact );
    }
    session.getTransaction().commit();
    session.close();
  }
}

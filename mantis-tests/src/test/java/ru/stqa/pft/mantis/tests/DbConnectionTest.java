package ru.stqa.pft.mantis.tests;

import org.testng.annotations.Test;

import ru.stqa.pft.mantis.model.UserData;
import ru.stqa.pft.mantis.model.Users;

import java.sql.*;

public class DbConnectionTest {
  @Test
  public void TestDbConnection() {
    Connection conn = null;
    try {
      conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bugtracker?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC"
              + "&user=root&password=");
      Statement st = conn.createStatement();
      ResultSet rs = st.executeQuery("select id from mantis_user_table where id='4'");

      Users users = new Users();
      while (rs.next()) {
        users.add(new UserData()
                .withId(rs.getInt("id")));
      }
      rs.close();
      st.close();
      conn.close();

      System.out.println(users);



    } catch (SQLException ex) {
      System.out.println("SQLException: " + ex.getMessage());
      System.out.println("SQLState: " + ex.getSQLState());
      System.out.println("VendorError: " + ex.getErrorCode());
    }
  }
}
package ru.stqa.pft.mantis.tests;

import org.testng.annotations.Test;

import java.sql.*;

public class db2 {

  @Test
  public void TestDbConnection() throws SQLException {
    String login="";
    Integer id=0;

    Connection conn = null;
    try {
      conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bugtracker?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC"
              + "&user=root&password=");
      Statement st = conn.createStatement();
      ResultSet rs = st.executeQuery("select id, username from mantis_user_table where username!='administrator' LIMIT 1 ");
      while (rs.next()) {
        id = rs.getInt("id");
        login=rs.getString("username");

      }


      String str = Integer.toString(id);

      System.out.println(str);
      System.out.println(login);

      rs.close();
      st.close();
      conn.close();

    } catch (SQLException ex) {
      System.out.println("SQLException: " + ex.getMessage());
      System.out.println("SQLState: " + ex.getSQLState());
      System.out.println("VendorError: " + ex.getErrorCode());
    }
  }
}
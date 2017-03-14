package com.leekli.demo.derby;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DerbyTest {
    public static void main(String[] args) throws Exception {
      //Define key variables and objects
        String driver = "org.apache.derby.jdbc.EmbeddedDriver";
        String dbName="jdbcDemoDB";
        String connectionURL = "jdbc:derby:" + dbName + ";create=true";

        String createString = "CREATE TABLE WISH_LIST "
          + "(WISH_ID INT NOT NULL GENERATED ALWAYS AS IDENTITY ," 
          + " WISH_ITEM VARCHAR(32) NOT NULL) ";

        Connection conn = null;
        //Boot the database
        try {
          conn = DriverManager.getConnection(connectionURL);

        } catch (Throwable e) {  

        }
        //Set up program to execute SQL

        Statement s = conn.createStatement();
        if (! WwdUtils.wwdChk4Table(conn))
        {  
          System.out.println (" . . . . creating table WISH_LIST");
          s.execute(createString);
        }



        PreparedStatement psInsert = conn.prepareStatement
          ("insert into WISH_LIST(WISH_ITEM) values (?)");

        //Interact with the database
        psInsert.setString(1,"23");
        psInsert.executeUpdate();

        ResultSet myWishes = s.executeQuery("select  *  from WISH_LIST");



        while (myWishes.next())
        {
          System.out.println("On " + myWishes.getInt(1) +
          " I wished for " + myWishes.getString(2));
        }
        myWishes.close();
        
        //Shut down the database

        if (driver.equals("org.apache.derby.jdbc.EmbeddedDriver")) {
          boolean gotSQLExc = false;
          try {
             DriverManager.getConnection("jdbc:derby:;shutdown=true");
          } catch (SQLException se) {
             if ( se.getSQLState().equals("XJ015") ) {
                gotSQLExc = true;
          }
          }
          if (!gotSQLExc) {
          System.out.println("Database did not shut down normally");
          } else {
          System.out.println("Database shut down normally");
          }
        }

    }
}


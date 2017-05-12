package com.leekli.demo.derby;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DerbyDemo {

    private static String driver = "org.apache.derby.jdbc.EmbeddedDriver";
    private static String protocol = "jdbc:derby:memory:";//内存数据库
    private static String protocol1 = "jdbc:derby:";//磁盘存储数据库
    String dbName = "memorydb1/abc";

    public static void main(String[] args) {
        DerbyDemo.loadDriver();
        DerbyDemo dd = new DerbyDemo();
        dd.doIt();
    }
    static void loadDriver() {
       System.setProperty("derby.system.home", "D:\\test\\");
        try {
            Class.forName(driver).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void doIt() {
        Connection conn = null;
        Statement s = null;
        ResultSet rs = null;
        try {
            conn = DriverManager.getConnection(protocol1 + dbName + ";create=true");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Connected to and created database:" + dbName);

        try {
            s = conn.createStatement();
            String sql = "CREATE TABLE firsttable(id int,name varchar(255)) ";
            s.execute(sql );
            String insertSql = "insert into firsttable(id,name) values(100,'leekli')";
            s.execute(insertSql);
            rs = s.executeQuery("select * from firsttable");
            while (rs.next()) {
                System.out.println(rs.getInt("id") + " " +rs.getString("name"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
                conn = null;
                s.close();
                s = null;
                rs.close();
                rs = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
 
}

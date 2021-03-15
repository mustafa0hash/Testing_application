package design1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbconnection {
    public static final String CON ="jdbc:sqlite:test2.db";

    public static Connection getconnection() throws SQLException {

        try {
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection(CON);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        return null;
    }
}

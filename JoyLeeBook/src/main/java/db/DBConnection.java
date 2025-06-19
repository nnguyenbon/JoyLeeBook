package db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;

public class DBConnection {

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        try {
            String url = "jdbc:sqlserver://MAYTINHCUABON:1433;databaseName=JoyLeeBook;encrypt=false";
            String username = "sa";
            String password = "admin";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}

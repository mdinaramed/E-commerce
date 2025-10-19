package db;
import java.sql.Connection;
import java.sql.DriverManager;

public class Database {
    private static final String URL = "jdbc:postgresql://localhost:5432/ecommerce";
    private static final String USER = "postgres";
    private static final String PASS = "1234";

    public static Connection get() throws Exception {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
package Database;
import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    public static Connection con = null;
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String URL = "jdbc:mysql://localhost:3306/bankinfo";
            String user = "root";
            String pass = "19384556";
            con = DriverManager.getConnection(URL, user, pass);
            System.out.println("Database connected successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }
}

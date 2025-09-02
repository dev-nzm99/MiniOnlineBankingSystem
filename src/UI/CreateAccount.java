package UI;
import Database.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CreateAccount {
    private Connection con;

    public CreateAccount() {
        this.con = DBConnection.getConnection();
    }
    public boolean addNewAccount(String name, int pass) {
        if (name == null || name.isEmpty() || pass == 0) {
            System.out.println("All fields required.");
            return false;
        }
        String sql = "INSERT INTO customer(c_name, balance, pass_code) VALUES (?, 1000, ?)";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, name);
            st.setInt(2, pass);
            if (st.executeUpdate() == 1) {
                System.out.println(name + ", your account has been created! Please login.");
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Error: Username might already exist!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}

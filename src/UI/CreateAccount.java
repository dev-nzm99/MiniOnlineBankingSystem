package UI;
import Database.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CreateAccount {
    public boolean addNewAccount(String name, int pass) {
        if (name == null || name.isEmpty() || pass == 0) {  //input validation
            System.out.println("All fields required.");
            return false;
        }
        String sql = "INSERT INTO customer(c_name, balance, pass_code) VALUES (?, 1000, ?)"; //placeholder
        try (Connection con = DBConnection.getConnection();
             PreparedStatement st = con.prepareStatement(sql)){    // try-with-resources for auto-closes the connection
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
    static boolean changePassword(int senderAc,int curr_pass,int new_pass){
        String check_sql ="SELECT pass_code FROM customer WHERE ac_no = ?";
        String update_sql ="UPDATE customer SET pass_code = ? WHERE ac_no = ?";
        //verify current password
        try(Connection con = DBConnection.getConnection()){
            try(PreparedStatement checkPs = con.prepareStatement(check_sql)){
            checkPs.setInt(1,senderAc);
            ResultSet rs = checkPs.executeQuery();
            if(rs.next()){
                int currentPass = rs.getInt("pass_code");
                if(currentPass != curr_pass){
                    System.out.println("ERR: Current password is incorrect!");
                    return false;
                }
            }else{
                System.out.println("ERR: Account not found!");
                return false;
            }
             rs.close();
            }
            //add new password
            try(PreparedStatement updatePs = con.prepareStatement(update_sql)) {
                updatePs.setInt(1, new_pass);
                updatePs.setInt(2, senderAc);
                int r = updatePs.executeUpdate();
                return(r > 0)?true:false;
           }
          }catch (SQLException e){
              System.out.println("ERR: Database error!");
              e.printStackTrace();
          }
          catch (Exception e){
              e.printStackTrace();
          }
        return false;
    }
}

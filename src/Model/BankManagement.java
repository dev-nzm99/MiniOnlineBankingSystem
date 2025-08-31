package Model;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import Database.*;

public class BankManagement {
    static Connection con = DBConnection.getConnection();
    public static boolean createAccount(String name,int pass){
        try{
            if(name == "" || pass == 0){
                System.out.println("All field required.");
                return false;
            }
            Statement st = con.createStatement();
            String sql = "INSERT INTO customer(c_name,balance,pass_code) values('"+ name + "',1000," + pass + ")";
            if(st.executeUpdate(sql)==1){
                System.out.println(name +", now you login!");
            }
            return true;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
}


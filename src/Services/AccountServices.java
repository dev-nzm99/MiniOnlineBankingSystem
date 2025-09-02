package Services;
import Database.DBConnection;
import jdk.jshell.spi.ExecutionControl;
import java.sql.*;

public class AccountServices implements Management {
    private Connection con;
    public AccountServices(){
        this.con = DBConnection.getConnection();
    }
    @Override
    public boolean depositAmount(int amount,int ac_no){
       if(amount <= 0){
           System.out.println("ERR: Money must be greater then 0.");
           return false;
       }
       try{
          String query =  "UPDATE customer SET balance = balance + ? WHERE ac_no = ?";
          try(PreparedStatement ps = con.prepareStatement(query)){
            ps.setInt(1,amount);
            ps.setInt(2,ac_no);
            int rs = ps.executeUpdate();
            return (rs==1)?true:false;
          }
       }catch (Exception e){
           e.printStackTrace();
           return false;
       }
    }
    @Override
    public boolean transferMoney(int senderAcc, int receiverAcc, int amount) {
        if (receiverAcc == 0 || amount <= 0) {
            System.out.println("All fields required.");
            return false;
        }
        try {
            String sql = "SELECT balance FROM customer WHERE ac_no = ?";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, senderAcc);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    if (rs.getInt("balance") < amount) {
                        System.out.println("Insufficient balance.");
                        return false;
                    }
                } else {
                    System.out.println("Sender account not found.");
                    return false;
                }
            }
            // debit sender
            sql = "UPDATE customer SET balance = balance - ? WHERE ac_no = ?";
            try (PreparedStatement st = con.prepareStatement(sql)) {
                st.setInt(1, amount);
                st.setInt(2, senderAcc);
                st.executeUpdate();
            }

            // credit receiver
            sql = "UPDATE customer SET balance = balance + ? WHERE ac_no = ?";
            try (PreparedStatement st = con.prepareStatement(sql)) {
                st.setInt(1, amount);
                st.setInt(2, receiverAcc);
                st.executeUpdate();
            }

            System.out.println("Transaction successful!");
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void getBalance(int ac_no) {
        try {
            String sql = "SELECT * FROM customer WHERE ac_no = ?";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, ac_no);
                ResultSet rs = ps.executeQuery();

                System.out.println("-----------------------------------------------------------");
                System.out.printf("%-12s %-20s %-10s%n", "Account No", "Name", "Balance");
                System.out.println("-----------------------------------------------------------");

                while (rs.next()) {
                    int acNo = rs.getInt("ac_no");
                    String name = rs.getString("c_name");
                    double balance = rs.getDouble("balance");

                    System.out.printf("%-12d %-20s %-10.2f%n", acNo, name, balance);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

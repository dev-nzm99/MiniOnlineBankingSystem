package Services;
import Database.DBConnection;
import jdk.jshell.spi.ExecutionControl;
import java.net.ConnectException;
import java.sql.*;

public class AccountServices implements Management {
    private  String sql_query;
    @Override
    public boolean depositAmount(int amount,int ac_no){
       if(amount <= 0){
           System.out.println("ERR: Money must be greater then 0!");
           return false;
       }
       try(Connection con = DBConnection.getConnection()){
          sql_query =  "UPDATE customer SET balance = balance + ? WHERE ac_no = ?";
          try(PreparedStatement ps = con.prepareStatement(sql_query)){
            ps.setInt(1,amount);
            ps.setInt(2,ac_no);
            int rs = ps.executeUpdate();
            if(rs > 0){
                logTransaction(ac_no,0,amount,"Deposit");
                return true;
            }
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
    @Override
    public boolean withdrawAmount(int amount,int ac_no){
        if(amount <= 0){
            System.out.println("ERR: Money must be greater then 0!");
            return false;
        }
        try(Connection con = DBConnection.getConnection()) {
            sql_query = "SELECT balance FROM customer WHERE ac_no = ? ";
            try(PreparedStatement ps = con.prepareStatement(sql_query)){
                ps.setInt(1,ac_no);
                ResultSet rs = ps.executeQuery();
                if(rs.next()){
                    int curr_balance = rs.getInt("balance");
                    if(curr_balance<amount){
                        System.out.println("ERR: Insufficient balance!");
                        return false;
                    }
                }
            }
            sql_query = "UPDATE customer SET balance = balance - ? WHERE ac_no = ?";
            try(PreparedStatement ps = con.prepareStatement(sql_query)){
                ps.setInt(1,amount);
                ps.setInt(2,ac_no);
                int r = ps.executeUpdate();
                if(r>0){
                    logTransaction(ac_no,0,amount,"Withdraw");
                    return true;
                }
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
    @Override
    public boolean transferMoney(int senderAcc, int receiverAcc, int amount) {
        if (receiverAcc == 0 || amount <= 0) {
            System.out.println("All fields required.");
            return false;
        }
        try (Connection con = DBConnection.getConnection()){
            sql_query = "SELECT * FROM customer WHERE ac_no = ?";
            try (PreparedStatement ps = con.prepareStatement(sql_query)) {
                ps.setInt(1, receiverAcc);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    if (rs.getInt("balance") < amount) {
                        System.out.println("Insufficient balance.");
                        return false;
                    }
                } else {
                    System.out.println("Receiver account not found.");
                    return false;
                }
            }
            // debit sender
            sql_query = "UPDATE customer SET balance = balance - ? WHERE ac_no = ?";
            try (PreparedStatement st = con.prepareStatement(sql_query)) {
                st.setInt(1, amount);
                st.setInt(2, senderAcc);
                st.executeUpdate();
            }
            // credit receiver
            sql_query = "UPDATE customer SET balance = balance + ? WHERE ac_no = ?";
            try (PreparedStatement st = con.prepareStatement(sql_query)) {
                st.setInt(1, amount);
                st.setInt(2, receiverAcc);
                st.executeUpdate();
            }
            logTransaction(senderAcc, receiverAcc, amount, "Transfer");
            System.out.println("Transaction successful!");
            return true;

        } catch (SQLException e){
            System.out.println("ERR: Database error!");
            e.printStackTrace();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    @Override
    public void getBalance(int ac_no) {
        try(Connection con = DBConnection.getConnection()) {
            sql_query = "SELECT * FROM customer WHERE ac_no = ?";
            try (PreparedStatement ps = con.prepareStatement(sql_query)) {
                ps.setInt(1, ac_no);
                ResultSet rs = ps.executeQuery();

                System.out.println("-----------------------------------------------------------");
                System.out.printf("%-12s %-20s %-10s%n", "Account No", "Name", "Balance");
                System.out.println("-----------------------------------------------------------");

                while (rs.next()) {
                    int acNo = rs.getInt("ac_no");
                    String name = rs.getString("c_name");
                    double balance = rs.getDouble("balance");
                    System.out.printf("%-12d %-20s %-10s%n", acNo, name, String.format("%.2f Tk", balance));
                }
            }
        }catch (SQLException e){
            System.out.println("ERR: Database error!");
            e.printStackTrace();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void logTransaction(int senderAcc,int receiverAcc,double amount,String type){
       sql_query = "INSERT INTO transaction_history(sender_ac,receiver_ac,amount,type) VALUES(?,?,?,?)";
       try(Connection con = DBConnection.getConnection();
           PreparedStatement ps = con.prepareStatement(sql_query)){
           ps.setInt(1,senderAcc);
           ps.setInt(2,receiverAcc);
           ps.setDouble(3,amount);
           ps.setString(4,type);
           ps.executeUpdate();
       }catch (SQLException e){
           System.out.println("ERR: Database error!");
           e.printStackTrace();
       }
       catch (Exception e){
           e.printStackTrace();
       }
    }
    @Override
    public void showAllTransactions(int ac_no){
          sql_query = "SELECT * FROM transaction_history WHERE sender_ac = ? ORDER BY timestamp DESC";
          try(Connection con = DBConnection.getConnection();
              PreparedStatement ps = con.prepareStatement(sql_query)){
              ps.setInt(1,ac_no);
              try(ResultSet rs = ps.executeQuery()){
                 if(rs.next()){
                     System.out.printf("%-12s %-12s %-10s %-15s %-20s%n",
                             "Sender", "Receiver", "Amount", "Type", "Date/Time");
                         int sender_ac = rs.getInt("sender_ac");
                         int receiver_ac = rs.getInt("receiver_ac");
                         double amount = rs.getDouble("amount");
                         String Type = rs.getString("type");
                         String date_time = rs.getString("timestamp");
                         System.out.printf("%-12d %-12d %-10.2f %-15s %-20s%n",
                                 sender_ac, receiver_ac, amount, Type, date_time);

                 }else{
                     System.out.println("No transaction history found!");
                 }
              }
          }catch (SQLException e){
              System.out.println("ERR: Database error!");
              e.printStackTrace();
          }
          catch (Exception e){
              e.printStackTrace();
          }
    }
}

package UI;
import Database.DBConnection;
import Services.AccountServices;
import Services.AccountServices;
import Services.Management;
import java.io.BufferedReader;
import java.sql.*;

public class LoginAccount {
    Management O_P = new AccountServices();

    private Connection con;
    public LoginAccount(){
        this.con = DBConnection.getConnection();
    }
    public boolean authenticate_User(String name, int passCode, BufferedReader sc) {
        if (name == null || name.isEmpty() || passCode == 0) {
            System.out.println("All fields required!");
            return false;
        }
        String sql = "SELECT * FROM customer WHERE c_name = ? AND pass_code = ?";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, name);
            st.setInt(2, passCode);

            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    int senderAc = rs.getInt("ac_no");
                    String userName = rs.getString("c_name");

                    while (true) {
                        System.out.println("\n-----------------------------------------------------------");
                        System.out.printf("Welcome, %s%n", userName);
                        System.out.println("-----------------------------------------------------------");
                        System.out.printf("%-20s %-20s %-20s%n", "1) Deposit", "2) Withdraw", "3) Transfer Money");
                        System.out.printf("%-20s %-20s %-20s%n", "4) View Balance", "5) Transaction History", "6) Change Password");
                        System.out.printf("%-20s%n", "7) LogOut");
                        System.out.println("-----------------------------------------------------------");

                        System.out.print("Enter Choice: ");
                        int ch = Integer.parseInt(sc.readLine());

                        if(ch == 1){
                            System.out.print("Enter the amount that you deposit: ");
                            int amt = Integer.parseInt(sc.readLine());
                            if(O_P.depositAmount(amt,rs.getInt("ac_no"))){
                                System.out.println("MSG: Money deposit successfully!");
                            } else {
                                System.out.println("ERR: Deposit failed!");
                            }
                        }
                        else if (ch == 3) {
                            System.out.print("Enter Receiver A/c No: ");
                            int receiverAc = Integer.parseInt(sc.readLine());
                            System.out.print("Enter Amount: ");
                            int amt = Integer.parseInt(sc.readLine());

                            if (O_P.transferMoney(senderAc, receiverAc, amt)) {
                                System.out.println("MSG: Money sent successfully!");
                            } else {
                                System.out.println("ERR: Transaction failed.");
                            }

                        } else if (ch == 4) {
                            O_P.getBalance(senderAc);
                        } else if (ch == 7) {
                            System.out.println("Logging out successfully.");
                            break;
                        } else {
                            System.out.println("Enter a valid option!");
                        }
                    }
                    return true;
                } else {
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}

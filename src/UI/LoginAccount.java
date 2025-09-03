package UI;
import Database.DBConnection;
import Services.AccountServices;
import Services.AccountServices;
import Services.Management;
import java.io.BufferedReader;
import java.sql.*;
import java.util.SortedMap;

public class LoginAccount {
    Management O_P = new AccountServices();
    private int amount;
    public boolean authenticate_User(String name, int passCode, BufferedReader sc) {
        if (name == null || name.isEmpty() || passCode == 0) {
            System.out.println("All fields required!");
            return false;
        }
        String sql = "SELECT * FROM customer WHERE c_name = ? AND pass_code = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement st = con.prepareStatement(sql)){   // try-with-resources for auto-closes the connection
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

                        if (ch == 1) {
                            System.out.print("Enter the amount: ");
                            amount = Integer.parseInt(sc.readLine());
                            if (O_P.depositAmount(amount, senderAc)) {
                                System.out.println("MSG: Money deposit successfully!");
                            }else {
                                System.out.println("Error: Deposit failed!");
                            }
                        }else if(ch == 2){
                            System.out.print("Enter the amount: ");
                            amount = Integer.parseInt(sc.readLine());
                            if(O_P.withdrawAmount(amount,senderAc)){
                                System.out.println("MSG: Money withdrawal successful. Amount: " + amount + " Tk");
                            }else{
                                System.out.println("Error: Withdrawal failed!");
                            }
                        }else if (ch == 3) {
                            System.out.print("Enter Receiver A/c No: ");
                            int receiverAc = Integer.parseInt(sc.readLine());
                            System.out.print("Enter Amount: ");
                            int amt = Integer.parseInt(sc.readLine());

                            if (O_P.transferMoney(senderAc, receiverAc, amt)) {
                                System.out.println("MSG: Money sent successfully!");
                            } else {
                                System.out.println("Error: Transaction failed.");
                            }
                        } else if (ch == 4) {
                            O_P.getBalance(senderAc);
                        } else if(ch == 5){
                            O_P.showAllTransactions(senderAc);
                        }else if(ch == 6){
                            System.out.print("Enter current password: ");
                            int curr_pass = Integer.parseInt(sc.readLine());
                            System.out.print("Enter new password: ");
                            int new_pass = Integer.parseInt(sc.readLine());
                            if(CreateAccount.changePassword(senderAc,curr_pass,new_pass)){
                                System.out.println("MSG: Password change successfully! Please re-login.");
                                System.out.println("-----------------------------------------------------------");
                                System.out.print("Enter password: ");
                                int pass_code =  Integer.parseInt(sc.readLine());;
                                if(authenticate_User(userName,pass_code,sc)){
                                    System.out.println("MSG: login Successfully!\n");
                                }else{
                                    System.out.println("⚠️  LOGIN FAILED!");
                                    System.out.println("Please provide the correct username and password.");
                                }
                                break;
                            }else{
                                System.out.println("ERR: Could not change password!");
                            }
                        }else if (ch == 7) {
                            System.out.println("Logging out successfully.");
                            break;
                        } else {
                            System.out.println("Enter a valid option!");
                        }
                    }
                    return true;
                }
            }
        }catch (SQLException e){
            System.out.println("ERR: Database error!");
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

}

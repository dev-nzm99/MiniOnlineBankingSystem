package Model;
import Database.DBConnection;
import java.io.BufferedReader;
import java.sql.*;

public class BankManagement {
    public static Connection con = DBConnection.getConnection();

    public static boolean createAccount(String name, int pass) {
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

    public static boolean loginAccount(String name, int passCode, BufferedReader sc) {
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
                        System.out.printf("Hello, %s%n", userName);
                        System.out.println("-----------------------------------------------------------");
                        System.out.printf("%-20s %-20s %-20s%n", "1) Deposit", "2) Withdraw", "3) Transfer Money");
                        System.out.printf("%-20s %-20s %-20s%n", "4) View Balance", "5) Transaction History", "6) Change Password");
                        System.out.printf("%-20s%n", "7) LogOut");
                        System.out.println("-----------------------------------------------------------");

                        System.out.print("Enter Choice: ");
                        int ch = Integer.parseInt(sc.readLine());

                        if (ch == 3) {
                            System.out.print("Enter Receiver A/c No: ");
                            int receiverAc = Integer.parseInt(sc.readLine());
                            System.out.print("Enter Amount: ");
                            int amt = Integer.parseInt(sc.readLine());

                            if (transferMoney(senderAc, receiverAc, amt)) {
                                System.out.println("MSG: Money sent successfully!");
                            } else {
                                System.out.println("ERR: Transaction failed.");
                            }

                        } else if (ch == 4) {
                            BankManagement.getBalance(senderAc);
                        } else if (ch == 7) {
                            System.out.println("Logging out...");
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

    public static boolean transferMoney(int senderAcc, int receiverAcc, int amount) {
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
    public static void getBalance(int ac_no){
        try{
            String sql = "SELECT * FROM customer WHERE ac_no = "+ac_no;
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery(sql);
            System.out.println("-----------------------------------------------------------");
            System.out.printf("%-12s %-20s %-10s%n", "Account No", "Name", "Balance");
            System.out.println("-----------------------------------------------------------");
            
            while (rs.next()) {
                int acNo = rs.getInt("ac_no");
                String name = rs.getString("c_name");
                double balance = rs.getDouble("balance");

                System.out.printf("%-12d %-20s %-10.2f%n", acNo, name, balance);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}

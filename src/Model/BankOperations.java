package Model;
import java.io.BufferedReader;

public interface BankOperations {
    boolean createAccount(String name, int pass);
    boolean loginAccount(String name, int passCode, BufferedReader sc);
    boolean transferMoney(int senderAcc, int receiverAcc, int amount);
    void getBalance(int ac_no);
}


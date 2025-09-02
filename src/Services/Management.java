package Services;

public interface Management {
    boolean transferMoney(int senderAcc, int receiverAcc, int amount);
    void getBalance(int ac_no);
    boolean depositAmount(int amount,int ac_no);
    void logTransaction(int senderAcc,int receiverAcc,double amount,String type);
}


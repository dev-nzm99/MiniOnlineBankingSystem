package BankApp;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class OnlineBankingApp {
    public static void main(String[] args) {
        BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
        String name = "";
        int pass_code;
        int acc_no;
        int choice;
        do {
            System.out.println(
                    "\n ->||    Welcome to InBank    ||<- \n");
            System.out.println("1)Create Account.");
            System.out.println("2)Login Account.");
        try{
            System.out.print("\nEnter input:");
            choice = Integer.parseInt(sc.readLine());
            if(choice > 2)
                throw new Exception();
            switch(choice){
                case 1:
                    System.out.print("Enter unique UserName: ");
                    name = sc.readLine();
                    System.out.print("Enter new password : ");
                    pass_code =  Integer.parseInt(sc.readLine());
                    if(Model.BankManagement.createAccount(name,pass_code)){
                        System.out.println("MSG: Account created successfully!");
                    }else{
                        System.out.println("ERR: Account Creation failed.");
                    }
                    break;
                case 2:
                    System.out.print("Enter UserName: ");
                    name =  sc.readLine();
                    System.out.print("\nEnter password: ");
                    pass_code =  Integer.parseInt(sc.readLine());;
                    break;
            }
        }catch(Exception e){
            System.out.println("Please,,,Enter Valid Entry!");
        }
        }while(true);
    }
}

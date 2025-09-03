package UI;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {
        BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
        CreateAccount create_new_acc = new CreateAccount();
        LoginAccount logIn = new LoginAccount();
        String name = "";
        int pass_code;
        int acc_no;
        int choice;
        do {
            System.out.println("===========================================================");
            System.out.println("           ->||    Welcome to SwiftPay Bank    ||<-       ");
            System.out.println("===========================================================");
            System.out.printf("%-5s %-20s%n", "1)", "Create Account");
            System.out.printf("%-5s %-20s%n", "2)", "Login Account");
            System.out.printf("%-5s %-20s%n", "3)", "Exit");
            System.out.println("===========================================================");
            try{
            System.out.print("\nEnter input:");
            choice = Integer.parseInt(sc.readLine());
            if(choice > 3)
                throw new Exception();
            switch(choice){
                case 1:
                    System.out.print("Enter unique Username: ");
                    name = sc.readLine();
                    System.out.print("Enter new password : ");
                    pass_code =  Integer.parseInt(sc.readLine());
                    if(create_new_acc.addNewAccount(name,pass_code)){
                        System.out.println("MSG: Account created successfully!");
                    }else{
                        System.out.println("Error: Account Creation failed.");
                    }
                    break;
                case 2:
                    System.out.print("Enter Username: ");
                    name =  sc.readLine();
                    System.out.print("Enter password: ");
                    pass_code =  Integer.parseInt(sc.readLine());;
                    if(logIn.authenticate_User(name,pass_code,sc)){
                        System.out.println("MSG: login Successfully!\n");
                    }else{
                        System.out.println("⚠️  LOGIN FAILED!");
                        System.out.println("Please provide the correct username and password.");
                    }
                    break;
                case 3:
                    System.exit(0);
            }
        }catch(Exception e){
            System.out.println("Please, Enter Valid Entry!");
        }
        }while(true);
    }
}

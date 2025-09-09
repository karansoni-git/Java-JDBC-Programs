package projects.banking_system;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Scanner;

public class Bankapp {
    private static final String url = "jdbc:mysql://localhost:3306/karan";
    private static final String user = "root";
    private static final String password = "";

    public static void main(String[] args) {
        try {
            Connection con = DriverManager.getConnection(url, user, password);
            Scanner sc = new Scanner(System.in);
            Users us = new Users(con, sc);
            Accounts ac = new Accounts(con, sc);
            AccountManager am = new AccountManager(con, sc);

            String email;
            long account_number;

            while (true) {
                System.out.println("----------- Welcome To Banking System -----------");
                System.out.println("1. Register");
                System.out.println("2. Login");
                System.out.println("3. Exit From The System");
                System.out.print("Enter Your Choice : ");
                int choice1 = sc.nextInt();
                switch (choice1) {
                    case 1:
                        us.Register();
                        break;
                    case 2:
                        email = us.Login();
                        if (email != null) {
                            System.out.println();
                            System.out.println("User Logged In!");
                            if (!ac.AccountExists(email)) {
                                System.out.println();
                                System.out.println("1. Open New Bank Account");
                                System.out.println("2. Exit");
                                int choice2 = sc.nextInt();
                                if (sc.nextInt() == 1) {
                                    account_number = ac.OpenAccount(email);
                                    System.out.println("Account Created Successful");
                                    System.out.println("Your Account Number : " + account_number);
                                }else {
                                    System.out.println("Thank For Visiting Us!");
                                }
                            }
                            account_number = ac.GetAccountNumber(email);
                            int choice3 = 0;
                            while (choice3 != 5) {
                                System.out.println("1. Debit Money");
                                System.out.println("2. Credit Money");
                                System.out.println("3. Transfer Money");
                                System.out.println("4. Check Balance");
                                System.out.println("5. Logout");
                                System.out.print("Enter Your Choice : ");
                                choice3 = sc.nextInt();
                                switch (choice3) {
                                    case 1:
                                        am.DebitMoney(account_number);
                                        break;
                                    case 2:
                                        am.CreditMoney(account_number);
                                        break;
                                    case 3:
                                        am.TransferMoney(account_number);
                                        break;
                                    case 4:
                                        am.CheckBalance(account_number);
                                        break;
                                    case 5:
                                        break;
                                    default:
                                        System.out.println("Enter Valid Choice!");
                                }
                            }
                        } else {
                            System.out.println("Please Register And Then Try To Login");
                        }
                    case 3:
                        System.out.println("Thank You For Using Banking System");
                        System.out.print("Exiting System in");
                        for (int i = 5; i > 0; i--) {
                            System.out.print(". ");
                            Thread.sleep(400);
                        }
                        return;
                    default:
                        System.out.println("Enter Valid Choice!");
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

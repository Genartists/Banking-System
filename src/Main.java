// Hao Phong Le
// N01605830

import BankManagement.Bank;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Main main = new Main();
        main.run();
    }

    public void run() {
        Scanner sc = new Scanner(System.in);
        Bank bank = new Bank();
        UserAuth userAuth = new UserAuth();

        try {
            userAuth.readUserFIle();
        } catch (FileNotFoundException e) {
            System.out.println("No account in our Database yet. Please, press 1 to register!");
        }

        while (true) {
            displayMenu();
            actionHandler(sc, bank, userAuth);
        }
    }

    public void displayMenu() {
        System.out.println("--- Welcome to Boring Bank System (BBS) ---");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("3. Exit");
        System.out.print("Please, enter your choice: ");
    }

    public void actionHandler(Scanner sc, Bank bank, UserAuth userAuth) {
        switch (sc.nextInt()) {
            case 1:
                userAuth.registerUser();
                break;
            case 2:
                boolean loggedIn = false;
                while (!loggedIn) {
                    System.out.println("\n--- Please Log In ---");
                    if (userAuth.verifyLogin()) {
                        System.out.println("Login successful!\n");
                        loggedIn = true;
                    } else {
                        System.out.println("Invalid username or password. Please try again.");
                    }
                }
                bank.readAccount();
                while (true) {
                    System.out.println("\n**********************");
                    System.out.println("1. Create Account");
                    System.out.println("2. List All Accounts");
                    System.out.println("3. Deposit to Account");
                    System.out.println("4. Withdraw from Account");
                    System.out.println("5. Find Account");
                    System.out.println("6. Exit");
                    System.out.print("Enter your choice: ");
                    int option = sc.nextInt();
                    sc.nextLine();

                    switch (option) {
                        case 1:
                            System.out.print("Enter the number of accounts you want to create: ");
                            int accounts = sc.nextInt();
                            sc.nextLine();
                            bank.createNumberOfAccounts(accounts);
                            break;
                        case 2:
                            bank.listAccounts();
                            break;
                        case 3:
                            bank.depositToAccount();
                            break;
                        case 4:
                            bank.withdrawFromAccount();
                            break;
                        case 5:
                            bank.findAccount();
                            break;
                        case 6:
                            System.out.println("Thank you for using BBS!");
                            System.exit(0);
                            break;
                        default:
                            System.out.println("Invalid choice.");
                    }
                }
            case 3:
                sc.close();
                System.out.println("Thank you, see you again!");
                System.exit(0);
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }
}

package BankManagement;// Hao Phong Le
// N01605830

import BankAccount.BankAccount;
import BankAccount.CheckingAccount;
import BankAccount.SavingsAccount;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

// The BankManagement.Bank class implements the BankManagement.BankOperations interface, representing a bank system.
public class Bank implements BankOperations {
    private ArrayList<BankAccount> accounts = new ArrayList<>(); // Stores the list of accounts
    private Scanner sc = new Scanner(System.in);

    // Method to create a new account based on user input
    @Override
    public void createAccount() {
        System.out.print("Enter account holder name: ");
        String holder = sc.nextLine();
        System.out.print("Enter initial balance: ");
        double balance = sc.nextDouble();
        System.out.println("Choose account type: 1. Savings 2. Checking");
        int type = sc.nextInt();

        // Create a BankAccount.SavingsAccount or BankAccount.CheckingAccount based on user choice
        if (type == 1) {
            System.out.print("Enter interest rate: ");
            double rate = sc.nextDouble();
            accounts.add(new SavingsAccount(holder, balance, rate));
        } else if (type == 2) {
            System.out.print("Enter overdraft limit: ");
            double limit = sc.nextDouble();
            accounts.add(new CheckingAccount(holder, balance, limit));
        } else {
            System.out.println("Invalid account type.");
        }
        sc.nextLine();
    }

    // Method to create multiple accounts in sequence
    public void createNumberOfAccounts(int n) {
        for (int i = 0; i < n; i++) {
            System.out.println("Creating account number " + (i + 1));
            createAccount();
            System.out.println("--------------------------------");
        }
        storeAccount(); // Save accounts to file after creation
        System.out.println("All accounts have been added to the system!");
    }

    // Method to store all accounts to a file
    public void storeAccount() {
        try (BufferedWriter accountsFile = new BufferedWriter(new FileWriter("accounts.txt"))) {
            for (BankAccount account : accounts) {
                accountsFile.write(account.getAccountNumber());
                accountsFile.newLine();
                accountsFile.write(account.getAccountHolder());
                accountsFile.newLine();
                accountsFile.write(String.valueOf(account.getBalance()));
                accountsFile.newLine();

                // Check for specific account types and write relevant details
                if (account instanceof SavingsAccount) {
                    accountsFile.write("Savings");
                    accountsFile.newLine();
                    accountsFile.write(String.valueOf(((SavingsAccount) account).getInterestRate()));
                } else if (account instanceof CheckingAccount) {
                    accountsFile.write("Checking");
                    accountsFile.newLine();
                    accountsFile.write(String.valueOf(((CheckingAccount) account).getOverdraftLimit()));
                }
                accountsFile.newLine();
                accountsFile.flush();
            }
        } catch (IOException e) {
            System.out.println("Error writing to file.");
        }
    }

    // Method to read accounts from a file
    public void readAccount() {
        try {
            BufferedReader accountsFile = new BufferedReader(new FileReader("accounts.txt"));
            accounts.clear(); // Clear existing accounts before loading new ones
            String line;

            while ((line = accountsFile.readLine()) != null) {
                String accountNumber = line;
                String accountHolder = accountsFile.readLine();
                double balance = Double.parseDouble(accountsFile.readLine());
                String accountType = accountsFile.readLine();

                // Recreate account objects based on type and stored data
                if (accountType.equals("Savings")) {
                    double interestRate = Double.parseDouble(accountsFile.readLine());
                    accounts.add(new SavingsAccount(accountHolder, balance, interestRate));
                } else if (accountType.equals("Checking")) {
                    double overdraftLimit = Double.parseDouble(accountsFile.readLine());
                    accounts.add(new CheckingAccount(accountHolder, balance, overdraftLimit));
                }
            }
            accountsFile.close();
        } catch (IOException e) {
            System.out.println("No Accounts found! Press 1 to create one.");
        } catch (NumberFormatException e) {
            System.out.println("Error parsing account data.");
        } catch (NullPointerException e) {
            System.out.println("No information in the database");
        }
    }

    // Method to list all accounts
    @Override
    public void listAccounts() {
        if (accounts.isEmpty()) {
            System.out.println("No accounts found.");
            return;
        }

        System.out.println("Listing accounts...");
        for (BankAccount account : accounts) {
            System.out.println(" ");
            account.displayInfo();
            System.out.println("--------------------");
        }
    }

    // Method to deposit to a specific account
    @Override
    public void depositToAccount() {
        System.out.print("Enter account number: ");
        String accNumber = sc.nextLine();
        BankAccount account = findAccountByNumber(accNumber);

        if (account != null) {
            if (account instanceof SavingsAccount) {
                SavingsAccount savings = (SavingsAccount) account;
                System.out.print("Enter deposit amount: ");
                double amount = sc.nextDouble();
                savings.applyInterest(amount);
            } else if (account instanceof  CheckingAccount) {
                CheckingAccount checking = (CheckingAccount) account;
                System.out.print("Enter deposit amount: ");
                double amount = sc.nextDouble();
                checking.deposit(amount);
            }
        } else {
            System.out.println("Account not found.");
        }
        storeAccount(); // Update file storage after deposit
        sc.nextLine(); // Consume newline left-over
    }

    // Method to withdraw from a specific account
    @Override
    public void withdrawFromAccount() {
        System.out.print("Enter account number: ");
        String accNumber = sc.nextLine();
        BankAccount account = findAccountByNumber(accNumber);

        if (account != null) {
            if (account instanceof SavingsAccount) {
                System.out.print("Enter withdraw amount: ");
                double amount = sc.nextDouble();
                account.withdraw(amount);
            }
            else if (account instanceof  CheckingAccount) {
                CheckingAccount checking = (CheckingAccount) account;
                System.out.print("Enter withdraw amount: ");
                double amount = sc.nextDouble();
                checking.withdraw(amount);
            }
        } else {
            System.out.println("Account not found.");
        }
        storeAccount(); // Update file storage after withdrawal
        sc.nextLine(); // Consume newline left-over
    }

    // Method to find and display details of a specific account
    @Override
    public void findAccount() {
        if (accounts.isEmpty()) {
            readAccount();
        }

        System.out.print("Enter account number: ");
        String accNumber = sc.nextLine();
        BankAccount account = findAccountByNumber(accNumber);

        if (account != null) {
            System.out.println(" ");
            account.displayInfo();
        } else {
            System.out.println("Account not found.");
        }
    }

    // Helper method to find an account by account number
    private BankAccount findAccountByNumber(String accNumber) {
        for (BankAccount acc : accounts) {
            if (acc.getAccountNumber().equals(accNumber)) {
                return acc;
            }
        }
        return null;
    }
}

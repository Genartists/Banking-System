package BankAccount;

// Hao Phong Le
// N01605830
public class CheckingAccount extends BankAccount {
    private double overdraftLimit;

    public CheckingAccount(String accountHolder, double initialBalance, double overdraftLimit) {
        super(accountHolder, initialBalance);
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    public void withdraw(double amount) {
        if (amount > 0 && getBalance() + overdraftLimit >= amount) {
            double newBalance = getBalance() - amount;
            System.out.println("Withdrawn $" + amount + ". New balance: $" + newBalance);
        } else {
            System.out.println("Withdrawal exceeds overdraft limit.");
        }
    }

    public double getOverdraftLimit() {
        return overdraftLimit;
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Account Type: Checking");
        System.out.println("Overdraft Limit: $" + overdraftLimit);
    }
}

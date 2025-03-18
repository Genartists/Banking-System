package BankAccount;

// Hao Phong Le
// N01605830
public class SavingsAccount extends BankAccount {
    private double interestRate;

    public SavingsAccount(String accountHolder, double initialBalance, double interestRate) {
        super(accountHolder, initialBalance);
        this.interestRate = interestRate;
    }

    // Apply interest
    public void applyInterest(double amount) {
        amount += (amount * (interestRate / 100)); // 1.5
        deposit(amount);
        System.out.println("Interest of $" + interestRate + " applied. New balance: $" + getBalance());
    }

    public double getInterestRate() {
        return interestRate;
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Account Type: Savings");
        System.out.println("Interest Rate: " + interestRate + "%");
    }
}

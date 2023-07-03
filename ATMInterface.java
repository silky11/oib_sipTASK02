import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

class Transaction {
    private String type;
    private double amount;

    public Transaction(String type, double amount) {
        this.type = type;
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }
}

class Account {
    private String userId;
    private String password;
    private double balance;
    private ArrayList<Transaction> transactionHistory;

    public Account(String userId, String password, double balance) {
        this.userId = userId;
        this.password = password;
        this.balance = balance;
        this.transactionHistory = new ArrayList<>();
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
    return password;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
        transactionHistory.add(new Transaction("Deposit", amount));
        System.out.println("Deposited: $" + amount);
    }

    public void withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            transactionHistory.add(new Transaction("Withdrawal", amount));
            System.out.println("Withdrawn: $" + amount);
        } else {
            System.out.println("Insufficient balance");
        }
    }

    public void transfer(Account recipient, double amount) {
        if (balance >= amount) {
            balance -= amount;
            recipient.deposit(amount);
            transactionHistory.add(new Transaction("Transfer", amount));
            System.out.println("Transferred: $" + amount);
        } else {
            System.out.println("Insufficient balance");
        }
    }

    public void displayTransactionHistory() {
        System.out.println("Transaction History:");
        for (Transaction transaction : transactionHistory) {
            System.out.println(transaction.getType() + ": $" + transaction.getAmount());
        }
    }
}

public class ATMInterface {
    private static HashMap<String, Account> accounts;
    private static Account loggedInAccount;
    private static Scanner scanner;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        accounts = new HashMap<>();
        initializeAccounts();

        while (true) {
            System.out.println("\nATM Interface");
            System.out.println("1. Login");
            System.out.println("2. Quit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            if (choice == 1) {
                login();
            } else if (choice == 2) {
                System.out.println("Thank you for using the ATM. Goodbye!");
                break;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }

    private static void initializeAccounts() {
        Account account1 = new Account("user1", "password1", 5000.0);
        Account account2 = new Account("user2", "password2", 2000.0);
        accounts.put(account1.getUserId(), account1);
        accounts.put(account2.getUserId(), account2);
    }

    private static void login() {
        System.out.print("Enter User ID: ");
        String userId = scanner.next();
        System.out.print("Enter Password: ");
        String password = scanner.next();

        Account account = accounts.get(userId);

        if (account != null && account.getPassword().equals(password)) {
            loggedInAccount = account;
            showOperations();
        } else {
            System.out.println("Invalid credentials. Please try again.");
        }
    }

   private static void showOperations() {
    while (true) {
        System.out.println("\nWelcome, " + loggedInAccount.getUserId());
        System.out.println("Current Balance: $" + loggedInAccount.getBalance());
        System.out.println("1. Deposit");
        System.out.println("2. Withdraw");
        System.out.println("3. Transfer");
        System.out.println("4. Transaction History");
        System.out.println("5. Logout");
        System.out.println("6. Quit");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                System.out.print("Enter the amount to deposit: ");
                double depositAmount = scanner.nextDouble();
                loggedInAccount.deposit(depositAmount);
                break;
            case 2:
                System.out.print("Enter the amount to withdraw: ");
                double withdrawalAmount = scanner.nextDouble();
                loggedInAccount.withdraw(withdrawalAmount);
                break;
            case 3:
                System.out.print("Enter the recipient's User ID: ");
                String recipientId = scanner.next();
                System.out.print("Enter the amount to transfer: ");
                double transferAmount = scanner.nextDouble();
                Account recipientAccount = accounts.get(recipientId);
                if (recipientAccount != null) {
                    loggedInAccount.transfer(recipientAccount, transferAmount);
                } else {
                    System.out.println("Recipient not found. Please try again.");
                }
                break;
            case 4:
                loggedInAccount.displayTransactionHistory();
                break;
            case 5:
                loggedInAccount = null;
                return;
            case 6:
                System.out.println("Thank you for using the ATM. Goodbye!");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }
}
}

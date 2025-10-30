public class ATMBanking {

    // Inner Account class
    public static class Account {
        private String name;
        private String pin;
        private int id;
        private double balance;
        private static int count = 0;

        // Constructor to initialize Account with a name and pin
        public Account(String name, String pin) {
            this.name = name;
            this.pin = pin;
            this.balance = 0;  // Initial balance is 0
            this.id = count;   // Unique account ID
            count++;           // Increment the counter for the next account ID
        }

        // Method to check if the provided pin matches the account pin
        public boolean isPin(String attempt) {
            return this.pin.equals(attempt);
        }

        // Method to withdraw money from the account
        public double withdraw(double amount) {
            if (amount > 0 && amount <= balance) {
                balance -= amount;
                return amount;
            } else {
                return 0; // Insufficient funds or invalid amount
            }
        }

        // Method to deposit money into the account
        public void deposit(double amount) {
            if (amount > 0) {
                balance += amount;
            }
        }

        // Getter methods for account details
        public String getName() {
            return name;
        }

        public String getPin() {
            return pin;
        }

        public int getId() {
            return id;
        }

        public double getBalance() {
            return balance;
        }

        // Overriding toString method to display account details
        @Override
        public String toString() {
            return String.format("Name: %s, Account ID: %d, Balance: $%.02f", name, id, balance);
        }
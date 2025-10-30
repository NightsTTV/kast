
    public class Account {
        private String name;
        private String pin;
        private int id;
        private double balance;
        private static int count = 0;

      
        public Account(String name, String pin) {
            this.name = name;
            this.pin = pin;
            this.balance = 0.0; 
            this.id = count++;             
        }

        public boolean isPin(String attempt) {
            return this.pin.equals(attempt);
        }

       
        public double withdraw(double amount) {
            if (balance >= amount && amount > 0) {
                balance -= amount;
            } else {
                System.out.println("Cant exceed balance.");
            }
               return amount;
        }

      
        public void deposit(double amount) {
            if (amount > 0) {
                balance += amount;
            }
        }

      
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

   
        @Override
        public String toString() {
            return String.format("Name: %s, Account ID: %d, Balance: $%.02f", name, id, balance);
        }
    }
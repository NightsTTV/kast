public class ATMBankingTester {
    public static void main(String[] args) {
        // Create an instance of ATMBanking to access the Account inner class
        ATMBanking atmBanking = new ATMBanking();
        
        // Create accounts for Tim and Ted
        ATMBanking.Account tim = atmBanking.new Account("Tim", "1234");
        ATMBanking.Account ted = atmBanking.new Account("Ted", "9999");
        
        // Print details of each account
        System.out.println(tim.toString()); // Expected: Name: Tim, Account ID: 0, Balance: $0.00
        System.out.println(ted.toString()); // Expected: Name: Ted, Account ID: 1, Balance: $0.00
    }
}

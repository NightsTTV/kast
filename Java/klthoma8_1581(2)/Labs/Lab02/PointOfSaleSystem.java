import java.util.Scanner;

public class PointOfSaleSystem {
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        int testcase = input.nextInt();
        input.nextLine();

        
            double regHamBurger = 1.50; // declare regHamBurger variable
            double regCheeseBurger = 1.75; //declare regCheeseBurger variable
            double fishSandwich = 2.50; // declare fishSandwich variable
            double halfPoundwcheese = 2.75; // halfPoundwCheese variable
            double frenchFries = 0.99; // frenchFries variable
            double largeSoda = 1.25; // declare largeSoda variable

                for (int t = 0; t < testcase; t++) { // creates a for loop that will repeat whats in it x amount of times based on input
                        double money = 0.00; // declare money variable
                        double taxrate = 0.065;  // declare taxTotal variable

                        String inputLine = input.nextLine();
                        Scanner intScanner = new Scanner(inputLine);

                         while (intScanner.hasNextInt()) {
                            int item = intScanner.nextInt();
                            switch(item) {
                            case 1: 
                                money += regHamBurger;
                                break;
                            case 2:
                                money += regCheeseBurger;
                                break;
                            case 3:
                                money += fishSandwich;
                                break;
                            case 4:
                                money += halfPoundwcheese;
                                break;
                            case 5:
                                money += frenchFries;
                                break;
                            case 6:
                                money += largeSoda;
                                break;
                            default: 
                                break;
                            }
                        }
                        double taxTotal = money * taxrate;
                        double totalwithTax = money + taxTotal;
                        System.out.printf("Please pay $%.2f\nThank you for eating at McDowell's!%n", totalwithTax);
                    }
                }
            }
        
            
        

/*
Kaspian Thomas
03/19/25
*/
import java.util.*;
import java.io.*;
import java.util.HashMap;
import java.util.Stack;
import java.util.Vector;

public class BudgetCalculator {
    // Instance variables
    private String name;
    private double income;
    private double rent;
    private double food;
    private double transport;
    private double entertainment;
    private int id;
    private static int nextId = (BudgetFileHandler.getHighestId() != -1) ? BudgetFileHandler.getHighestId() + 1 : 1;
    private static final String FILE_NAME = "budgets.txt";
    public static Scanner input = new Scanner(System.in);
    public static Vector<BudgetCalculator> budgets = new Vector<>();
    public static Stack<BudgetCalculator> budgetHistory = new Stack<>();
    public static HashMap<Integer, BudgetCalculator> budgetMap = new HashMap<>();

    // Constructor
    public BudgetCalculator(Scanner input) {
        while (true) {
            this.id = nextId;
            if (!BudgetFileHandler.doesIdExist(this.id)) {
                nextId++;
                break; // Id is unique, exit the loop
            }
        }
        this.id = nextId++;
        System.out.print("Enter your name: ");
        this.name = input.nextLine();
        System.out.print("Enter your monthly income: $");

        while (!input.hasNextDouble()) {
            System.out.print("Invalid input. Enter a valid monthly income: $");
            input.next();
        }
        this.income = input.nextDouble();

        System.out.print("Enter your monthly rent: $");
        this.rent = input.nextDouble();
        System.out.print("Enter your monthly transport expenses: $");
        this.transport = input.nextDouble();
        System.out.print("Enter your monthly entertainment expense: $");
        this.entertainment = input.nextDouble();
        System.out.print("Enter your monthly food expense: $");
        this.food = input.nextDouble();
        input.nextLine(); // Consume leftover newline

        budgets.add(this);
        budgetMap.put(this.id, this);
    }
        // Copy constructor
    public BudgetCalculator(BudgetCalculator other) {
        this.id = other.id;
        this.name = other.name;
        this.income = other.income;
        this.rent = other.rent;
        this.food = other.food;
        this.transport = other.transport;
        this.entertainment = other.entertainment;
    }

            // Constructor for loading budgets from file
    public BudgetCalculator(String name, double income, double rent, double food, double transport, double entertainment, int id) {
            this.id = id;
            this.name = name;
            this.income = income;
            this.rent = rent;
            this.food = food;
            this.transport = transport;
            this.entertainment = entertainment;
    }
    // Provide public static getters

    public static Vector<BudgetCalculator> getBudgets() {
        return budgets;
    }

    public static Stack<BudgetCalculator> getBudgetHistory() {
        return budgetHistory;
    }

    public static HashMap<Integer, BudgetCalculator> getBudgetMap() {
        return budgetMap;
    }

    public static void setBudgets(Vector<BudgetCalculator> newBudgets) {
        budgets = newBudgets;
    }

    public static void setBudgetHistory(Stack<BudgetCalculator> newBudgetHistory) {
        budgetHistory = newBudgetHistory;
    }

    public static void setBudgetMap(HashMap<Integer, BudgetCalculator> newBudgetMap) {
        budgetMap = newBudgetMap;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public double getIncome() {
        return income;
    }

    public void setRent(double rent) {
        this.rent = rent;
    }

    public double getRent() {
        return rent;
    }

    public void setFood (double food) {
        this.food = food;
    }

    public double getFood() {
        return food;
    }

    public void setTransport(double transport) {
        this.transport = transport;
    }

    public double getTransport() {
        return transport;
    }

    public double getEntertainment() {
        return entertainment;
    }

    public void setEntertainment(double entertainment) {
        this.entertainment = entertainment;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    
    public double calculateBalance() {
        return income - (rent + food + transport + entertainment);
    }

    public boolean isWithinBudget() {
        return calculateBalance() >= 0;
    }

    public void displayBudget() {
        System.out.printf("\nMonthly Budget for %s%n-------------------------%n", name);
        System.out.printf("Total Income: $%.2f%n", income);
        System.out.printf("Expenses:%nRent: $%.2f%nFood: $%.2f%nTransport: $%.2f%nEntertainment: $%.2f%n", rent, food, transport, entertainment);
        System.out.printf("-------------------------%nRemaining Balance: $%.2f%n", calculateBalance());
        System.out.printf("Are you within budget? %b%n", isWithinBudget() ? "Yes" : "No");
    }

    public void addExpenses() {
        try (FileWriter writer = new FileWriter(FILE_NAME, true)) {
            String data = String.format("%d, %s, %.2f, %.2f, %.2f, %.2f, %.2f%n",
                    id, name, income, rent, food, transport, entertainment);
            writer.write(data);
            System.out.println("Budget details saved to file.");
        } catch (IOException e) {

            System.out.println("An error occurred while writing to the file.");
        }
    }

    public void addExpensesValidation() {
        if (income < 0 || rent < 0 || food < 0 || transport < 0 || entertainment < 0) {
            System.out.println("Error: Income and expenses cannot be negative.");
            return;
        }
        addExpenses();
    }

    public static void main(String[] args) {
        System.out.println("BudgetMap Reference: " + System.identityHashCode(BudgetCalculator.getBudgetMap()));

        BudgetFileHandler.loadBudgetsFromFile();
        BudgetCalculator budget = null;

        while (true) {
            System.out.println("\nMoney Management System");
            System.out.println("1. Add Expenses");
            System.out.println("2. Display Budget");
            System.out.println("3. Show ALL Accounts");
            System.out.println("4. Remove a Budget by ID");
            System.out.println("5. Update existing budget details");
            System.out.println("6. Undo Last Update");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");

            int choice;
            try {
                choice = input.nextInt();
                input.nextLine(); // Consume the newline
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a number from the list.");
                input.nextLine();
                continue;
            }

            switch (choice) {
                case 1:
                    budget = new BudgetCalculator(input);
                    BudgetFileHandler.saveBudget(budget);
                    break;
                case 2:
                    if (budget != null) { // if a budget exists
                        budget.displayBudget(); // display budget
                    } else {
                        System.out.println("You need to create a budget before displaying it.");
                    }
                    break;
                case 3:
                    BudgetFileHandler.viewAllBudgets();
                    break;
                case 4:
                    System.out.print("Enter the ID of the budget to remove: ");
                    int idToRemove = input.nextInt();
                    BudgetFileHandler.removeBudget(idToRemove);
                    break;
                case 5:
                     System.out.println("Enter the ID of the budget being updated: ");
                     int idToUpdate = input.nextInt();
                     input.nextLine();
                     BudgetFileHandler.updateBudget(idToUpdate, input);
                     break;
                case 6: 
                    BudgetFileHandler.undoLastUpdate();
                    break;
                case 7:
                    System.out.println("Exiting...");
                    input.close();
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}

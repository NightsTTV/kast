/*
Kaspian Thomas
03/19/25
*/
import java.io.*;
import java.util.*;

public class BudgetFileHandler {
    private static final String FILE_NAME = "budgets.txt";

    public static void saveBudget(BudgetCalculator budget) {
        try (FileWriter writer = new FileWriter(FILE_NAME, true)) {
            String data = String.format("%d, %s, %.2f, %.2f, %.2f, %.2f, %.2f%n",
                    budget.getId(), budget.getName(), budget.getIncome(), budget.getRent(),
                    budget.getFood(), budget.getTransport(), budget.getEntertainment());
            writer.write(data);
            System.out.println("Budget details saved to file.");
        } catch (IOException e) {
            System.out.println("An error occurred while attempting to write the file.");
        }
    }

    public static void saveBudgetsToFile() {
        try (FileWriter writer = new FileWriter(FILE_NAME)) {
            for (BudgetCalculator budget : BudgetCalculator.getBudgets()) {
                String data = String.format("%d, %s, %.2f, %.2f, %.2f, %.2f, %.2f%n",
                        budget.getId(), budget.getName(), budget.getIncome(), budget.getRent(),
                        budget.getFood(), budget.getTransport(), budget.getEntertainment());
                writer.write(data);
            }
        } catch (IOException e) {
            System.out.println("Error saving budgets to file.");
        }
    }

    public static void removeBudget(int id) {
        // Remove from in-memory data
        BudgetCalculator.getBudgetMap().remove(id);
        BudgetCalculator.getBudgets().removeIf(budget -> budget.getId() == id);

        // Remove from file
        List<String> budgets = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(", ");
                int currentId = Integer.parseInt(parts[0]);
                if (currentId != id) {
                    budgets.add(line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error occurred while reading the file.");
            return;
        }

        try (FileWriter writer = new FileWriter(FILE_NAME)) {
            for (String budget : budgets) {
                writer.write(budget + "\n");
            }
            System.out.println("Budget with ID " + id + " has been removed.");
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
        }
    }

    public static void updateBudget(int id, Scanner input) {

        BudgetCalculator budgetToUpdate = BudgetCalculator.getBudgetMap().get(id);
        if (!BudgetCalculator.getBudgetMap().containsKey(id)) {
            System.out.println("Budget with ID " + id + " was not found.");
            System.out.println("Existing IDs: " + BudgetCalculator.getBudgetMap().keySet());
            return;
        }

        // Clone the current budget and push it onto the stack (previous state)
        BudgetCalculator originalBudget = new BudgetCalculator(budgetToUpdate);
        BudgetCalculator.getBudgetHistory().push(originalBudget);

        System.out.print("Enter new monthly income: ");
        double income = input.nextDouble();
        System.out.print("Enter new monthly rent: ");
        double rent = input.nextDouble();
        System.out.print("Enter new monthly transport expenses: ");
        double transport = input.nextDouble();
        System.out.print("Enter new monthly entertainment expenses: ");
        double entertainment = input.nextDouble();
        System.out.print("Enter new monthly food expenses: ");
        double food = input.nextDouble();
        input.nextLine(); // Consume leftover newline

        // Update the budget
        budgetToUpdate.setIncome(income);
        budgetToUpdate.setRent(rent);
        budgetToUpdate.setTransport(transport);
        budgetToUpdate.setFood(food);
        budgetToUpdate.setEntertainment(entertainment);

        System.out.println("Budget with ID " + id + " has been updated.");
    }

    public static void undoLastUpdate() {
        if (BudgetCalculator.getBudgetHistory().isEmpty()) {
            System.out.println("No updates to undo.");
            return;
        }

        // Pop the previous version of the budget from the stack
        BudgetCalculator lastBudget = BudgetCalculator.getBudgetHistory().pop();

        // Restore the previous version in the HashMap
        BudgetCalculator.getBudgetMap().put(lastBudget.getId(), lastBudget);
        

        System.out.println("Last update has been undone.");
    }

    public static int getHighestId() {
        int highestId = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(", ");
                int currentId = Integer.parseInt(parts[0]);
                if (currentId > highestId) {
                    highestId = currentId;
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file.");
        }
        return highestId;
    }

    public static void loadBudgetsFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(", ");
                if (parts.length == 7) {
                int id = Integer.parseInt(parts[0]);
                String name = parts[1];
                double income = Double.parseDouble(parts[2]);
                double rent = Double.parseDouble(parts[3]);
                double food = Double.parseDouble(parts[4]);
                double transport = Double.parseDouble(parts[5]);
                double entertainment = Double.parseDouble(parts[6]);

                // Create a new BudgetCalculator object and add it to the in-memory data
                BudgetCalculator budget = new BudgetCalculator(name, income, rent, food, transport, entertainment, id);
                BudgetCalculator.getBudgets().add(budget);
                BudgetCalculator.getBudgetMap().put(id, budget);
            	} else { 
            		System.out.println("Skipping invalid line: " + line);
            	}
       		}
        } catch (IOException e) {
            System.out.println("Error loading budgets from file.");
        }
    }

    public static void viewAllBudgets() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            System.out.println("\nAll Budgets:");
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(", ");
                if (parts.length == 7) {
                    System.out.printf("ID: %s, Name: %s, Income: $%.2f, Rent: $%.2f, Food: $%.2f, Transport: $%.2f, Entertainment: $%.2f%n",
                            parts[0], parts[1], Double.parseDouble(parts[2]), Double.parseDouble(parts[3]),
                            Double.parseDouble(parts[4]), Double.parseDouble(parts[5]), Double.parseDouble(parts[6]));
                } else {
                    System.out.println("Skipping invalid line: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file.");
        }
    }

    public static boolean doesIdExist(int id) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(", ");
                int currentId = Integer.parseInt(parts[0]);
                if (currentId == id) {
                    return true; // ID does exist
                }
            }
        } catch (IOException e) {
            System.out.println("File not found or error reading the file.");
        }
        return false; // ID does not exist
    }
}
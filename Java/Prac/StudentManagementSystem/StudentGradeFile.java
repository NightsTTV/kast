import java.io.*;
import java.util.*;


public class StudentGradeFile {
	public static final String FILE_NAME = "grades.txt"; // static means the String will belong to the class itself, and not to any specfic instance of the class

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in); // creates an instance of the scanner object from the scanner class
		while (true) { // while the program is running
			System.out.println("\nStudent Grade Management System");
			System.out.println("1. Add Student Grade");
			System.out.println("2. View All Grades");
			System.out.println("3. Calculate Average Grade");
			System.out.println("4. Exit");
			System.out.print("Choose an option: ");
			int choice = scanner.nextInt();
			scanner.nextLine(); // Consume newline

			switch (choice) { // switch loop based on user input 1-4
			case 1: 
				addGrade(scanner);
				break;
			case 2: 
				viewGrades();
				break;
			case 3:
				calculateAverageGrade(scanner);
				break;
			case 4:
				System.out.println("Exiting...");
				scanner.close();
				return;
			default:
				System.out.println("Invalid option. Please try again.");
			}
		}
	}

	private static void addGrade(Scanner scanner) {
		System.out.print("Enter student name: ");
		String name = scanner.nextLine(); // creates name variable = user input
		System.out.print("Enter student grade: ");
		int grade = scanner.nextInt(); // creates variable grade = user input
		scanner.nextLine(); // consumer newline

		try (FileWriter writer = new FileWriter(FILE_NAME, true)) {// forces the console to create a writer file "writer" (if one exists with FILE_NAME)
			writer.write(name + "," + grade + "\n"); // calls the write class from the writer object to write name & grade
		} catch (IOException e) { // if an exception under IOExceptions occurs catch it before execution 
			System.out.println("An error occured while writing to the file."); 
			e.printStackTrace(); // prints the identifying information for why the exception occurred
		}
	}

	private static void viewGrades() { // method to viewGrades
		try (BufferedReader reader = new BufferedReader(new FileReader (FILE_NAME))) { // create a reader object from BufferReader class and it is then directly instanciatiated 
			String line; // uninitilized string
			System.out.println("\nAll Grades");
			while ((line = reader.readLine()) != null) { // while the reader reads a single line at a time if (String line) that condition checks null the loop stops
				System.out.println(line); // print line
			}
		} catch (IOException e) { // if an exception under IOExceptions occurs catch it before execution 
			System.out.println("An error occured while reading this file."); //
			e.printStackTrace(); // prints the identifying information for why the exception occurred
		}
	}

	private static void calculateAverageGrade(Scanner scanner) { // method to calculateAverageGrade
		System.out.print("Enter student name: "); 
		String name = scanner.nextLine(); // name is initilzed to user input

		try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) { // creates a reader object from BufferReader class and it is then directly instanciatiated 
			String line; // uninitilzed variable line
			int total = 0; // stores total sum of grades
			int count = 0; // stores count of grades counted for the student
			while ((line = reader.readLine()) != null) {
				/*
				The while loop reads the file line by line.
				reader.readLine() reads a line from the file and assigns it to line.				
				If line is null, it means the end of the file has been reached, and the loop stops.*/
				String[] parts = line.split(","); // splits the expected line ("Alice,85") to ["Alice", "85"]
				if (parts[0].equalsIgnoreCase(name)) { // if the first (0) value of parts[] matches the name sent to the method then execute the rest
					total += Integer.parseInt(parts[1]); // take the second (1) value of parts[] and change it from String->Int and add it to total
					count++; // increase count
				}
			}
			if (count > 0) { // if student has more than 0 grades
				double average = (double) total / count; // find average by dividing
				System.out.printf("Average grade for %s: %.2f\n", name, average); // print that students name and average
			} else {
				System.out.println("No grades found for " + name); // if 0 grades found prints no grades found
			}
		} catch (IOException e) { // if an exception under IOExceptions occurs catch it before execution 
			System.out.println("An error occured while reading this file."); //
			e.printStackTrace(); // prints the identifying information for why the exception occurred
		}
	}
}
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class World {
    private String[][][] levels;

    // Constructor that loads levels from a file
    public World(String filename) {
        try {
            Scanner input = new Scanner(new File(filename));

            int count = input.nextInt(); // Number of levels
            levels = new String[count][][];

            for (int lvl = 0; lvl < count; lvl++) {
                int rows = input.nextInt(); // Number of rows
                int cols = input.nextInt(); // Number of columns
                input.nextLine(); // Consume the rest of the line after reading rows and cols

                setLevel(lvl, rows, cols, input);
            }
            input.close();
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + filename);
        }
    }

    // Method to set the level
    private void setLevel(int lvl, int rows, int cols, Scanner input) {
        levels[lvl] = new String[rows][cols];
        for (int y = 0; y < rows; y++) {
            String line = input.nextLine(); // Read the whole line for the row
            for (int x = 0; x < cols; x++) {
                levels[lvl][y][x] = String.valueOf(line.charAt(x)); // Store each character
            }
        }
    }
    public int getLength() {
        return this.levels.length;
    }
   public String[][] getLevel(int level) {
    return levels[level];
   }
}

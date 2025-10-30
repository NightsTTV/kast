import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;

public class CartoonCharacterSerializer {

    private static final String FILE_NAME = "cartoonCharacter.ser"; // File to store serialized data
    private static final String TEXT_FILE_NAME = "output.txt"; // File to store text output

    private static ObjectOutputStream outputCartoon;
    private static ObjectInputStream inputCartoon;
    private static PrintWriter writer;

    public static void serializeCharacter(CartoonCharacter character) {
        try {
            // Create ObjectOutputStream with FileOutputStream
            outputCartoon = new ObjectOutputStream(new FileOutputStream(FILE_NAME));

            // Write the CartoonCharacter object to the ObjectOutputStream
            outputCartoon.writeObject(character);

            // Close the ObjectOutputStream to ensure proper resource management
            outputCartoon.close();

            System.out.println("Character serialized successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static CartoonCharacter deserializeCharacter() {
        CartoonCharacter cartoon = null;

        try {
            // Create ObjectInputStream with FileInputStream
            inputCartoon = new ObjectInputStream(new FileInputStream(FILE_NAME));

            // Read the CartoonCharacter object from the ObjectInputStream and cast it
            cartoon = (CartoonCharacter) inputCartoon.readObject();

            // Close the ObjectInputStream to ensure proper resource management
            inputCartoon.close();

            System.out.println("Character deserialized successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return cartoon;
    }

    public static void printToFile(String stringToFile) throws FileNotFoundException {
        try {
            // Create PrintWriter with FileWriter
            writer = new PrintWriter(TEXT_FILE_NAME);

            // Write the specified string to the file
            writer.println(stringToFile);

            // Print a formatted line to the file
            writer.printf("Formatted output: %s%n", stringToFile);

            // Close the PrintWriter to ensure proper resource management
            writer.close();

            System.out.println("Text written to file successfully.");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
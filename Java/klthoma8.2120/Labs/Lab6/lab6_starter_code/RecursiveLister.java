import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.file.DirectoryStream;
import java.io.IOException;

public class RecursiveLister {

    public static void main(String[] args) {

    // first, make sure that an argument has been passed in, otherwise
    // tell the user the correct way to invoke the program
    if (args.length != 1) {
        System.err.println("Usage: RecursiveLister directoryname");
        System.exit(1); // exit with an error code
    }

    // get the argument
    String dirName = args[0];

    Path targetPath = Paths.get(dirName);

    recursivelyListDirectories(targetPath);

    } // end main

// This will be the recursive method
    public static void recursivelyListDirectories(Path targetPath) {

        // Base case:
        // Test the base case - what might that be? Print out the result and
        // don't recurse any further 
        if (!Files.isDirectory(targetPath)) {
            System.out.println(targetPath.toString());
            return;
        }
        // General case:
        // If the targetPath happens to be a directory, print out the name
        // of the directory, create a DirectoryStream object, iterate through
        // the Paths contained in that directory stream, calling this method
        // recursively on each one - after the last Path, print out an empty line;
        else {
            System.out.println(targetPath.toString() + ":");
    
               try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(targetPath)) {
                for (Path path : directoryStream) {
                    recursivelyListDirectories(path);
                }
               } catch (IOException e) {
                System.err.println("Error accessing directory: " + targetPath);
                e.printStackTrace();
               }

            System.out.println();
        }
    }

} // end class

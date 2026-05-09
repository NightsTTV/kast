import java.util.*;

public class UsernameEntry {
    private String userName;

    public String UsernameEntry() {
        Scanner in = new Scanner(System.in);

        System.out.println("Please enter Username:");
        // Captures the input first
        userName = in.nextLine();
        
        System.out.println("Username is: " + userName); // Output user input
        return userName;
    }
}

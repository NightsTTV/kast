import java.util.Scanner;

public class PlayerMoveOverworld {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int testCase = input.nextInt();
        input.nextLine();  // Consume the newline after the integer input

        for (int i = 0; i < testCase; i++) {
            String xy = input.nextLine();
            String[] num = xy.split(" ");
            int x = Integer.parseInt(num[0]);
            int y = Integer.parseInt(num[1]);

            String inputIn = input.nextLine();
            String[] wasd = inputIn.split(" ");

            for (int j = 0; j < wasd.length; j++) {
                switch (wasd[j]) {
                    case "w":  // Move up
                        y -= 1;
                        break;
                    case "a":  // Move left
                        x -= 1;
                        break;
                    case "s":  // Move down
                        y += 1;
                        break;
                    case "d":  // Move right
                        x += 1;
                        break;
                }
            }

            // Print final coordinates after all moves
            System.out.println(x + " " + y);
        }

  
    }
}
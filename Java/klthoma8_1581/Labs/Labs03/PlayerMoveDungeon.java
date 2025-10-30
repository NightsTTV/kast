import java.util.Scanner;

public class PlayerMoveDungeon {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int testCase = input.nextInt();
        input.nextLine();  

        for (int i = 0; i < testCase; i++) { // test case loop

            String boxSize = input.nextLine();
            String[] axis = boxSize.split(" ");
            int xaxis = Integer.parseInt(axis[0]);
            int yaxis = Integer.parseInt(axis[1]);

            String xy = input.nextLine();
            String[] num = xy.split(" ");
            int x = Integer.parseInt(num[0]);
            int y = Integer.parseInt(num[1]);

            String inputIn = input.nextLine();
            String[] wasd = inputIn.split(" ");

            for (int j = 0; j < wasd.length; j++) { // loop for key entry
                    if  (wasd[j].equals("w") && y > 0)
                    {  // Move up
                        y -= 1;
                    }
                    else if (wasd[j].equals("a") && x > 0)
                    {  // Move left
                        x -= 1;
                    }
                     else if (wasd[j].equals("s") && y < yaxis - 1)
                     { // Move down
                        y += 1;
                    }  
                     else if (wasd[j].equals("d") && x < xaxis - 1)
                     {
                        x += 1;
                     } // Move right
                     else{

                         }
                     }
                      
                 // Print final coordinates after all moves
            System.out.println(x + " " + y);   
                }

            }  
        }


  
    

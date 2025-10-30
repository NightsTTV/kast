import java.util.Scanner;

public class ZombieApocalypse{
	public static void main(String[] args){

		// Setup Game Data
		Scanner input = new Scanner(System.in);
		boolean gameOver = false; // 
		boolean levelOne = true;
		boolean levelTwo = false;
		boolean levelThree = false;
		int colSize = 10;
		int rowSize = 10;
		int playerX = 0;
		int playerY = 0;
		int exitX = colSize - 1;
		int exitY = rowSize - 1;
		int zombie1X = 5;
		int zombie1Y = 5;
		int zombie2X = 7;
		int zombie2Y = 7;
		int zombie3X = 9;
		int zombie3Y = 2;
		int zombie4X = 0;
		int zombie4Y = 0;
		String zombieTile = "+ ";
		String zombieTile2 = "^ ";
		String zombieTile3 = "<> ";
		String zombieTile4 = "/";
		String floorTile = ". ";
		String exitTile = "#";
		String playerTile = "@ ";

		// Game Loop
		// Level 1
		

		while (!gameOver) {
			while (levelOne == true) {

				
				// Draw Game Scene
			for (int y = 0; y < rowSize; y++) {
                for (int x = 0; x < colSize; x++) {
                    if (x == playerX && y == playerY) {
                        System.out.print(playerTile);
                    } else if (x == exitX && y == exitY) {
                        System.out.print(exitTile);
                    } else if (x == zombie1X && y == zombie1Y) {
                        System.out.print(zombieTile);
                    } else {
                        System.out.print(floorTile);
                    }
                }
                System.out.print("\n");
            }

				// Get Player Input
			String choice = input.nextLine();
				// Execute Player Action
			if (choice.equals("w")) {
				playerY = --playerY >= 0 ? playerY : (rowSize - 1); // Move Up
			} else if (choice.equals("a")) {
				playerX = --playerX >= 0 ? playerX : (colSize - 1); // Move Left
			} else if (choice.equals("s")) {
				playerY = (playerY + 1) % rowSize; // Move Down
			} else if (choice.equals("d")) {
				playerX = (playerX + 1) % colSize; // Move Right
			}

				// Check Win Condition
			if (playerX == exitX && playerY == exitY){
				System.out.println("You survived and made it to the exit!");
				levelOne = false;
				levelTwo = true;
			}
		
				// Execute Monster Action
			
			int zombieChoice = (int) (Math.random()*4);
			if (zombieChoice == 0) {
				zombie1X = (zombie1X + 1) % colSize; // Move Right
			} else if (zombieChoice == 1) {
				zombie1X = --zombie1X >= 0 ? zombie1X : (colSize - 1);// Move left 
			} else if (zombieChoice == 2) {
				zombie1Y = --zombie1Y >= 0 ? zombie1Y : (rowSize - 1); // Move Up
			} else if (zombieChoice == 3) {
				zombie1Y = (zombie1Y + 1) % rowSize; // Move Down
			}

			
			// Check Lose Condition
				if (zombie1X == playerX && zombie1Y == playerY){
                levelOne = false;
                gameOver = true;
				System.out.println("Your brains were eaten by the zombie");

			}
			// Level 2
			while (levelTwo) {
			    // Reset player and zombie positions before entering the loop
			    playerX = 0; playerY = 0; // Reset player position
			    zombie1X = 5; zombie1Y = 5;
			    zombie2X = 6; zombie2Y = 6; // Add initial positions for zombie2
			    zombie3X = 7; zombie3Y = 7; // Add initial positions for zombie3
			    exitX = colSize - 1; exitY = rowSize - 1;

			    // Game Loop for Level 2
			    while (levelTwo) {
				// Draw Game Scene
			for (int y = 0; y < rowSize; y++) {
                for (int x = 0; x < colSize; x++) {
                    if (x == playerX && y == playerY) {
                        System.out.print(playerTile);
                    } else if (x == exitX && y == exitY) {
                        System.out.print(exitTile);
                    } else if (x == zombie1X && y == zombie1Y) {
                        System.out.print(zombieTile);
                    } else if (x == zombie2X && y == zombie2Y) {
                        System.out.print(zombieTile2);
                    } else if (x == zombie3X && y == zombie3Y) {
                        System.out.print(zombieTile3);
                    } else {
                        System.out.print(floorTile);
                    }
                }
                System.out.print("\n");
            }

				// Get Player Input
			choice = input.nextLine();
				// Execute Player Action
			if (choice.equals("w")) {
				playerY = --playerY >= 0 ? playerY : (rowSize - 1); // Move Up
			} else if (choice.equals("a")) {
				playerX = --playerX >= 0 ? playerX : (colSize - 1); // Move Left
			} else if (choice.equals("s")) {
				playerY = (playerY + 1) % rowSize; // Move Down
			} else if (choice.equals("d")) {
				playerX = (playerX + 1) % colSize; // Move Right
			}

				// Check Win Condition
			if (playerX == exitX && playerY == exitY){
				System.out.println("You survived and made it to the exit!");
				levelTwo = false;
				levelThree = true;
			}
		
				// Execute Monster Action
			
			zombieChoice = (int) (Math.random()*4);
			if (zombieChoice == 0) {
				zombie1X = (zombie1X + 1) % colSize; // Move Right
			} else if (zombieChoice == 1) {
				zombie1X = --zombie1X >= 0 ? zombie1X : (colSize - 1);// Move left 
			} else if (zombieChoice == 2) {
				zombie1Y = --zombie1Y >= 0 ? zombie1Y : (rowSize - 1); // Move Up
			} else if (zombieChoice == 3) {
				zombie1Y = (zombie1Y + 1) % rowSize; // Move Down
			}

			zombieChoice = (int) (Math.random()*2);                     // zombie 2
			  if (zombieChoice == 0) {
				zombie2Y = --zombie2Y >= 0 ? zombie2Y : (rowSize - 1); // Move Up
			} else if (zombieChoice == 1) {
				zombie2Y = (zombie2Y + 1) % rowSize;  // Move Down
			}
			
			zombieChoice = (int) (Math.random()*2);                      // zombie 3
			if (zombieChoice == 0) {
				zombie3X = (zombie3X + 1) % colSize; // Move Right
			} else if (zombieChoice == 1) {
				zombie3X = --zombie3X >= 0 ? zombie3X : (colSize - 1);// Move left 
			} 
			// Check Lose Condition
				if ((zombie1X == playerX && zombie1Y == playerY) ||
                (zombie2X == playerX && zombie2Y == playerY) ||
                (zombie3X == playerX && zombie3Y == playerY)) {
                levelTwo = false;
				gameOver = true;
				System.out.println("Your brains were eaten by the zombie");

			}
		}
	}

			while (levelThree == true){
				colSize = 15; rowSize = 15; // Update grid size
                playerX = 0; playerY = 0; // Reset player position
                exitX = colSize - 1; exitY = rowSize - 1;
                zombie1X = 4; zombie1Y = 3;
                zombie2X = 2; zombie2Y = 7;
                zombie3X = 9; zombie3Y = 2;
                zombie4X = 14; zombie4Y = 14;

                	while (levelThree == true){
				// Draw Game Scene
			for (int y = 0; y < rowSize; y++) {
                for (int x = 0; x < colSize; x++) {
                    if (x == playerX && y == playerY) {
                        System.out.print(playerTile);
                    } else if (x == exitX && y == exitY) {
                        System.out.print(exitTile);
                    } else if (x == zombie1X && y == zombie1Y) {
                        System.out.print(zombieTile);
                    } else if (x == zombie2X && y == zombie2Y) {
                        System.out.print(zombieTile2);
                    } else if (x == zombie3X && y == zombie3Y) {
                        System.out.print(zombieTile3);
                    } else if (x == zombie4X && y == zombie4Y) {
                        System.out.print(zombieTile4);
                    } else {
                        System.out.print(floorTile);
                    }
                }
                System.out.print("\n");
            }

				// Get Player Input
			choice = input.nextLine();
				// Execute Player Action
			if (choice.equals("w")) {
				playerY = --playerY >= 0 ? playerY : (rowSize - 1); // Move Up
			} else if (choice.equals("a")) {
				playerX = --playerX >= 0 ? playerX : (colSize - 1); // Move Left
			} else if (choice.equals("s")) {
				playerY = (playerY + 1) % rowSize; // Move Down
			} else if (choice.equals("d")) {
				playerX = (playerX + 1) % colSize; // Move Right
			}

				// Check Win Condition
			if (playerX == exitX && playerY == exitY){
				levelThree = false;
				gameOver = true;
				System.out.println("You survived and made it to the exit!");
			}
		
				// Execute Monster Action
			// Zombie ^<>!
			zombieChoice = (int) (Math.random()*4);
			if (zombieChoice == 0) {
				zombie1X = (zombie1X + 1) % colSize; // Move Right
			} else if (zombieChoice == 1) {
				zombie1X = --zombie1X >= 0 ? zombie1X : (colSize - 1);// Move left 
			} else if (zombieChoice == 2) {
				zombie1Y = --zombie1Y >= 0 ? zombie1Y : (rowSize - 1); // Move Up
			} else if (zombieChoice == 3) {
				zombie1Y = (zombie1Y + 1) % rowSize; // Move Down
			}
			// Zombie ^!
			zombieChoice = (int) (Math.random()*2);
			  if (zombieChoice == 0) {
				zombie2Y = --zombie2Y >= 0 ? zombie2Y : (rowSize - 1); // Move Up
			} else if (zombieChoice == 1) {
				zombie2Y = (zombie2Y + 1) % rowSize;  // Move Down
			}
			// Zombie <>
			zombieChoice = (int) (Math.random()*2);
			if (zombieChoice == 0) {
				zombie3X = (zombie3X + 1) % colSize; // Move Right
			} else if (zombieChoice == 1) {
				zombie3X = --zombie3X >= 0 ? zombie3X : (colSize - 1);// Move left 
			} 
			// Zombie Diagonal X
			zombieChoice = (int) (Math.random()*4);
			if (zombieChoice == 0) {
				zombie4X = (zombie4X + 1) % colSize; // Move Right-Up
				zombie4Y = --zombie4Y >= 0 ? zombie4Y : (rowSize - 1); 
			} else if (zombieChoice == 1) {
				zombie4X = --zombie4X >= 0 ? zombie4X : (colSize - 1);// Move left-down
				zombie4Y = (zombie4Y + 1) % rowSize;
			} else if (zombieChoice == 2) {
				zombie4X = --zombie4X >= 0 ? zombie4X : (colSize - 1);
				zombie4Y = --zombie4Y >= 0 ? zombie4Y : (rowSize - 1); // Move left-Up
			} else if (zombieChoice == 3) {
				zombie4X = (zombie4X + 1) % colSize;
				zombie4Y = (zombie4Y + 1) % rowSize; // Move Right-Down
			}
			// Check Lose Condition
				if ((zombie1X == playerX && zombie1Y == playerY) ||
                (zombie2X == playerX && zombie2Y == playerY) ||
                (zombie3X == playerX && zombie3Y == playerY)||
                (zombie4X == playerX && zombie4Y == playerY)) {
                levelThree = false;
				gameOver = true;
				System.out.println("Your brains were eaten by the zombie");

				}
			}
			}
		}
		}
		}
	}

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class SnakesLadders {
	public static void main(String[] args){
		//Prints the  welcome message.
		System.out.println("Welcome to Snakes & Ladders Variation A");
		System.out.println("Created by Arif Haque 2009403");
		//scanner to parse the input
		Scanner scan = new Scanner (System.in);
		
		
		//user inputs how many players
		int numofPlayers = 0;
		while (numofPlayers <= 0 || numofPlayers >3 ){
			System.out.print("Please enter the number of player (1-3): " );
			numofPlayers = scan.nextInt();
			if(numofPlayers>=4) {
				System.out.println("Invalid input, please start again");
				System.exit(numofPlayers);
			}
		}
		
		//store the players within a list
		List<Player> players = new ArrayList<Player>();
		for (int idx = 0; idx < numofPlayers; idx++){
			Player player = new Player("P" + (idx + 1));
			players.add(player);
		}
		
		//board is setup
		SLboard board = new SLboard(players);
		
		//there will be a loop up to the 100th tile
		//Players take turns to roll the die and move on the board
		boolean winner = false;
		int index = 0;
		while (!winner){
			//player takes their turn
			Player activePlayer = players.get(index);
			int roll = activePlayer.takeTurn();
			
			//Update the board
			winner = board.movePlayer(activePlayer, roll);
			
			//Print the board
			System.out.println(board);
			System.out.println("-----------------------\n");
			
			//If a player lands on the 100th tile, print end message.
			if (winner){
				System.out.println(activePlayer + " wins");
			}
			
			//Set up for next player
			index++;
			if (index == numofPlayers){
				index = 0;
			}
			
		}
	}

}

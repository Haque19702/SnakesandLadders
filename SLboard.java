import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class SLboard {
	//Constant values: Board size, and number of snakes and ladders
		private final int BoardRow = 10;
		private final int BoardColumn = 10;
		private final int numSnakes = 10;
		private final int numLadders = 9;
		private final int numCookie = 3;
		private final int numSticks = 3;
		

		//Board variables
		private int[][] gameBoard;
		private int[][] snakes;
		private int[][] ladders;
		private int[] cookie;
		private int[] sticks;
		private boolean CookieInventory = false;

		//Map of player positions.
		//Key = player, Value = player position
		Map<Player, Integer> playerLocation;

		public SLboard(List<Player> players){

			//the player positions
			this.playerLocation = new HashMap<Player, Integer>();
			for (Player player : players){
				this.playerLocation.put(player, 0);
			}

			//Set up the ROWS X COLS board
			gameBoard = new int[BoardRow][BoardColumn];
			for (int row = 0; row < BoardRow; row++){
				for (int col = 0; col < BoardColumn; col++){
					gameBoard[row][col] = row*BoardRow + col + 1;
				}
			}

			//generates the snakes, ladders, cookie, and sticks on to the board
			setSnakes();
			setLadders();
			setCookie();
			setStick();

		}

		public boolean movePlayer(Player player, int value){

			//Compute the players updated position
			int location = playerLocation.get(player);
			location += value;


			if (location >= 100){
				//If the new position is 100 (or above), the player wins!
				playerLocation.put(player, 100);
				return true;
			} else {
				//If the new position is less than 100

				//Check if the new position is the starting point for a snake
				for (int idx = 0; idx < numSnakes; idx++){
				//if the player has a cookie they are able to bypass the snake
					if(snakes[idx][0] == location && player.hasCookie == true){
						location = snakes[idx][0];
						//change it to false once used
						player.hasCookie = false;
						playerLocation.put(player, location);
						System.out.println("Noice "+ player + " has fed the snake and stays on the tile");
						
					}
					//If the new position is the starting point for a snake
					//Move the player to the end position for the snake					
					else if(snakes[idx][0] == location){
						location = snakes[idx][1];
						playerLocation.put(player, location);
						
						System.out.println("Uh oh. " + player + " takes snake from " + snakes[idx][0] + " to " + snakes[idx][1]);

						return false;
					}
				}

				//Check if the new position is the starting point for a ladder
				for (int idx = 0; idx < numLadders; idx++){
					if (ladders[idx][0] == location){
						//If the new position is the starting point for a ladder
						//Move the player to the end position for the ladder
						location = ladders[idx][1];
						playerLocation.put(player, location);

						System.out.println("Yay! " + player + " takes ladder from " + ladders[idx][0] + " to " + ladders[idx][1]);
						
						return false;
					}else if(ladders[idx][0] == location && player.hasStick == true) {
						//if the player has the stick on the starting point of the ladder, they increase the ladder length by 10
						location = ladders[idx + 1][1]; 
						//change it to false once used
						player.hasStick = false;
						playerLocation.put(player, location);
						
						System.out.println("WOHOOOO " + player + "takes the ladder and uses the stick which goes and extra row! from:" + ladders[idx][0] + "to " + ladders[idx+1][1]);
					}
				}
				
				//Check if the new position contains a cookie or stick
				for (int idx = 0; idx < numCookie; idx++){
					if (cookie[idx] == location){
						player.earnCookie();
					} else if (sticks[idx] == location){
						player.earnStick();
					}
				}

				//If the player is not on a snake/ladder, then just update
				//its position normally
				playerLocation.put(player, location);
				return false;
			}

		}

		//Sets the snake on to the board 
		private void setSnakes(){
			snakes = new int[numSnakes][2];

			snakes[0][0] = 17;
			snakes[0][1] = 7;
			snakes[1][0] = 54;
			snakes[1][1] = 34;
			snakes[2][0] = 62;
			snakes[2][1] = 19;
			snakes[3][0] = 64;
			snakes[3][1] = 60;
			snakes[4][0] = 87;
			snakes[4][1] = 24;
			snakes[5][0] = 93;
			snakes[5][1] = 73;
			snakes[6][0] = 95;
			snakes[6][1] = 75;
			snakes[7][0] = 99;
			snakes[7][1] = 78;
		}

		//Generates the ladders on to the board
		private void setLadders(){
			ladders = new int[numLadders][2];

			ladders[0][0] = 4;
			ladders[0][1] = 14;
			ladders[1][0] = 9;
			ladders[1][1] = 31;
			ladders[2][0] = 20;
			ladders[2][1] = 38;
			ladders[3][0] = 28;
			ladders[3][1] = 84;
			ladders[4][0] = 40;
			ladders[4][1] = 59;
			ladders[5][0] = 51;
			ladders[5][1] = 67;
			ladders[6][0] = 63;
			ladders[6][1] = 81;
			ladders[7][0] = 71;
			ladders[7][1] = 91;
		}
		
		//Generates the Cookies on to the board
		private void setCookie(){
			cookie = new int[numCookie];			
			cookie[0] = 2;
			cookie[1] = 21;
			cookie[2] = 50;
		}
		
		//Generates the Sticks on to the board
		private void setStick(){
			sticks = new int[numSticks];			
			sticks[0] = 6;
			sticks[1] = 25;
			sticks[2] = 44;
		}

		//Prints the board on to the console using toString()
		public String toString(){
			//Use StringBuilder for creating the string
			StringBuilder boardBuilder = new StringBuilder();
			boolean revrow = true;
			//rows would be in reverse order
			for (int row = BoardRow-1; row >= 0; row--){
				for (int col = 0; col < BoardColumn; col++){
					if (revrow){
						//oddRow: row = 1,3,5,7,9
						String space = "";
						boolean occupied = false;
						for (Player temp : playerLocation.keySet()){
							if (playerLocation.get(temp) == gameBoard[row][BoardColumn-1-col]){
								occupied = true;
								space += temp + " ";
							}
						}
						
						if (occupied){
							//If at least one player occupies the location, then print those players
							space += "\t";
							boardBuilder.append(space);
						} else {
							//Else, print the position number
							boardBuilder.append(gameBoard[row][BoardColumn-1-col] + "\t");						
						}
					} else {
						//evenRow: row = 8, 6, 4, 2, 0
						//Check if any of the players occupy the current location
						boolean occupied = false;
						String pl = "";
						for (Player temp : playerLocation.keySet()){
							if (playerLocation.get(temp) == gameBoard[row][col]){
								occupied = true;
								pl += (temp + " ");
							}
						}
						
						if (occupied){
							//If at least one player occupies the location, then print those players
							pl += "\t";
							boardBuilder.append(pl);	
						} else {
							//Else,  print the position number
							boardBuilder.append(gameBoard[row][col] + "\t");
						}
					}
				} 
				//Switch oddRow and and print a new row on the console on a new line
				revrow = !revrow;
				boardBuilder.append("\n");
			}
			boardBuilder.append("\n");

			return boardBuilder.toString();
		}

}

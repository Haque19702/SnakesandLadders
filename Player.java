import java.util.Scanner;
public class Player {
	private String name;
	private static Dice dice = new Dice();
	public static boolean hasCookie;
	public boolean hasStick;
	
	//Initialize the fields
	public Player(String name){
		this.name = name;
	}
	
	/**
	 * Sets hasCookie to true
	 */
	public void earnCookie(){
		this.hasCookie = true;
		System.out.println(name + " gained a cookie");
	}
	
	//Sets the Stick to true
	public void earnStick(){
		this.hasStick = true;
		System.out.println(name + " has gained stick");
	}
	
	
	public int takeTurn(){
		
		//Initialise scanner
		Scanner scan = new Scanner(System.in);
		//Prompt user for roll
		System.out.print(name+"'s turn: ");
		String input = scan.nextLine();
		
		//Translate the input to some numeric value between 1 and 9
				int val = 0;
				for (int idx = 0; idx < input.length(); idx++){
					val+= input.charAt(idx);
				}
				val = val % 10;
				if (val == 0){
					val = 1;
				}
				
				//Throw away the next 'val' random values
				for (int idx = 0; idx < val; idx++){
					dice.roll6SD();
				}
		
		
		//Get the roll
		int roll = 0;
		if (hasCookie){
			//If the player lands on a cookie, set hasCookie as true
			roll = dice.roll6SD();
			hasCookie = true;
		} else if (hasStick){
			roll = dice.roll6SD();
			//If the player lands on a stick, then set hasStick to true
			hasStick = true;
			
			} 
		 else {
			//Otherwise, roll normally
			 roll = dice.roll6SD();
		}
		
		System.out.println(name + " rolled " + roll + ".");
		
		return roll;
	}
	
	
	public String toString(){
		return name;
	}

}

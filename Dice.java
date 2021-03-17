import java.util.Random;
public class Dice {
private Random random;
	
	//Initialise the dice
	public Dice(){
		random = new Random();
	}
	
	
	//rolls a six sided dice and returns the value
	public int roll6SD(){
		return random.nextInt(6)+1;
	}

}

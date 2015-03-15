import java.util.Arrays;
import java.util.Scanner;

/**
 * Combination Lock class, a data structure 
 * representing a lock which can be opened 
 * or closed using 3 integers by default.
 * @author Pradyumn Vij
 */
public class CombinationLock {
	private int[] combination;
	boolean isLocked;
	
	/**
	 * 2nd constructor allows user to make
	 * instances with larger combinations
	 * @param combinationSize
	 */
	public CombinationLock(int combinationSize){
		//Combination is defaulted to zeroes
		combination = new int[combinationSize];
		//Lock arrives closed
		isLocked = true;
	}
	
	/**
	 * Default constructor will have 3 positions
	 * in the lock combination.
	 */
	public CombinationLock(){
		this(3);
	}
	
	/**
	 * Opens lock, requires correct
	 * combination.
	 */
	public void open(){
		if(checkKey()){
			isLocked = false;
			System.out.println("Correct Combination, \nlock is open.");
		}
		else{
			System.out.println("Incorrect Combination.");
		}
	}
	
	/**
	 * Closes lock, doesn't require the
	 * combination.
	 */
	public void close(){
		isLocked = true;
		System.out.println("The lock is now closed.");
	}
	
	/**
	 * Changes the combination, required correct
	 * combination first.
	 * Cannot change the size of the combination.
	 */
	public void changeCombo(){
		if(checkKey()){
			int digit;
			Scanner userInput = new Scanner(System.in);
			System.out.println("Enter new key combination: \n");
			for(int i = 0; i<combination.length; i++){
				System.out.println("Enter digit " + (i+1) + ": ");
				digit = userInput.nextInt();
				combination[i] = digit;
			}
		userInput.close();
		}
		System.out.println("The new key is: " + Arrays.toString(combination));
	}
	
	/**
	 * Checks the combination in the array.
	 * @return boolean if key is correct or not.
	 */
	public boolean checkKey(){
		Scanner userInput = new Scanner(System.in);
		int digit;
		for(int i = 0; i<combination.length; i++){
			System.out.println("Enter digit "+ (i+1) + ":");
			digit = userInput.nextInt();
			if(combination[i] != digit){
				return false;
			}
		}
		return true;
	}
	}
	
	//public static void main(String[] args){
		//CombinationLock c = new CombinationLock();
			//c.changeCombo();
			//c.close();
			//c.open();
		//}
	//}

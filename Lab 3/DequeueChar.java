import CITS2200.Overflow;
import CITS2200.Underflow;

public class DequeueChar {
	
	private char [] line;
	private int left;
	private int right;
	public int currentSize;
	
	DequeueChar(int s){
		line = new char [s];
		makeEmpty();
	}
	
	/**
	 * Reset Double End Queue
	 */
	public void makeEmpty(){
		right = -1;
		left = 0;
		currentSize =0;
	}
	
	public boolean isEmpty(){
		return left == right+1; 
		//left has passed right in search of an element
		//return currentSize == 0;
	}
	
	public boolean isFull(){
		return (right == line.length - 1  && left == 0);
		//right is pushed to the last space in the array
	}
	
	public void pushLeft(char c) throws Overflow {
		if(!isFull()){
			if(left == 0){
				moveRight();
			}
			line[0] = c;
			left --;
			currentSize++;
		}
		else throw new Overflow("Dequeue is full!");
	}
	
	public void pushRight(char c) throws Overflow {
		if(!isFull()){
			if(right == line.length -1){
				moveLeft();
			}
			line[-1] = c;
			right++;
			currentSize++;
		}
		else throw new Overflow("Dequeue is full!");
	}
	
	public char peekLeft() throws Underflow {
		if(!isEmpty()){
			return line[left];
		}
		else throw new Underflow("Dequeue is empty!");
	}
	
	public char peekRight() throws Underflow {
		if(!isEmpty()){
			return line[right];
		}
		else throw new Underflow("Dequeue is empty!");
	}
	
	public char popLeft() throws Underflow {
		if(!isEmpty()){
			char temp = line[left];
			moveLeft();
			currentSize--;
			return temp;
		}
		else throw new Underflow("Dequeue is empty!");
	}
	
	public char popRight() throws Underflow {
		if(!isEmpty()){
			char temp = line[right];
			moveRight();
			currentSize--;
			return temp;
		}
		else throw new Underflow("Dequeue is empty!");
	}
	
	/**
	 * Helper function to move elements one space left
	 * without checks.
	 */
	private void moveLeft(){
		int i = left;
		while(i <= right){
			line[i-1] = line[i];
			i++;
		}
		left--;
		right--;
	}
	
	/**
	 * Helper function to move elements one space right
	 * without checks.
	 */
	private void moveRight(){
		int i = right;
		while(i >= left){
			line[i+1] = line[i];
			i--;
		}
		right++;
		left++;
	}
}
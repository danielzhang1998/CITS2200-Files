import CITS2200.Deque;
import CITS2200.Overflow;
import CITS2200.Underflow;


public class DequeCyclic implements Deque{
	
	private int currentSize;
	public int right;
	public int left;
	private Object [] line;
	
	/*
	 * Constructor where capacity indicates the size
	 * of the array that builds DequeCyclic, not the
	 * data structure itself.
	 */
	public DequeCyclic(int capacity){
		line = new Object[capacity];
		left = 0;
		right = 1;
		currentSize = 0;
		// create second index currentSize for dequeue size
	}
	
	/*
	 * @return true if empty 
	 */
	public boolean isEmpty(){
		return currentSize == 0;
	}
	
	/**
	 * 
	 * @return true if entire capacity of array is
	 * equal to the length of the deque as define by
	 * left and right.
	 */
	public boolean isFull(){
		//return right - left + 1 == line.length;
		return currentSize == line.length-2;
	}
	
	/**
	 * Define new empty Left most position by removing 1
	 * adding array size and modding by array size.
	 * Insert c to the new start of the deque and 
	 * adding counter. 
	 * @param c
	 * @throws Overflow if deque is full.
	 */
	public void pushLeft(Object c) throws Overflow{
		if(!isFull()){
			left = (left - 1 + line.length) % line.length;
			line[left] = c;
			currentSize++;
		}
		else throw new Overflow("Deque is full");
	}
	
	/**
	 * Pushing right is easier as the successor to right
	 * will always be + 1 unless the entire array is full
	 * which is checked for by an Overflow.
	 * @param c
	 */
	public void pushRight(Object c) throws Overflow{
		if(!isFull()){
			right = (right + 1)%line.length;
			line[right] = c;
			currentSize++;
		}
		else throw new Overflow("Deque is full");
	}
	
	/**
	 * Returns the element under Left, however if empty
	 * throws Underflow.
	 * 
	 */
	public Object peekLeft() throws Underflow{
		if(!isEmpty()){
			return line[left];
		}
		else throw new Underflow("Deque is empty");
	}
	
	/*
	 * Returns element under right,
	 * throws underflow if deque is empty.
	 */
	public Object peekRight() throws Underflow{
		if(!isEmpty()){
			return line[right];
		}
		else throw new Underflow("Deque is empty");
	}
	
	/**
	 * Removes item under left and assigns left to
	 * next object element.
	 * @return object at left
	 * @throws Underflow
	 */
	public Object popLeft() throws Underflow{
		if(!isEmpty()){
			Object temp = peekLeft();
			left = left + 1;
			left = (left + 1 + line.length)%line.length;
			currentSize--;
			return temp;
		}
		else throw new Underflow("Deque is empty");
	}
	
	public Object popRight() throws Underflow{
		if(!isEmpty()){
			Object temp = peekRight();
			right = (right - 1 + line.length)%line.length;
			currentSize--;
			return temp;
		}
		else throw new Underflow("Deque is empty");
	}
}
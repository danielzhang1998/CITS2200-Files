import CITS2200.Link;
import CITS2200.Underflow;

/**
 * 
 */

/**
 * @author Pradyumn
 *
 */
public class StackLinked<E>{
	// Global sentinel cell representing top of stack
	Link top;
	
	public StackLinked(){
		top = null;	// Initiated as null
	}
	
	/**
	 * Returns top stack item but doesn't delete.
	 * @return Top stack item
	 * @throws Underflow if stack is empty
	 */
	public E examine() throws Underflow {
		if(!isEmpty()){
			return (E) top.item;
		}
		else
			throw new Underflow("Stack is Empty");
	}
	
	/**
	 * Checks to see if Stack is empty
	 * @return	True if stack is empty
	 */
	public boolean isEmpty() {
		return top == null;
	}
	
	/**
	 * Removes and returns top item.
	 * @return Top item
	 * @throws Underflow if Stack is empty
	 */
	public E pop() throws Underflow {
		if(!isEmpty()){
			Object temp = top.item;
			top = top.successor;
			return (E) temp;
		}
		else
			throw new Underflow("Stack is Empty");
	}
	
	/**
	 * Adds an item to the top of the stack
	 * @param item
	 */
	public void push(Object item) {
		top = new Link(item, top);
	}
}

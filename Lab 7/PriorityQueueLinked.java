import CITS2200.IllegalValue;
import CITS2200.Iterator;
import CITS2200.OutOfBounds;
import CITS2200.PriorityQueue;
import CITS2200.Underflow;

/**
 * 
 */

/**
 * @author Pradyumn
 *
 */
public class PriorityQueueLinked implements PriorityQueue<Object>{
	
	/**
	 * Initialise global link variable "front" as the 
	 * front of the queue with the item with the highest
	 * priority at the front.
	 */
	private Link<Object> front;
	
	/**
	 * 	Constructor for PriorityQueueLinked class.
	 * 	Start with initial null link.
	 */
	public PriorityQueueLinked(){
		front = null;
	}

	/**
	 *	Returns the object at the front of the queue with
	 *	the highest priority and removes it.
	 *	Throws underflow if the queue is empty.
	 */
	@Override
	public Object dequeue() throws Underflow {
		if(!isEmpty()){
			Object temp = (Object) front.element;
			front = front.next;
			return temp;
		}
		else throw new Underflow("Queue is Empty!");
	}
	
	/**
	 * 	Adds a new item to the queue based on the associated
	 * 	priority "importance".
	 * 	Throws illegal value if importance is a negative.
	 */
	@Override
	public void enqueue(Object item, int importance) throws IllegalValue {
		if(importance < 0){
			throw new IllegalValue("Priority must be a non negative integer.");
		}
		else{
			if(isEmpty() || importance > front.priority){
				front = new Link<Object>(item,importance,front);
			}
			else{
				Link<Object> findSpot = front;
				while(findSpot.next != null && findSpot.next.priority >= importance){
					findSpot = findSpot.next;
				}
				findSpot.next = new Link<Object>(item,importance,findSpot.next);
			}
		}
	}

	/**
	 * Returns the object at the front of the priority Queue
	 */
	@Override
	public Object examine() throws Underflow {
		if(!isEmpty()){
			return (Object) front.element;
		}
		else throw new Underflow("Queue is Empty!");
	}
	
	/**
	 * 	Checks if the priority queue is empty.
	 * 	Returns boolean true if empty.
	 */
	@Override
	public boolean isEmpty() {
		return front == null;
	}

	@Override
	public Iterator<Object> iterator() {
		return new PriorityIterator();
	}
	
	
	/**
	 * Internal iterator class for the priority queue, starts
	 * at a position before first.
	 * @author Pradyumn
	 *
	 */
	public class PriorityIterator implements Iterator<Object>{
		//	Use a link like a window in a position pointing to the
		//	first item in the queue.
		Link<Object> current;
		
		public PriorityIterator(){
			current = new Link<Object>(null,0,front);
		}
		
		//	Returns true if next reference is not to null
		@Override
		public boolean hasNext() {
			return current.next != null;
		}
		
		//	Moves link to next item and returns its Object
		//	Throws Out of Bounds if reaches end of queue.
		@Override
		public Object next() throws OutOfBounds {
			if(hasNext()){
				current = current.next;
				return current.element;
			}
			else{
				throw new OutOfBounds("No more items!");
			}
		}
	}
	
	/**
	 * Internal class Link to make a base structure
	 * that holds an Object, an integer representing
	 * priority and a Link indicating the reference
	 * to the next item.
	 * @author Pradyumn
	 *
	 */
	public class Link<Object>{
		Object element;
		int priority;
		Link<Object> next;
		
		public Link(Object item, int priority, Link<Object> n){
			this.element = item;
			this.priority = priority;
			this.next = n;
		}
	}
}

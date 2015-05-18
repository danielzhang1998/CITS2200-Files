import CITS2200.Queue;
import CITS2200.Underflow;
import CITS2200.WindowLinked;

/**
 * 
 */

/**
 * @author Pradyumn
 *
 */
public class LinkedQueue<E> implements Queue{
	
	ListLinked line;
	WindowLinked w;
	
	LinkedQueue(){
		line = new ListLinked();
		w = new WindowLinked();
	}
	
	/**
	 * Returns first item in the queue,
	 * the item that has been present for 
	 * the longest time.
	 */
	@Override
	public Object dequeue() throws Underflow {
		if(!line.isEmpty()){
			line.beforeFirst(w);
			line.next(w);
			return (Object) line.delete(w);
		}
		else throw new Underflow("Queue is Empty");
	}

	/**
	 * Inserts new item as the last item
	 */
	@Override
	public void enqueue(Object item) {
		line.afterLast(w);
		line.insertBefore(item, w);
	}
	
	/**
	 * Returns the head of the queue but does not
	 * delete it.
	 */
	@Override
	public Object examine() throws Underflow {
		if(!line.isEmpty()){
			line.beforeFirst(w);
			line.next(w);
			return (Object) line.examine(w);
		}
		else throw new Underflow("Queue is Empty");
	}
	
	/**
	 * Boolean check to see if Queue is empty.
	 */
	@Override
	public boolean isEmpty() {
		return line.isEmpty();
	}
}

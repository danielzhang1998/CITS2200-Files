import CITS2200.Link;
import CITS2200.OutOfBounds;
import CITS2200.WindowLinked;
import CITS2200.List;

public class ListLinked implements List {

	private Link beforeFirst;
	private Link afterLast;
	/*
	 * Constructor for Linked List,
	 * produces empty list with 2 sentinel cells
	 * for window object(s).
	 * position afterLast is initalised
	 * with null item and null pointer,
	 * beforeFirst has null items but is initally
	 * pointing to afterLast.
	 */
	public ListLinked(){
		afterLast = new Link(null,null);
		beforeFirst = new Link(null,afterLast);
	}
	/*
	 * Places the window after the last position on the list.
	 * @see CITS2200.List#afterLast(CITS2200.WindowLinked)
	 */
	public void afterLast(WindowLinked w) {
		w.link = afterLast;
	}
	/*
	 * Places the window before the first item in the list.
	 * @see CITS2200.List#beforeFirst(CITS2200.WindowLinked)
	 */
	public void beforeFirst(WindowLinked w) {
		w.link = beforeFirst;
	}
	/*
	 * Checks to see if the window is over the afterLast position.
	 * @see CITS2200.List#isAfterLast(CITS2200.WindowLinked)
	 */
	public boolean isAfterLast(WindowLinked w){
		return w.link == afterLast;
	}
	
	/*
	 * Checks to see if the window is over the beforeFirst position.
	 * @see CITS2200.List#isBeforeFirst(CITS2200.WindowLinked)
	 */
	public boolean isBeforeFirst(WindowLinked w) {
		return w.link == beforeFirst;
	}
	
	/*
	 * Moves the window object to the next link in the list.
	 * Throwing an exception if over the sentinel cell afterLast.
	 * @see CITS2200.List#next(CITS2200.WindowLinked)
	 */
	public void next(WindowLinked w) throws OutOfBounds {
		if(!isAfterLast(w)){
			w.link = w.link.successor;
		}
		else throw new OutOfBounds("Calling position after end of list.");
	}
	
	/*
	 * Moves the window object to the previous link in the list.
	 * Throws exception if over the sentinel cell beforeFirst.
	 * Does so in linear time by traversing over the cell with 
	 * helper links that are coupled.
	 * @see CITS2200.List#previous(CITS2200.WindowLinked)
	 */
	public void previous(WindowLinked w) throws OutOfBounds {
		if(!isBeforeFirst(w)){
			Link current = beforeFirst.successor;
			Link previous = beforeFirst;
			while(current != w.link){
				previous = current;
				current = current.successor;
			}
			w.link = previous;
		}
		else throw new OutOfBounds("Trying to find position before first item.");			
	}
	
	/*
	 * Returns the object that is in the link over which the
	 * window is over.  Cannot be done over a sentinel cell and
	 * throws an exception if it is.
	 * @see CITS2200.List#examine(CITS2200.WindowLinked)
	 */
	public Object examine(WindowLinked w) throws OutOfBounds {
		if(!isAfterLast(w) && !isBeforeFirst(w)){
			return w.link.item;
		}
		else throw new OutOfBounds("Trying to call object outside of list.");
	}
	
	/*
	 * Checks to see if the list is empty by checking if the
	 * only pointer present is between sentinel cells
	 * @see CITS2200.List#isEmpty()
	 */
	public boolean isEmpty() {
		return beforeFirst.successor.equals(afterLast);
	}
	
	/*
	 * Inserts a new link with an item after the windowed link.
	 * Reorganises pointers so that the windowed link points to the
	 * new link and the new link points to the prior successor link.
	 * 
	 * Throws an exception if over the sentinel afterLast so as not
	 * to insert links outside of the defined data structure. 
	 * @see CITS2200.List#insertAfter(java.lang.Object, CITS2200.WindowLinked)
	 */
	public void insertAfter(Object newItem, WindowLinked w)
			throws OutOfBounds {
		if(!isAfterLast(w)){
			Link newLink = new Link(newItem,w.link.successor);
			w.link.successor = newLink;
			if(isBeforeFirst(w)){
				beforeFirst.successor = newLink;
			}
		}
		else throw new OutOfBounds("Cannot insert after the list.");
	}
	
	/*
	 * Inserts an item before the window in constant time.
	 * Does so by copying the windowed link and linking to the copy.
	 * If the window was over the sentinel afterLast, afterLast is assigned
	 * this copy.
	 * The windowed link has it's item overwritten by the new item.
	 * Move the window forward and it appears as if a new link has been inserted
	 * before without traversing the data structure.
	 * @see CITS2200.List#insertBefore(java.lang.Object, CITS2200.WindowLinked)
	 */
	public void insertBefore(Object newItem, WindowLinked w)
			throws OutOfBounds {
		if(!isBeforeFirst(w)){
			//create copy of link that is windowed and link to it
			w.link.successor = new Link(w.link.item,w.link.successor);
			//in the case of afterLast position, the new afterLast must
			//be assigned.
			if(isAfterLast(w)){
				afterLast = w.link.successor;
			}
			//place new item into windowed link
			w.link.item = newItem;
			//move window over the new link
			w.link = w.link.successor;
		}
		else throw new OutOfBounds("Cannot insert before list.");
	}
	
	/*
	 * Deletes the link that the window is over unless it is over
	 * a sentinel cell, at which point an exception is thrown.
	 * Returns the item that was in the deleted link.
	 * Does so by just overwriting the deleted item wit the successive
	 * link's item and pointer.
	 * If the windowed link was initially the last item in the list
	 * the windowed link is now assigned to being afterLast in addition
	 * to inheriting the null item and pointer.
	 * @see CITS2200.List#delete(CITS2200.WindowLinked)
	 */
	public Object delete(WindowLinked w) throws OutOfBounds {
		if(!isAfterLast(w) && !isBeforeFirst(w)){
			//store the item from the link being removed
			Object deletedItem = w.link.item;
			//copy item from the next link into the windowed one
			w.link.item = w.link.successor.item;
			if(w.link.successor == afterLast){
				afterLast = w.link;
			}
			//copy pointer from next link to the windowed link
			w.link.successor = w.link.successor.successor;
			return deletedItem;
		}
		else throw new OutOfBounds("Trying to delete outside of list.");
	}
	
	/*
	 * Replaces the item in the windowed link with a new item,
	 * returns the overwritten item however pointer structure is unchanged.
	 * @see CITS2200.List#replace(java.lang.Object, CITS2200.WindowLinked)
	 */
	public Object replace(Object newItem, WindowLinked w)
			throws OutOfBounds {
		if(!isBeforeFirst(w) && !isAfterLast(w)){
			Object oldItem = w.link.item;
			w.link.item = newItem;
			return oldItem;
		}
		else throw new OutOfBounds("Trying to replace at position outside of List.");
	}
	
}
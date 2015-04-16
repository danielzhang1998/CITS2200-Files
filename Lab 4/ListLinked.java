import CITS2200.Link;
import CITS2200.OutOfBounds;
import CITS2200.WindowLinked;
import CITS2200.List;

public class ListLinked implements List {
	
	private Link head;
	private Link tail;
	int listSize;
	private WindowLinked w;

	/*
	 * Constructor for Linked List,
	 * head has null item and null link,
	 * tail has null item and links to head,
	 * head.successor is set to point to 
	 * tail.
	 * There are no items so size is 0.
	 */
	private Link head;
	private Link tail;
	private Link beforeFirst;
	private Link afterLast;
	int listSize;
	private WindowLinked w;
	
	public ListLinked(){
		//Make beforeFirst and afterLast positions
		beforeFirst = new Link(null,null);
		afterLast = new Link(null,null);
		beforeFirst.successor = head;
		tail.successor = afterLast;
		
		//initialise LinkedList
		head = new Link(null,null);
		tail = new Link(null, head);
		head.successor = tail;
		int listSize = 0;
		
		
	}
	
	@Override
	public void afterLast(WindowLinked w) {
		w.link = afterLast;
	}
	@Override
	public void beforeFirst(WindowLinked w) {
		w.link = beforeFirst;
	}
	@Override
	public boolean isAfterLast(WindowLinked w){
		return w.link.equals(afterLast);
	}
	@Override
	public boolean isBeforeFirst(WindowLinked w) {
		return w.link.equals(beforeFirst);
	}
	@Override
	public void next(WindowLinked w) throws OutOfBounds {
		if(!isAfterLast(w)){
			w.link = w.link.successor;
		}
		else throw new OutOfBounds("Calling next outside of list.");
	}
	@Override
	public void previous(WindowLinked w) throws OutOfBounds {
		if(!isBeforeFirst(w)){
			for(Link p = beforeFirst.successor; p.successor.equals(w.link); p=p.successor){
				w.link = p;
			}				
		}
		else throw new OutOfBounds("Trying to find previous before first item.");			
	}
	@Override
	public Object examine(WindowLinked w) throws OutOfBounds {
		if(!isAfterLast(w) && !isBeforeFirst(w)){
			return w.link.item;
		}
		else throw new OutOfBounds("Trying to call object outside of list.");
	}
	@Override
	public boolean isEmpty() {
		return listSize == 0;
		//return head == null && tail == null;
	}
	
	@Override
	public void insertAfter(Object newItem, WindowLinked w)
			throws OutOfBounds {
		if(!isAfterLast(w)){
			Link newLink = new Link(newItem,w.link.successor);
			w.link.successor = newLink;
			if(isBeforeFirst(w)){
				beforeFirst.successor = newLink;
				head = newLink;
			}
			listSize++;
		}
		else throw new OutOfBounds("Cannont insert outside of list.");
	}

	@Override
	public void insertBefore(Object newItem, WindowLinked w)
			throws OutOfBounds {
		if(!isBeforeFirst(w)){
			//create copy of link that is windowed and link to it
			w.link.successor = new Link(w.link.item,w.link.successor);
			//in the case of afterLast position, the new afterLast must
			//be assigned.
			if(isAfterLast(w)){
				afterLast = w.link.successor;
				tail = w.link;
			}
			//place new item into windowed link
			w.link.item = newItem;
			//move window over the new link
			w.link = w.link.successor;
			listSize++;
		}
		else throw new OutOfBounds("Cannot insert outside of list.");
	}
	
	//DO THE DELETING-------------------------------------------------
	@Override
	public Object delete(WindowLinked w) throws OutOfBounds {
		if(!isAfterLast(w) && !isBeforeFirst(w)){
			//copy item from the next link into the windowed one
			w.link.item = w.link.successor.item;
			w.link = w.link.successor.successor;
			//set the successor to null
			w.link.successor = null;
			listSize--;
			return w.link.item;
		}
		else throw new OutOfBounds("Trying to delete outside of list.");
	}
	
	@Override
	public Object replace(Object arg0, WindowLinked arg1)
			throws OutOfBounds {
		// TODO Auto-generated method stub
		return null;
	}public ListLinked() {
		head = new Link(null, null);
		tail = new Link(null, head);
		head.successor = tail;
		listSize = 0;
	}
	
	
	
	public void insertAfter(WindowLinked w, Object a){
		Link newLink = new Link(a, w.link.successor);
		w.link = new Link(w.link.item,newLink);
	}
	
	public void insertBefore(WindowLinked w, Object a) throws OutOfBounds{
		if(!isBeforeFirst(w)){
			w.link.successor = new Link(w.link.item, w.link.successor);
			if(isAfterLast(w)){
				after = w.link.successor;
			}
			w.link.item = a;
			w.link = w.link.successor;
		}
		else throw new OutOfBounds("Inserting before start of list");
	}
	
	//can't be done before first or after last
	public void delete(WindowLinked w) throws OutOfBounds{
		if(!isBeforeFirst(w) || !isAfterLast(w)){
			w.link.item = w.link.successor.item;
			w.link.successor = w.link.successor.successor;
		}
		else throw new OutOfBounds("Deleting outside of list.");
	}

	public void previous(WindowLinked w) throws OutOfBounds{
		if(!isBeforeFirst(w)){
			Link current = before.successor;
			Link previous = before;
			while(current != w.link){
				previous = current;
				current = current.successor;
			}
			//after while loops has finished current = w
			//previous is the link that we are after, move w.link to previous
			w.link = previous;
		}
		else throw new OutOfBounds ("Calling previous before start of list.");
	}
	//this type of walking on the list is called link coupling, it is linear.

	@Override
	public Object delete(WindowLinked arg0) throws OutOfBounds {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object examine(WindowLinked arg0) throws OutOfBounds {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insertAfter(Object arg0, WindowLinked arg1) throws OutOfBounds {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void insertBefore(Object arg0, WindowLinked arg1) throws OutOfBounds {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object replace(Object arg0, WindowLinked arg1) throws OutOfBounds {
		// TODO Auto-generated method stub
		return null;
	}

	
}

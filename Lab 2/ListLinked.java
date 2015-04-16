import CITS2200.Link;
import CITS2200.OutOfBounds;


public class ListLinked {
	
	private Link before;
	private Link after;

	public ListLinked() {
		after = new Link(null, null);
		before = new Link(null, after);
	}
	
	public boolean isEmpty(){
		return before.successor == after;
	}
	
	public void beforeFirst(WindowLinked w){
		w.link = before;
	}
	
	public void afterLast(WindowLinked w){
		w.link = after;
	}
	
	public boolean isAfterLast(WindowLinked w){
		return w.link.equals(after);
	}
	
	public boolean isBeforeFirst(WindowLinked w){
		return w.link.equals(before);
	}
	
	public void next(WindowLinked w){
		if(!isAfterLast(w)){
			w.link = w.link.successor;
		}
		else{
			throw new OutOfBounds("Calling next after list end.");
		}
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

	
}

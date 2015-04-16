import CITS2200.Link;
import CITS2200.Underflow;

class LinkedStack{
	
	Link first;
	
	//Constructor makes empty stack
	LinkedStack(){
		first = null;	//empty head assume pointing to null
	}
	
	//test whether stack is empty
	void insert(Object o){
		first = new Link(o,first);
		//head now is o has a tail first is the pointer
	}
	
	//Test whether stack is empty
	boolean isEmpty(){
		return first == null;
	}
	
	Object examine() throws Underflow{
		if(!isEmpty()){
			return first.item;
		}
		else throw new Underflow("Stack is Empty");
	}
	
	void delete(){
		if(!isEmpty()){
			first = first.successor;
		}
		else throw new Underflow("Stack is Empty");
	}
	
	public String toString(){
		Link cursor = first; //use a copy so as not to affect the original
		String s = "";
		while(cursor != null)
		{
			s = s + cursor.item;
			cursor = cursor.successor;
		}
		return s;
	}

}

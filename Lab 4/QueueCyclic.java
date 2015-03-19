import CITS2200.Queue;
import CITS2200.Underflow;


public class QueueCyclic implements Queue {
	
	private int count; //size of dequeue
	private int first; //position of the first char
	
	public void dequeue(){
		count = 0;
	}

	@Override
	public Object dequeue() throws Underflow {
		if(!isEmpty){
			
		}
		else throw new Underflow("Trying to dequeue from an empty queue.");
	}

	@Override
	public void enqueue(Object arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object examine() throws Underflow {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}
	

}

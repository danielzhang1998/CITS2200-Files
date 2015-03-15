
import CITS2200.Overflow;
import CITS2200.Stack;
import CITS2200.Underflow;

public class StackBlock implements Stack{
	
	private Object[] panCake;
	private int top;
	
	public StackBlock(int s){
		top = -1;
		panCake = new Object[s];
	}

	@Override
	public Object examine() throws Underflow {
		if(!isEmpty()){
			return panCake[top];
		}
		else throw new Underflow("Stack is empty!");
	}

	@Override
	public boolean isEmpty() {
		return top == -1;
	}
	
	public boolean isFull(){
		return top == panCake.length -1;
	}

	@Override
	public void push(Object o) throws Overflow {
		if(!isFull()){
			panCake[++top] = o;
		}
		else throw new Overflow("Stack is full!");
	}
	
	@Override
	public Object pop() throws Underflow {
		if(!isEmpty()){
			Object firstItem = panCake[top];
			top--;
			return firstItem;
		}
		else throw new Underflow("Stack is Empty");
	}
}

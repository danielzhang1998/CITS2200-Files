
public class crap {
	
	public static void main(String[] args){
		DequeCyclic a = new DequeCyclic(200);
		
			a.left = 5;
			a.right = 10;
	
		System.out.println(a.isEmpty());
		System.out.println(a.isFull());
		//System.out.println(arg0);
		a.pushLeft(25);
		System.out.println(a);
		a.pushRight("Quenton was double dipping");
		System.out.println(a);
		System.out.println(a.peekLeft());
		
	}
	}
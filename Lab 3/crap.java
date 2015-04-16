

public class crap {
	
	public static void main(String[] args){
		
		DequeCyclic a = new DequeCyclic(0);
		
		/**
		a.pushRight("o");
		System.out.println(a.peekRight());
		a.pushRight("d");
		System.out.println(a.peekRight());
		a.pushRight("n");
		System.out.println(a.peekRight());
		a.pushRight("a");
		System.out.println(a.peekRight());
		a.pushRight("N");
		System.out.println(a.peekRight());
		
		System.out.println(a.peekRight()+ "\n before POP");
		a.popRight();
		System.out.println(a.peekRight()+ "\n after POP");
		**/

		a.pushRight('f');
		a.popLeft();
		
		
		if(a.isEmpty()){
			System.out.println("isEmpty reporting empty");
			if(a.isFull()){
				System.out.println("isFull reporting full");
				}
			else{System.out.println("isFull reporting not full");}
			}
			else{
				System.out.println("isEmpty reporting not empty");
				if(a.isFull()){
					System.out.println("isFull reporting full");
					}
				else{System.out.println("isFull reporting not full");}
			}
	}
}
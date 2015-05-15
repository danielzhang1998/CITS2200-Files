
public class muckaround {
	
	private final static int D = 0;

	public static void main(String[] args) {
		RBTree<String> a = new RBTree<String>();
		
		a.insert("10");
		a.insert("20");
		a.insert("30");
		a.insert("40");
		a.insert("50");
		a.insert("60");
		
		a.printTree();
		
		a.remove("20");
		System.out.println("------node delete-------");
		a.printTree();
	}
}

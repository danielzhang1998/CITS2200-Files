import java.util.TreeSet;

import CITS2200.WindowLinked;


public class muckaround {
	
	private final static int D = 0;

	public static void main(String[] args) {
		
		RBTree<String> d = new RBTree<String>();
		
		for(Integer j = 0; j <11; j++){
			d.insert(j.toString());
		}
		
		d.remove("0");
		d.printTree();
		d.remove("1");
		
		/*for(Integer k = 0; k<11; k++){
			d.remove(k.toString());
		}
		*/
		
		
		
		/*for(Integer b = 0; b < 10; b++){
			d.remove(b.toString());
		}*/
		//System.out.println(d.find("0").toString());
		
		//d.add("10");
		
		//System.out.println(d.contains("9999"));
		//d.add("aaa");
		//d.add("bbb");
		//d.add("ccc");
		//System.out.println(d.predecessor("bbb"));
		//System.out.println(d.predecessor("ccc"));
		//System.out.println(d.predecessor("aaa"));
		//d.delete("aaa");
		//System.out.println(d.predecessor("aaa"));
		//System.out.println(d.predecessor("bbb"));
		//System.out.println(d.predecessor("ccc"));
		//System.out.println(d.predecessor("mario"));
		
		
		System.out.println("-------------------------");
		
		
	}
}

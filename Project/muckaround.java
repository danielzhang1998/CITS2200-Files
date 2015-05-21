import java.util.Iterator;
import java.util.TreeSet;

import CITS2200.ItemNotFound;
import CITS2200.WindowLinked;


public class muckaround {
	
	private final static int D = 0;

	public static void main(String[] args) {

		Dictionary<String> d = new Dictionary<String>();
		

		
		d.add("zz");
		d.add("ww");
		d.add("bb");
		d.add("ss");
		d.add("aa");
		d.add("cc");
		d.add("aa");
		
		d.contains(null);
		d.predecessor("ww");
		
		System.out.println(d.getLogString());
		
		Iterator<String> it = d.iterator();
		it.hasNext();
		it.hasNext();
		
		
		
		
		
		/*for(Integer j = 0; j <10000; j++){
			e.add(j.toString());
		}
			
		for(Integer k = 0; k<10000; k++){
			if(!e.delete(k.toString())){
				System.out.println("broken");
				break;
			}
		}*/
		
		
		
		
		
		
		
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
		
		
		//System.out.println("-------------------------");
		
		
	
}}

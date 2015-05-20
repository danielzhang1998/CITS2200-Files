import java.util.TreeSet;

import CITS2200.ItemNotFound;
import CITS2200.Iterator;
import CITS2200.WindowLinked;


public class muckaround {
	
	private final static int D = 0;

	public static void main(String[] args) {

		Dictionary<String> d = new Dictionary<String>();
		//Iterator<String> di = d.iterator();
		//System.out.println(di.next().toString());
		
		try{
			Iterator<String> d1 = d.iterator();
			System.out.println(d1.next().toString());
		}catch(ItemNotFound expectedException){
			System.out.println("caught");
		}

		d.add("ff");
		d.add("bb");
		d.add("cc");
		d.add("dd");
		d.add("ee");
		d.add("aa");
		d.add("zz");
		d.add("ww");
		d.add("bb");
		d.add("ss");
		d.add("aa");
		d.add("cc");
		d.add("aa");
		
		//System.out.println(d.max());
				
		Iterator<String>di = d.iterator("zz");
			while(di.hasNext()){
			System.out.println(di.next().toString());
		}
		/*
		System.out.println("----------");
		d.add("zz");
		d.add("ww");
		d.add("bb");
		d.add("ss");
		d.add("aa");
		d.add("cc");
		d.add("aa");
		Iterator<String> startFromC = d.iterator("aa");
		while(startFromC.hasNext()){
			System.out.println(startFromC.next().toString());
		}*/
		
		
		
		
		
		
		
		
		
		
		
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

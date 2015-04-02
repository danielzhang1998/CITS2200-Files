import CITS2200.BinaryTree;
import CITS2200.Iterator;

/**
 * @author Pradyumn
 * Extends the abstract class Binary Tree
 *
 */
public class BinTree extends BinaryTree<Object>{
	

	public BinTree() {
		super();
	}
	
	public BinTree(Object item, BinaryTree<Object> b1, BinaryTree<Object> b2){
		super(item,b1,b2);
	}
	
	//Returns true if left child present
	public boolean hasLeft(){
		return this.getLeft() != null;
	}
	
	//Returns true if left child present
	public boolean hasRight(){
		return this.getRight() != null;
	}
	
	@Override
	public boolean equals(Object bT) {
		
		//checks where abstract types match
		if(!(bT instanceof BinaryTree<?>)){
			return false;
		}
		
		//casts object as subclass BinTree
		BinTree trial = (BinTree) bT;
		
		//checks if left and right nodes are similar
		//in external and internal properties
		if(hasLeft() != trial.hasLeft() || hasRight() != trial.hasRight()){
			return false;
		}
		
		//checks if items are NOT equal at the current node
		if(!this.getItem().equals(trial.getItem())){
			return false;
		}
		
		//checks if left has child and if both left
		//items are NOT equal
		if(hasLeft() && !this.getLeft().equals(trial.getLeft())){
			return false;
		}
		
		//checks if right is internal node and if both right
		//items are NOT equal
		if(hasRight() && !this.getRight().equals(trial.getRight())){
			return false;
		}
		
		//passes true when if statements pass true
		return true;
		
	}
	

	@Override
	public Iterator<Object> iterator() {
		// TODO Auto-generated method stub
		return null;
	}
	

}

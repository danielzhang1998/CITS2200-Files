import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

import CITS2200.BinaryTree;
import CITS2200.Iterator;

/**
 * @author Pradyumn
 * Extends the abstract class Binary Tree
 * @param <E>
 *
 */
public class BinTree<E> extends BinaryTree<E>{

	public BinTree() {
		super();
	}
	
	public BinTree(E item, BinaryTree<E> b1, BinaryTree<E> b2){
		super(item,b1,b2);
	}
	
	BinaryTree<E> root = this;
	
	//Returns true if left child present
	public boolean hasLeft(){
		return !this.getLeft().isEmpty();
	}
	
	//Returns true if left child present
	public boolean hasRight(){
		return !this.getRight().isEmpty();
	}
	
	/**
	 * Checks if one Binary Tree is equal in all
	 * values and node locations to this binary tree
	 */
	@Override
	public boolean equals(Object bT) {
		
		/**
		 * checks where abstract types match
		 * for root check 
		**/
		if(!(bT instanceof BinaryTree<?>)){
			return false;
		}
	
		/**
		 * casts object as subclass BinTree
		 */
		BinTree<E> trial = new BinTree<E>();
		trial = (BinTree<E>) bT;
		
		/*
		 * checks if node does NOT have similar 
		 * external and internal properties
		 */
		if(hasLeft() != trial.hasLeft() || hasRight() != trial.hasRight()){
			return false;
		}
		
		/**
		 * Check if item objects are NOT equal
		 */
		if(!this.getItem().equals(trial.getItem())){
			return false;
		}
		
		/*
		 * Checks if left has child and recursively calls this
		 * method to continue checks along the left tree
		 */
		if(hasLeft() && !this.getLeft().equals(trial.getLeft())){
			return false;
		}
		
		/*
		 * Checks if right has child and recursively calls this
		 * method to continue checks along the right tree
		 */
		if(hasRight() && !this.getRight().equals(trial.getRight())){
			return false;
		}
		
		/**
		 * Passes true when if statements pass true
		 */
		return true;
		
	}
	
	/**
	 * Returns an iterator that returns E types.
	 * It will be used to traverse the BinaryTree
	 */
	@Override
	public Iterator<E> iterator() {
		
		final Queue<BinaryTree<E>> treeBuffer = new LinkedList<BinaryTree<E>>();
		
		if (root != null){
			treeBuffer.add(root);
		}
		
		
		Iterator<E> treeWalk = new Iterator<E>(){
			
			@Override
			public boolean hasNext(){
				return !treeBuffer.isEmpty();
			}
			
			@Override
			public E next(){
				
				if(!hasNext()){
					throw new NoSuchElementException("No more items!");
				}
				
				//pointer is removed from front of queue
				//and into temporary variable
				BinaryTree<E> focus = treeBuffer.poll();
				
				//adds left child pointer to queue if present
				if(!focus.getLeft().isEmpty()){
					treeBuffer.add(focus.getLeft());
				}
				
				//adds right child pointer to queue if present
				if(!focus.getRight().isEmpty()){
					treeBuffer.add(focus.getRight());
				}
				
				//returns the actual object from the queue
				return focus.getItem();
			}
		
		};
		return treeWalk;
		}
}
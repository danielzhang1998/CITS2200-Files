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
		BinaryTree<E> trial = (BinaryTree<E>) bT;
		
		/*
		 * checks if node does NOT have similar 
		 * external and internal properties
		 */
		if(!hasLeft() != trial.getLeft().isEmpty() || 
				!hasRight() != trial.getRight().isEmpty()){
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
		
		return new InOrderIterator();
	}
	
	
	/**
	 * Sub Class TreeIterator
	 * Returns E from a tree which it
	 * iterates over.
	 * @author Pradyumn
	 *
	 * @param <E>
	 */
	public class InOrderIterator implements Iterator<E>{
		
		//Initialising a Queue of appropriate type
		private Queue<BinaryTree<E>> treeBuffer;
		//Initialising a BinaryTree<E> object
		
		//If tree root is not empty adds pointer to Queue
		public InOrderIterator(){
		treeBuffer = new LinkedList<BinaryTree<E>>();
			if(root != null){
				//add pointer to end of queue
				treeBuffer.add(root);
			}
		}
		
		//Checks if queue is populated
		@Override
		public boolean hasNext() {
			return !treeBuffer.isEmpty();
		}
		
		//@returns next E extracted form list
		@Override
		public E next(){
			//End scenario of iteration
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
		
		public void remove(){
			throw new UnsupportedOperationException("Invalid operation can't remove.");
		}
	}
}
	
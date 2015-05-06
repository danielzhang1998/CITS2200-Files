import CITS2200.DuplicateItem;
import CITS2200.ItemNotFound;

/**
 * 
 */

/**
 * @author Pradyumn
 *
 */
public class BinarySearchTree<E extends Comparable<? super E>> {
	
	protected BinaryNode<E> root;
	
	public BinarySearchTree(){
		root = null;
	}
	
	public void insert(E item){
		root = insert(item, root);
	}
	
	public void remove(E item){
		root = remove(item, root);
	}
	
	public void removeMin(E item){
		root = removeMin(root);
	}
	
	public E findMin(){
		return elementAt(findMin(root));
	}
	
	public E findMax(){
		return elementAt(findMax(root));
	}
	
	public E find(E item){
		return elementAt(find(item,root));
	}
	
	public void makeEmpty(){
		root = null;
	}
	
	public boolean isEmpty(){
		return root == null;
	}
	
	private E elementAt(BinaryNode<E> node){
		return node == null ? null : node.item;
	}
	
	private BinaryNode<E> find(E item, BinaryNode<E> node){
		while(node != null){
			if(item.compareTo(node.item) < 0){
				node = node.leftChild;
			}
			else if (item.compareTo(node.item) > 0){
				node = node.rightChild;
			}
			else
				return node;	//match
		}
		throw new ItemNotFound("Item not found.");
	}
	
	protected BinaryNode<E> findMin(BinaryNode<E> node){
		if( node != null){
			while(node.leftChild!= null){
				node = node.leftChild;
			}
		}
		return node;
	}
	
	private BinaryNode<E> findMax(BinaryNode<E> node){
		if( node != null){
			while(node.rightChild!= null){
				node = node.rightChild;
			}
		}
		return node;
	}
	
	private BinaryNode<E> insert(E item, BinaryNode<E> node){
		if(node == null){
			node = new BinaryNode<E>(item);
		}
		else if(item.compareTo(node.item)<0){
			node.leftChild = insert(item, node.leftChild);
		}
		else if(item.compareTo(node.item)>0){
			node.rightChild = insert(item, node.rightChild);
		}
		else throw new DuplicateItem(item.toString());
		return node;
	}
	
	protected BinaryNode<E> removeMin(BinaryNode<E> node){
		if(node == null){
			throw new ItemNotFound("Item not found!");
		}
		else if(node.leftChild != null){
			node.leftChild = removeMin(node.leftChild);
			return node;
		}
		else
			return node.rightChild;
	}
	
	private BinaryNode<E> remove(E item, BinaryNode<E> node){
		if(node == null){
			throw new ItemNotFound("Item not found!");
		}
		if(item.compareTo(node.item) < 0){
			node.leftChild = remove(item, node.leftChild);
		}
		else if(item.compareTo(node.item) > 0){
			node.rightChild = remove(item, node.rightChild);
		}
		else if(node.leftChild != null && node.rightChild != null){	 // 2 children
			node.item = findMin(node.rightChild).item;
			node.rightChild = removeMin(node.rightChild);
		}
		else
			node = (node.leftChild != null) ? node.leftChild : node.rightChild;
		return node;
	}
	
	
	

}

class BinaryNode<E>{
	
	E item;	//object being held by node
	BinaryNode<E> leftChild;	//link to left
	BinaryNode<E> rightChild;	// link to right
	
	BinaryNode(E content){
		item = content;
		leftChild = rightChild = null;
	}
}
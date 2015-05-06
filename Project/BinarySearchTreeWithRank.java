import CITS2200.DuplicateItem;
import CITS2200.ItemNotFound;



public class BinarySearchTreeWithRank<E extends Comparable<? super E>> extends BinarySearchTree<E> {


	private static class BinaryNodeWithSize<E> extends BinaryNode<E>{
		int size;
		BinaryNodeWithSize(E content) {
			super(content);
			size = 0;
		}
	}
	
	public E findKth(int k){
		return findKth(k,root).item;
	}
	
	// k is between 1 and size of subtree
	private BinaryNode<E> findKth(int k, BinaryNode<E> node){
		if(node == null) throw new IllegalArgumentException();
		int leftSize = (node.leftChild != null) ?
				((BinaryNodeWithSize<E>) node.leftChild).size : 0;
		if(k <= leftSize){
			return findKth(k, node.leftChild);
		}
		if(k == leftSize + 1){
			return node;
		}
		return findKth(k - leftSize -1, node.rightChild);
	}
	
	private BinaryNode<E> insert(E item, BinaryNode<E> node){
		BinaryNodeWithSize<E> nodeWithSize = (BinaryNodeWithSize<E>) node;
		
		if(nodeWithSize == null){
			node = new BinaryNodeWithSize<E>(item);
		}
		else if(item.compareTo(nodeWithSize.item) < 0){
			nodeWithSize.leftChild = insert(item,nodeWithSize.leftChild);
		}
		else if(item.compareTo(nodeWithSize.item) > 0){
			nodeWithSize.rightChild = insert(item,nodeWithSize.rightChild);	
		}
		else
			throw new DuplicateItem(item.toString());
		++nodeWithSize.size;
		return node;
	}
	
	private BinaryNode<E> remove(E item, BinaryNode<E> node){
		BinaryNodeWithSize<E> nodeWithSize = (BinaryNodeWithSize<E>) node;
		
		if(nodeWithSize == null){
			throw new ItemNotFound("Item Not Found");
		}
		if(item.compareTo(nodeWithSize.item) < 0){
			nodeWithSize.leftChild = remove(item, nodeWithSize.leftChild);
		}
		else if(item.compareTo(nodeWithSize.item) > 0){
			nodeWithSize.rightChild = remove(item, nodeWithSize.rightChild);
		}
		else if(nodeWithSize.leftChild != null && nodeWithSize.rightChild != null){	 // 2 children
			nodeWithSize.item = findMin(nodeWithSize.rightChild).item;
			nodeWithSize.rightChild = removeMin(nodeWithSize.rightChild);
		}
		else
			return (nodeWithSize.leftChild != null) ? nodeWithSize.leftChild : nodeWithSize.rightChild;
		
		nodeWithSize.size--;
		return nodeWithSize;
	}
	
	private BinaryNode<E> removeMin(E item, BinaryNode<E> node){
		BinaryNodeWithSize<E> nodeWithSize = (BinaryNodeWithSize<E>) node;
		
		if(nodeWithSize == null){
			throw new ItemNotFound("Item Not Found");
		}
		if(nodeWithSize.leftChild == null){
			return nodeWithSize.rightChild;
		}
		nodeWithSize.leftChild = removeMin(nodeWithSize.leftChild);
		nodeWithSize.size--;
		return nodeWithSize;
	}
}

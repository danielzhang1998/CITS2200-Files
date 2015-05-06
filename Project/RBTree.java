import CITS2200.DuplicateItem;

/**
 * 
 */

public class RBTree<E extends Comparable<? super E>>{
	
	// Global static integers that represent colour state
	public static final int BLACK = 1;
	public static final int RED = 0;
	// Sentinel nodes
	private RBnode<E> beforeRoot;	// Parent of root.
	private RBnode<E> ghostNode;	// All nulls point to this node
	// Helper nodes
	private RBnode<E> currentNode;	// Assists find, insert
	private RBnode<E> parentNode;	// Assists insert
	private RBnode<E> grandNode;	// Assists insert
	private RBnode<E> greatNode;	// Assists insert
	
	/**
	 * Class constructor.
	 * Initiates all pointers that would usually be nulls
	 * to ghostNode.
	 */
	public RBTree(){
		ghostNode = new RBnode<E>(null, ghostNode, ghostNode);
		//ghostNode.left = ghostNode.right = ghostNode;
		beforeRoot = new RBnode<E>(null, ghostNode, ghostNode);
		//beforeRoot.left = beforeRoot.right = ghostNode;
	}
	
	/**
	 * Inserts item at the correct point in the tree.
	 * Checks for duplicate item, and controls tree structure
	 * to fit Red Black Tree rules.
	 * As sentinel beforeRoot is possibly equal to the
	 * currentNode use compare method for comparison 
	 * instead of compareTo.
	 * @param freshItem	Item/object to be inserted
	 */
	public void insert(E freshItem){
		// Grand and great nodes used by fixItem method.
		currentNode = parentNode = grandNode = beforeRoot;
		// Assigning freshItem to ghostNode.data, catches loop
		ghostNode.data = freshItem;	// ghostNode is a boundary
		// End when freshItem is inserted into currentNode
		while(compare(freshItem, currentNode) != 0){
			greatNode = grandNode;
			grandNode = parentNode;
			parentNode = currentNode;
			// If freshItem is lower go left else go right
			currentNode = compare(freshItem,currentNode) < 0 ?
					currentNode.left : currentNode.right;
			// Check for two red children while iterating down tree
			// Avoids case with red parent and red uncle plus
			// moving back up tree to swap colours appropriately
			if(currentNode.left.colour == RED && currentNode.right.colour == RED){
				fixTree(freshItem);
			}
		}
		// Fail if duplicate item
		if(currentNode != ghostNode)
			throw new DuplicateItem(freshItem.toString());
		// Ready currentNode to be inserted with freshItem.
		// Loop will halt at next iteration.
		currentNode = new RBnode<E>(freshItem, ghostNode, ghostNode);
		// Connect parent, 
		// if less than parent add left else add right
		if(compare(freshItem, parentNode)<0)
			parentNode.left = currentNode;
		else
			parentNode.right = currentNode;
		// Fix tree to make sure RBTree rules followed
		fixTree(freshItem);
	}
	
	public void remove(E item){
		// TODO
	}
	
	public E findMin(){
		return null;
		//	TODO
	}
	
	public E findMax(){
		return null;
		// TODO
	}
	
	/**
	 * Find method iterates through from root to ghostNode
	 * checking for target item. The ghostNode is temporarily
	 * assigned the target value so that while loop can not
	 * run indefinitely, also makes finding target easy.
	 * @param target	The object being searched for.
	 * @return			null if target not found or the
	 * 					target item.
	 */
	public E find(E target){
		ghostNode.data = target;		// ghostNode is boundary
		currentNode = beforeRoot.right;	// currentNode is root
		while(true){
			if(target.compareTo(currentNode.data)<0){
				currentNode = currentNode.left;
			}
			else if(target.compareTo(currentNode.data)>0){
				currentNode = currentNode.right;
			}
			else if(currentNode != ghostNode){
				return currentNode.data;
			}
			else 
				return null;
		}
	}
	
	/**
	 * Efficient method to empty the tree, the only
	 * link that could contain further pointers now
	 * points to the end sentinel cell.
	 */
	public void makeEmpty(){
		beforeRoot.right = ghostNode;
	}
	
	/**
	 * Checks to see if RBTree is empty.
	 * @return	true if beforeRoot is the exact same as
	 * ghostNode, else there are other pointers or data
	 * present.
	 */
	public boolean isEmpty(){
		return beforeRoot == ghostNode;
	}
	
	/**
	 * Public method for printing the entire data
	 * structure, starts from sentinel node.
	 */
	public void printTree(){
		printTree(beforeRoot.right);
	}
	
	/**
	 * The internal class method of printing data
	 * held by a subtree sorted in order.
	 * Terminates if tree is the ghostNode.
	 * @param tree	Starting node from which to print.
	 */
	private void printTree(RBnode<E> tree){
		if(tree != ghostNode){
			printTree(tree.left);
			System.out.println(tree.data);
			printTree(tree.right);
		}
	}
	
	/**
	 * Helper method for comparisons when a node could
	 * be equal to the sentinel cell beforeRoot
	 * @param currentItem	Object being compared to node's
	 * 						object.
	 * @param tree		Node with object being compared to item
	 * @return	Returns 1 if node is sentinel to move from
	 * 			sentinel to root.
	 * 			If not sentinel returns normal comparison. 
	 * */
	private final int compare(E currentItem, RBnode<E> tree){
		if(tree == beforeRoot){
			return 1;
		}
		else{
			return currentItem.compareTo(tree.data);
		}
		
	}
	
	/**
	 * Method enforces the rules of the Red Black Tree
	 * If parent is black only performs a colour swap and
	 * reinforces root colour being black.
	 * However if the parent is red, checks are performed
	 * to decide whether to do a single or double swap and
	 * change colour of changing node to black.
	 * @param currentItem	value used to check if new insertion
	 * requires inner single or double rotations.
	 */
	private void fixTree(E currentItem){
		// Swap colours between children and current
		currentNode.colour = RED;
		currentNode.left.colour = BLACK;
		currentNode.right.colour = BLACK;
		
		if(parentNode.colour == RED){	// if parent has red child rotate
			grandNode.colour = RED;
			// if inner case (inner left or right) start double rotate
			if((compare(currentItem, grandNode)<0) != (compare(currentItem,parentNode)<0)){
				parentNode = rotate(currentItem, grandNode);
			}
			currentNode = rotate(currentItem, greatNode);	// single rotate
			currentNode.colour = BLACK;
		}
		beforeRoot.right.colour = BLACK;	// Root must always be black
	}
	
	/**
	 * Method that can be used for a single or double rotation.
	 * Addresses all 4 cases of rotations possibly required.
	 * 
	 * @param currentItem	item/object currently focused passed from fixTree
	 * @param ancestor	grandparent or great grandparent passed
	 * 					from fixTree
	 * @return	returns the root of rotated sub tree
	 */
	private RBnode<E> rotate(E currentItem, RBnode<E> ancestor){
		if(compare(currentItem, ancestor)<0){	// left of grand or great
			return ancestor.left = compare(currentItem, ancestor.left) < 0 ?
					rotateRight(ancestor.left) :		// left - left
						rotateLeft(ancestor.left);	// left - right
		}
		else{									// right of grand or great
			return ancestor.right = compare(currentItem, ancestor.right) < 0 ?
					rotateRight(ancestor.right) :	// right - left
						rotateLeft(ancestor.right);	// right - right
		}		
	}
	
	/**
	 * Helper method for rotate, rotates current parent's left
	 * child to be new root of sub tree, makes the parent the new
	 * right child of the subtree.
	 * @param parent	Node passed to rotate right
	 * @return	returns the new parent of the subtree that
	 * 			was rotated right.
	 */
	private static <E> RBnode<E> rotateRight(RBnode<E> parent){
		RBnode<E>leftChild = parent.left;
		parent.left = leftChild.right;
		leftChild.right = parent;
		return leftChild;
	}
	
	/**
	 * Helper method for rotate, rotates current parent's right
	 * child to be new root of sub tree, makes the parent the new
	 * left child of the subtree.
	 * @param parent	Node passed to rotate left
	 * @return	returns the new parent of the subtree that
	 * 			was rotated left.
	 */
	private static <E> RBnode<E> rotateLeft(RBnode<E> parent){
		RBnode<E>rightChild = parent.right;
		parent.right = rightChild.left;
		rightChild.left = parent;
		return rightChild;
	}


	/**
	 * 	The base Red Black Node structure that forms the
	 * 	building blocks of the Red Black Tree.
	 * 	Information extra to a Binary Search Tree is
	 * 	colour data.
	 * 	@author Pradyumn
	 */
	private static class RBnode<E>{
		
		E data;
		RBnode<E> left;
		RBnode<E> right;
		int colour;
		
		/**
		 * 
		 * @param item			Data to be held by the node.
		 * @param leftChild		The left subtree reference
		 * @param rightChild	The right subtree reference.
		 */
		RBnode(E item, RBnode<E> leftChild, RBnode<E> rightChild){
			data = item;			// Data in the node
			left = leftChild;		// left child
			right = rightChild;		// right child
			colour = RBTree.BLACK;	// colour
		}
		
		/**
		 * Class Constructor no children
		 * @param item	Data to be held by the node
		 */
		RBnode(E item){
			this(item, null, null);
		}
	}
}
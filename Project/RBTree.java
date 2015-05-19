import CITS2200.DuplicateItem;
import CITS2200.Underflow;
import CITS2200.ItemNotFound;

/**
 * 
 */

public class RBTree<E extends Comparable<? super E>>{
	
	// Global static integers that represent colour state
	private static final int BLACK = 1;
	private static final int RED = 0;
	// Sentinel nodes
	private RBnode<E> beforeRoot;	// Parent of root.
	private RBnode<E> ghostNode;	// Replaces references to null
	// Helper nodes
	private RBnode<E> currentNode;	// Assists find, insert
	private RBnode<E> parentNode;	// Assists insert and remove
	private RBnode<E> grandNode;	// Assists insert and remove
	private RBnode<E> greatNode;	// Assists insert
	private RBnode<E> siblingNode;	// Assists remove
	
	/**
	 * Class constructor.
	 * Initiates all pointers that would usually be nulls
	 * to ghostNode.
	 */
	public RBTree(){
		ghostNode = new RBnode<E>(null, ghostNode, ghostNode);
		ghostNode.left = ghostNode.right = ghostNode;
		beforeRoot = new RBnode<E>(null, ghostNode, ghostNode);
		beforeRoot.left = beforeRoot.right = ghostNode;
	}
	
	/**
	 * Inserts item at the correct point in the tree in a top
	 * down approach, no recursion required.
	 * Checks for duplicate item, and controls tree structure
	 * to fit Red Black Tree rules when fitting a new black node.
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
			// Avoids case with red parent and red uncle and
			// moving back up tree to swap colours appropriately
			if(currentNode.left.colour == RED && currentNode.right.colour == RED){
				fixTree(freshItem);	// Colour flip
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
	
	/**
	 * Helper method for Remove.
	 * Finds the next node in the path towards
	 * the object, is used in Remove to avoid
	 * moving outside of the Tree.
	 * @param target	Object to be deleted
	 * @return			Returns the next node to move to.
	 */
	public RBnode<E> nextNode(E target){
		return (compare(target,currentNode) < 0) ? 
				currentNode.left : currentNode.right;
	}
	
	/**
	 * Method for removing an object, like insert this method
	 * is complex due to the modification of the tree structure
	 * and having to make sure that the Red Black rules are 
	 * followed.
	 * @param target
	 */
	public void remove(E target){
		if (!isEmpty()){
			ghostNode.data = null;
		    RBnode<E> locked = null;
		    
		    // Set values for helper nodes
		    greatNode = grandNode = parentNode = currentNode = beforeRoot;
		    
		    // Search for the target node
		    while (nextNode(target) != ghostNode){
		    	// Update helpers at start of loop
		    	greatNode = grandNode;
		    	grandNode = parentNode;
		    	parentNode = currentNode;
		    	// Move current node down towards target
		    	currentNode = nextNode(target);
		    	// If new current node holds target data, save the node
		    	if(currentNode.data.equals(target)){
		    		locked = currentNode;
		    	}
		    	RBnode<E>nextNode = nextNode(target);
		    	if(currentNode.colour == BLACK && nextNode.colour == BLACK){
		    		RBnode<E>nextNodeSibling = siblingOf(nextNode,currentNode);
					// If nextNode's sibling is red:
		    		if(nextNodeSibling.colour == RED){
		    			// rotate red sibling of nextNode with currentNode
		    			// pushing down currentNode and the nextNode
		    			// reassign parentNode due to shuffle
		    			currentNode.colour = RED;
		    			nextNodeSibling.colour = BLACK;
		    			parentNode = rotate(nextNodeSibling.data,parentNode);
		    			
	    			}
		    		// If nextNode's sibling is black:
		    		else if(nextNodeSibling.colour == BLACK){
		    			siblingNode = siblingOf(currentNode, parentNode);
		    			// If currentNode's sibling exists:
		    			if(siblingNode != ghostNode){
		    				// Black Sibling Case 1:
		    				// If both siblingNode's children are black:
		    				if(siblingNode.left.colour == BLACK && 
		    						siblingNode.right.colour == BLACK){
		    					// Swap colours between levels
		    					parentNode.colour = BLACK;
		    					siblingNode.colour = RED;
		    					currentNode.colour = RED;
		    				}
		    				else{
		    					// Find Red nephew
		    					RBnode<E> redNephew = siblingNode.left.colour == RED ?
		    							siblingNode.left : siblingNode.right;
		    					// Black Sibling Case 2: Sibling's outer nephew is red				
		    					// If siblingNode and redNephew are in the same direction
		    					// from their respective parents, redNephew is outer.
		    					// Outer red nephew or 2 red nephews do a single rotation.
		    					if((redNephew.data.compareTo(siblingNode.data)<0) ==
		    							siblingNode.data.compareTo(parentNode.data)<0){
		    						parentNode.colour = BLACK;
		    						siblingNode.colour = RED;
		    						redNephew.colour = BLACK;
		    						currentNode.colour = RED;
		    						rotate(siblingNode.data, grandNode);
		    					}
		    					// Else is an inner case.
		    					// Black Sibling Case 3: Sibling's inner child is red
		    					// Double Rotation
		    					else{
		    						currentNode.colour = RED;
		    						parentNode.colour = BLACK;
		    						rotate(redNephew.data, parentNode);
		    						rotate(redNephew.data, grandNode);
		    					}
		    				}
		    			}
		    		}
		    	}
		    }
		    // Replace data of deleted node if found
		    if(locked != null){
		    	locked.data = currentNode.data;
		    	// If currentNode is right child assign its children 
		    	// as the  right of parentNode, else assign as left child 
		    	if(parentNode.right == currentNode){
		    		parentNode.right = (currentNode.left == ghostNode) 
		    				? currentNode.right : currentNode.left;
		    	}
		    	else{
		    		parentNode.left = (currentNode.left == ghostNode) 
		    				? currentNode.right : currentNode.left;
		    	}
		    }
		    else throw new ItemNotFound(target.toString());
		    // Root is fixed and made black again
		    beforeRoot.right.colour = BLACK;
		}
	}
	
	/**
	 * Helper function to attain sibling node.
	 * Requires calling method to know current parentNode.
	 * @param node		The node which is known
	 * @param parent	The known parent of node
	 * @return		The sibling of node.
	 */
	private RBnode<E> siblingOf(RBnode<E> node, RBnode<E> parent){
		return (node.data == parent.left.data) ? parent.right :
			parent.left;
	}
	
	/**
	 * Finds the minimum data item stored in the tree
	 * @return	Smallest data item.
	 */
	public E findMin(){
		if(isEmpty())
			throw new Underflow("Tree is Empty");
		RBnode<E> focus = beforeRoot.right;
		while(focus.left != ghostNode){
			focus = focus.left;
		}
		return focus.data;
	}
	
	/**
	 * Finds the maximum data item stored in the tree
	 * @return	Largest data item.
	 */
	public E findMax(){
		if(isEmpty())
			throw new Underflow("Tree is Empty");
		RBnode<E> focus = beforeRoot.right;
		while(focus.right != ghostNode){
			focus = focus.right;
		}
		return focus.data;
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
				throw new ItemNotFound(target.toString() + "in find");
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
		return beforeRoot.right == ghostNode;
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
			System.out.println("Root is " + beforeRoot.right.data.toString());
			printTree(tree.left);
			System.out.println(tree.data);
			System.out.print((tree.colour==0)?" red\n":" black\n");
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
		
		if(parentNode.colour == RED){	// if parent red need to rotate
			grandNode.colour = RED;
			// if inner case (left or right) start double rotate
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
	 * Addresses all 4 cases of rotations required.
	 * 
	 * @param currentItem	item/object currently focused passed from fixTree
	 * @param ancestor		Grandparent or great grandparent
	 * 						from fixTree or remove methods
	 * @return	returns 	root of rotated sub tree
	 */
	private RBnode<E> rotate(E currentItem, RBnode<E> ancestor){
		if(compare(currentItem, ancestor)<0){	// left of grand
			return ancestor.left = compare(currentItem, ancestor.left) < 0 ?
					rotateRight(ancestor.left) :		// left - left
						rotateLeft(ancestor.left);	// left - right
		}
		else{									// right of grand
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
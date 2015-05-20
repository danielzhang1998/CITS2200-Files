import CITS2200.IllegalValue;
import CITS2200.ItemNotFound;
import CITS2200.Iterator;
import CITS2200.OutOfBounds;


/**
 * A Dictionary data structure which is based upon the
 * functionality of a Red Black Tree.
 */


/**
 * @author Pradyumn Vij
 * Student Number: 21469477
 *
 */
public class Dictionary<E extends Comparable<E>>{
	
	// Global static integers that represent colour state
	private static final int BLACK = 1;
	private static final int RED = 0;
	// Modification count for fail fast iterator
	private int modCount = 0;
	// Sentinel nodes
	private Dnode<E> beforeRoot;	// Parent of root.
	private Dnode<E> ghostNode;		// All non-data points.
	// Helper nodes
	private Dnode<E> currentNode;	// Assists find, add
	private Dnode<E> parentNode;	// Assists add and delete
	private Dnode<E> grandNode;		// Assists add and delete
	private Dnode<E> greatNode;		// Assists add
	// String Builder for Log
	private StringBuilder dictionaryLog;
	
	/**
	* Class constructor.
	* Initiates all pointers that would usually be nulls
	* to ghostNode.
	*/
	public Dictionary(){
		ghostNode = new Dnode<E>(null, ghostNode, ghostNode);
		ghostNode.left = ghostNode.right = ghostNode;
		beforeRoot = new Dnode<E>(null, ghostNode, ghostNode);
		beforeRoot.left = beforeRoot.right = ghostNode;
		dictionaryLog = new StringBuilder("<Dictionary Constructued>\n");
	}

	/**
	* Checks to see whether the Dictionary is empty
	* Does so by checking if the only pointer is from a
	* sentinel start node to the node representing null.
	* @return	true if and only if the Dictionary is Empty
	* 			
	**/
	public boolean isEmpty(){
		return beforeRoot.right == ghostNode;
	}

	/**
	* Checks to see if an element is contained in the Dictionary.
	* Traverse and checks tree recursively via find helper method.
	* @param item 	Object being searched for.
	* @return true 	if and only if the Dictionary contains something equal to item.
	**/
	public boolean contains(E item){
		if(item == null)
			return false;
		return find(item) != null;
	}

	/**
	* Checks to see if an element has a predecessor in the dictionary by find min Item.
	* @return 	true if and only if there is an element strictly less than item in the Dictionary
	* @param 	item the item to be checked
	**/ 
	public boolean hasPredecessor(E item){
		if(!isEmpty() && contains(item)){
			return min().compareTo(item)<0;
		}
		else return false;
	}

	/**
	*Checks to see if an element has a successor in the dictionary by finding max Item.
	*@return true if and only if there is an element strictly greater than item in the Dictionary
	*@param item the item to be checked
	**/ 
	public boolean hasSuccessor(E item){
		if(!isEmpty() && contains(item)){
			return max().compareTo(item)>0;
		}
		else return false;
	}

	/**
	* Find the greatest element less than the specified element
	* Only 3 cases as Dictionary red-black nature self balances
	* 
	* @return the element strictly less than item in the Dictionary
	* @param item the item to be checked
	* @throws ItemNotFound if there is no lesser element.
	**/ 
	public E predecessor(E item) throws ItemNotFound{
		if(item == null || !hasPredecessor(item))
			throw new ItemNotFound("Item has no predecessor");
		Dnode<E> checkInfront = find(item);
		// Alternate
		// Case 1: left child exists
		if(checkInfront.left != ghostNode){
			// Return strict successor by finding the
			// maximum of the left sub-tree
			return max(checkInfront.left).data;
		}
		// Case 2: left child doesn't exist
		else{
			Dnode<E> predecessor = null; // behind traversal
			// Make root a starting point
			Dnode<E> ancestor = beforeRoot.right;
			// When ancestor == checkBehind, successor will be
			// on the correct node.
			while(ancestor != checkInfront){
				if(checkInfront.data.compareTo(ancestor.data)>0){
					predecessor = ancestor;
					ancestor = ancestor.right;
				}
				// Predecessor doesn't follow when moving the
				// wrong direction.
				else ancestor = ancestor.left;
			}
			return predecessor.data;
		}
	}

	/**
	*Find the least element greater than the specified element
	*@return the element strictly greater than item in the Dictionary
	*@param item the item to be checked
	*@throws ItemNotFound if there is no greater element.
	**/ 
	public E successor(E item) throws ItemNotFound{
		if(item == null || !hasSuccessor(item))
			throw new ItemNotFound("Item has no successor");
		Dnode<E> checkInfront = find(item);
		// Alternate
		// Case 1: right child exists
		if(checkInfront.right != ghostNode){
			// Return strict successor by finding the
			// minimum of the right sub-tree
			return min(checkInfront.right).data;
		}
		// Case 2: right child doesn't exist
		else{
			Dnode<E> successor = null; // ahead of traversal
			// Make root a starting point
			Dnode<E> ancestor = beforeRoot.right;
			// When ancestor == checkInfront, successor will be
			// on the correct node.
			while(ancestor != checkInfront){
				if(checkInfront.data.compareTo(ancestor.data)<0){
					successor = ancestor;
					ancestor = ancestor.left;
				}
				// Successor doesn't move unless in the
				// direction of the checkInfront.
				else ancestor = ancestor.right;
			}
			return successor.data;
		}
	}

	/**
	* Return the least item in the Dictionary
	* @return the least element in the Dictionary
	* @throws ItemNotFound if the Dictionary is empty.
	**/ 
	public E min() throws ItemNotFound{
		if(!isEmpty()){
			return min(beforeRoot.right).data;
		}
		else
			throw new ItemNotFound("Tree is Empty");
	}
	
	/**
	*Return the greatest element in the dictionary
	*@return the greatest element in the Dictionary
	*@throws ItemNotFound if the Dictionary is empty.
	**/ 
	public E max() throws ItemNotFound{
		if(!isEmpty()){
			return max(beforeRoot.right).data;
		}
		else
			throw new ItemNotFound("Tree is Empty");
	}
	
	/**
	* Adds a new element to the Dictionary 
	* If there is an equal element already in the table, or the 
	* item is null it returns false.
	* @param	freshItem the item to be added.
	* @return	true if the item is not null, and not already in 
	* the dictionary.
	**/
	public boolean add(E freshItem){
		if(freshItem == null){
			return false;
		}
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
				fixDictionary(freshItem);	// Colour flip
			}
		}
		// Fail if duplicate item
		if(currentNode != ghostNode) return false;
		// Ready currentNode to be inserted with freshItem.
		// Loop will halt at next iteration.
		currentNode = new Dnode<E>(freshItem, ghostNode, ghostNode);
		// Connect parent, 
		// if less than parent add left else add right
		if(compare(freshItem, parentNode)<0)
			parentNode.left = currentNode;
		else
			parentNode.right = currentNode;
		// Fix tree to make sure Red Black Tree rules followed
		fixDictionary(freshItem);
		modCount++;		// Record Dictionary modification
		return true;
	}

	/**
	* Deletes the specified element from the Dictionary if it is 
	* present.
	* @param target 	the element to be removed
	* @return true 		if the element was in the Dictionary and has 
	* now been removed. False otherwise.
	**/
	public boolean delete(E target){
		if (isEmpty() || target == null){
			return false;
		}
		ghostNode.data = null;
	    Dnode<E> locked = null;
	    
	    // Set values for helper nodes
	    grandNode = parentNode = currentNode = beforeRoot;
	    
	    // Search for the target node
	    while (nextNode(target) != ghostNode){
	    	// Update helpers at start of loop
	    	grandNode = parentNode;
	    	parentNode = currentNode;
	    	// Move current node down towards target
	    	currentNode = nextNode(target);
	    	// If new current node holds target data, save the node
	    	if(currentNode.data.equals(target)){
	    		locked = currentNode;
	    	}
	    	Dnode<E> nextNode = nextNode(target);
	    	if(currentNode.colour == BLACK && nextNode.colour == BLACK){
	    		Dnode<E>nextNodeSibling = siblingOf(nextNode, currentNode);
				// If nextNode's sibling is red:
	    		if(nextNodeSibling.colour == RED){
	    			// Red Child Case:
	    			// Rotate red child into currentNode
	    			// pushing down currentNode and the nextNode
	    			currentNode.colour = RED;
	    			nextNodeSibling.colour = BLACK;
	    			parentNode = rotate(nextNodeSibling.data,parentNode);
    			}
	    		// Else sibling is black and if not ghostNode:
	    		else if(siblingOf(currentNode,parentNode) != ghostNode){
	    			Dnode<E>siblingNode = siblingOf(currentNode, parentNode);
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
    					// Black Sibling Case 2: Outer nephew or both nephews
    					// are red
    					// Arbitrarily pick node.
    					Dnode<E> redNephew = siblingNode.left.colour == RED ?
    							siblingNode.left : siblingNode.right;
    					// If outer child or both children are red do a single rotation
    					if((redNephew.data.compareTo(siblingNode.data)<0) ==
    							siblingNode.data.compareTo(parentNode.data)<0){
    						parentNode.colour = BLACK;
    						siblingNode.colour = RED;
    						redNephew.colour = BLACK;
    						currentNode.colour = RED;
    						rotate(siblingNode.data, grandNode);
    					}
    					// Black Sibling Case 3: Inner nephew is red
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
	    	beforeRoot.right.colour = BLACK;
	    	modCount++;	// Record Modification
	    	return true;
	    }
	    // Root is fixed and made black again if it still exists
	    beforeRoot.right.colour = BLACK;
	    return false;
	}

	/**
	*Provides a fail fast iterator for the Dictionary, starting 
	*at the least element.
	*The iterator should implement all methods of the iterator 
	*class including remove.
	*@return an iterator whose next element is the least element 
	*in the dictionary, and which will iterate through all the 
	*elements in the Dictionary in ascending order. 
	*/
	public Iterator<E> iterator(){
		if(!isEmpty()){
			return new DictionaryIterator();
		}
		else throw new ItemNotFound("Empty Dictionary");
	}

	/**
	*Provides a fail fast iterator for the Dictionary, starting 
	*at the least element greater than or equal to start
	*The iterator should implement all methods of the iterator 
	*class including remove
	*@param start the element at which to start iterating at.
	*@return an iterator whose next element is the least element 
	*greater than or equal to start in the dictionary, and which 
	*will iterate through all the elements in the Dictionary in 
	*ascending order. 
	*/
	public Iterator<E> iterator(E start){
		ghostNode.data = null;
		if(start != null && !isEmpty()){
			return new StepIterator(start);
		}
		else 
			throw new OutOfBounds("Null Start Point or Empty Dictionary");
	}

	/**
	*Provides a string describing all operations performed on the 
	*table since its construction, or since the last time 
	*getLogString was called.
	*As each operation returns (either called directly on the 
	*Dictionary, or on an iterator generated by the dictionary) 
	*append a new line to the 
	*String:"Operation <name of op>(<parameter values>) 
	*completed using [n] comparisons". 
	*@return A sting listing all operations called on the Dictionary, 
	*and how many comparisons were required to complete each operation.
	**/ 
	public String getLogString(){
		//	TODO
		return null;
	}

	/**
	*Provides a String representation of the Dictionary
	*@return a String representation of the Dictionary
	**/
	public String toString(){
		if(isEmpty())
			return "[ ]";
		StringBuilder allWords = new StringBuilder("[ ");
		while(iterator().hasNext()){
			allWords.append(String.valueOf(iterator().next()) + ", ");
		}
		// Replace last comma and space with the closing bracket
		allWords.replace(allWords.length()-2, allWords.length(), " ]");
		return allWords.toString();
	}
	
	/**
	 * Find method iterates through from root to ghostNode
	 * checking for target item. The ghostNode is temporarily
	 * assigned the target value so that while loop can not
	 * run indefinitely, also makes finding target easy.
	 * @param target	The object being searched for.
	 * @return			null if target not found or the
	 * 					target item's node.
	 */
	private Dnode<E> find(E target){
		ghostNode.data = target;		// ghostNode is boundary
		currentNode = beforeRoot.right;
		while(true){
			if(target.compareTo(currentNode.data)<0){
				currentNode = currentNode.left;
			}
			else if(target.compareTo(currentNode.data)>0){
				currentNode = currentNode.right;
			}
			else if(currentNode != ghostNode){
				return currentNode;
			}
			else 
				return null;
		}
	}
	
	/**
	 * Returns Minimum of any tree structure given root.
	 * @param headOfSearch	Head of tree from which to
	 * 						search for minimum value.
	 * @return	Returns node holding object with
	 * 			minimum value
	 * @throws ItemNotFound	If no minimum found
	 */
	private Dnode<E> min(Dnode<E> root){
		Dnode<E> minFocus = root;
		while(minFocus.left != ghostNode){
			minFocus = minFocus.left;
		}
		return minFocus;
	}
	
	/**
	 * Finds Maximum of any tree structure given root.
	 * @param root	Root of tree to search
	 * @return
	 * @throws ItemNotFound
	 */
	private Dnode<E> max(Dnode<E> root){
		Dnode<E> maxFocus = root;
		while(maxFocus.right != ghostNode){
			maxFocus = maxFocus.right;
		}
		return maxFocus;
	}
	
	/**
	 * Helper method for comparisons when a node could
	 * be equal to the sentinel cell beforeRoot
	 * @param currentItem	Object being compared to node's
	 * 						object.
	 * @param node		Node with object being compared to item
	 * @return	Returns 1 if node is sentinel to move from
	 * 			sentinel to root.
	 * 			If not sentinel returns normal comparison. 
	 * */
	private final int compare(E currentItem, Dnode<E> node){
		if(node == beforeRoot){
			return 1;
		}
		else
			return currentItem.compareTo(node.data);
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
	private void fixDictionary(E currentItem){
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
	 * @param currentItem	item/object currently focused passed from fixDictionary
	 * @param ancestor		Grandparent or great grandparent
	 * 						from fixDictionary or delete methods
	 * @return	returns 	root of rotated sub tree
	 */
	private Dnode<E> rotate(E currentItem, Dnode<E> ancestor){
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
	private static <E> Dnode<E> rotateRight(Dnode<E> parent){
		Dnode<E>leftChild = parent.left;
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
	private static <E> Dnode<E> rotateLeft(Dnode<E> parent){
		Dnode<E>rightChild = parent.right;
		parent.right = rightChild.left;
		rightChild.left = parent;
		return rightChild;
	}
	
	/**
	 * Helper function to attain sibling node.
	 * Requires calling method to know current parentNode.
	 * @param node		The node which is known
	 * @param parent	Direct parent to node
	 * @return		The sibling of node.
	 */
	private Dnode<E> siblingOf(Dnode<E> node, Dnode<E> parent){
		return (node.data == parent.left.data) ? parent.right :
		parent.left;
	}
	
	/**
	 * Helper method for Remove.
	 * Finds the next node in the path towards
	 * the object, is used in Remove to avoid
	 * moving outside of the Tree.
	 * @param target	Object to be deleted
	 * @return			Returns the next node to move to.
	 */
	public Dnode<E> nextNode(E target){
		return (compare(target,currentNode) < 0) ? currentNode.left : currentNode.right;
	}
	
	/**
	 * Internal Iterator class for Dictionary structure.
	 * Makes use of Successor and hasSuccessor methods
	 * for traversal rather than a faster
	 * @author Pradyumn
	 *
	 */
	private class DictionaryIterator implements Iterator<E>{
		
		StackLinked<Dnode<E>> wordPile;
		// Sets expectedModCount for this instance of 
		// DictionaryIterator 
		private int expectedModCount = modCount;
		// Keep track of next method to allow valid remove
		private boolean nextCalled = false;
		private E nextItemOut;
		
		/**
		 * Constructor for initial start point is known
		 * @param startNode	Starting node, included in iteration.
		 */
		public DictionaryIterator(E start){
			if(start == ghostNode)
				throw new OutOfBounds("Must start within Dictionary.");
			ghostNode.data = start;
			wordPile = new StackLinked<Dnode<E>>();
			Dnode<E> walk = beforeRoot.right;
			if(start.compareTo(beforeRoot.right.data)>0){
				while(walk != ghostNode){
					if(start.compareTo(walk.data)<=0){
						wordPile.push(walk);
						walk = walk.left;
					}
				}
			}
			else{
				while(walk != ghostNode){
					if(start.compareTo(walk.data)<= 0){
						wordPile.push(walk);
						walk = walk.left;
					}
				}
			}
		}
		
		/**
		 * Second constructor for full tree iteration.
		 */
		public DictionaryIterator(){
			Dnode<E> walk = beforeRoot.right;
			wordPile = new StackLinked<Dnode<E>>();
			// Find minimum value while filling stack
			while(walk != ghostNode){
				wordPile.push(walk);
				walk = walk.left;
			}
		}
		
		/**
		 * Returns true if stack is populated.
		 * Has concurrency check.
		 */
		@Override
		public boolean hasNext(){
			if(expectedModCount != modCount)
				throw new IllegalValue("Concurrent modification.");
			return (!wordPile.isEmpty());
		}
		
		/**
		 * Returns next item in order.
		 */
		@Override
		public E next() throws OutOfBounds{
			if(!hasNext())
				throw new OutOfBounds("No further Items.");
			Dnode<E> focusNode = wordPile.pop();
			nextItemOut = focusNode.data;
			if(focusNode.right != ghostNode){
				focusNode = focusNode.right;
				while(focusNode != ghostNode){
					wordPile.push(focusNode);
					focusNode = focusNode.left;
				}	
			}
			nextCalled = true;
			return nextItemOut;
		}
		
		/**
		 * Removes the last item called by Next.
		 * Cannot execute until next method has been called.
		 * Cannot execute twice in a row.
		 * Cannot execute unless modCount of current Dictionary
		 * matches with the instance being iterated over.
		 * New Iterator required if modCounts do not match up.
		 */
		public void remove(){
			if(expectedModCount != modCount)
				throw new IllegalValue("Concurrent modification.");
			if(nextCalled)
				delete(nextItemOut);
			else
				throw new OutOfBounds("Cannot remove without calling next.");
			nextCalled = false; // Cannot run twice in a row
			// Record modification to structure in iterator
			expectedModCount++;
		}
	}
	
	/**
	 * Iterator class that starts at a certain item.
	 * @author Pradyumn
	 *
	 */
	private class StepIterator implements Iterator<E>{
		// Sets expectedModCount for this instance of 
		// DictionaryIterator 
		private int expectedModCount = modCount;
		// Keep track of next method to allow valid remove
		private boolean nextCalled = false;
		private E nextItemOut;
		private E focus;
		boolean first;
		private E whisker;
		
		/**
		 * Constructor for initial start point is known
		 * @param startNode	Starting node, included in iteration.
		 */
		public StepIterator(E start){
			focus = nextItemOut = start;
			first = true;
		}
		
		/**
		 * @return true if successor to nextItemOut exists
		 */
		@Override
		public boolean hasNext(){
			if(expectedModCount != modCount)
				throw new IllegalValue("Concurrent modification.");
			return hasSuccessor(nextItemOut);
		}
		
		/**
		 * Returns next item in order.
		 */
		@Override
		public E next() throws OutOfBounds{
			
			//if(!hasNext()){
				//throw new OutOfBounds("No further Items.");
			//}
			//else{
				
				nextCalled = true;
				if(first){
					first = false;
					return nextItemOut;
				}else{
					focus = nextItemOut;
					nextItemOut = successor(nextItemOut);
					return nextItemOut;
				}
			//}
		}
		
		/**
		 * Removes the last item called by Next.
		 * Cannot execute until next method has been called.
		 * Cannot execute twice in a row.
		 * Cannot execute unless modCount of current Dictionary
		 * matches with the instance being iterated over.
		 * New Iterator required if modCounts do not match up.
		 */
		public void remove(){
			if(expectedModCount != modCount)
				throw new IllegalValue("Concurrent modification.");
			if(nextCalled)
				delete(nextItemOut);
			else
				throw new OutOfBounds("Cannot remove without calling next.");
			nextCalled = false; // Cannot run twice in a row
			// Record modification to structure in iterator
			expectedModCount++;
		}
	}
	
	/**
	 * 	The base node structure that forms the
	 * 	building blocks of the Dictionary.
	 * 	Has the properties of a Red Black Node.
	 * 	@author Pradyumn Vij
	 */
	private static class Dnode<E>{
		
		E data;
		Dnode<E> left;
		Dnode<E> right;
		int colour;
		
		/**
		 * @param item			Data to be held by the node.
		 * @param leftChild		The left subtree reference
		 * @param rightChild	The right subtree reference.
		 */
		Dnode(E item, Dnode<E> leftChild, Dnode<E> rightChild){
			data = item;				// Data in the node
			left = leftChild;			// left child
			right = rightChild;			// right child
			colour = Dictionary.BLACK;	// colour
		}
		
		/**
		 * Class Constructor no children
		 * @param item	Data to be held by the node
		 */
		Dnode(E item){
			this(item, null, null);
		}
	}
}

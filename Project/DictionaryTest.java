import java.util.Iterator;

import CITS2200.IllegalValue;
import CITS2200.ItemNotFound;
import CITS2200.OutOfBounds;
import junit.framework.TestCase;

/**
 * 
 */

/**
 * @author Pradyumn
 *
 */
public class DictionaryTest extends TestCase {
	
	
	Dictionary<String> dOne = new Dictionary<String>();
	Dictionary<String> emptyDictionary = new Dictionary<String>();

	/**
	 * @param name
	 */
	public DictionaryTest(String name) {
		super(name);
		
	}

	/**
	 * @throws java.lang.Exception
	 */
	protected static void setUpBeforeClass() throws Exception {
		
	}

	/**
	 * Test method for {@link Dictionary#isEmpty()}.
	 */
	public final void testIsEmpty() {
		
		Dictionary<String> d = new Dictionary<String>();
		assertTrue("Dictionary should start Empty.",d.isEmpty());
		d.add(null);
		assertTrue("Nothing is added if null passed.", d.isEmpty());
		d.add("First");
		assertFalse("Dictionary has one item.",d.isEmpty());
		d.add("Second");
		assertFalse("Dictionary has two items.",d.isEmpty());
	}

	/**
	 * Test method for {@link Dictionary#contains(java.lang.Comparable)}.
	 */
	public final void testContains() {
		Dictionary<String> d = new Dictionary<String>();
		assertFalse("Dictionary just created, is Empty", d.contains(null));
		d.add("One");
		assertTrue("Dictionary contains One but nothing else", d.contains("One"));
		assertFalse("Dictionary contains One but nothing else", d.contains("Two"));
		assertFalse("Dictionary contains One but nothing else", d.contains("Three"));
		d.add("Two");
		assertTrue("Dictionary contains One and Two", d.contains("Two"));
		assertFalse("Dictionary contains One and Two", d.contains("Three"));
		d.add("Three");
		assertTrue("Dictionary contains One, Two and Three.", d.contains("One"));
		assertTrue("Dictionary contains One, Two and Three.", d.contains("Two"));
		assertTrue("Dictionary contains One, Two and Three.", d.contains("Three"));
		assertFalse("Dictionary contains One, Two and Three.", d.contains("Four"));
	}

	/**
	 * Test method for {@link Dictionary#hasPredecessor(java.lang.Comparable)}.
	 */
	public final void testHasPredecessor() {
		Dictionary<String> d = new Dictionary<String>();
		assertFalse("Dictionary is Empty",d.hasPredecessor(null));
		d.add("aaa");
		assertFalse("Dictionary only has one item.",d.hasPredecessor("aaa"));
		d.add("bbb");
		assertFalse("First item has no predecessor.",d.hasPredecessor("aaa"));
		assertTrue("Dictionary has two items.",d.hasPredecessor("bbb"));
		d.add("ccc");
		assertTrue("Dictionary has 3 items, should have predecessor.",d.hasPredecessor("ccc"));
		d.delete("aaa");
		assertFalse("After deleting first item, shouldn't have predecessor.",d.hasPredecessor("bbb"));
		assertFalse("Item doesn't exist in dictionary",d.hasPredecessor("mario"));
	}

	/**
	 * Test method for {@link Dictionary#hasSuccessor(java.lang.Comparable)}.
	 */
	public final void testHasSuccessor() {
		Dictionary<String> d = new Dictionary<String>();
		assertFalse("Dictionary is Empty",d.hasSuccessor(null));
		d.add("aaa");
		assertFalse("Dictionary only has one item.",d.hasSuccessor("aaa"));
		d.add("bbb");
		assertTrue("First item has successor.",d.hasSuccessor("aaa"));
		assertFalse("Dictionary has two items, second has no successor.",d.hasSuccessor("bbb"));
		d.add("ccc");
		assertTrue("Dictionary has 3 items, should have successor.",d.hasSuccessor("bbb"));
		d.delete("ccc");
		assertFalse("After deleting first item, 2nd shouldn't have successor.",d.hasSuccessor("bbb"));
		assertFalse("Item doesn't exist in dictionary",d.hasSuccessor("mario"));
	}
	
	/**
	 * Test method for {@link Dictionary#predecessor(java.lang.Comparable)}.
	 */
	public final void testPredecessor() {
		Dictionary<String> d = new Dictionary<String>();
		d.add("aaa");
		try{
			d.predecessor("aaa");
			fail("Single item shouldn't have predecessor");
		}
		catch(ItemNotFound expectedException){
			assertTrue(true);
		}
		d.add("bbb");
		d.add("ccc");
		d.add("aa");
		d.add("ab");
		d.add("ac");
		d.add("ad");
		d.add("ae");
		d.add("af");
		// Predecessors are 2 or 3 levels separated.
		assertEquals("ac", d.predecessor("ad"));
		assertEquals("af",d.predecessor("bbb"));
		assertEquals("bbb",d.predecessor("ccc"));
		try{
			d.predecessor("aa");
			fail("First item shouldn't have predecessor");
		}
		catch(ItemNotFound expectedException){
			assertTrue(true);
		}
		try{
			d.predecessor(null);
			fail("null entry");
		}
		catch(ItemNotFound expectedException){
			assertTrue(true);
		}
		try{
			d.predecessor("mario");
			fail("Item is not in collection.");
		}
		catch(ItemNotFound expectedException){
			assertTrue(true);
		}
	}

	/**
	 * Test method for {@link Dictionary#successor(java.lang.Comparable)}.
	 */
	public final void testSuccessor() {
		Dictionary<String> d = new Dictionary<String>();
		try{
			emptyDictionary.max();
			fail("Dictionary is empty");
		}
		catch(ItemNotFound expectedException){
			assertTrue(true);
		}
		
		
		d.add("aaa");
		try{
			d.successor("aaa");
			fail("Single item shouldn't have successor");
		}
		catch(ItemNotFound expectedException){
			assertTrue(true);
		}
		d.add("bbb");
		d.add("ccc");
		assertEquals("bbb",d.successor("aaa"));
		assertEquals("ccc",d.successor("bbb"));
		d.add("aa");
		d.add("ab");
		d.add("ac");
		d.add("ad");
		d.add("ae");
		d.add("af");
		// Test case where grandparent is successor
		assertEquals("bbb",d.successor("af"));
		
		try{
			d.successor("ccc");
			fail("Last item shouldn't have successor");
		}
		catch(ItemNotFound expectedException){
			assertTrue(true);
		}
		// Null entry
		try{
			d.successor(null);
			fail("null entry");
		}
		catch(ItemNotFound expectedException){
			assertTrue(true);
		}
		// Out of collection
		try{
			d.successor("mario");
			fail("Item is not in collection.");
		}
		catch(ItemNotFound expectedException){
			assertTrue(true);
		}
	}

	/**
	 * Test method for {@link Dictionary#max()}.
	 */
	public final void testMin() {
		Dictionary<String> d = new Dictionary<String>();
		try{
			emptyDictionary.min();
			fail("Dictionary is empty");
		}
		catch(ItemNotFound expectedException){
			assertTrue(true);
		}
		d.add("aaa");
		assertEquals("aaa",d.min());
		d.add("bbb");
		assertEquals("aaa",d.min());
		d.add("ccc");
		assertEquals("aaa",d.min());
		d.delete("aaa");
		assertEquals("bbb",d.min());
	}

	/**
	 * Test method for {@link Dictionary#max()}.
	 */
	public final void testMax() {
		Dictionary<String> d = new Dictionary<String>();
		try{
			emptyDictionary.max();
			fail("Dictionary is empty");
		}
		catch(ItemNotFound expectedException){
			assertTrue(true);
		}
		d.add("aaa");
		assertEquals("aaa",d.max());
		d.add("bbb");
		assertEquals("bbb",d.max());
		d.add("ccc");
		assertEquals("ccc",d.max());
		d.delete("ccc");
		assertEquals("bbb",d.max());
	}

	/**
	 * Test method for {@link Dictionary#add(java.lang.Comparable)}.
	 */
	public final void testAdd() {
		Dictionary<String> d = new Dictionary<String>();
		assertFalse("Trying to add null",d.add(null));
		assertTrue("Adding one item to empty dictionary", d.add("10"));
		assertTrue("Check that new item exists", d.contains("10"));
		assertFalse("Add item that already exists", d.add("10"));
		for(Integer i = 11; i<10000; i++){
			assertTrue("Add multiple items", d.add(i.toString()));
		}
		assertTrue("Check random number exists", d.contains("999"));
		assertFalse("Check if a number outside of the range exists",d.contains("20000"));
	}

	/**
	 * Test method for {@link Dictionary#delete(java.lang.Comparable)}.
	 */
	public final void testDelete() {
		assertFalse("Trying to delete from empty dictionary", emptyDictionary.delete("anything"));
		Dictionary<String> d = new Dictionary<String>();
		assertFalse("Trying to delete null",d.delete(null));
		d.add("90");
		assertTrue("Deleting Root", d.delete("90"));
		d.add("90");
		d.add("10");
		assertTrue("Delete red leaf", d.delete("10"));
		d.add("10");
		d.add("20");
		d.add("30");
		assertTrue("Delete black node with one red child", d.delete("30"));
		d.add("30");
		d.add("100");
		assertTrue("Delete red node with red sibling", d.delete("30"));
		d.add("30");
		assertTrue("Delete black node with a black sibling which has red child", d.delete("10"));
		d.add("10");
		d.add("100");
		d.add("25");
		assertTrue("Delete black node with sibling's inner red child", d.delete("10"));
		for(Integer j = 0; j < 10000; j++){
			d.add(j.toString());
		}
		for(Integer k = 0; k < 10000; k++){
			assertTrue("Delete multiple items", d.delete(k.toString()));
		}
	}

	/**
	 * Test method for {@link Dictionary#iterator()}.
	 */
	public final void testIterator() {
		// empty test
		try{
			Iterator<String> emptyIterator = emptyDictionary.iterator();
			emptyIterator.next();
			fail("Dictionary is Empty.");
		}catch (ItemNotFound expectedException){
			assertTrue(true);
		}
		
		// single item test
		Dictionary<String> d = new Dictionary<String>();
		d.add("zz");
		Iterator<String> singleIterator = d.iterator();
		assertEquals("zz",singleIterator.next());
		try{
			singleIterator.next();
			fail("Dictionary is empty after single iteration.");
		}catch(OutOfBounds expectedException){
			assertTrue(true);
		}
		// add more items
		d.add("ww");
		d.add("bb");
		d.add("ss");
		d.add("aa");
		// check if it is fail fast
		try{
			singleIterator.next();
			fail("Should fail if calling next after modifying Dictionary");
		}catch(IllegalValue expectedException){
			assertTrue(true);
		}
		// Create new iterator for new Dictionary instance
		Iterator<String> fullIterator = d.iterator();
		assertEquals("aa",fullIterator.next());
		assertEquals("bb",fullIterator.next());
		assertEquals("ss",fullIterator.next());
		assertEquals("ww",fullIterator.next());
		assertEquals("zz",fullIterator.next());
		fullIterator.remove();
		// Create new iterator after remove() function;
		Iterator<String> afterRemove = d.iterator();
		assertEquals("aa",afterRemove.next());
		assertEquals("bb",afterRemove.next());
		assertEquals("ss",afterRemove.next());
		assertEquals("ww",afterRemove.next());
		try{
			fullIterator.next();
			fail("Should fail as 'zz' was remove");
		}catch(OutOfBounds expectedException){
			assertTrue(true);
		}
		//Further Remove Check
		Iterator<String> doubleRemove = d.iterator();
		assertEquals("aa",doubleRemove.next());
		doubleRemove.remove();
		try{
			doubleRemove.remove();
			fail("Should fail if remove called without a prior next() call");
		}catch(OutOfBounds expectedException){
			assertTrue(true);
		}
	}

	/**
	 * Test method for {@link Dictionary#iterator(java.lang.Comparable)}.
	 */
	public final void testIteratorE() {
		// has the exact same methods as iterator
		// just different starting point
		Dictionary<String> d = new Dictionary<String>();
		d.add("zz");
		d.add("ww");
		d.add("bb");
		d.add("ss");
		d.add("aa");
		d.add("cc");
		d.add("aa");
		Iterator<String> startFromC = d.iterator("cc");
		assertEquals("cc",startFromC.next());
		assertEquals("ss",startFromC.next());
		assertEquals("ww",startFromC.next());
		assertEquals("zz",startFromC.next());
	}

	/**
	 * Test method for {@link Dictionary#getLogString()}.
	 */
	public final void testGetLogString() {
		Dictionary<String> d = new Dictionary<String>();
		assertTrue(d.add("zz"));
	}

	/**
	 * Test method for {@link Dictionary#toString()}.
	 */
	public final void testToString() {
		// Empty Dictionary
		assertEquals("[ ]", emptyDictionary.toString());
		//  Filled Dictionary
		Dictionary<Integer> d = new Dictionary<Integer>();
		d.add(1);
		d.add(4);
		d.add(2);
		d.add(3);
		d.add(5);
		assertEquals("[ 1, 2, 3, 4, 5 ]",d.toString());	
	}

}

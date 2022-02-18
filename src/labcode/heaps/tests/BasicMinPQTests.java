package heaps.tests;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Timeout;

import heaps.MinHeap;
import heaps.MinPriorityQueue;
import heaps.PQEntry;

/**
 * These tests focus on the MinPriorityQueue interface methods' behavior. (They are about a PQ's behavior and not the specific details of a heap implementation.)
 */
@TestMethodOrder(OrderAnnotation.class)
class BasicMinPQTests {

    /**
     * Generate PQ with integer data and integer priorities.
     *
     * @return new PQ
     */
	public static MinPriorityQueue<Integer, Integer> getPQ() {
		return new MinHeap<Integer, Integer>();
	}
	
	
    /**
     * Sanity check on empty heap
     */
	@Test
	@Order(1) 
	@Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
	public void testEmptyHeap() {
		// Check empty PQ's properties
		MinPriorityQueue<Integer, Integer> mh = getPQ();
	
		assertTrue(mh.isEmpty(), 
			   	   "isEmpty() is incorrect");
		
		assertEquals(0, 
					 mh.size(),
					 "size() is incorrect");
		
		assertEquals("", 
  				     mh.toString(),
				     "String representation is incorrect");
		
		assertNull(mh.peekMin(),
			     	"peekMin() is incorrect");
	}

    /**
     * Test inserting one value.
     */
	@Test
	@Order(2) 
	@Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
	public void testInsertOne() {

		// Confirm that operations work as expected after inserting one item
		MinPriorityQueue<Integer, Integer> mh = getPQ();
		
		PQEntry<Integer, Integer> entry = mh.insert(1, 2);
		
		assertFalse(mh.isEmpty(), 
			   	   "isEmpty() is incorrect");

		assertEquals(1, 
					 mh.size(),
					 "size() is incorrect");
		
		assertEquals("(E=1, P=2)", 
  				     mh.toString(),
				     "String representation is incorrect");
		
		assertNotNull(mh.peekMin(),
		         "peekMin() return isincorrect");
		
		assertNotNull(mh.peekMin().getElement(),
		         "peekMin()'s returned entry is incorrect");
		
		assertEquals(1, 
				     mh.peekMin().getElement(),
			         "Minimum item's element is incorrect");
		
		assertEquals(2, 
			     mh.peekMin().getPriority(),
		         "Minimum item's priority is incorrect");

		assertNotNull(entry,
		         "inserts()'s returned entry is incorrect");

		assertEquals(1,
				 entry.getElement(),
		         "inserts()'s returned entry is incorrect");

		assertEquals(2,
				 entry.getPriority(),
		         "inserts()'s returned entry is incorrect");
	}

    /**
     * Test inserting two values, configuration A.
     */
	@Test
	@Order(3) 
	@Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
	public void testInsertTwoA() {
		
		// Confirm that operations work as expected after inserting two items
		MinPriorityQueue<Integer, Integer> mh = getPQ();
		
		PQEntry<Integer, Integer> entry1 = mh.insert(1, 2);
		PQEntry<Integer, Integer> entry2 = mh.insert(3, 4);
		
		assertNotNull(entry1,
		         "inserts()'s returned entry is incorrect");

		assertEquals(1,
				 entry1.getElement(),
		         "inserts()'s returned entry is incorrect");

		assertEquals(2,
				 entry1.getPriority(),
		         "inserts()'s returned entry is incorrect");
				
		assertNotNull(entry2,
		         "inserts()'s returned entry is incorrect");

		assertEquals(3,
				 entry2.getElement(),
		         "inserts()'s returned entry is incorrect");

		assertEquals(4,
				 entry2.getPriority(),
		         "inserts()'s returned entry is incorrect");
		
		assertFalse(mh.isEmpty(), 
			   	   "isEmpty() is incorrect");
		
		assertEquals(2, 
					 mh.size(),
					 "size() is incorrect");
		
		assertNotNull(mh.peekMin(),
		         "peekMin() return isincorrect");
		
		assertNotNull(mh.peekMin().getElement(),
		         "peekMin()'s returned entry is incorrect");

		
		assertEquals(1, 
				     mh.peekMin().getElement(),
			         "Minimum item's element is incorrect");

		assertEquals(2, 
			     mh.peekMin().getPriority(),
		         "Minimum item's priority is incorrect");
		

	}

    /**
     * Test inserting two values, configuration B.
     */
	@Test
	@Order(4) 
	@Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
	public void testInsertTwoB() {
		
		// Confirm that operations work as expected after inserting two items (reverse order) 
		MinPriorityQueue<Integer, Integer> mh = getPQ();

		// Reverse order of insertions from TwoA
		mh.insert(3, 4);
		mh.insert(1, 2);
		
		assertFalse(mh.isEmpty(), 
			   	   "isEmpty() is incorrect");
		
		assertEquals(2, 
					 mh.size(),
					 "size() is incorrect");
		

		assertNotNull(mh.peekMin(),
		         "peekMin() return isincorrect");
		
		assertNotNull(mh.peekMin().getElement(),
		         "peekMin()'s returned entry is incorrect");

		
		assertEquals(1, 
				     mh.peekMin().getElement(),
			         "Minimum item's element is incorrect");

		assertEquals(2, 
			     mh.peekMin().getPriority(),
		         "Minimum item's priority is incorrect");
		
	}
	
	
    /**
     * Test inserting three values, configuration A.
     */
	@Test
	@Order(5) 
	@Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
	public void testInsertThreeA() {
		MinPriorityQueue<Integer, Integer> mh = getPQ();
		// Confirm that operations work as expected after inserting three items
		// This just checks the min item (via peek)
		
		mh.insert(1, 2);
		mh.insert(3, 4);
		mh.insert(5, 6);
		
		assertFalse(mh.isEmpty(), 
			   	   "isEmpty() is incorrect");

		assertEquals(3, 
					 mh.size(),
					 "size() is incorrect");

		assertNotNull(mh.peekMin(),
		         "peekMin() return isincorrect");
		
		assertNotNull(mh.peekMin().getElement(),
		         "peekMin()'s returned entry is incorrect");

		assertEquals(1, 
				     mh.peekMin().getElement(),
			         "Minimum item's element is incorrect");
		
		assertEquals(2, 
			     mh.peekMin().getPriority(),
		         "Minimum item's priority is incorrect");
		
	}
	
    /**
     * Test inserting three values, configuration B.
     */
	@Test
	@Order(6) 
	@Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
	public void testInsertThreeB() {
		MinPriorityQueue<Integer, Integer> mh = getPQ();

		// Confirm that operations work as expected after inserting three items
		// This just checks the min item (via peek) 
		// Min priority is now second insertion (Compared to TheeA)
		mh.insert(3, 4);
		mh.insert(1, 2);
		mh.insert(5, 6);
		
		assertFalse(mh.isEmpty(), 
			   	   "isEmpty() is incorrect");
		
		assertEquals(3, 
					 mh.size(),
					 "size() is incorrect");

		assertNotNull(mh.peekMin(),
		         "peekMin() return isincorrect");
		
		assertNotNull(mh.peekMin().getElement(),
		         "peekMin()'s returned entry is incorrect");

		assertEquals(1, 
				     mh.peekMin().getElement(),
			         "Minimum item's element is incorrect");
		
		assertEquals(2, 
			     mh.peekMin().getPriority(),
		         "Minimum item's priority is incorrect");
		
	}
	
    /**
     * Test inserting three values, configuration C.
     */
	@Test
	@Order(7) 
	@Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
	public void testInsertThreeC() {
		MinPriorityQueue<Integer, Integer> mh = getPQ();
		
		// Confirm that operations work as expected after inserting three items
		// This just checks the min item (via peek) 
		// Min priority is now third insertion (Compared to TheeA)
		mh.insert(3, 4);
		mh.insert(5, 6);
		mh.insert(1, 2);
		
		assertFalse(mh.isEmpty(), 
			   	   "isEmpty() is incorrect");
		
		assertEquals(3, 
					 mh.size(),
					 "size() is incorrect");

		assertNotNull(mh.peekMin(),
		         "peekMin() return isincorrect");
		
		assertNotNull(mh.peekMin().getElement(),
		         "peekMin()'s returned entry is incorrect");

		assertEquals(1, 
				     mh.peekMin().getElement(),
			         "Minimum item's element is incorrect");
		
		assertEquals(2, 
			     mh.peekMin().getPriority(),
		         "Minimum item's priority is incorrect");
		
	}
	
    /**
     * Test extractMin operation on a 1-entry PQ.
     */
	@Test
	@Order(8) 
	@Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
	public void testInsertExtractA() {

		MinPriorityQueue<Integer, Integer> mh = getPQ();
		
		// Insert one item and extract it 
		mh.insert(1, 2);
		PQEntry<Integer, Integer> min = mh.extractMin();
		
		assertTrue(mh.isEmpty(), 
			   	   "isEmpty() is incorrect");

		assertEquals(0, 
					 mh.size(),
					 "size() is incorrect");
		
		assertNull(mh.peekMin(),
		         "peekMin() return isincorrect");
		
		assertNotNull(min,
		         "extractMin()'s returned entry is incorrect");

		assertNotNull(min.getElement(),
		         "extractMin()'s returned entry is incorrect");
		
		assertEquals(1, 
					min.getElement(),
			         "Minimum item's element is incorrect");
		
		assertEquals(2, 
			     min.getPriority(),
		         "Minimum item's priority is incorrect");
		
	}

    /**
     * Test inserting, then extracting two elements.
     */
	@Test
	@Order(9) 
	@Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
	public void testInsertExtractB() {

		MinPriorityQueue<Integer, Integer> mh = getPQ();
		
		// Insert two items and extract them
		mh.insert(1, 2);
		mh.insert(3, 4);
		PQEntry<Integer, Integer> min1 = mh.extractMin();
		PQEntry<Integer, Integer> min2 = mh.extractMin();
		
		assertTrue(mh.isEmpty(), 
			   	   "isEmpty() is incorrect");

		assertEquals(0, 
					 mh.size(),
					 "size() is incorrect");
		
		assertNull(mh.peekMin(),
		         "peekMin() return is incorrect");
		
		assertNotNull(min1,
		         "extractMin()'s returned entry is incorrect");

		assertNotNull(min1.getElement(),
		         "extractMin()'s returned entry is incorrect");
		
		assertEquals(1, 
					min1.getElement(),
			         "Minimum item's element is incorrect");
		
		assertEquals(2, 
			     min1.getPriority(),
		         "Minimum item's priority is incorrect");
		
		assertNotNull(min2,
		         "extractMin()'s returned entry is incorrect");

		assertNotNull(min2.getElement(),
		         "extractMin()'s returned entry is incorrect");
		
		assertEquals(3, 
					min2.getElement(),
			         "Minimum item's element is incorrect");
		
		assertEquals(4, 
			     min2.getPriority(),
		         "Minimum item's priority is incorrect");

	
	}
	
}

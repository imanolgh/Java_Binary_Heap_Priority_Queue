package heaps.tests_extra;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import heaps.MinHeap;
import heaps.MinPriorityQueue;
import heaps.PQEntry;
import heaps.visualizer.TreeSVGRenderer;

@TestMethodOrder(OrderAnnotation.class)
class MinPQTests {
	
	public static MinPriorityQueue<Integer, Integer> getIntIntPQ() {
		return new MinHeap<Integer, Integer>();
	}
	
	
	
	public static String applyOperation(MinPriorityQueue<?,?> pq, String before, String operation, Runnable operations, String expected, String filename) {
		// 1. Run the operations
		operations.run();
		// 2. Get the result
		String result = pq.toString();

		// Generate the file
		try {
			TreeSVGRenderer.showError(before, operation, expected, result, filename);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(result.equals(expected)) {
			return result;
		} else {		
			return null;  // Return null and end test
		}
	}

	
	public static String checkErrorFile(String filename) {
		return "Error: Check outputs/" + filename + ".html for details";
	}
	
	@Test
	@Order(1) 
	@Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
	public void testSimpleInsertOne() {
		String thisTest = new Object(){}.getClass().getEnclosingMethod().getName();

		// Values to insert (in order of insertion)
		int[] valuesToInsert = {2};

		// Tree expected after each insert
		String[] expectedTrees = {  // Result following each insert
				"(E=2, P=2)"
		};
		checkInserts(valuesToInsert, expectedTrees, thisTest);
		
	}


	@Test
	@Order(2) 
	@Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
	public void testSimpleInsertTwo() {
		String thisTest = new Object(){}.getClass().getEnclosingMethod().getName();

		// Values to insert (in order of insertion)
		int[] valuesToInsert = {2, 1};

		// Tree expected after each insert
		String[] expectedTrees = {  // Result following each insert
				"(E=2, P=2)", 
				"(E=1, P=1) (E=2, P=2)"
		};
		checkInserts(valuesToInsert, expectedTrees, thisTest);
	

	}
	
	@Test
	@Order(3) 
	@Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
	public void testSimpleInsertThreeA() {
		String thisTest = new Object(){}.getClass().getEnclosingMethod().getName();

		// Values to insert (in order of insertion)
		int[] valuesToInsert = {2, 1, 3};

		// Tree expected after each insert
		String[] expectedTrees = {  // Result following each insert
				"(E=2, P=2)", 
				"(E=1, P=1) (E=2, P=2)",
				"(E=1, P=1) (E=2, P=2) (E=3, P=3)"
		};
		checkInserts(valuesToInsert, expectedTrees, thisTest);

	}
	
	@Test
	@Order(4) 
	@Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
	public void testSimpleInsertThreeB() {
		String thisTest = new Object(){}.getClass().getEnclosingMethod().getName();

		// Values to insert (in order of insertion)
		int[] valuesToInsert = {2, 1, -1};

		// Tree expected after each insert
		String[] expectedTrees = {  // Result following each insert
				"(E=2, P=2)", 
				"(E=1, P=1) (E=2, P=2)",
				"(E=-1, P=-1) (E=2, P=2) (E=1, P=1)",
		};
		checkInserts(valuesToInsert, expectedTrees, thisTest);
	}
	
	@Test
	@Order(5) 
	@Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
	public void testInsertSequenceA() {
		String thisTest = new Object(){}.getClass().getEnclosingMethod().getName();

		// Values to insert (in order of insertion)
		int[] valuesToInsert = {2, 4, 6, 8, 10, 12, 14, 3, 16, 5, 18, 7, 1};

		// Tree expected after each insert
		String[] expectedTrees = {  // Result following each insert
				"(E=2, P=2)",
				"(E=2, P=2) (E=4, P=4)",
				"(E=2, P=2) (E=4, P=4) (E=6, P=6)",
				"(E=2, P=2) (E=4, P=4) (E=6, P=6) (E=8, P=8)",
				"(E=2, P=2) (E=4, P=4) (E=6, P=6) (E=8, P=8) (E=10, P=10)",
				"(E=2, P=2) (E=4, P=4) (E=6, P=6) (E=8, P=8) (E=10, P=10) (E=12, P=12)",
				"(E=2, P=2) (E=4, P=4) (E=6, P=6) (E=8, P=8) (E=10, P=10) (E=12, P=12) (E=14, P=14)",
				"(E=2, P=2) (E=3, P=3) (E=6, P=6) (E=4, P=4) (E=10, P=10) (E=12, P=12) (E=14, P=14) (E=8, P=8)",
				"(E=2, P=2) (E=3, P=3) (E=6, P=6) (E=4, P=4) (E=10, P=10) (E=12, P=12) (E=14, P=14) (E=8, P=8) (E=16, P=16)",
				"(E=2, P=2) (E=3, P=3) (E=6, P=6) (E=4, P=4) (E=5, P=5) (E=12, P=12) (E=14, P=14) (E=8, P=8) (E=16, P=16) (E=10, P=10)",
				"(E=2, P=2) (E=3, P=3) (E=6, P=6) (E=4, P=4) (E=5, P=5) (E=12, P=12) (E=14, P=14) (E=8, P=8) (E=16, P=16) (E=10, P=10) (E=18, P=18)",
				"(E=2, P=2) (E=3, P=3) (E=6, P=6) (E=4, P=4) (E=5, P=5) (E=7, P=7) (E=14, P=14) (E=8, P=8) (E=16, P=16) (E=10, P=10) (E=18, P=18) (E=12, P=12)",
				"(E=1, P=1) (E=3, P=3) (E=2, P=2) (E=4, P=4) (E=5, P=5) (E=6, P=6) (E=14, P=14) (E=8, P=8) (E=16, P=16) (E=10, P=10) (E=18, P=18) (E=12, P=12) (E=7, P=7)",
		};
		checkInserts(valuesToInsert, expectedTrees, thisTest);
	}
	
	@Test
	@Order(6) 
	@Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
	public void testInsertSequenceB() {
		String thisTest = new Object(){}.getClass().getEnclosingMethod().getName();

		// Values to insert (in order of insertion)
		int[] valuesToInsert = {12, 6, 0, 11, 5, 16, 10, 4, 15, 9, 3, 14, 8, 2, 13, 7, 1};

		// Tree expected after each insert
		String[] expectedTrees = {  // Result following each insert
				"(E=12, P=12)",
				"(E=6, P=6) (E=12, P=12)",
				"(E=0, P=0) (E=12, P=12) (E=6, P=6)",
				"(E=0, P=0) (E=11, P=11) (E=6, P=6) (E=12, P=12)",
				"(E=0, P=0) (E=5, P=5) (E=6, P=6) (E=12, P=12) (E=11, P=11)",
				"(E=0, P=0) (E=5, P=5) (E=6, P=6) (E=12, P=12) (E=11, P=11) (E=16, P=16)",
				"(E=0, P=0) (E=5, P=5) (E=6, P=6) (E=12, P=12) (E=11, P=11) (E=16, P=16) (E=10, P=10)",
				"(E=0, P=0) (E=4, P=4) (E=6, P=6) (E=5, P=5) (E=11, P=11) (E=16, P=16) (E=10, P=10) (E=12, P=12)",
				"(E=0, P=0) (E=4, P=4) (E=6, P=6) (E=5, P=5) (E=11, P=11) (E=16, P=16) (E=10, P=10) (E=12, P=12) (E=15, P=15)",
				"(E=0, P=0) (E=4, P=4) (E=6, P=6) (E=5, P=5) (E=9, P=9) (E=16, P=16) (E=10, P=10) (E=12, P=12) (E=15, P=15) (E=11, P=11)",
				"(E=0, P=0) (E=3, P=3) (E=6, P=6) (E=5, P=5) (E=4, P=4) (E=16, P=16) (E=10, P=10) (E=12, P=12) (E=15, P=15) (E=11, P=11) (E=9, P=9)",
				"(E=0, P=0) (E=3, P=3) (E=6, P=6) (E=5, P=5) (E=4, P=4) (E=14, P=14) (E=10, P=10) (E=12, P=12) (E=15, P=15) (E=11, P=11) (E=9, P=9) (E=16, P=16)",
				"(E=0, P=0) (E=3, P=3) (E=6, P=6) (E=5, P=5) (E=4, P=4) (E=8, P=8) (E=10, P=10) (E=12, P=12) (E=15, P=15) (E=11, P=11) (E=9, P=9) (E=16, P=16) (E=14, P=14)",
				"(E=0, P=0) (E=3, P=3) (E=2, P=2) (E=5, P=5) (E=4, P=4) (E=8, P=8) (E=6, P=6) (E=12, P=12) (E=15, P=15) (E=11, P=11) (E=9, P=9) (E=16, P=16) (E=14, P=14) (E=10, P=10)",
				"(E=0, P=0) (E=3, P=3) (E=2, P=2) (E=5, P=5) (E=4, P=4) (E=8, P=8) (E=6, P=6) (E=12, P=12) (E=15, P=15) (E=11, P=11) (E=9, P=9) (E=16, P=16) (E=14, P=14) (E=10, P=10) (E=13, P=13)",
				"(E=0, P=0) (E=3, P=3) (E=2, P=2) (E=5, P=5) (E=4, P=4) (E=8, P=8) (E=6, P=6) (E=7, P=7) (E=15, P=15) (E=11, P=11) (E=9, P=9) (E=16, P=16) (E=14, P=14) (E=10, P=10) (E=13, P=13) (E=12, P=12)",
				"(E=0, P=0) (E=1, P=1) (E=2, P=2) (E=3, P=3) (E=4, P=4) (E=8, P=8) (E=6, P=6) (E=5, P=5) (E=15, P=15) (E=11, P=11) (E=9, P=9) (E=16, P=16) (E=14, P=14) (E=10, P=10) (E=13, P=13) (E=12, P=12) (E=7, P=7)",				
		};
		// Check it
		checkInserts(valuesToInsert, expectedTrees, thisTest);
	}

	
	
	@Test
	@Order(7) 
	@Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
	public void testEntryObjects() {
		// Values to insert (in order of insertion)
		int[] valuesToInsert = {12, 6, 0, 11, 5, 16, 10, 4, 15, 9, 3, 14, 8, 2, 13, 7, 1};
		PQEntry<Integer,Integer>[] entries = new PQEntry[valuesToInsert.length];

		
		/**
		 * This test the PQEntry objects returned by insert(). 
		 * It verifies that the priority and element stay the same for a particular 
		 * object even if the object moves around to a new position in the PQ
		 */
		
		MinPriorityQueue<Integer, Integer> pq = getIntIntPQ();

		
		for(int i=0;i<valuesToInsert.length;i++) {
			int value = valuesToInsert[i];

			// Get the "entry" after inserting this item
			entries[value] = pq.insert(value,value);
			
			assertNotNull(entries[value], "Invalid PQEntry returned from insert");
			
			// Check all Entry objects to make sure they are still correct
			int validEntryObject = 0;
			for(int j=0;j<entries.length;j++) {
				if(entries[j]!=null) {
					assertEquals(j, entries[j].getElement(), String.format("Entry for %d's element has changed", j));
					assertEquals(j, entries[j].getElement(), String.format("Entry for %d's priority has changed", j));
				} 
			}			
		}
	}
	

	
	@Test
	@Order(8) 
	@Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
	public void testSimpleExtractOne() {
		String thisTest = new Object(){}.getClass().getEnclosingMethod().getName();

		MinPriorityQueue<Integer, Integer> pq = getIntIntPQ();

		// Values to insert (in order of insertion);  Same as insert test above (already passed???)
		int[] valuesToInsert = {2};
		for(int i: valuesToInsert) {
			pq.insert(i,i);
		}
		
		int[] expectedValues = {2};
		
		// Tree expected after each insert
		String[] expectedTrees = {  // Result following each extract
				""
		};

		// Check it
		checkExtracts(pq, expectedValues, expectedTrees, thisTest);
	}
	
	
	
	@Test
	@Order(9) 
	@Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
	void testSimpleExtractTwo() {
		String thisTest = new Object(){}.getClass().getEnclosingMethod().getName();

		MinPriorityQueue<Integer, Integer> pq = getIntIntPQ();

		// Values to insert (in order of insertion);  Same as insert test above (already passed???)
		int[] valuesToInsert = {2, 1};
		for(int i: valuesToInsert) {
			pq.insert(i,i);
		}
		
		int[] expectedValues = {1,2};

		
		// Tree expected after each insert
		String[] expectedTrees = {  // Result following each extract
				"(E=2, P=2)",
				""
		};

		// Check it
		checkExtracts(pq, expectedValues, expectedTrees, thisTest);
	}

	
	@Test
	@Order(10) 
	@Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
	void testSimpleExtractThreeA() {
		String thisTest = new Object(){}.getClass().getEnclosingMethod().getName();

		MinPriorityQueue<Integer, Integer> pq = getIntIntPQ();

		// Values to insert (in order of insertion);  Same as insert test above (already passed???)
		int[] valuesToInsert = {2, 1, 3};;
		for(int i: valuesToInsert) {
			pq.insert(i,i);
		}
		
		int[] expectedValues = {1,2,3};

		
		// Tree expected after each insert
		String[] expectedTrees = {  // Result following each extract
				"(E=2, P=2) (E=3, P=3)",
				"(E=3, P=3)",
				"",
		};

		// Check it
		checkExtracts(pq, expectedValues, expectedTrees, thisTest);
	}
	
	@Test
	@Order(11) 
	@Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
	void testSimpleExtractThreeB() {
		String thisTest = new Object(){}.getClass().getEnclosingMethod().getName();

		MinPriorityQueue<Integer, Integer> pq = getIntIntPQ();

		// Values to insert (in order of insertion);  Same as insert test above (already passed???)
		int[] valuesToInsert = {2, 1, -1};
		for(int i: valuesToInsert) {
			pq.insert(i,i);
		}
		
		int[] expectedValues = {-1,1,2};

		
		// Tree expected after each insert
		String[] expectedTrees = {  // Result following each extract
				"(E=1, P=1) (E=2, P=2)",
				"(E=2, P=2)",
				"",
		};

		// Check it
		checkExtracts(pq, expectedValues, expectedTrees, thisTest);
	}
	
	
	@Test
	@Order(12) 
	@Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
	void testExtractSequenceA() {
		String thisTest = new Object(){}.getClass().getEnclosingMethod().getName();

		MinPriorityQueue<Integer, Integer> pq = getIntIntPQ();

		// Values to insert (in order of insertion);  Same as insert test above (already passed???)
		int[] valuesToInsert = {2, 4, 6, 8, 10, 12, 14, 3, 16, 5, 18, 7, 1};
		
		for(int i: valuesToInsert) {
			pq.insert(i,i);
		}
		
		int[] expectedValues = {1,2,3,4,5,6,7,8,10,12,14,16,18};

		
		// Tree expected after each insert
		String[] expectedTrees = {  // Result following each extract
				"(E=2, P=2) (E=3, P=3) (E=6, P=6) (E=4, P=4) (E=5, P=5) (E=7, P=7) (E=14, P=14) (E=8, P=8) (E=16, P=16) (E=10, P=10) (E=18, P=18) (E=12, P=12)",
				"(E=3, P=3) (E=4, P=4) (E=6, P=6) (E=8, P=8) (E=5, P=5) (E=7, P=7) (E=14, P=14) (E=12, P=12) (E=16, P=16) (E=10, P=10) (E=18, P=18)",
				"(E=4, P=4) (E=5, P=5) (E=6, P=6) (E=8, P=8) (E=10, P=10) (E=7, P=7) (E=14, P=14) (E=12, P=12) (E=16, P=16) (E=18, P=18)",
				"(E=5, P=5) (E=8, P=8) (E=6, P=6) (E=12, P=12) (E=10, P=10) (E=7, P=7) (E=14, P=14) (E=18, P=18) (E=16, P=16)",
				"(E=6, P=6) (E=8, P=8) (E=7, P=7) (E=12, P=12) (E=10, P=10) (E=16, P=16) (E=14, P=14) (E=18, P=18)",
				"(E=7, P=7) (E=8, P=8) (E=14, P=14) (E=12, P=12) (E=10, P=10) (E=16, P=16) (E=18, P=18)",
				"(E=8, P=8) (E=10, P=10) (E=14, P=14) (E=12, P=12) (E=18, P=18) (E=16, P=16)",
				"(E=10, P=10) (E=12, P=12) (E=14, P=14) (E=16, P=16) (E=18, P=18)",
				"(E=12, P=12) (E=16, P=16) (E=14, P=14) (E=18, P=18)",
				"(E=14, P=14) (E=16, P=16) (E=18, P=18)",
				"(E=16, P=16) (E=18, P=18)",
				"(E=18, P=18)",
				"",
		};

		// Check it
		checkExtracts(pq, expectedValues, expectedTrees, thisTest);
	}
	
	@Test
	@Order(13) 
	@Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
	void testExtractSequenceB() {
		String thisTest = new Object(){}.getClass().getEnclosingMethod().getName();

		MinPriorityQueue<Integer, Integer> pq = getIntIntPQ();

		// Values to insert (in order of insertion);  Same as insert test above (already passed???)
		int[] valuesToInsert = {12, 6, 0, 11, 5, 16, 10, 4, 15, 9, 3, 14, 8, 2, 13, 7, 1};
		
		for(int i: valuesToInsert) {
			pq.insert(i,i);
		}
		
		int[] expectedValues = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16};

		
		// Tree expected after each insert
		String[] expectedTrees = {  // Result following each extract
				"(E=1, P=1) (E=3, P=3) (E=2, P=2) (E=5, P=5) (E=4, P=4) (E=8, P=8) (E=6, P=6) (E=7, P=7) (E=15, P=15) (E=11, P=11) (E=9, P=9) (E=16, P=16) (E=14, P=14) (E=10, P=10) (E=13, P=13) (E=12, P=12)",
				"(E=2, P=2) (E=3, P=3) (E=6, P=6) (E=5, P=5) (E=4, P=4) (E=8, P=8) (E=10, P=10) (E=7, P=7) (E=15, P=15) (E=11, P=11) (E=9, P=9) (E=16, P=16) (E=14, P=14) (E=12, P=12) (E=13, P=13)",
				"(E=3, P=3) (E=4, P=4) (E=6, P=6) (E=5, P=5) (E=9, P=9) (E=8, P=8) (E=10, P=10) (E=7, P=7) (E=15, P=15) (E=11, P=11) (E=13, P=13) (E=16, P=16) (E=14, P=14) (E=12, P=12)",
				"(E=4, P=4) (E=5, P=5) (E=6, P=6) (E=7, P=7) (E=9, P=9) (E=8, P=8) (E=10, P=10) (E=12, P=12) (E=15, P=15) (E=11, P=11) (E=13, P=13) (E=16, P=16) (E=14, P=14)",
				"(E=5, P=5) (E=7, P=7) (E=6, P=6) (E=12, P=12) (E=9, P=9) (E=8, P=8) (E=10, P=10) (E=14, P=14) (E=15, P=15) (E=11, P=11) (E=13, P=13) (E=16, P=16)",
				"(E=6, P=6) (E=7, P=7) (E=8, P=8) (E=12, P=12) (E=9, P=9) (E=16, P=16) (E=10, P=10) (E=14, P=14) (E=15, P=15) (E=11, P=11) (E=13, P=13)",
				"(E=7, P=7) (E=9, P=9) (E=8, P=8) (E=12, P=12) (E=11, P=11) (E=16, P=16) (E=10, P=10) (E=14, P=14) (E=15, P=15) (E=13, P=13)",
				"(E=8, P=8) (E=9, P=9) (E=10, P=10) (E=12, P=12) (E=11, P=11) (E=16, P=16) (E=13, P=13) (E=14, P=14) (E=15, P=15)",
				"(E=9, P=9) (E=11, P=11) (E=10, P=10) (E=12, P=12) (E=15, P=15) (E=16, P=16) (E=13, P=13) (E=14, P=14)",
				"(E=10, P=10) (E=11, P=11) (E=13, P=13) (E=12, P=12) (E=15, P=15) (E=16, P=16) (E=14, P=14)",
				"(E=11, P=11) (E=12, P=12) (E=13, P=13) (E=14, P=14) (E=15, P=15) (E=16, P=16)",
				"(E=12, P=12) (E=14, P=14) (E=13, P=13) (E=16, P=16) (E=15, P=15)",
				"(E=13, P=13) (E=14, P=14) (E=15, P=15) (E=16, P=16)",
				"(E=14, P=14) (E=16, P=16) (E=15, P=15)",
				"(E=15, P=15) (E=16, P=16)",
				"(E=16, P=16)",
				"",
		};

		// Check it
		checkExtracts(pq, expectedValues, expectedTrees, thisTest);
	}
	
	
	
	@Test
	@Order(14) 
	@Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
	void testUpdatingPriorities() {
		String thisTest = new Object(){}.getClass().getEnclosingMethod().getName();

		MinPriorityQueue<Integer, Integer> pq = getIntIntPQ();

		// Values to insert (in order of insertion);  Same as insert test above 
        					  //0  1  2  3   4  5    6  7  8   9  10  11 12
		int[] valuesToInsert = {2, 4, 6, 8, 10, 12, 14, 3, 16, 5, 18, 7, 1};

		PQEntry<Integer,Integer>[] entries = new PQEntry[valuesToInsert.length];

		for(int i=0;i<valuesToInsert.length;i++) {
			int value = valuesToInsert[i];
			entries[i] = pq.insert(value,value);
		}

		int[] indices = {  0, 10,  7,   1, 4,  12,  5, 0 };
		int[] newPrior = { 9, 0,   18, 17, 3,  19, 10, 1};
		// Change 2's priority to 9
		// 18's to 0
		// 3's to 18
		// 4 to 17
		// 10 to 3
		// 1 to 19
		// 12 to 10 again
		//  

		String[] expectedTrees = {  
				"(E=1, P=1) (E=3, P=3) (E=6, P=6) (E=4, P=4) (E=5, P=5) (E=7, P=7) (E=14, P=14) (E=8, P=8) (E=16, P=16) (E=10, P=10) (E=18, P=18) (E=12, P=12) (E=2, P=9)",
				"(E=18, P=0) (E=1, P=1) (E=6, P=6) (E=4, P=4) (E=3, P=3) (E=7, P=7) (E=14, P=14) (E=8, P=8) (E=16, P=16) (E=10, P=10) (E=5, P=5) (E=12, P=12) (E=2, P=9)",
				"(E=18, P=0) (E=1, P=1) (E=6, P=6) (E=4, P=4) (E=5, P=5) (E=7, P=7) (E=14, P=14) (E=8, P=8) (E=16, P=16) (E=10, P=10) (E=3, P=18) (E=12, P=12) (E=2, P=9)",
				"(E=18, P=0) (E=1, P=1) (E=6, P=6) (E=8, P=8) (E=5, P=5) (E=7, P=7) (E=14, P=14) (E=4, P=17) (E=16, P=16) (E=10, P=10) (E=3, P=18) (E=12, P=12) (E=2, P=9)",
				"(E=18, P=0) (E=1, P=1) (E=6, P=6) (E=8, P=8) (E=10, P=3) (E=7, P=7) (E=14, P=14) (E=4, P=17) (E=16, P=16) (E=5, P=5) (E=3, P=18) (E=12, P=12) (E=2, P=9)",
				"(E=18, P=0) (E=10, P=3) (E=6, P=6) (E=8, P=8) (E=5, P=5) (E=7, P=7) (E=14, P=14) (E=4, P=17) (E=16, P=16) (E=1, P=19) (E=3, P=18) (E=12, P=12) (E=2, P=9)",
				"(E=18, P=0) (E=10, P=3) (E=6, P=6) (E=8, P=8) (E=5, P=5) (E=7, P=7) (E=14, P=14) (E=4, P=17) (E=16, P=16) (E=1, P=19) (E=3, P=18) (E=12, P=10) (E=2, P=9)",
				"(E=18, P=0) (E=10, P=3) (E=2, P=1) (E=8, P=8) (E=5, P=5) (E=6, P=6) (E=14, P=14) (E=4, P=17) (E=16, P=16) (E=1, P=19) (E=3, P=18) (E=12, P=10) (E=7, P=7)",
		};
		checkUpdatePrioroities(pq, entries, indices, newPrior, expectedTrees, thisTest);		
	}
	
	
	@Test
	@Order(15) 
	@Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
	void testRemovesA() throws IOException {
		String thisTest = new Object(){}.getClass().getEnclosingMethod().getName();

		MinPriorityQueue<Integer, Integer> pq = getIntIntPQ();
		  					  //0  1  2  3   4  5    6  7  8   9  10  11 12
		int[] valuesToInsert = {2, 4, 6, 8, 10, 12, 14, 3, 16, 5, 18, 7, 1};

		PQEntry<Integer,Integer>[] entries = new PQEntry[valuesToInsert.length];

		for(int i=0;i<valuesToInsert.length;i++) {
			int value = valuesToInsert[i];
			entries[i] = pq.insert(value,value);
		}

		int[] indices = { 10,  7, 1, 4, 0,   12,  5, 9, 11, 3 };
		
		int size = pq.size();
		for(int i=0;i<indices.length;i++) {
			String before = pq.toString();
			int data = valuesToInsert[indices[i]];
			pq.remove(entries[indices[i]]);
			// Check that order property is maintained and size decreases
			size--;
			assertEquals(size, pq.size(), "size() after remove() incorrect");
			String after = pq.toString();
			String err = childrenNoSmallerThanParent(pq);
			if(err.contentEquals("")==false) {
				TreeSVGRenderer.showErrorNoDelta(before, String.format("Remove item with data %d",data), after, thisTest);				
				assertFalse(true, err);
			}
		}
	}
	
	@Test
	@Order(16) 
	@Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
	void testRemovesB() throws IOException {
		String thisTest = new Object(){}.getClass().getEnclosingMethod().getName();

		MinPriorityQueue<Integer, Integer> pq = getIntIntPQ();
		  					  //0   1  2   3   4   5  6  
		int[] valuesToInsert = {4, 27, 5, 32, 29, 7, 6};

		PQEntry<Integer,Integer>[] entries = new PQEntry[valuesToInsert.length];

		for(int i=0;i<valuesToInsert.length;i++) {
			int value = valuesToInsert[i];
			entries[i] = pq.insert(value,value);
		}

		int[] indices = { 4, 3 };
		
		int size = pq.size();
		for(int i=0;i<indices.length;i++) {
			String before = pq.toString();
			int data = valuesToInsert[indices[i]];
			pq.remove(entries[indices[i]]);
			// Check that order property is maintained and size decreases
			size--;
			assertEquals(size, pq.size(), "size() after remove() incorrect");
			String after = pq.toString();
			String err = childrenNoSmallerThanParent(pq);
			if(err.contentEquals("")==false) {
				TreeSVGRenderer.showErrorNoDelta(before, String.format("Remove item with data %d",data), after, thisTest);				
				assertFalse(true, err);
			}
		}
	}
	
	
	@Test
	@Order(17) 
	@Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
	void testRemovesC() throws IOException {
		String thisTest = new Object(){}.getClass().getEnclosingMethod().getName();

		MinPriorityQueue<Integer, Integer> pq = getIntIntPQ();
							  //0  1  2  3   4  5    6  7  8   9  10  11 12
		int[] valuesToInsert = {2, 4, 6, 8, 10, 12, 14, 3, 16, 5, 18, 7, 1};

		PQEntry<Integer,Integer>[] entries = new PQEntry[valuesToInsert.length];

		for(int i=0;i<valuesToInsert.length;i++) {
			int value = valuesToInsert[i];
			entries[i] = pq.insert(value,value);
		}

		for(int i=0;i<entries.length;i++) {
			// Trying to remove once should return true
			assertTrue(pq.remove(entries[i]));
			// Trying to remove twise should return true
			assertFalse(pq.remove(entries[i]));
		}
	}
	
	
	
	public static void checkInserts(int[] valuesToInsert, String[] expectedTrees, String thisTest) {

		// Get a PQ
		MinPriorityQueue<Integer, Integer> pq = getIntIntPQ();

		String result = pq.toString();
		for(int i=0;i<valuesToInsert.length; i++) {
			// Get the value to insert
			int v = valuesToInsert[i];  			
			
			// Apply the insert operation
			result = applyOperation(pq, 
									result, 
									String.format("insert(%d, %d)",v, v),
									()->{ pq.insert(v, v);},
									expectedTrees[i], 
									thisTest);
			assertNotNull(result, checkErrorFile(thisTest));
			assertEquals(i+1, pq.size(), "size() is incorrect");
			int expectedPosition=0;
			for(PQEntry<?,?> entry: pq) {
				int actualPosition = getPosition(entry);
				assertEquals(expectedPosition, actualPosition, String.format(".position of item at index %d is incorrect", expectedPosition));
				expectedPosition++;
			}
			assertEquals(i+1, expectedPosition, "Not enough entries"); // Should never happen!
		}
		
	}
	
	
	public static PQEntry<Integer, Integer> shared;  // Share an object while test is running 
	
	public static void checkExtracts(MinPriorityQueue<Integer, Integer> pq, int[] expectedValues, String[] expectedTrees, String thisTest) {

		// Get a PQ
		int currentSize = pq.size();
		String result = pq.toString();
		for(int i=0;i<expectedValues.length; i++) {
			// Get the value to insert
			int v = expectedValues[i];  			
			// Apply the insert operation
			shared = null;
			result = applyOperation(pq, 
									result, 
									String.format("extractMin()",v, v),
									()->{ shared = pq.extractMin();},
									expectedTrees[i], 
									thisTest);

			assertNotNull(shared, "No Entry returned from extractMin");
			assertEquals(expectedValues[i], shared.getPriority(), String.format("Extracting the %d item had incorrect priority", i+1));
			assertEquals(expectedValues[i], shared.getElement(), String.format("Extracting the %d item had incorrect element", i+1));
			assertNotNull(result, checkErrorFile(thisTest));
			currentSize--;
			assertEquals(currentSize, pq.size(), "size() is incorrect");
			int expectedPosition=0;
			for(PQEntry<?,?> entry: pq) {
				int actualPosition = getPosition(entry);
				assertEquals(expectedPosition, actualPosition, String.format("Position of item at index %d is incorrect", expectedPosition));
				expectedPosition++;
			}
			assertEquals(currentSize, expectedPosition, "Not enough entries"); // Should never happen!
		}		
	}
	
	
	public static void checkUpdatePrioroities(MinPriorityQueue<Integer, Integer> pq, 
											  PQEntry<Integer, Integer>[] entries, 
											  int[] forItem,
											  int[] newPriorityValues, 
											  String[] expectedTrees, 
											  String thisTest) {

		// Get a PQ
		int currentSize = pq.size();
		String result = pq.toString();
		// Iterate through list of priority Values
		for(int i=0;i<newPriorityValues.length; i++) {
			int itemIndex = forItem[i];
			// Get the element data before the update
			PQEntry<Integer, Integer> item = entries[itemIndex];
			int elementData = item.getElement();

			// Get the new value for its priority
			int v = newPriorityValues[i];  			
			// Apply the insert operation
			result = applyOperation(pq, 
									result, 
									String.format("updatePriority of item with data %d to %d", elementData, v),
									()->{ item.updatePriority(v);},
									expectedTrees[i], 
									thisTest);

			assertEquals(v, item.getPriority(), String.format("After update the entry had incorrect priority"));
			assertEquals(elementData, item.getElement(), String.format("After update the entry had incorrect element"));
			assertNotNull(result, checkErrorFile(thisTest));
			assertEquals(currentSize, pq.size(), "size() is incorrect");
			int expectedPosition=0;
			for(PQEntry<?,?> entry: pq) {
				int actualPosition = getPosition(entry);
				assertEquals(expectedPosition, actualPosition, String.format("Position of item at index %d is incorrect", expectedPosition));
				expectedPosition++;
			}
			assertEquals(currentSize, expectedPosition, "Not enough entries"); // Should never happen!
		}		
	}
	
	private static int getPosition(PQEntry<?,?> entry) {
		Object o = entry;
  	    Field field = null;
		try {
			field = o.getClass().getDeclaredField("position");
		} catch (NoSuchFieldException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}    
	    field.setAccessible(true);
	    Integer val = null;
		try {
			val = (Integer)field.get(o);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return val;
	}
	
	/**
	 * Scan the heap and make sure that no child's value exceeds its
	 * parent's value.   Recall from lecture that empty nodes in the binary
	 * heap tree are essentially infinite, so we only have to check a child
	 * that is in the active part of the heap.
     *
     * @param pq PQ whose entries to verify
     *
     * @return String representing result of check
	 */
	public String childrenNoSmallerThanParent(MinPriorityQueue<Integer, Integer> pq) {
		String err = "";
		PQEntry<Integer, Integer>[] currentEntries = new PQEntry[pq.size()];
		int j=0;
		for(PQEntry<Integer, Integer> e: pq) {
			currentEntries[j++] = e;
		}
		//
		// Loop while the node at i is not a leaf. 
		//
		for (int i=0; 2*i+1 < currentEntries.length; ++i) {

			if (2*i + 2 < currentEntries.length) {
				if(currentEntries[i].getPriority().compareTo(currentEntries[2*i + 2].getPriority()) > 0) {
					err = err + "The node at index " 
							+ i 
							+ " is larger than its right child: "
							+ currentEntries[i] + ">" + currentEntries[2*i+2]
							+ "\n"
							;
				}
			}

			if(currentEntries[i].getPriority().compareTo(currentEntries[2*i+1].getPriority()) > 0) {
				err = err + "The node at index " 
						+ i 
						+ " is larger than its left child: "
						+ currentEntries[i] + ">" + currentEntries[2*i+1]
						+ "\n"
						;			
			}


		}
		return err;

	}
	
	
}

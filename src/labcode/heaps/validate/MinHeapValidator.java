package heaps.validate;

import java.lang.reflect.Field;

import heaps.MinHeap;
import heaps.PQEntry;
import heaps.util.HeapToStrings;

public class MinHeapValidator<E, P extends Comparable<P>> {
	final public MinHeap<E,P> pq;
	private String before;
	private PQEntry<E,P>[] currentEntries;

	public MinHeapValidator(final MinHeap<E,P> pq) {
		this.pq = pq;
		this.before = pq.toString();

		//
		//Every time the heap is altered, check to make sure that no
		//min-heap properties are violated
		//
	}
	
	/**
	 * The instance variable "before" captures the state of the heap
	 * last time we looked.   This method runs our validation methods,
	 * and if something goes wrong, it reports the before tree, the error,
	 * and the after tree.  This should provide enough information to
	 * diagnose your problems with your binary heap implementation.
	 */
	public void check() {
		try {
			// Get all the current entries in an array
			currentEntries = (PQEntry<E, P>[]) HeapToStrings.toEntriesArray(pq);
			checkForGaps();
			childrenNoSmallerThanParent();
			checkLocsCorrect();
			before = HeapToStrings.toTree(pq);
		} catch(Throwable t) {
			String oops = "\nTree before the problem occurred:\n";
			oops += before + "\n";
			oops += "What went wrong: " + t.getMessage() + "\n";
			// System.out.println("Its stack trace is ");
			// t.printStackTrace();
			oops += "Tree that triggered this problem:" + "\n";
			oops += HeapToStrings.toTree(pq);
			t.printStackTrace();
			throw new HeapValidationError(t + "" + oops);
		}
	}





	/**
	 * Scan the heap and make sure that no child's value exceeds its
	 * parent's value.   Recall from lecture that empty nodes in the binary
	 * heap tree are essentially infinite, so we only have to check a child
	 * that is in the active part of the heap.
	 */
	public void childrenNoSmallerThanParent() {
		//
		// Loop while the node at i is not a leaf. 
		//
		for (int i=0; 2*i+1 < pq.size(); ++i) {
			String err = "";

			if (2*i + 2 < pq.size()) {
				if(currentEntries[i].getPriority().compareTo(currentEntries[2*i + 2].getPriority()) > 0) {
					err = "The node at index " 
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
			if (!err.equals("")) {
				System.err.println("ERROR: " +err);
				throw new Error(err);
			}

		}
	}

	/**
	 * Make sure that the binary heap is "nearly complete" as described
	 * in lecture.   This consists of two thing:
	 *   1)  There should be no null entries in the currently active part
	 *       of your heap array.   The range of the active heap is
	 *       from 1...pq.size() inclusively.
	 *   2)  In the inactive part of the heap, all of the elements there
	 *       should be null.  The range of the inactive part is
	 *       from pq.size()+1...pq.capacity() inclusively.
	 *       
	 *       You might say, and you'd be right, that your heap would function
	 *       correctly even if those elements in the inactive part are *not* null.
	 *       However, references from the heap contribute to the liveness of
	 *       their referenced objects.  Objects with live references cannot be
	 *       garbage collected, and we do not want the binary heap to be responsible
	 *       for artificially extending the life of otherwise dead objects.
	 */
	public void checkForGaps() {
		//
		// No nulls in active part of heap
		//
		for (int i=0; i < pq.size(); ++i) {
			if (currentEntries[i] == null) {
				String err = "I found a null entry within the currently occupied portion of the heap, at index " + i;
				System.err.println("ERROR: " +err);
				throw new Error(err);
			}
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
	 * Check that each HeapPQEntry object has the proper position
	 *   information.
	 */
	public void checkLocsCorrect() {
		for (int i=0; i < pq.size(); ++i) {			
			int loc = getPosition(currentEntries[i]);
			if (loc != i) {
				String err = "In your array at index " + i + 
						" the PQEntry object has the wrong position."
						+ " It should be " + i + " but was " + loc;
				System.err.println("ERROR: " +err);
				throw new Error(err);
			}
		}
	}
}

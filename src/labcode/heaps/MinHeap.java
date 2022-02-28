package heaps;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import javax.swing.JOptionPane;
import heaps.util.HeapToStrings;
import heaps.validate.MinHeapValidator;
import timing.Ticker;


public class MinHeap<E, P extends Comparable<P>> implements MinPriorityQueue<E,P> {


	private static final int INVALID_POSITION = -1;    // Sentinel value for an invalid position in heap storage


	/**
	 * Class to hold a PQEntry (Value and Priority pair).
	 * Also includes data needed specifically for an Adaptable Heap-based PQ's Entry: 
	 *  the position of the Entry in the heap storage.  This is useful when updating an Entry's
	 *  priority directly via the Entry object, to avoid needing to search through the 
	 *  heap storage to find the Entry in order to fix up the heap to restore the heap
	 *  ordering property, if necessary.
	 * This is an "inner class".  See: https://docs.oracle.com/javase/tutorial/java/javaOO/nested.html for details.
	 * 

	 */

	private class HeapPQEntry implements PQEntry<E, P> {
		// DONE: Do not modify
		private final E element;  // Data Element
		private P priority;       // Priority
		private int position;     // Current index in heap storage

		/**
		 * Construct a new Entry element 
		 * @param element element data
		 * @param priority priority value
		 */

		private HeapPQEntry(E element, P priority) {
			// DONE: Do not modify
			this.element = element;
			this.priority = priority;	
			this.position = INVALID_POSITION;
		}


		@Override

		public E getElement() {
			// DONE: Do not modify
			return element;
		}


		@Override

		public P getPriority() {
			// DONE: Do not modify
			return priority;
		}

		@Override

		public void updatePriority(P newPriority) {
			// DONE: Do not modify
			// Change the priority
			this.priority = newPriority; 
			// Ask the MinHeap to fix up the heap as necessary 
			//  to restore the heap ordering property, in light of the updated priority.
			MinHeap.this.repairHeapAtEntry(this);
		}


		@Override
		public String toString() {
			// DONE: Do not modify (We really really mean that.  The unit tests depend on this!)
			return "(E=" + element + ", P=" + priority + ")";
		}
	}


	/****************************** CLASS FIELDS ******************************/



	// Heap storage: an array of Entry objects
	
	private final ArrayList<HeapPQEntry> entries;

	// Bookkeeping: not necessary for functionality

	private final Ticker ticker;


	// DONE: Do not add more instance variables


	@SuppressWarnings("unchecked")

	@Override

	public Iterator<PQEntry<E, P>> iterator() {
		// DONE: Do not modify (We really really mean that.  The unit tests depend on this!)		
		ArrayList<? extends PQEntry<E,P>> alsoEntries = this.entries;
		return (Iterator<PQEntry<E, P>>)alsoEntries.iterator();
	}


	/**
	 *   Represent the heap as a sequence of ordered pairs, with each
	 *   pair containing the data and priority of an Entry in the heap.
	 *   Sequence is in order of heap storage, so level-by-level starting 
	 *   with the root. 
	 */

	public String toString() {
		// DONE: Do not modify (We really really mean that.  The unit tests depend on this!)
		String s = "";
		for(PQEntry<?,?> e:entries) {
			s+=e + " ";
		}
		return s.trim();  // Remove the trailing space
	}


	/**
	 * Create a min heap with given capacity and bookkeeping ticker.
	 * 
	 * @param maxSize max anticipated size of heap
	 * @param ticker bookkeeping operations accumulator
	 */
	@SuppressWarnings("unchecked")
	public MinHeap(int maxSize, Ticker ticker) {
		// DONE: Do not modify
		this.entries = new ArrayList<HeapPQEntry>(maxSize);
		this.ticker = ticker;
	}	

	/**
	 * Create an empty min heap.
	 */
	public MinHeap() {
		// DONE: Do not modify
		this.entries = new ArrayList<HeapPQEntry>();
		this.ticker = new Ticker();
	}

	/**
	 * Swap the Entries at indices i and j in the heap storage.
	 * 
	 * @param i position of one object to swap
	 * @param j position of other object to swap
	 */

	private void swap(int i, int j) {
		// TODO
		// Remember to update the Entries to reflect their new positions.
		HeapPQEntry temp1st = entries.get(i);
		HeapPQEntry temp2nd = entries.get(j);
		temp1st.position = j;
		temp2nd.position = i;
		entries.set(j, temp1st);
		entries.set(i, temp2nd);
		ticker.tick(6);
	}

@Override
	public boolean isEmpty() {
		// DONE: Do not modify
		return entries.isEmpty();
	}

@Override
	public int size() {
		// TODO
		ticker.tick();
		return entries.size();
	}


	/**
	 * Update heap to restore the heap ordering property if necessary,
	 *  given that the Entry passed as a parameter (and only this Entry) 
	 *  may currently violate the heap ordering property due to having its
	 *  priority updated.
	 *

	 * @param entry Entry with which to start 

	 */
	private void repairHeapAtEntry(MinHeap<E, P>.HeapPQEntry entry) {
		// TODO 
		// Heap should be in a consistent state after executing this method.
		int beginning = entry.position;
		ticker.tick();
		bubbleDown(beginning);
		bubbleUp(beginning);
	}


	@Override
	public PQEntry<E, P> insert(E thing, P priority) {
		// TODO
		HeapPQEntry element =new HeapPQEntry(thing, priority);
		element.position =size();
		entries.add(element) ;
		repairHeapAtEntry(element) ;
		ticker.tick(4) ;
		return element ;
	}


	@Override

	public PQEntry<E, P> extractMin() {
		// TODO
		ticker.tick();
		if(0 == size() ) {
			return null;
		} 
		
		else if(1 == size() ) {
			HeapPQEntry rtNode = entries.get(0);
			entries.remove(0);
			ticker.tick(3);
			return rtNode;
		} 
		
		else {
			HeapPQEntry rtNode = entries.get(0);
			swap(rtNode.position, size() - 1);
			entries.remove(-1 + size());
			bubbleDown(0);
			ticker.tick(5);
			return rtNode;
		}
	}

	/**
	 * Remove Entry stored at the specified index from the heap,
	 *  and repair the heap as necessary.
	 *  
	 * @param index index in array storage at which to remove Entry
	 */
	
	private void removeAtIndex(int index) {
		// TODO	
		// NOTE: implementing this method is optional.
		//       Full credit may be earned for the assignment
		//       without implementing this method.
	}

	@Override
	
	public PQEntry<E, P> peekMin() {
		// DONE: Do not modify
		return isEmpty() ? null : entries.get(0);
	}

	/**
	 * Index of the Entry in heap storage that is the
	 *  parent of the index passed as a parameter.
	 * @param index current index
	 * @return parent index
	 */

	private int parentIndex(int index) {
		ticker.tick();
		int parentI = (index - 1) / 2;
		return (int)(parentI);
		
	}


	/**
	 * Index of the Entry in heap storage that is the
	 *  left child of the index passed as a parameter.
	 * @param index current index
	 * @return left child index
	 */
	
	private int leftChildIndex(int index) {
		// TODO
		ticker.tick();
		return 1 + (index * 2);
	}

	/**
	 * Index of the Entry in heap storage that is the
	 *  right child of the index passed as a parameter.
	 * @param index current index
	 * @return right child index
	 */

	private int rightChildIndex(int index) {
		// TODO
		ticker.tick();
		return 2 + (index * 2);
	}

	/**
	 * Propagate the Entry stored in the heap at the index
	 * passed in as a parameter downward as necessary 
	 * to restore the heap ordering property.
	 * @param startIndex current index
	 */

	private void bubbleDown(int startIndex) {
		// TODO
		int rtIndex = rightChildIndex(startIndex);
		int lftIndex = leftChildIndex(startIndex);
		HeapPQEntry minimum = null;
		int minimumIndex = -1;
		HeapPQEntry parent = entries.get(startIndex);
		ticker.tick(5);
		if(size() - 1 < lftIndex ) {
			ticker.tick(2);
			return;
		}

		if(size() - 1 < rtIndex ) {
			minimum = entries.get(lftIndex);
			minimumIndex = lftIndex;
			ticker.tick(3);

			if(minimum.getPriority().compareTo(parent.getPriority()) < 0) {
				swap(minimum.position, parent.position);
				ticker.tick(2);
				bubbleDown(minimumIndex);
			}
		
		} else {
			ticker.tick();

			if(entries.get(rtIndex).getPriority().compareTo(entries.get(lftIndex).getPriority()) > 0) {
				minimum = entries.get(lftIndex);
				minimumIndex = lftIndex;
				ticker.tick(3);
			} 
			
			else if(entries.get(rtIndex).getPriority().compareTo(entries.get(lftIndex).getPriority()) == 0){
				minimum = entries.get(rtIndex);
				minimumIndex = rtIndex;
				ticker.tick(3);
			}
			
			else {
				minimum = entries.get(rtIndex);
				minimumIndex = rtIndex;
				ticker.tick(3);
			}

			if(minimum.getPriority().compareTo(parent.getPriority()) < 0) {
				swap(minimum.position, parent.position);
				ticker.tick(2);
				bubbleDown(minimumIndex);
			}

		}

	}

	
	/**
	 * Propagate the Entry stored in the heap at the index
	 * passed in as a parameter upward as necessary 
	 * to restore the heap ordering property.
	 * @param startIndex current index
	 */

	private void bubbleUp(int startIndex) {
		// TODO
		HeapPQEntry childNode = entries.get(startIndex);
		int parentIndex = parentIndex(startIndex);
		ticker.tick(2);

		if(0 <= parentIndex) {
			HeapPQEntry parent = entries.get(parentIndex(startIndex));
			ticker.tick(2);
			
			if(childNode.getPriority().compareTo(parent.getPriority()) < 0){
				swap(childNode.position, parent.position);
				bubbleUp(parentIndex);
				ticker.tick(3);
			}

		} 
		
		else {
			ticker.tick();
			return;
		}
	}

@Override

	public boolean remove(PQEntry<E, P> entry) {
		// Type cast the PQEntry to get access to the .position
		HeapPQEntry heapEntry = (HeapPQEntry)entry;
		// TODO
		// NOTE: implementing this method is optional.
		//       Full credit may be earned for the assignment
		//       without implementing this method.
		return false;
	}

	/**
	 * This is not the unit test, but you can run this as a Java Application
	 * and it will insert and extract 100 elements into the heap, printing
	 * the heap each time it inserts.
	 * 
	 * @param args arguments to main method
	 * @throws IllegalAccessException illegal access
	 * @throws IllegalArgumentException illegal argument
	 */

	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException {
		JOptionPane.showMessageDialog(null, "You are welcome to run this, but be sure also to run the TestMinHeap JUnit test");
		MinHeap<Integer, Integer> h = new MinHeap<Integer, Integer>(500, new Ticker());
		MinHeapValidator<Integer, Integer> v = new MinHeapValidator<Integer, Integer>(h);
		Random r = new Random();

		for (int i=0; i < 100; ++i) {
			v.check();
			Integer value = r.nextInt(1000);
			h.insert(value, value);
			v.check();
			System.out.println(HeapToStrings.toTree(h));
			//System.out.println("heap is " + h);
		}

		while (!h.isEmpty()) {
			int next = h.extractMin().getElement();
			System.out.println("Got " + next);
		}
	}
}
package heaps.util;

import java.util.LinkedList;
import java.util.List;

import heaps.MinHeap;
import heaps.PQEntry;

public class HeapToStrings {
	
	// ************ CLASS METHODS *******************
	
	
	public static PQEntry<?,?>[] toEntriesArray(MinHeap<?,?> heap) {
		PQEntry<?,?>[] array = new PQEntry<?,?>[heap.size()];
		int i=0;
		for(PQEntry<?,?> e: heap) {
			array[i++] = e;
		}
		return array;
	}
	

	public static String toTree(MinHeap<?,?> heap) {
		return new HeapToStrings(heap).formatAsTree();
	}

	public static String toOutline(MinHeap<?,?> heap) {
		return new HeapToStrings(heap).formatVertically();
	}
	
	
	
	
	private static String center(String s, int width) {
		if (s.length() > width) {
			throw new Error("String  " + s + " already longer than " + width);
		}
		String ans = s;
		boolean left = true;
		while (ans.length() < width) {
			if (left)
				ans = " " + ans;
			else
				ans = ans + " ";
			left = !left;
		}
		return ans;
	}
	
	
	//
	// The template type can be ? because we don't need the type
	//   for anything when we print.
	//
	final private PQEntry<?,?>[] heapEntries;
	

	private HeapToStrings(MinHeap<?,?> heap) {
		this.heapEntries = toEntriesArray(heap);
	}
	
	private String spacing(int level) {
		String ans = "";
		for (int i=0; i < level; ++i) {
			ans = ans + "  ";
		}
		return ans;
	}

	/**
	 * Formats the tree with indentation to show level.
	 * This is NOT the way you see heaps in a text, but it is
	 * common to show nested and recursive structures this way.
	 * @param n index into the heap
	 * @param level depth of recursion
	 * @return a String representing this node and below
	 */
	private String formatVertically(int n, int level) {
		if (n == -1 || n >= heapEntries.length || heapEntries[n] == null) 
			return "";
		else {
			String ans = spacing(level);
			ans = ans + heapEntries[n] + "\n";
			ans = ans + formatVertically(getLeftChildIndex(n),  level+1);
			ans = ans + formatVertically(getRightChildIndex(n), level+1);
			return ans;
		}
	}
	
	private String formatVertically() {
		return formatVertically(1, 0);
	}

	private int getParentIndex(int child) {
		return (child-1) / 2;
	}

	private int getLeftChildIndex(int parent) {
		int ans = parent*2+1;
		return ans >= heapEntries.length ? -1 : ans;
	}

	
	private int getRightChildIndex(int parent) {
		int ans = parent*2+2;
		return ans >= heapEntries.length ? -1 : ans;
	}

	private int treeHeight() {
		int ans = 0;
		int s   = heapEntries.length;
		while(s != 0) {
			s = s >>> 1;
		ans = ans + 1;
		}
		return ans;
	}



	private String formatEntry(String s, int maxWidth, int centeredOver) {
		String entry = center(s, maxWidth);
		int answidth =  maxWidth*centeredOver + (centeredOver-1);
		return center(entry,answidth);
	}
	
	/**
	 * Returns a representation of the heap in the form one usually sees.
	 *   It looks like a tree with the root centered on the first line.
	 * @return the heap as a tree
	 */
	public String formatAsTree() {
		int height = treeHeight();
		int maxWidth = 0;
		for (int i = 0; i < heapEntries.length; ++i) {
			Object thing = heapEntries[i];
			String s = thing + "";
			maxWidth = Math.max(maxWidth, s.length());
		}
		if (maxWidth % 2 == 0)
			maxWidth = maxWidth + 1;
		int frontier = (1 << (height-1)) & 0x7fffffff;
		String ans = "";
		List<Integer> lnodes = new LinkedList<Integer>();
		List<Integer> cnodes = new LinkedList<Integer>();
		if (heapEntries.length > 0)
			lnodes.add(0);
		while (!lnodes.isEmpty()) {
			while (!lnodes.isEmpty()) {
				int index = lnodes.remove(0);
				String e = index == -1 ? "" : ""+heapEntries[index];
				String s = formatEntry(e, maxWidth, frontier);
				ans = ans + s;
				if (!lnodes.isEmpty()) {
					ans = ans + " ";
				}
				if (index != -1) {
					for (int c : new int[] { getLeftChildIndex(index), getRightChildIndex(index) }) {
						if (c != -1) 
							cnodes.add(c);
					}
				}
			}
			ans = ans + "\n";
			frontier = frontier / 2;
			lnodes = cnodes;
			cnodes = new LinkedList<Integer>();
		}

		return ans;
	}
	
}

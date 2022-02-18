package heaps.tests;

import timing.ExecuteAlgorithm;
import timing.utils.GenSizes;
import timing.utils.IntArrayGenerator;

/**
 * 
 * Run timing tests on MinHeap
 * implementation via HeapSort. Exercises running time of extractMin() and insert() operations.
 *
 */
public class HeapTimer {


	public HeapTimer() {
	}



	public static void main(String[] args) {
		runExperiment(30000);   
	}

	/**
	 * Run HeapSort experiment on random arrays of incremental sizes
	 * based on the factor passed in.
	 * Store timing results for both wall-clock time and "ticker" counts
	 * in the "outputs" folder.
	 * 
	 * @param factor scale factor by which to multiply start/end array sizes
	 *        to generate random arrays for HeapSort tests
	 */
	private static void runExperiment(int factor) {
		int start = 5;
		int end   = 25;
		GenSizes sizes = GenSizes.arithmetic(start*factor, end*factor, factor);
		ExecuteAlgorithm.timeAlgorithm(
				"heapsort", 
				"heaps.HeapSort", 
				new IntArrayGenerator(), 
				sizes
				);
	}

}

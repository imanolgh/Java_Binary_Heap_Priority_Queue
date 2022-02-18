package heaps.testOld;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import heaps.MinHeap;
import heaps.MinPriorityQueue;

@TestMethodOrder(OrderAnnotation.class)
public class MinPQPerformanceTests {
	public static MinPriorityQueue<Integer, Integer> getIntIntPQ() {
		return new MinHeap<Integer, Integer>();
	}
	public static Integer[] genInput(int n) {
		ArrayList<Integer> values = new ArrayList();
		for(int i=0;i<n; i++) {
			values.add(i);
		}
		Collections.shuffle(values);
		return values.toArray(new Integer[1]);
		
	}
	
//	static volatile long junk;
	
	public static void heapSort(MinPriorityQueue<Integer, Integer> pq, Integer[] originalArray, Integer[] sorted) {
		int junk=0;
		for (Integer num : originalArray) {
				pq.insert(num, num);
				junk++;
			}
			int i=0;
			while(pq.isEmpty() == false) {
				int next = pq.extractMin().getElement();
				sorted[i++] = next;
				junk++;
				for(int j=0;j<sorted.length;j++) {
					if(sorted[j]!=null)
						junk++;
				}
			}
		}

	
	public static double timingTest(int n) {
		
		double time = 0;
		
		// 1. Generate input
		for(int i=0;i<3;i++) {
			Integer[] sorted = new Integer[n];
			Integer[] input = genInput(n);
			MinPriorityQueue<Integer, Integer> pq = getIntIntPQ();

			long start = System.nanoTime();
			heapSort(pq, input, sorted);
			long stop = System.nanoTime();
			time += stop-start;
		}
		time /= 3;  // Average time
		return time;
	
	}
	
	public static double lg2(double x) {
		return Math.log(x)/Math.log(2);
	}
	
	@Test
	@Order(1) 
	@Timeout(value = 1500, unit = TimeUnit.MILLISECONDS)
	public void testSimpleInsertOne() {
			
		double timeFor4000 = timingTest(4000);
		// Find time constant
		double c = timeFor4000/(4000*lg2(4000));

		double timeFor16000 = timingTest(16000);
		double expectedTime = 16000*lg2(16000)*c;
		
		System.out.println("c: "+c);
		System.out.println("4000: "+timeFor4000);
		System.out.println("16000: "+timeFor16000);
		System.out.println("Predicted: "+expectedTime);
		double relativeError = Math.abs(timeFor16000)/expectedTime;
		System.out.println(relativeError);
	}
}

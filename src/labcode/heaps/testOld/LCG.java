package heaps.testOld;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Linear Congruential Generator
 * 
 * See: https://en.wikipedia.org/wiki/Linear_congruential_generator
 * @author bsiever
 *
 */
public class LCG {
	
	private int seed;
	private int multiplierA;  
	private int modulusM; 	  
	private int incrementC;   
	
	public LCG(int seed) {
		this.seed = seed;
		// Defaults from glibc
		this.multiplierA = 1103515245;
		this.incrementC = 12345;
		this.modulusM = 1<<31;  
	}

	public LCG(int seed, int multiplier, int increment, int modulus) {
		this.seed = seed;
		this.multiplierA = multiplier;
		this.incrementC = increment;
		this.modulusM = modulus;  
	}

	public int nextInt() {
		seed = (multiplierA*seed + incrementC) % modulusM;
		return seed;
	}	
	
	public static void main(String[] args) {
		// Two Digit numbers
		//   97 is prime 
		//   Any c is co-prime; any a is valid
		
		// If m is prime and c is 0, a must be a primitive element
		// If m is a power of 2 and c is 0, period is m/4 if a is congurent to either 3 or 5 mod 8
		// If c!=0, m and c are coprime, A-1 is divisible by all prime factors of M; A-1 is divisible by 4 if m is
		               // s   A   C   M
		LCG lcg = new LCG(1, 52, 11, 17);  // Sequence 0-16

		
//		LCG lcg = new LCG(1, 195, 15, 97);
		HashMap<Integer, Integer> hs = new HashMap<Integer, Integer>();
		for(int i=0;i<100;i++) {
			int value = lcg.nextInt();
			
			Integer lastIndex = hs.get(value);
			if(lastIndex!=null)
				System.out.println("Duplicate at index: " + lastIndex);

			System.out.println(i + " : " + value);
			hs.put(value, i);
		}
	}
}

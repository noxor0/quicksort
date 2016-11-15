package quicksort;

import java.util.Random;

/**
 * @author concox
 * 11.14.16
 *
 * Ignoring raw type warnings because I'm lazy.
 *
 * Quick Sort Class for Assignment 3
 * Sources referenced:
 * http://www.java2novice.com/java-sorting-algorithms/quick-sort/
 * http://stackoverflow.com/questions/6740183/quicksort-with-first-element-as-pivot-example
 * https://github.com/ahorn/benchmarks/blob/master/path-symex/quick-sort-safe.c
 */
public class QuickSort {

	public static void main(String[] args) {
		//Make 2 int arrays with random numbers
		int n = 10000;
		Integer[] aFirst = new Integer[n];
		Integer[] aMid = new Integer[n];
		Integer[] aSafe = new Integer[n];
		
		for(int i = 0; i < n; i++) {
			Random rand = new Random();
			int randNumb = rand.nextInt(10000);
			aFirst[i] = randNumb;
			aMid[i] = randNumb;
			aSafe[i] = randNumb;
		}
		
		RunQSFirst(aFirst);
		RunQSMid(aMid);
		RunQSSafe(aSafe);
	}
	
	@SuppressWarnings("rawtypes")
	private static void QuicksortFirst(Comparable[] a) {
		Quicksort1st(a, 0, a.length - 1);
	}

	@SuppressWarnings("rawtypes")
	private static void Quicksort1st(Comparable[] a, int l, int r) {
		if (r > l) {
			int p = PivotFirst(a, l, r);
			Quicksort1st(a, l, p - 1);
			Quicksort1st(a, p + 1, r);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static int PivotFirst(Comparable[] a, int l, int r) {
		Comparable pivot = a[l];
		int i = l + 1;
		int j = i;
		while (j <= r) {
			if (a[j].compareTo(pivot) < 0) {
				swap(a, i++, j);
			}
			j++;
		}
		i--;
		swap(a, l, i);
		return i;
	}
	
	@SuppressWarnings("rawtypes")
	private static void QuicksortMid(Comparable[] a) {
		QuicksortMid(a, 0, a.length - 1);
	}

	@SuppressWarnings("rawtypes")
	private static void QuicksortMid(Comparable[] a, int l, int r) {
		if (r > l) {
			int p = PivotMid(a, l, r);
			QuicksortMid(a, l, p - 1);
			QuicksortMid(a, p + 1, r);
		}
	}

	@SuppressWarnings("rawtypes")
	private static int PivotMid(Comparable[] a, int l, int r) {
		swap(a, l, (r+l)/2);
		return PivotFirst(a, l, r);
	}
	
	@SuppressWarnings("rawtypes")
	private static void QuicksortSafe(Comparable[] a) {
		QuicksortSafe(a, 0, a.length - 1);
	}

	@SuppressWarnings("rawtypes")
	private static void QuicksortSafe(Comparable[] a, int l, int r) {
		if (r > l) {
			int p = PivotMid(a, l, r);
			if (p - 1 <= r -p) {
				QuicksortSafe(a, l, p -1); //shorter
				l = p + 1;
			} else {
				QuicksortSafe(a, p + 1, r);
				r = p - 1;
			}
		}
	}

	@SuppressWarnings("rawtypes")
	private static void swap(Comparable[] a, int i, int j) {
		Comparable t = a[i];
		a[i] = a[j];
		a[j] = t;
	}
	
	private static void RunQSSafe(Integer[] input) {
		long elapsedSafe = 0;
		long elapsedSafeS = 0;
		long start;
		long end;
		try {
			start = System.nanoTime();
			QuicksortSafe(input);
			end = System.nanoTime();
			elapsedSafe = (end - start) / 1000000;
		} catch(StackOverflowError e) {
			System.out.println("Unsorted Safe Quicksort overflow on input size " + input.length);
		}
		
		start = 0;
		end = 0;
		try {
			start = System.nanoTime();
			QuicksortSafe(input);
			end = System.nanoTime();
			elapsedSafeS = (end - start) / 1000000;
		} catch(StackOverflowError e) {
			System.out.println("Sorted Safe Quicksort overflow on input size " + input.length);
		}
		System.out.println("Unsorted QuicksortFirst time: " + elapsedSafe + " ms");
		System.out.println("Sorted QuicksortFirst time: " + elapsedSafeS + " ms\n");
	}

	private static void RunQSFirst(Integer[] input) {
		long elapsedFirst = 0;
		long elapsedFirstS = 0;
		long start;
		long end;

		try {			
			start = System.nanoTime();
			QuicksortFirst(input);
			end = System.nanoTime();
			elapsedFirst = (end - start) / 1000000;
		} catch(StackOverflowError e) {
			System.out.println("Unsorted First Quicksort overflow on input size " + input.length);
		}	
		
		start = 0;
		end = 0;
		try {		
			start = System.nanoTime();
			QuicksortFirst(input);
			end = System.nanoTime();
			elapsedFirstS = (end - start) / 1000000;
		} catch(StackOverflowError e) {
			System.out.println("Sorted First Quicksort overflow on input size " + input.length);
		}
		System.out.println("Unsorted QuicksortFirst time: " + elapsedFirst + " ms");
		System.out.println("Sorted QuicksortFirst time: " + elapsedFirstS + " ms\n");
	}
	
	private static void RunQSMid(Integer[] input) {
		long elapsedMid = 0;
		long elapsedMidS = 0;
		long start;
		long end;
		
		try {
			start = System.nanoTime();
			QuicksortMid(input);
			end = System.nanoTime();
			elapsedMid = (end - start) / 1000000;
		} catch(StackOverflowError e) {
			System.out.println("Unsorted Mid Quicksort overflow on input size" + input.length);
		}
		
		try {
			start = System.nanoTime();
			QuicksortMid(input);
			end = System.nanoTime();
			elapsedMidS = (end - start) / 1000000;
		} catch(StackOverflowError e) {
			System.out.println("Sorted Mid Quicksort overflow on input size" + input.length);
		}
		
		System.out.println("Unsorted QuicksortMid time: " + elapsedMid + " ms");
		System.out.println("Sorted QuicksortMid time: " + elapsedMidS + " ms\n");
	}
	
}

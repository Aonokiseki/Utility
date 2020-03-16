package execrise;

import java.util.Arrays;

import execrise.sort.Sort;

public class TestSort {
	public static void main(String[] args) {
		int[] array = new int[1000000];
		int[] array2 = new int[1000000];
		int current;
		for(int i=0; i<1000000; i++) {
			current = (int)(Math.random()*Integer.MAX_VALUE);
			array[i] = current;
			array2[i] = current;
		}
		
		long start = System.currentTimeMillis();
		Sort.threeWayquickSort(array2, 0, array2.length-1);
		long end = System.currentTimeMillis();
		Arrays.sort(array);
		long end2 = System.currentTimeMillis();
		System.out.println("time :"+(end - start)+", time2: "+(end2 - end));
	}
}

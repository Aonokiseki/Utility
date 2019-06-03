package execrise.sort;

import utility.Other;

public class MySort {
	private MySort(){}
	
	public static void quickSort(int[] array, int left, int right){
		if(!checkRange(array, left, right))
			return;
		innerQuickSort(array, left, right);
	}
	
	private static boolean checkRange(int[] array, int left, int right){
		if(left < 0 || right >= array.length || left > right)
			return false;
		return true;
	}
	
	private static void innerQuickSort(int[] array, int left, int right){
		while(left < right){
			int i = left, j = i+1, k = right;
			int temp = array[i];
			while(j <= k){
				if(array[j] < temp)
					Other.exchangeTwoElementOfArray(array, j++, i++);
				else if(array[j] > temp)
					Other.exchangeTwoElementOfArray(array, k--, j);
				else
					j++;
			}
			quickSort(array, left, i-1);
			left = k + 1;
			System.out.println(toString(array));
		}
	}
	
	public static void main(String[] args){
		int[] array = new int[]{};
		quickSort(array, 0, array.length-1);
//		System.out.println(toString(array));
	}
	
	public static String toString(int[] array){
		StringBuilder sb = new StringBuilder();
		sb.append("[ ");
		for(int i=0; i<array.length; i++){
			sb.append(array[i]+" ");
		}
		sb.append("]");
		return sb.toString();
	}
}

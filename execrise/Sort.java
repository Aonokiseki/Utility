package execrise;

public class Sort {
	private Sort(){}
	private final static int INSERTION_SORT_THRESHOLD = 15;
	
	public static void display(int[] array, int left, int right){
		if(left > right || left < 0 || right > array.length - 1)
			return;
		StringBuilder sb = new StringBuilder();
		for(int i=left; i<=right; i++){
			sb.append("array["+i+"] == "+array[i]+ System.lineSeparator());
		}
		System.out.println(sb.toString());
	}
	/**
	 * 3路快速排序
	 * @param array
	 * @param left
	 * @param right
	 */
	public static void quickSort(int[] array, int left, int right){
		if(left > right || left < 0 || right > array.length - 1)
			return;
		if(right - left < INSERTION_SORT_THRESHOLD){
			insertionSort(array, left, right);
			return;
		}
		while(left < right){
			exchangeTwoElementOfArray(array, left, middleIndex(array, left, right));
			int pointerFromLeftToRight = left;
			int pointerBetweenLeftAndRight = pointerFromLeftToRight + 1;
			int pointerFromRightToLeft = right;
			int temp = array[left];
			while(pointerBetweenLeftAndRight <= pointerFromRightToLeft){
				if(array[pointerBetweenLeftAndRight] < temp)
					exchangeTwoElementOfArray(array, pointerFromLeftToRight++, pointerBetweenLeftAndRight++);
				else if(array[pointerBetweenLeftAndRight] > temp)
					exchangeTwoElementOfArray(array, pointerFromRightToLeft--, pointerBetweenLeftAndRight);
				else
					pointerBetweenLeftAndRight++;
			}
			quickSort(array, left, pointerFromLeftToRight - 1);
			left = pointerFromRightToLeft + 1;
		}
	}
	
	private static int middleIndex(int[] array, int left, int right){
		int between = (int)((left + right)/2);
		if(array[left] < array[between] && array[between] < array[right])
			return between;
		if(array[left] < array[right] && array[right] < array[between])
			return right;
		if(array[between] < array[left] && array[left] < array[right])
			return left;
		if(array[between] < array[right] && array[right] < array[left])
			return right;
		if(array[right] < array[left] && array[left] < array[between])
			return left;
		else
			return between;
	}
	/**
	 * 插入排序
	 * @param array
	 * @param left
	 * @param right
	 */
	public static void insertionSort(int[] array, int left, int right){
		int i = Integer.MIN_VALUE;
		int temp = Integer.MIN_VALUE;
		for(int j=left+1; j<=right; j++){
			i = j - 1;
			temp = array[j];
			while(i >= left && array[i] > temp){
				array[i + 1] = array[i];
				i--;
			}
			array[i + 1] = temp;
		}
	}
	
	private static void exchangeTwoElementOfArray(int[] array, int index1, int index2){
    	if(index1 < 0 || index1 > (array.length - 1) || index2 < 0 || index2 > (array.length - 1))
    		return;
    	int temp = array[index1];
    	array[index1] = array[index2];
    	array[index2] = temp;
    }
	/**
	 * 堆排序
	 * @param array
	 * @param left
	 * @param right
	 */
	public static void heapSort(int[] array, int left, int right){
		if(left > right || left < 0 || right > array.length - 1)
			return;
		buildMaxHeapify(array, left, right);
		innerHeapSort(array, left, right);
	}
	private static void buildMaxHeapify(int[] array, int left, int right){
		for(int i=right/2; i>=left; i--)
			maxHeapify(array, (right-left)+1, i);
	}
	private static void maxHeapify(int[] array, int heapSize, int current){
		int left = current << 1;
		int right = left + 1;
		int biggest = current;
		if(left < heapSize && array[left] > array[biggest])
			biggest = left;
		if(right < heapSize && array[right] > array[biggest])
			biggest = right;
		if(biggest != current){
			exchangeTwoElementOfArray(array, biggest, current);
			maxHeapify(array, heapSize, biggest);
		}
	}
	private static void innerHeapSort(int[] array, int left, int right){
		for(int j=right; j>=left; j--){
			exchangeTwoElementOfArray(array, left, j);
			maxHeapify(array, j, left);
		}
	}
	/**
	 * 归并排序
	 * @param array
	 * @param left
	 * @param right
	 */
	public static void mergeSort(int[] array, int left, int right){
		if(left > right || left < 0 || right > array.length - 1)
			return;
		int[] auxiliaryArray = new int[right - left + 1];
		innerMergeSort(array, left, right, auxiliaryArray);
	}
	private static void innerMergeSort(int[] array, int left, int right, int[] temp){
		if(left >= right)
			return;
		int privot = (left + right) / 2;
		innerMergeSort(array, left, privot, temp);
		innerMergeSort(array, privot+1, right, temp);
		merge(array, left, right, privot, temp);
	}
	private static void merge(int[] array, int left, int right, int mid, int[] temp){
		int i = left; int j = mid + 1; int tempPointer = 0;
		while(i <= mid && j <= right){
			if(array[i] > array[j])
				temp[tempPointer++] = array[j++];
			else
				temp[tempPointer++] = array[i++];
		}
		while(i <= mid)
			temp[tempPointer++] = array[i++];
		while(j <= mid)
			temp[tempPointer++] = array[j++];
		for(int x=0; x<tempPointer; x++)
			array[left+x] = temp[x];
	}
}

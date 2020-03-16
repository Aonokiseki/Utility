package execrise;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.RandomAccess;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

public class SimpleArrayList<E> implements List<E>, RandomAccess, Cloneable, java.io.Serializable{
	
	private static final long serialVersionUID = 8683452581122892189L;
	private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE;
	
	/*默认容量*/
	private static final int DEFAULT_CAPACITY = 10;
	/*空数组*/
	private static final Object[] EMPTY_ELEMENTDATA = {};
	/*默认构造器使用的空容量数组*/
	private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};
	/*保存ArrayList的数组*/
	private Object[] elementData;
	/*ArrayListd包含元素的个数*/
	private int size;
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for(int i=0; i<elementData.length-1; i++){
			sb.append(elementData[i]);
			sb.append(", ");
		}
		sb.append(elementData[elementData.length-1]);
		sb.append("]");
		return sb.toString();
	}
	
	public SimpleArrayList(int initialCapacity){
		if(initialCapacity > 0){
			/*初始化的容量大于0, 那就按照参数要求生成一个新的数组*/
			this.elementData = new Object[initialCapacity];
		}else if(initialCapacity == 0){
			this.elementData = EMPTY_ELEMENTDATA;
		}else{
			throw new IllegalArgumentException("Illegal Capacity: "+initialCapacity);
		}
	}
	
	public SimpleArrayList(){
		this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
	}
	
	public SimpleArrayList(Collection<? extends E> c){
		elementData = c.toArray();
		if((size = elementData.length) != 0){
			if(elementData.getClass() != Object[].class){
				elementData = Arrays.copyOf(elementData, size, Object[].class);
			}
		}else{
			this.elementData = EMPTY_ELEMENTDATA;
		}
	}
	/**
     * 修改这个ArrayList实例的容量是列表的当前大小。 应用程序可以使用此操作来最小化ArrayList实例的存储。 
     */
	public void trimToSize(){
		if(size < elementData.length){
			System.out.println("size("+size+"), not equals elementData.length("+elementData.length+")");
			if(size == 0){
				elementData = EMPTY_ELEMENTDATA;
			}else{
				elementData = Arrays.copyOf(elementData, size);
			}
//			elementData = (size == 0) ? EMPTY_ELEMENTDATA : Arrays.copyOf(elementData, size);
		}
	}
	
	
	@Override
	public boolean removeIf(Predicate<? super E> paramPredicate) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Stream<E> stream() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Stream<E> parallelStream() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void forEach(Consumer<? super E> paramConsumer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int size() {
		return this.size;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public boolean contains(Object o) {
		return indexOf(o) >= 0;
	}

	@Override
	public Iterator<E> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[] toArray() {
		/*新开辟了一个数组, 把元素的值依次赋过去, 然后的返回*/
		return Arrays.copyOf(elementData, size);
	}

	@Override
	public <T> T[] toArray(T[] paramArrayOfT) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean add(E e) {
		/*每次插入新的记录前, 需要检查并确保(注意是确保)当前数组容量*/
		ensureCapacityInternal(size + 1);
		/*然后再写数据*/
		elementData[size++] = e;
		return true;
		
	}
	/*获取最小阔容量*/
	public void ensureCapacityInternal(int minCapacity){
		if(elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA){
			minCapacity = Math.max(DEFAULT_CAPACITY, minCapacity);
		}
		ensureExplicitCapacity(minCapacity);
	}
	/* 
	 * 判断是否需要扩容, 需要就执行扩容.
	 * 
	 * 参数中的minCapactiy表示的其实是, 我需要的最小容量是这个
	 */
	private void ensureExplicitCapacity(int minCapacity){
		/*
		 * 如果需求严格大于现有的容量, 再去扩容
		 * 
		 * 刚好相等都不需要再去扩容
		 */
		if(minCapacity - elementData.length > 0)
			grow(minCapacity);
	}
	/*
	 * 扩容
	 * 
	 * 参数仍然是需要的最小容量
	 * */
	private void grow(int minCapacity){
		/*先统计现有元素个数*/
		int oldCapacity = elementData.length;
		/*统计出来的个数 × 1.5, 然后向下取整, 拟作为新的容量*/
		int newCapacity = oldCapacity + (oldCapacity >> 1);
		/*拟作为新的容量比需求还小, 说明我们低估了需求, 既然如此, 就按照需求的要求准备空间*/
		if(newCapacity - minCapacity < 0)
			newCapacity = minCapacity;
		if(newCapacity - MAX_ARRAY_SIZE > 0)
			newCapacity = hugeCapacity(minCapacity);
		/*数组是没法扩容的, 但可以通过复制数组的方式实现伪扩容(实际上是造了一个新的数组)*/
		elementData = Arrays.copyOf(elementData, newCapacity);
	}
	
	
	private static int hugeCapacity(int minCapacity){
		if(minCapacity < 0){
			throw new OutOfMemoryError();
		}
		if(minCapacity > MAX_ARRAY_SIZE)
			return Integer.MAX_VALUE;
		return MAX_ARRAY_SIZE;
	}

	@Override
	public boolean remove(Object o) {
		if(o == null){
			for(int index = 0; index < size; index++)
				if(elementData[index] == null){
					fastRemove(index);
					return true;
				}
		}else{
			for(int index = 0; index < size; index++)
				if(o.equals(elementData[index])){
					/*遍历, 找到就快速移除*/
					fastRemove(index);
					return true;
				}
		}
		return false;
	}
	
	private void fastRemove(int index){
		/* 
		 * size - 1 是末位元素的位置, 再减去index, 就是(index+1)位到末位元素的个数
		 * 
		 * 举例:
		 * 
		 * size = 4 
		 * 
		 * 0 - A
		 * 1 - B -> index (要移除它)
		 * 2 - C
		 * 3 - D <- (size - 1) = 3, 这就是D元素的所在位置
		 * 
		 * size - 1 - index == 2, 也就是等会儿要向前挪动的元素的个数
		 * 
		 * */
		int numMoved = size - index - 1;
		System.out.println("index=="+index+", numMoved=="+numMoved);
		if(numMoved > 0)
			/* 
			 * 从elementData的(index+1)位置起, 连续获得 numMoved 个元素, 
			 * 
			 * 并将其追加到elementData的以index为起始位置的内存空间中
			 * 
			 * */
			System.arraycopy(elementData, index+1, elementData, index, numMoved);
		elementData[--size] = null;
	}

	@Override
	public boolean containsAll(Collection<?> paramCollection) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		Object[] a = c.toArray();
		int numNew = a.length;
		ensureCapacityInternal(size + numNew);
		System.arraycopy(a, 0, elementData, size, numNew);
		size += numNew;
		return numNew != 0;
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		rangeCheckForAdd(index);
		Object[] a = c.toArray();
		int numNew = a.length;
		ensureCapacityInternal(size + numNew);
		int numMoved = size - index;
		if(numMoved > 0)
			System.arraycopy(elementData, index, elementData, index+numNew, numMoved);
		System.arraycopy(a, 0, elementData, index, numNew);
		return numNew != 0;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		Objects.requireNonNull(c);
		return batchRemove(c, true);
	}

	private boolean batchRemove(Collection<?> c, boolean complement) {
		final Object[] elementData = this.elementData;
		int r = 0, w = 0;
		boolean modified = false;
		try{
			for(;r < size; r++)
				if(c.contains(elementData[r]) == complement)
					elementData[w++] = elementData[r];
		}finally{
			if(r != size){
				System.arraycopy(elementData, r, elementData, w, size - r);
				w += size -r;
			}
			if(w != size){
				for(int i = w; i<size; i++)
					elementData[i] = null;
				size = w;
				modified = true;
			}
				
		}
		return modified;
	}

	@Override
	public boolean retainAll(Collection<?> paramCollection) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void replaceAll(UnaryOperator<E> paramUnaryOperator) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sort(Comparator<? super E> paramComparator) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clear() {
		for(int i=0; i<size; i++)
			elementData[i] = null;
		size = 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public E get(int index) {
		rangeCheck(index);
		return (E) elementData[index];
	}

	@SuppressWarnings("unchecked")
	@Override
	public E set(int index, E element) {
		rangeCheck(index);
		E oldValue = (E) elementData[index];
		elementData[index] = element;
		return oldValue;
	}

	@Override
	public void add(int index, E element) {
		rangeCheckForAdd(index);
		/*插入记录前(不管它放到哪个位置上, 一定多了一个元素), 要进行扩容检查和准备, 保证有足够空间进行插入*/
		ensureCapacityInternal(size + 1);
		/*
		 * 数组复制,如果是在后端插入新的记录, size - index刚好为0, 则这个语句完全没有效果;
		 * 
		 * 如果是在中间插入一条新纪录, 会将插入位置的后一个位置的元素整体后移一位
		 * 
		 * 从elementData的index开始, 数出(size - index)个元素, 并将这些元素依次从elementData的[index + 1]位置到放完为止。
		 * 
		 * 为何 size - index 就是要移动元素的个数, 见下
		 * 
		 * 0 - A
		 *         <-  这里要插入一个元素D, 也就是1号位置放元素D
		 * 1 - B    
		 * 2 - C
		 * 
		 * 插入前 size = 3
		 * 
		 * 准备插入时, size = 3, index = 1, size - index = 2,  要移动2个元素
		 * 
		 * 0 - A
		 * 1 - D   <-  插入完成
		 * 2 - B
		 * 3 - C
		 * 
		 * size = 4
		 */
		System.arraycopy(elementData, index, elementData, index + 1, size - index);
		elementData[index] = element;
		size++;
	}

	private void rangeCheckForAdd(int index) {
		if(index > size || index < 0)
			throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
	}

	@SuppressWarnings("unchecked")
	@Override
	public E remove(int index) {
		rangeCheck(index);
		E oldValue = (E) elementData[index];
		int numMoved = size - index - 1;
		if(numMoved > 0)
			System.arraycopy(elementData, index+1, elementData, index, numMoved);
		elementData[--size] = null;
		return oldValue;
	}
	

	private void rangeCheck(int index) {
		if(index >= size)
			throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
	}

	private String outOfBoundsMsg(int index) {
		return "Index: "+index+", Size: "+size;
	}

	@Override
	public int indexOf(Object paramObject) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int lastIndexOf(Object paramObject) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ListIterator<E> listIterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ListIterator<E> listIterator(int paramInt) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<E> subList(int paramInt1, int paramInt2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Spliterator<E> spliterator() {
		// TODO Auto-generated method stub
		return null;
	}

}

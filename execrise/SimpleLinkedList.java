package execrise;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

public class SimpleLinkedList<E> implements List<E>,Deque<E>{
	
	private long modCount = 0;
	private int size = 0;
	private Node<E> first;
	private Node<E> last;
	
	public SimpleLinkedList(){}
	
	public SimpleLinkedList(Collection<? extends E> c){
		this();
		addAll(c);
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		Node<E> node = first;
		sb.append("[");
		while(node != null && node.next != null){
			sb.append(node.item);
			sb.append(", ");
			node = node.next;
		}
		sb.append(node.item);
		sb.append("]");
		return sb.toString();
	}
	
	private void linkFirst(E e){
		final Node<E> f = first;
		final Node<E> newNode = new Node<>(null, e, f);
		last = newNode;
		if(f == null)
			first = newNode;
		else
			f.prev = newNode;
		size++;
		modCount++;
	}

	private E unlinkFirst(Node<E> f){
		final E element = f.item;
		final Node<E> next = f.next;
		f.item = null;
		f.next = null;
		first = next;
		if(next == null){
			last = null;
		}else{
			next.prev = null;
		}
		size--;
		modCount++;
		return element;
	}
	
	/*尾插法*/
	private void linkLast(E e){
		/*插入前, 给当前的尾节点做个标记, 因为他马上就不是尾节点了, 而是尾节点的前一个节点*/
		final Node<E> l = last;
		/*创造一个新节点, 这个的节点的prev一定指向当前的尾节点*/
		final Node<E> newNode = new Node<>(l, e, null);
		/*新插入的节点永远都是尾节点*/
		last = newNode;
		if(l == null){
			/*如果当前LinkedList没有元素, 让这个新插入的节点既当头节点, 又当尾节点*/
			first = newNode;
		}else{
			/*对应有元素的情况, 让原来的尾节点指向新插入的节点*/
			l.next = newNode;
		}
		size++;
		modCount++;
	}
	
	private void linkBefore(E e, Node<E> succ){
		/*插入位置, 这个位置上保存着原来的节点succ, 获取succ的前驱结点, 作为新节点的前驱节点;*/
		final Node<E> pred = succ.prev;
		/*新插入节点的前驱就是succ的前驱, succ则是新插入节点的后继*/
		final Node<E> newNode = new Node<>(pred, e, succ);
		/*新节点一定是succ的前驱节点*/
		succ.prev = newNode;
		if(pred == null)
			/*如果succ的前驱为空, 说明succ是原来的头节点, 新插入的节点既然是succ的前驱，那就一定是头节点*/
			first = newNode;
		else
			/*新节点的前驱节点，的后继，自然就是新插入的节点*/
			pred.next = newNode;
		size++;
		modCount++;
	}
	
	/*新节点准备在某个位置插入前, 获取在这个位置上的原节点*/
	Node<E> node(int index){
		if(index < (size >> 1)) {
			/*如果希望插入的位置在整个列表的前半段, 就从表头开始遍历*/
			Node<E> x = first;
			for(int i = 0; i < index; i++)
				x = x.next;
			return x;
		}else{
			/*反之, 从表尾开始遍历*/
			Node<E> x = last;
			for(int i = size - 1; i > index; i--)
				x = x.prev;
			return x;
		}
	}
	
	private static class Node<E>{
		E item;
		Node<E> next;
		Node<E> prev;
		
		Node(Node<E> prev, E element, Node<E> next){
			this.item = element;
			this.next = next;
			this.prev = prev;
		}
	}

	@Override
	public Stream<E> parallelStream() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean removeIf(Predicate<? super E> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Stream<E> stream() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void forEach(Consumer<? super E> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addFirst(E e) {
		linkFirst(e);
	}

	@Override
	public void addLast(E e) {
		linkLast(e);
	}

	@Override
	public Iterator<E> descendingIterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public E element() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public E getFirst() {
		final Node<E> f = first;
		if(f == null)
			throw new NoSuchElementException();
		return f.item;
	}

	@Override
	public E getLast() {
		final Node<E> f = last;
		if(f == null)
			throw new NoSuchElementException();
		return f.item;
	}

	@Override
	public boolean offer(E arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean offerFirst(E arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean offerLast(E arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public E peek() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public E peekFirst() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public E peekLast() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public E poll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public E pollFirst() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public E pollLast() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public E pop() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void push(E arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public E remove() {
		return removeFirst();
	}

	@Override
	public E removeFirst() {
		final Node<E> f = first;
		if(f == null)
			throw new NoSuchElementException();
		return unlinkFirst(f);
	}

	@Override
	public boolean removeFirstOccurrence(Object arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public E removeLast() {
		final Node<E> l = last;
		if(l == null)
			throw new NoSuchElementException();
		return unlink(l);
	}

	@Override
	public boolean removeLastOccurrence(Object arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean add(E e) {
		linkLast(e);
		return false;
	}

	private E unlink(Node<E> x){
		final E element = x.item;
		final Node<E> next = x.next;
		final Node<E> prev = x.prev;
		if(prev == null) {
			first = next;
		}else{
			prev.next = next;
			x.prev = null;
		}
		if(next == null){
			last = prev;
		}else{
			next.prev = prev;
			x.next = null;
		}
		x.item = null;
		size--;
		modCount++;
		return element;
	}

	@Override
	public void add(int index, E element) {
		checkPositionIndex(index);
		if(index == size)
			linkLast(element);
		else
			linkBefore(element, node(index));
	}

	private void checkPositionIndex(int index) {
		if(! isPositionIndex(index))
			throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
	}

	private boolean isPositionIndex(int index) {
		return index >= 0 && index <= size;
	}
	
	 private String outOfBoundsMsg(int index) {
	        return "Index: "+index+", Size: "+size;
	    }

	@Override
	public boolean addAll(Collection<? extends E> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addAll(int arg0, Collection<? extends E> arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean contains(Object o) {
		return indexOf(o) != -1;
	}

	@Override
	public boolean containsAll(Collection<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public E get(int index) {
		checkPositionIndex(index);
		return node(index).item;
	}

	@Override
	public int indexOf(Object o) {
		int index = 0;
		if(o == null){
			for(Node<E> x = first; x != null; x = x.next){
				if(x.item == null)
					return index;
				index++;
			}
		}else{
			for(Node<E> x = first; x != null; x = x.next){
				if(o.equals(x.item))
					return index;
				index++;
			}
		}
		return 0;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterator<E> iterator() {
		return null;
	}

	@Override
	public int lastIndexOf(Object o) {
		int index = size;
		if(o == null){
			for(Node<E> x = last; x != null; x = x.prev){
				index--;
				if(x.item == null)
					return index;
			}
		}else{
			for(Node<E> x = last; x != null; x = x.prev){
				index--;
				if(o.equals(x.item))
					return index;
			}
		}
		return 0;
	}

	@Override
	public ListIterator<E> listIterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ListIterator<E> listIterator(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean remove(Object o) {
		if(o == null) {
			for (Node<E> x = first; x != null; x = x.next) {
				if (x.item == null){
					unlink(x);
					return true;
				}
			}
		}else{
			for(Node<E> x = first; x != null; x = x.next){
				if(o.equals(x.item)){
					unlink(x);
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public E remove(int index) {
		checkElementIndex(index);
		return unlink(node(index));
	}

	@Override
	public boolean removeAll(Collection<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void replaceAll(UnaryOperator<E> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean retainAll(Collection<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public E set(int index, E element) {
		checkElementIndex(index);
		Node<E> x = node(index);
		E oldValue = x.item;
		x.item = element;
		return oldValue;
	}
	
	private void checkElementIndex(int index){
		if(!isElementIndex(index))
			throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
	}
	
	private boolean isElementIndex(int index){
		return index >= 0 && index < size;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public void sort(Comparator<? super E> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Spliterator<E> spliterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<E> subList(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[] toArray() {
		Object[] result = new Object[size];
		int i = 0;
		for(Node<E> x = first; x != null; x = x.next)
			result[i++] = x.item;
		return result;
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return null;
	}
	
}

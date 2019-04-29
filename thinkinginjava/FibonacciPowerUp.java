package thinkinginjava;

import java.util.Iterator;

public class FibonacciPowerUp implements Iterable<Integer>{
	private Fibonacci fibonacci;
	private int size;
	public FibonacciPowerUp(int size){
		fibonacci = new Fibonacci();
		this.size = size;
	}
	
	@Override
	public Iterator<Integer> iterator() {
		return new Iterator<Integer>(){
			@Override
			public boolean hasNext() {
				return size > 0;
			}
			@Override
			public Integer next() {
				if(hasNext()){
					size--;
					return fibonacci.next();
				}
				return null;
			}
			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}
	
	public static void main(String[] args){
		FibonacciPowerUp fpu = new FibonacciPowerUp(10);
		while(fpu.iterator().hasNext())
			System.out.println(fpu.iterator().next());
	}
}

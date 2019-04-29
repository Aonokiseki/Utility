package thinkinginjava;

import java.util.Iterator;
import java.util.Random;

interface Generator<T>{
	T next();
}

public class Coffee {
	private static long counter = 0;
	private final long id = counter++;
	
	public String toString(){
		return getClass().getSimpleName() + " " + id;
	}
	
	
	public static void main(String[] args){
//		CoffeeGenerator gen = new CoffeeGenerator();
//		for(int i=0; i<5; i++)
//			System.out.println(gen.next());
//		for(Coffee c: new CoffeeGenerator(5))
//			System.out.println(c);
//		System.out.println(gen.next());
		Iterator<Coffee> gen = new CoffeeGenerator(5).iterator();
		System.out.println(gen.next());
	}
}

class Latte extends Coffee{}
class Mocha extends Coffee{}
class Cappuccino extends Coffee{}
class Americano extends Coffee{}
class Breve extends Coffee{}

class CoffeeGenerator implements Generator<Coffee>, Iterable<Coffee>{
	private Class[] types = {
		Latte.class, Mocha.class, Cappuccino.class, Americano.class, Breve.class
	};
	
	private static Random random = new Random(System.currentTimeMillis());
	public CoffeeGenerator(){
		System.out.println("CoffeeGenerator();");
	}
	
	private int size = 0;
	public CoffeeGenerator(int size){
		System.out.println("CoffeeGenerator("+size+");");
		this.size = size;
	}
	
	public Coffee next(){
		try{
			int randomIndex = random.nextInt(types.length);
			Coffee coffeePointer = (Coffee) types[randomIndex].newInstance();
			System.out.println("coffeePointer = (Coffee)"+types[randomIndex].getName()+".newInstance();");
			return coffeePointer;
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	class CoffeeIterator implements Iterator<Coffee>{
		int count = size;
		public boolean hasNext(){
			return count > 0;
		}
		public Coffee next(){
			System.out.println("CoffeeIterator.next();");
			count--;
			return CoffeeGenerator.this.next();
		}
		public void remove(){
			throw new UnsupportedOperationException();
		}
	}
	public Iterator<Coffee> iterator(){
		return new CoffeeIterator();
	}
}
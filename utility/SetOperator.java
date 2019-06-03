package utility;

import java.util.HashSet;
import java.util.Set;

public class SetOperator {
	private SetOperator(){}
	/**
	 * 并集
	 * @param a
	 * @param b
	 * @return
	 */
	public static <T> Set<T> union(Set<T> a, Set<T> b){
		Set<T> result = new HashSet<T>(a);
		result.addAll(b);
		return result;
	}
	/**
	 * 交集
	 * @param a
	 * @param b
	 * @return
	 */
	public static <T> Set<T> intersection(Set<T> a, Set<T> b){
		Set<T> result = new HashSet<T>(a);
		result.retainAll(b);
		return result;
	}
	/**
	 * 差集
	 * @param a
	 * @param b
	 * @return
	 */
	public static <T> Set<T> difference(Set<T> superSet, Set<T> subSet){
		Set<T> result = new HashSet<T>(superSet);
		result.removeAll(subSet);
		return result;
	}
	/**
	 * 补集
	 * @param a
	 * @param b
	 * @return
	 */
	public static <T> Set<T> complement(Set<T> a, Set<T> b){
		return difference(union(a, b), intersection(a, b));
	}
}

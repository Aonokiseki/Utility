package utility;

public class Tuple {
	private Tuple(){};
	/* 二元组 */
	public static class Two<A, B>{
		public final A first;
		public final B second;
		
		public Two(A a, B b){
			this.first = a;
			this.second = b;
		}
		@Override
		public String toString(){
			return "("+this.first+", "+this.second+")";
		}
	}
	/* 三元组 */
	public static class Three<A, B, C> extends Tuple.Two<A, B>{
		public final C third;
		
		public Three(A a, B b, C c){
			super(a, b);
			this.third = c;
		}
		@Override
		public String toString(){
			return "("+this.first+", "+this.second+", "+this.third+")";
		}
	}
	/* 四元组 */
	public static class Four<A, B, C, D> extends Tuple.Three<A, B, C>{
		public final D fourth;
		
		public Four(A a, B b, C c, D d){
			super(a, b, c);
			this.fourth = d;
		}
		@Override
		public String toString(){
			return "("+this.first+", "+this.second+", "+this.third+", "+this.fourth+")";
		}
	}
	/* 五元组 */
	public static class Five<A, B, C, D, E> extends Tuple.Four<A, B, C, D>{
		public final E fifth;
		
		public Five(A a, B b, C c, D d, E e){
			super(a, b, c, d);
			this.fifth = e;
		}
		@Override
		public String toString(){
			return "("+this.first+", "+this.second+", "+this.third+", "+this.fourth+", "+this.fifth+")";
		}
	}
	/* 六元组 */
	public static class Six<A, B, C, D, E, F> extends Tuple.Five<A, B, C, D, E>{
		public final F sixth;
		
		public Six(A a, B b, C c, D d, E e, F f){
			super(a, b, c, d, e);
			this.sixth = f;
		}
		@Override
		public String toString(){
			return "("+this.first+", "+this.second+", "+this.third+", "+this.fourth+", "+this.fifth+", "+this.sixth+")";
		}
	}
}
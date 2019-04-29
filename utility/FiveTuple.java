package utility;

public class FiveTuple<A,B,C,D,E> extends FourTuple<A,B,C,D>{

	public final E fivth;
	
	public FiveTuple(A a, B b, C c, D d, E e) {
		super(a, b, c, d);
		this.fivth = e;
	}
	
	@Override
	public String toString(){
		return "("+this.first+", "+this.second+", "+this.third+", "+this.fourth+", "+this.fivth+")";
	}
}

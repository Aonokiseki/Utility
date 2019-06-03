package execrise.astar;

import java.util.Objects;

public class AStarPoint{
	private int x;
	private int y;
	private int FValue;
	private int GValue;
	private int HValue;
	private boolean isBarrier;
	private AStarPoint father;
	
	public int x(){
		return x;
	}
	public int y(){
		return y;
	}
	public int F(){
		return FValue; 
	}
	public int G(){
		return GValue;
	}
	public int H(){
		return HValue;
	}
	public void setFather(AStarPoint father){
		this.father = father;
	}
	public AStarPoint father(){
		return this.father;
	}
	public boolean isBarrier(){
		return this.isBarrier;
	}
	public void updateWeight(int GValue, int HValue){
		this.GValue = GValue;
		this.HValue = HValue;
		this.FValue = Integer.MAX_VALUE;
		long sum = (this.GValue + this.HValue);
		if(sum < (long)Integer.MAX_VALUE){
			this.FValue = (int)sum;
		}
	}
	public AStarPoint(int x, int y, boolean isBarrier) {
		this.x = x;
		this.y = y;
		this.GValue = 0;
		this.HValue = 0;
		this.FValue = this.GValue + this.HValue;
		this.isBarrier = isBarrier;
	}
	@Override
	public int hashCode(){
		return Objects.hash(x, y);
	}
	@Override
	public boolean equals(Object o){
		if(o == this)
			return true;
		if(! (o instanceof AStarPoint))
			return false;
		AStarPoint point = (AStarPoint) o;
		return (this.x == point.x && this.y == point.y);
	}
}

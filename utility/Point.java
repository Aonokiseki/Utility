package utility;

import java.util.Map;
import java.util.Objects;

public class Point {
	private int x;
	private int y;
	private int z;
	
	public Point(){
		this.x = 0;
		this.y = 0;
		this.z = 0;
	}
	public Point(int x){
		this.x = x;
		this.y = 0;
		this.z = 0;
	}
	public Point(int x, int y){
		this(x);
		this.y = y;
	}
	public Point(int x, int y, int z){
		this(x, y);
		this.z = z;
	}
	public int x(){
		return this.x;
	}
	public int y(){
		return this.y;
	}
	public int z(){
		return this.z;
	}
	public int setx(int x){
		this.x = x;
		return this.x;
	}
	public int sety(int y){
		this.y = y;
		return this.y;
	}
	public int setz(int z){
		this.z = z;
		return this.z;
	}
	@Override
	public int hashCode(){
		return Objects.hash(x, y, z);
	}
	@Override
	public boolean equals(Object o){
		if(o == this)
			return true;
		if(! (o instanceof Point))
			return false;
		Point point = (Point) o;
		return (this.x == point.x && this.y == point.y && this.z == point.z);
	}
	@Override
	public String toString(){
		return "("+this.x+", "+this.y+", "+this.z+")";
	}
	
	private final static Generator<Point> pointGenerator = new Generator<Point>(){
		@Override
		public Point next(Map<String, String> options) {
			return new Point();
		}
	};
	public static Generator<Point> generator(){
		return pointGenerator;
	}
}
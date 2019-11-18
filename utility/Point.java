package utility;

import java.util.Objects;

public class Point {
	private int x;
	private int y;
	private int z;
	private Object value;
	
	public Point(){
		this.x = 0;
		this.y = 0;
		this.z = 0;
	}
	public Point(int x, Object value){
		this.x = x;
		this.y = 0;
		this.z = 0;
		this.value = value;
	}
	public Point(int x, int y, Object value){
		this(x, value);
		this.y = y;
	}
	public Point(int x, int y, int z, Object value){
		this(x, y, value);
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
	public Object value(){
		return this.value;
	}
	public Point setx(int x){
		this.x = x;
		return this;
	}
	public Point sety(int y){
		this.y = y;
		return this;
	}
	public Point setz(int z){
		this.z = z;
		return this;
	}
	public Point setValue(Object value){
		this.value = value;
		return this;
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
		public Point next() {
			return new Point();
		}
	};
	public static Generator<Point> generator(){
		return pointGenerator;
	}
}
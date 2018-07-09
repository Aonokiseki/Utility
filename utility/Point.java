package utility;

import java.util.Objects;

public class Point {
	private double x;
	private double y;
	
	public Point(double x, double y){
		this.x = x;
		this.y = y;
	}
	public double getX(){
		return this.x;
	}
	public double getY(){
		return this.y;
	}
	@Override
	public int hashCode(){
		return Objects.hash(x, y);
	}
	@Override
	public boolean equals(Object o){
		if(o == this)
			return true;
		if(! (o instanceof Point))
			return false;
		Point point = (Point) o;
		return (this.x == point.x && this.y == point.y);
	}
	@Override
	public String toString(){
		return "("+this.x+", "+this.y+")";
	}
}

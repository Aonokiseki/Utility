package utility;

import java.util.Objects;

public class Point {
	private int x;
	private int y;
	private int z;
	
	public Point(int x, int y){
		this.x = x;
		this.y = y;
		this.z = 0;
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
}

class Coordinate{
	private double x;
	private double y;
	private double z;
	
	public Coordinate(double x, double y){
		this.x = x;
		this.y = y;
		this.z = 0.0;
	}
	public Coordinate(double x, double y, double z){
		this(x, y);
		this.z = z;
	}
	public double x(){
		return this.x;
	}
	public double y(){
		return this.y;
	}
	public double z(){
		return this.z;
	}
	public void setX(double x){
		this.x = x;
	}
	public void setY(double y){
		this.y = y;
	}
	public void setZ(double z){
		this.z = z;
	}
	@Override
	public int hashCode(){
		return Objects.hash(x, y, z);
	}
	@Override
	public boolean equals(Object o){
		if(o == this)
			return true;
		if(!(o instanceof Coordinate))
			return false;
		Coordinate coordinate = (Coordinate)o;
		return (this.x == coordinate.x && this.y == coordinate.y && this.z == coordinate.z);
	}
	@Override
	public String toString(){
		return "("+x+", "+y+", "+z+")";
	}
}

package utility;

import java.util.Map;
import java.util.Objects;

public class Coordinate{
	private double x;
	private double y;
	private double z;
	
	public Coordinate(){
		this.x = 0.0;
		this.y = 0.0;
		this.z = 0.0;
	}
	public Coordinate(double x){
		this.x = x;
		this.y = 0;
		this.z = 0;
	}
	public Coordinate(double x, double y){
		this(x);
		this.y = y;
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
	public double setx(double x){
		this.x = x;
		return this.x;
	}
	public double sety(double y){
		this.y = y;
		return this.y;
	}
	public double setz(double z){
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
		if(!(o instanceof Coordinate))
			return false;
		Coordinate coordinate = (Coordinate)o;
		return (this.x == coordinate.x && this.y == coordinate.y && this.z == coordinate.z);
	}
	@Override
	public String toString(){
		return "("+x+", "+y+", "+z+")";
	}
	
	private final static Generator<Coordinate> coordinateGenerator = new Generator<Coordinate>(){
		@Override
		public Coordinate next(Map<String, String> options) {
			return new Coordinate();
		}
	};
	public static Generator<Coordinate> generator(){
		return coordinateGenerator;
	}
}
package utility;

import java.util.Objects;

public class Coordinate{
	private double x;
	private double y;
	private double z;
	private Object value;
	
	public Coordinate(){
		this.x = 0.0;
		this.y = 0.0;
		this.z = 0.0;
		this.value = null;
	}
	public Coordinate(double x, Object value){
		this.x = x;
		this.y = 0;
		this.z = 0;
		this.value = value;
	}
	public Coordinate(double x, double y, Object value){
		this(x, value);
		this.y = y;
	}
	public Coordinate(double x, double y, double z, Object value){
		this(x, y, value);
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
	public Object value(){
		return this.value;
	}
	public Coordinate setx(double x){
		this.x = x;
		return this;
	}
	public Coordinate sety(double y){
		this.y = y;
		return this;
	}
	public Coordinate setz(double z){
		this.z = z;
		return this;
	}
	public Coordinate setValue(Object value){
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
		public Coordinate next() {
			return new Coordinate();
		}
	};
	public static Generator<Coordinate> generator(){
		return coordinateGenerator;
	}
}
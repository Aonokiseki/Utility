package utility;

import java.util.Objects;

public class Coordinate<T>{
	private double x;
	private double y;
	private double z;
	private T value;
	
	public Coordinate(){
		this.x = 0.0;
		this.y = 0.0;
		this.z = 0.0;
		this.value = null;
	}
	public Coordinate(double x, T value){
		this.x = x;
		this.y = 0;
		this.z = 0;
		this.value = value;
	}
	public Coordinate(double x, double y, T value){
		this(x, value);
		this.y = y;
	}
	public Coordinate(double x, double y, double z, T value){
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
	public T value(){
		return this.value;
	}
	public Coordinate<T> setx(double x){
		this.x = x;
		return this;
	}
	public Coordinate<T> sety(double y){
		this.y = y;
		return this;
	}
	public Coordinate<T> setz(double z){
		this.z = z;
		return this;
	}
	public Coordinate<T> setValue(T value){
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
		Coordinate<?> coordinate = (Coordinate<?>)o;
		return (this.x == coordinate.x && this.y == coordinate.y && this.z == coordinate.z);
	}
	@Override
	public String toString(){
		return "("+x+", "+y+", "+z+")";
	}
}
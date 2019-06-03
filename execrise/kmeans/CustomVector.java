package execrise.kmeans;

import java.util.Vector;

public class CustomVector {
	private String name;
	public void setName(String name){
		this.name = name;
	}
	public String getName(){
		return this.name;
	}
	
	private Vector<Double> childVector = null;
	public void setChildVector(Vector<Double> childVector){
		this.childVector = childVector;
	}
	public Vector<Double> getChildVector(){
		return this.childVector;
	}
	
	public CustomVector(String name, Vector<Double> childVector){
		this.name = name;
		this.childVector = childVector;
	}
	
	@Override
	public String toString(){
		return this.name + " -> " + this.childVector;
	}
}

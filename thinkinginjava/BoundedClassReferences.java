package thinkinginjava;

public class BoundedClassReferences {
	public static void main(String[] args){
		Class<? extends Number> bounded = int.class;
		bounded = double.class;
		bounded = int.class;
		bounded = Number.class;
	}
}

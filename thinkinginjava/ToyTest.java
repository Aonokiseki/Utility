package thinkinginjava;

public class ToyTest {
	public static void main(String [] args){
		Class c = null;
		try{
			c = Class.forName("thinkinginjava.FancyToy");
		}catch(ClassNotFoundException e){
			e.printStackTrace();
			System.exit(1);
		}
		printInfo(c);
		for(Class face : c.getInterfaces())
			printInfo(face);
		Class up = c.getSuperclass();
		Object obj = null;
		try{
			obj = up.newInstance();
		}catch(InstantiationException e){
			e.printStackTrace();
		}catch(IllegalAccessException e){
			e.printStackTrace();
		}
		printInfo(obj.getClass());
	}
	
	static void printInfo(Class cc){
		System.out.println(
				".getName() : "+cc.getName()+System.lineSeparator()+
				".isInterface() : " + cc.isInterface() + System.lineSeparator()+
				".getSimpleName() : " + cc.getSimpleName() + System.lineSeparator()+
				".getCanonicalName() : " + cc.getCanonicalName() + System.lineSeparator()
				);
	}
}


interface HasBatteries{}
interface WaterProof{}
interface Shoots{}

class Toy{
	Toy(){}
	Toy(int i){System.out.println("Toy("+i+")");}
}

class FancyToy extends Toy implements HasBatteries,WaterProof,Shoots{
	FancyToy(){ super(1); }
}


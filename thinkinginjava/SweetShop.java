package thinkinginjava;

class Candy{
	static {System.out.println("Loading Candy");}
}

class Gum{
	static {System.out.println("Loading Gum");}
}

class Cookie{
	static {System.out.println("Loading Cookie");}
}

public class SweetShop {
	public static void main(String[] args){
		System.out.println("Inside main()");
		new Candy();
		System.out.println("After creating Candy");
		try{
			Class.forName("thinkinginjava.Gum");
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
		System.out.println("After creating Gum");
		new Cookie();
		System.out.println("After creating Cookie");
	}
}
package thinkinginjava;

import java.util.ArrayList;
import java.util.List;

public class ForNameCreator extends PetCreator{
	private static List<Class<? extends Pet>> types = new ArrayList<Class<? extends Pet>>();

	private static String[] typeNames = {
		"thinkinginjava.Mutt",
		"thinkinginjava.Pug",
		"thinkinginjava.EgyptianMau",
		"thinkinginjava.Manx",
		"thinkinginjava.Cymric",
		"thinkinginjava.Rat",
		"thinkinginjava.Mouse",
		"thinkinginjava.Hamster"
	};
	
	@SuppressWarnings("unchecked")
	private static void loader(){
		try{
			for(String name: typeNames){
				types.add((Class<? extends Pet>)Class.forName(name));
			}
		}catch(ClassNotFoundException e){
			throw new RuntimeException(e);
		}
	}
	
	static{loader();}
	
	@Override
	public List<Class<? extends Pet>> types() {
		return types;
	}
	
	public static void main(String[] args){
		ForNameCreator fnc = new ForNameCreator();
		System.out.println(fnc.arrayList(5));
	}
}

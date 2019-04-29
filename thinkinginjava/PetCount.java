package thinkinginjava;

import java.util.HashMap;

public class PetCount {
	static class PetCounter extends HashMap<String,Integer>{
		public void count(String type){
			Integer quantity = get(type);
			if(quantity == null)
				put(type, 1);
			else
				put(type, quantity + 1);
		}
	}
	public static void countPets(PetCreator creator){
		PetCounter counter = new PetCounter();
		for(Pet pet: creator.createArray(20)){
			System.out.println(pet.getClass().getSimpleName() + " ");
			if(pet instanceof Pet)
				counter.count("Pet");
		}
	}
	public static void main(String[] args){
		countPets(new ForNameCreator());
	}
}
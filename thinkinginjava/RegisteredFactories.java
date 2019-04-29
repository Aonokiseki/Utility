package thinkinginjava;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

interface Factory<T>{
	T create();
}

public class RegisteredFactories {
	public static void main(String[] args){
		System.out.println(Part.createRandom());
	}
}

class Part{
	public String toString(){
		return getClass().getSimpleName();
	}
	static List<Factory<? extends Part>> partFactories = new ArrayList<Factory<? extends Part>>();
	static{
		partFactories.add(new FuelFilter.Factory());
		partFactories.add(new AirFilter.Factory());
		partFactories.add(new CabinAirFilter.Factory());
		partFactories.add(new OilFilter.Factory());
	}
	private static Random random = new Random();
	public static Part createRandom(){
		return partFactories.get(random.nextInt(partFactories.size())).create();
	}
}

abstract class Filter extends Part{}

class FuelFilter extends Filter{
	static{System.out.println("FuelFilter");}
	public static class Factory implements thinkinginjava.Factory<FuelFilter>{
		@Override
		public FuelFilter create(){
			return new FuelFilter();
		}
	}
}

class AirFilter extends Filter{
	static{System.out.println("AirFilter");}
	public static class Factory implements thinkinginjava.Factory<AirFilter>{
		@Override
		public AirFilter create() {
			return new AirFilter();
		}
	}
}

class CabinAirFilter extends Filter{
	static{System.out.println("CarbinAirFilter");}
	public static class Factory implements thinkinginjava.Factory<CabinAirFilter>{
		@Override
		public CabinAirFilter create(){
			return new CabinAirFilter();
		}
	}
}

class OilFilter extends Filter{
	static{System.out.println("OilFilter");}
	public static class Factory implements thinkinginjava.Factory<OilFilter>{
		@Override
		public OilFilter create(){
			return new OilFilter();
		}
	}
}
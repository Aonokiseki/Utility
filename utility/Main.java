package utility;

import java.util.HashMap;
import java.util.Map;

import utility.MapOperator.OrderBy;



public class Main {
	public static void main(String[] args){
		Map<String,String> map = new HashMap<String,String>();
		map.put("1", "65536");
		map.put("2", "1024.2041");
		map.put("3", "-1.999");
		Map<String,String> newMap = MapOperator.sortByValue(map, OrderBy.Number, null, false);
		System.out.println(newMap);
	}	
}
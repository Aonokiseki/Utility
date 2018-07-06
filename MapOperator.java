package utility;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public final class MapOperator {
	private MapOperator(){}
	
	public enum OrderBy{
		HashCode, Number, Directory
	}
	
	public static Map<String,String> sortByKey(Map<String,String> map){
		return sortByKey(map, false);
	}
	
	public static Map<String, String> sortByKey(Map<String,String> map, boolean isDesc){
		ArrayList<Map.Entry<String, String>> list = new ArrayList<Map.Entry<String,String>>(map.entrySet());
		if(isDesc){
			Collections.sort(list, new Comparator<Map.Entry<String, String>>(){
				@Override
				public int compare(Entry<String,String>arg0, Entry<String,String>arg1){
					return arg1.getKey().compareTo(arg0.getKey());
				}
			});
		}else{
			Collections.sort(list, new Comparator<Map.Entry<String, String>>(){
				@Override
				public int compare(Entry<String,String>arg0, Entry<String,String>arg1){
					return arg0.getKey().compareTo(arg1.getKey());
				}
			});
		}
		Map<String, String> newMap = new LinkedHashMap<String, String>();
		for (int i = 0; i < list.size(); i++) {  
            newMap.put(list.get(i).getKey(), list.get(i).getValue());  
        }  
        return newMap;  
	}
	
	public static Map<String,String> sortByValue(Map<String,String> map, OrderBy orderBy){
		return sortByValue(map, orderBy, false);
	}
	
	public static Map<String, String> sortByValue(Map<String, String> map, OrderBy orderBy, boolean isDesc){
		ArrayList<Map.Entry<String, String>> list = new ArrayList<Map.Entry<String,String>>(map.entrySet());
		switch(orderBy){
			case HashCode:
				if(isDesc){
					Collections.sort(list, new Comparator<Map.Entry<String, String>>(){
						@Override
						public int compare(Entry<String,String>arg0, Entry<String,String>arg1){
							return arg1.getValue().hashCode() - arg0.getValue().hashCode();
						}
					});
				}else{
					Collections.sort(list, new Comparator<Map.Entry<String, String>>(){
						@Override
						public int compare(Entry<String,String>arg0, Entry<String,String>arg1){
							return arg0.getValue().hashCode() - arg1.getValue().hashCode();
						}
					});
				}
			break;
			case Number:
				if(isDesc){
					Collections.sort(list, new Comparator<Map.Entry<String, String>>(){
						@Override
						public int compare(Entry<String,String>arg0, Entry<String,String>arg1){
							double difference = Double.valueOf(arg1.getValue()) - Double.valueOf(arg0.getValue());
							int result = 0;
							if(difference < 0)
								result = -1;
							else if(difference == 0)
								result = 0;
							else
								result = 1;
							return result;
						}
					});
				}else{
					Collections.sort(list, new Comparator<Map.Entry<String, String>>(){
						@Override
						public int compare(Entry<String,String>arg0, Entry<String,String>arg1){
							double difference = Double.valueOf(arg0.getValue()) - Double.valueOf(arg1.getValue());
							int result = 0;
							if(difference < 0)
								result = -1;
							else if(difference == 0)
								result = 0;
							else
								result = 1;
							return result;
						}
					});
				}
			break;
			case Directory:
				if(isDesc){
					Collections.sort(list, new Comparator<Map.Entry<String, String>>(){
						@Override
						public int compare(Entry<String,String>arg0, Entry<String,String>arg1){
							return arg1.getValue().compareTo(arg0.getValue());
						}
					});
				}else{
					Collections.sort(list, new Comparator<Map.Entry<String, String>>(){
						@Override
						public int compare(Entry<String,String>arg0, Entry<String,String>arg1){
							return arg0.getValue().compareTo(arg1.getValue());
						}
					});
				}
			break;
		}
		Map<String, String> newMap = new LinkedHashMap<String, String>();
		for (int i = 0; i < list.size(); i++) {  
            newMap.put(list.get(i).getKey(), list.get(i).getValue());  
        }  
        return newMap;  
	}
	
	/**
	 * map是否有指定key的非空value
	 * 
	 * @param map
	 * @param key
	 * @return boolean
	 */
	public static boolean mapHasNonNullValue(Map<String,String> map, String key){
		if(map == null || map.isEmpty() || map.size() == 0 || key == null || "".equals(key.trim()))
			return false;
		if(!map.containsKey(key))
			return false;
		if(map.get(key) == null || "".equals(map.get(key).trim()))
			return false;
		return true;
	}
}

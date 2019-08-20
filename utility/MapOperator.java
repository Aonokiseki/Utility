package utility;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;


public final class MapOperator {
	
	private MapOperator(){}

	/**
	 * 对<code>Map&ltString, ?&gt</code>的values按自然排序放入<code>LinkedHashMap&ltString, Object&gt</code>对象中
	 * @param map 待处理的<code>Map&ltString, ?&gt</code>
	 * @return LinkedHashMap&ltString, Object&gt
	 */
	public static LinkedHashMap<String, Object> sortKeyOrderByNatural(Map<String, ?> map){
		ArrayList<Map.Entry<String, ?>> list = new ArrayList<Map.Entry<String, ?>>(map.entrySet());
		Collections.sort(list, new Comparator<Entry<String, ?>>(){
			@Override
			public int compare(Entry<String, ?> entry1, Entry<String, ?> entry2){
				return entry1.getKey().compareTo(entry2.getKey());
			}
		});
		LinkedHashMap<String, Object> newMap = new LinkedHashMap<String, Object>();
		for(int i=0; i<list.size(); i++)
            newMap.put(list.get(i).getKey(), list.get(i).getValue());   
        return newMap;  
	}
	/**
	 * 对<code>Map&ltString, Calendar&gt</code>的values按大小排序放入<code>LinkedHashMap&ltString, Calendar&gt</code>对象中
	 * @param map  待处理的<code>Map&ltString, Calendar&gt</code>
	 * @param desc 是否降序
	 * @return LinkedHashMap&ltString, Calendar&gt
	 */
	public static LinkedHashMap<String, Calendar> sortValueOrderByCalendar(Map<String, Calendar> map, boolean desc){
		ArrayList<Map.Entry<String, Calendar>> list = new ArrayList<Map.Entry<String, Calendar>>(map.entrySet());
		Collections.sort(list, new Comparator<Entry<String, Calendar>>(){
			@Override
			public int compare(Entry<String, Calendar> e1, Entry<String, Calendar> e2){
				return e1.getValue().compareTo(e2.getValue());
			}
		});
		LinkedHashMap<String, Calendar> newMap = new LinkedHashMap<String, Calendar>();
		if(desc){
			for(int i=0; i<list.size(); i++)
	            newMap.put(list.get(i).getKey(), list.get(i).getValue());   
		}else{
			for(int i=list.size()-1; i>=0; i--)
	            newMap.put(list.get(i).getKey(), list.get(i).getValue());   
		}
        return newMap;  
	}
	/**
	 * 对<code>Map&ltString, LocalDateTime&gt</code>的values按大小排序放入<code>LinkedHashMap&ltString, LocalDateTime&gt</code>对象中
	 * @param map 待处理的<code>Map&ltString, LocalDateTime&gt</code>
	 * @param desc 是否降序
	 * @return LinkedHashMap&ltString, LocalDateTime&gt
	 */
	public static LinkedHashMap<String, LocalDateTime> sortValueOrderByLocalDateTime(Map<String, LocalDateTime>map, boolean desc){
		ArrayList<Map.Entry<String, LocalDateTime>> list = new ArrayList<Map.Entry<String, LocalDateTime>>(map.entrySet());
		Collections.sort(list, new Comparator<Entry<String, LocalDateTime>>(){
			@Override
			public int compare(Entry<String, LocalDateTime> e1, Entry<String, LocalDateTime> e2){
				return e1.getValue().compareTo(e2.getValue());
			}
		});
		LinkedHashMap<String, LocalDateTime> newMap = new LinkedHashMap<String, LocalDateTime>();
		if(desc){
			for(int i=0; i<list.size(); i++)
	            newMap.put(list.get(i).getKey(), list.get(i).getValue());   
		}else{
			for(int i=list.size()-1; i>=0; i--)
	            newMap.put(list.get(i).getKey(), list.get(i).getValue());   
		}
        return newMap;
	}
	/**
	 * 对<code>Map&ltString, T&gt</code>的values按大小排序放入<code>LinkedHashMap&ltString, Double&gt</code>对象中
	 * @param map  待处理的<code>Map&ltString, T&gt</code>, value的类型必须为Number或其子类
	 * @param desc 是否降序
	 * @return LinkedHashMap&ltString, Double&gt
	 */
	public static <T extends Number> LinkedHashMap<String, Double> sortValueOrderByNumber(Map<String, T> map, boolean desc){
		ArrayList<Map.Entry<String, T>> list = new ArrayList<Map.Entry<String, T>>(map.entrySet());
		Collections.sort(list, new Comparator<Entry<String, T>>(){
			@Override
			public int compare(Entry<String, T> e1, Entry<String, T> e2){
				return compareResult(e1.getValue().doubleValue(), e2.getValue().doubleValue());
			}
		});
		LinkedHashMap<String, Double> newMap = new LinkedHashMap<String, Double>();
		if(desc){
			for(int i=list.size()-1; i>=0; i--)
				newMap.put(list.get(i).getKey(), list.get(i).getValue().doubleValue());
		}else{
			for(int i=0; i<list.size(); i++)
	            newMap.put(list.get(i).getKey(), list.get(i).getValue().doubleValue());
		}
        return newMap;  
	}
	/**
	 * 将<code>Map&ltString, String&gt</code>对象转换为<code>Map&ltString, Double&gt</code>对象
	 * @param map
	 * @return
	 */
	public static Map<String,Double> parseValuesToDouble(Map<String,String> map){
		Map<String, Double> result = new HashMap<String,Double>();
		for(Entry<String,String> e: map.entrySet())
			result.put(e.getKey(), Double.valueOf(e.getValue()));
		return result;
	}
	/**
	 * 将<code>Map&ltString, String&gt</code>对象转换为<code>Map&ltString, Calendar&gt</code>对象
	 * @param map
	 * @param timeFormat 日期格式
	 * @return
	 * @throws ParseException - 日期格式解析错误时
	 */
	public static Map<String, Calendar> parseValuesToCalendar(Map<String, String> map, TimeFormat timeFormat) throws ParseException{
		Map<String, Calendar> result = new HashMap<String, Calendar>();
		for(Entry<String,String> e: map.entrySet())
			result.put(e.getKey(), DateOperator.stringToCalendar(e.getValue(), timeFormat));
		return result;
	}
	/**
	 * 将<code>Map&ltString, String&gt</code>对象转换为<code>Map&ltString, LocalDateTime&gt</code>对象
	 * @param map
	 * @param pattern
	 * @return
	 */
	public static Map<String, LocalDateTime> parseValuesToLocalDateTime(Map<String, String> map, String pattern){
		Map<String, LocalDateTime> result = new HashMap<String, LocalDateTime>();
		for(Entry<String, String> e: map.entrySet())
			result.put(e.getKey(), ChronoOperator.stringToLocalDateTime(e.getValue(), pattern));
		return result;
	}
	
	private static int compareResult(double n1, double n2){
		if (n1 - n2 > 0) return 1;
		else if (n1 - n2 < 0) return -1;
		else return 0;
	}
	
	/**
	 * 安全获取map中的指定key的value<br>
	 * ① map以及key, 二者任意一个是空或空串, 直接返回null;<br>
	 * ② map不包含key, 直接返回null;<br>
	 * @param map
	 * @param key
	 * @return
	 */
	public static <T> T safetyGet(Map<String, T> map, String key){
		if(map == null || map.isEmpty() || map.size() == 0 || key == null || "".equals(key.trim()))
			return null;
		if(!map.containsKey(key))
			return null;
		return map.get(key);
	}
}

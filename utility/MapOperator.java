package utility;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;


public final class MapOperator {
	private MapOperator(){}
	
	public enum SortItem{
		Key, Value;
	}
	public enum OrderBy{
		Natural, Number, Calendar, HashCode
	}
	
	/**
	 * 对Map的Key按照字典顺序做排序
	 * @param map 原Map
	 * @param isDesc 是否降序
	 * @return 排序后的Map
	 */
	public static Map<String, String> sortByKey(Map<String,String> map, boolean isDesc){
		ArrayList<Map.Entry<String, String>> list = new ArrayList<Map.Entry<String,String>>(map.entrySet());
		Collections.sort(list, new EntryComparator(SortItem.Key, null, null, isDesc));
		Map<String, String> newMap = new LinkedHashMap<String, String>();
		for(int i=0; i<list.size(); i++)
            newMap.put(list.get(i).getKey(), list.get(i).getValue());   
        return newMap;  
	}
	/**
	 * 对Map的value按照不同方式排序, 此方法不会破坏原Map
	 * @param map 原Map
	 * @param orderBy 根据何种方式排序, 可选项 HashCode-哈希|Natural-字典|Number-数值|Calendar-日期<br>
	 * Number要求所有的value必须是数字,Calendar要求所有的value必须是日期, 若value格式非法, 则不会排序
	 * @param formatItem 如果按照日历排序,则需在此处指明日期字符串的格式, 另见{@link TimeFormat}
	 * @param isDesc 是否降序
	 * @return 排序后的Map
	 */
	public static Map<String, String> sortByValue(Map<String, String> map, OrderBy orderBy, TimeFormat timeFormat, boolean isDesc){
		ArrayList<Map.Entry<String, String>> list = new ArrayList<Map.Entry<String,String>>(map.entrySet());
		Collections.sort(list, new EntryComparator(SortItem.Value, orderBy, timeFormat, isDesc));
		Map<String, String> newMap = new LinkedHashMap<String, String>();
		for(int i=0; i<list.size(); i++)
            newMap.put(list.get(i).getKey(), list.get(i).getValue());   
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
	/**
	 * 
	 * 内部类, 实现了比较器接口的一个方法:compare(), 目的是为存放于Map中，简单的数据做排序
	 *
	 */
	private static class EntryComparator implements Comparator<Map.Entry<String,String>>{
		private SortItem sortItem;
		private OrderBy orderBy;
		private TimeFormat timeFormat;
		private boolean isDESC;
		
		public EntryComparator(SortItem sortItem, OrderBy orderBy, TimeFormat timeFormat, boolean isDESC){
			this.orderBy = orderBy;
			this.sortItem = sortItem;
			this.isDESC = isDESC;
			this.timeFormat = timeFormat;
		}
		@Override
		public int compare(Entry<String, String> arg0, Entry<String, String> arg1) {
			if(sortItem == SortItem.Key){
				if(isDESC)
					return arg1.getKey().compareTo(arg0.getKey());
				return arg0.getKey().compareTo(arg1.getKey());
			}
			if(orderBy == OrderBy.Natural){
				if(isDESC)
					return arg1.getValue().compareTo(arg0.getValue());
				return arg0.getValue().compareTo(arg1.getValue());
			}
			if(orderBy == OrderBy.HashCode){
				if(isDESC)
					return arg1.getValue().hashCode() - arg0.getValue().hashCode();
				return arg0.getValue().hashCode() - arg1.getValue().hashCode();
			}
			if(orderBy == OrderBy.Calendar){
				int result = 0;
				try{
					result = compare(
							DateOperator.stringToCalendar(arg0.getValue(), timeFormat),
							DateOperator.stringToCalendar(arg1.getValue(), timeFormat),
							isDESC);
				}catch (NullPointerException | ParseException e) {
					return 0;
				}
				return result;
			}
			try{
				return compare(Double.valueOf(arg0.getValue()), Double.valueOf(arg1.getValue()), isDESC);
			}catch(NumberFormatException e){
				return 0;
			}
		}
		public int compare(double arg0, double arg1, boolean isDESC){
			if(isDESC){
				if(arg1 - arg0 > 0) return 1;
				if(arg1 - arg0 < 0) return -1;
				return 0;
			}
			if(arg0 - arg1 > 0) return 1;
			if(arg0 - arg1 < 0) return -1;
			return 0;
		}
		public int compare(Calendar c1, Calendar c2, boolean isDESC){
			if(isDESC){
				if(c2.after(c1)) return 1;
				if(c2.before(c1)) return -1;
				return 0;
			}
			if(c1.after(c2)) return 1;
			if(c1.before(c2)) return -1;
			return 0;
		}
	}
}

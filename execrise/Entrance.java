package execrise;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import utility.MapOperator;
import utility.MathOperator;
import utility.Tuple;
import utility.Tuple.Seven;

public class Entrance {
	public static void main(String[] args){
		Map<String, Integer> category = new HashMap<String,Integer>();
		category.put("游戏", 12);
		category.put("游戏", 10);
		category.put("财经", 12);
		category.put("军事", 12);
		category.put("政治", 7);
		category.put("新闻", 9);
		category.put("娱乐", 8);
		category.put("科技", 10);
		category.put("国外", 9);
		category.put("国内", 11);
		long start = System.currentTimeMillis();
		Seven<List<Entry<String, Double>>, List<Entry<String, Double>>, BigDecimal, BigDecimal, BigDecimal, Double, List<Entry<String, Double>>> result = MapOperator.statistic(category);
		long end = System.currentTimeMillis();
		System.out.println("time:"+(end - start)+", result =="+result);
	}
}

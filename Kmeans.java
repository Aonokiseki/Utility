package utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;

public class Kmeans {
	private Kmeans(){}
	
	/**
	 * K-means 聚类
	 * 
	 * @param customVectorList  由CustomVector类的对象组成的List
	 * @param k 簇心数
	 * @return 聚类结果map,结构如下:<br><br>
	 * 
	 * [KEY,VALUE]<br>
	 * [簇心1, [点A,点B,点C,……]]<br>
	 * [簇心2, [点D,点E,点F,……]]<br>
	 * [簇心3, [点G,点H,点I,……]]<br>
	 * ......<br>
	 * [簇心k, [点X,点Y,点Z,……]]<br>
	 * 
	 */
	public static Map<CustomVector, List<CustomVector>> cluster(List<CustomVector> customVectorList, int k){
		if(k <= 1){
			throw new IllegalArgumentException("Illegal k value!");
		}
		if(customVectorList == null || customVectorList.isEmpty() || customVectorList.size() == 0){
			throw new NullPointerException();
		}
		if(k > customVectorList.size()){
			k = customVectorList.size();
		}
		Map<CustomVector, List<CustomVector>> currentClusterResult = buildFirstClusterStatus(customVectorList, k);
		Map<CustomVector, List<CustomVector>> lastClusterResult = null;
		double shortestDistance = -1.0;
		double minkowskiDistance = -1.0;
		CustomVector clusterHeart = null;
		while(true){
			for(CustomVector eachCustomVector:customVectorList){
				shortestDistance = Double.MAX_VALUE;
				for(Entry<CustomVector, List<CustomVector>> eachClusterHeart: currentClusterResult.entrySet()){
					minkowskiDistance = MathOperator.minkowskiDistance(
								eachCustomVector.getChildVector(),
								eachClusterHeart.getKey().getChildVector(), 
								2);
					if(minkowskiDistance < shortestDistance){
						shortestDistance = minkowskiDistance;
						clusterHeart = eachClusterHeart.getKey();
					}
				}
				currentClusterResult.get(clusterHeart).add(eachCustomVector);
			}
			if(isEqualResult(lastClusterResult, currentClusterResult)){
				break;
			}
			lastClusterResult = currentClusterResult;
			currentClusterResult = new HashMap<CustomVector, List<CustomVector>>();
			for(Entry<CustomVector, List<CustomVector>> entry: lastClusterResult.entrySet()){
				currentClusterResult.put(updateClusterHeart(entry.getValue()), new ArrayList<CustomVector>());
			}
		}
		return currentClusterResult;
	}
	
	private static CustomVector updateClusterHeart(List<CustomVector> customVectorList){
		double[] sum = new double[customVectorList.get(0).getChildVector().size()];
		for(int i=0; i<sum.length; i++){
			for(CustomVector cv:customVectorList){
				sum[i] += cv.getChildVector().get(i);
			}
		}
		CustomVector newClusterHeart = new CustomVector("VirtualCustomVector", new Vector<Double>());
		for(int i=0; i<sum.length; i++){
			newClusterHeart.getChildVector().add(sum[i]/customVectorList.size());
		}
		return newClusterHeart;
	}
	
	private static boolean isEqualResult(
			Map<CustomVector, List<CustomVector>> lastClusterResult, 
			Map<CustomVector, List<CustomVector>> currentClusterResult){
		boolean result = true;
		if(lastClusterResult == null || lastClusterResult.isEmpty() || lastClusterResult.size() == 0){
			return false;
		}
		List<Vector<Double>> currentClusterHeartList = new ArrayList<Vector<Double>>();
		for(Entry<CustomVector, List<CustomVector>> entry: currentClusterResult.entrySet()){
			currentClusterHeartList.add(entry.getKey().getChildVector());
		}
		for(Entry<CustomVector, List<CustomVector>> entry: lastClusterResult.entrySet()){
			if(!currentClusterHeartList.contains(entry.getKey().getChildVector())){
				return false;
			}
		}
		return result;
	}

	private static Map<CustomVector, List<CustomVector>> buildFirstClusterStatus(List<CustomVector> customVectorList, int k){
		Map<CustomVector, List<CustomVector>> firstClusterStatus = new HashMap<CustomVector, List<CustomVector>>();
		int randomIndex = -1;
		for(int i=0; i<k; i++){
			randomIndex = (int)(Math.random() * customVectorList.size());
			firstClusterStatus.put(customVectorList.get(randomIndex), new ArrayList<CustomVector>());
		}
		return firstClusterStatus;
	}
}

/**
 * 
 * CustomVector类,目前用于K-means简单聚类.<br><br>
 * 
 * 成员变量:<br>
 * String name  -  向量名<br>
 * Vector<"Double"> childVector - 子向量<br>
 *
 */
class CustomVector {
	private String name;
	public void setName(String name){
		this.name = name;
	}
	public String getName(){
		return this.name;
	}
	private Vector<Double> childVector = null;
	public void setChildVector(Vector<Double> childVector){
		this.childVector = childVector;
	}
	public Vector<Double> getChildVector(){
		return this.childVector;
	}
	public CustomVector(String name, Vector<Double> childVector){
		this.name = name;
		this.childVector = childVector;
	}
	@Override
	public String toString(){
		return this.name + " -> "+this.childVector;
	}
}

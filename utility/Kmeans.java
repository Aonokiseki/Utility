package utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Map.Entry;

import utility.MathOperator;

public class Kmeans {
	private Kmeans(){}
	
	public static class Vector{
		private String info;
		public Vector setInfo(String name){
			this.info = name;
			return this;
		}
		public String info(){
			return this.info;
		}
		
		private List<Double> attributes = null;
		public Vector setChildVector(List<Double> attributes){
			this.attributes = attributes;
			return this;
		}
		public List<Double> attributes(){
			return this.attributes;
		}
		
		public Vector(){
			this.info = "";
			this.attributes = new ArrayList<Double>();
		}
		public Vector(String info, List<Double> attributes){
			this();
			this.info = info;
			this.attributes = attributes;
		}
		public Vector(String info, double... attributes){
			this();
			this.info = info;
			for(int i=0; i<attributes.length; i++)
				this.attributes.add(attributes[i]);
		}
		@Override
		public String toString(){
			return this.info + " -> "+this.attributes;
		}
		@Override
		public int hashCode(){
			return Objects.hash(this.attributes);
		}
		@Override
		public boolean equals(Object o){
			if(o == this)
				return true;
			if(!(o instanceof utility.Kmeans.Vector))
				return false;
			Vector v = (Vector)o;
			return (v.attributes.equals(this.attributes));
		}
	}
	
	/**
	 * K-means 聚类
	 * 
	 * @param vectors  由CustomVector类的对象组成的List
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
	public static Map<Vector, List<Vector>> cluster(List<Vector> vectors, int k){
		if(k <= 1){
			throw new IllegalArgumentException("Illegal k value!");
		}
		if(vectors == null || vectors.isEmpty() || vectors.size() == 0){
			throw new NullPointerException();
		}
		if(k > vectors.size()){
			k = vectors.size();
		}
		Map<Vector, List<Vector>> currentClusterResult = buildFirstClusterStatus(vectors, k);
		Map<Vector, List<Vector>> lastClusterResult = null;
		double shortestDistance = -1.0;
		double minkowskiDistance = -1.0;
		Vector clusterHeart = null;
		while(true){
			for(Vector eachCustomVector:vectors){
				System.out.println("eachCustomVector=="+eachCustomVector);
				shortestDistance = Double.MAX_VALUE;
				for(Entry<Vector, List<Vector>> eachClusterHeart: currentClusterResult.entrySet()){
					System.out.println("eachClusterHeart=="+eachClusterHeart);
					minkowskiDistance = MathOperator.minkowskiDistance(
								eachCustomVector.attributes(),
								eachClusterHeart.getKey().attributes(), 
								2);
					System.out.println("distance=="+minkowskiDistance);
					if(minkowskiDistance < shortestDistance){
						shortestDistance = minkowskiDistance;
						clusterHeart = eachClusterHeart.getKey();
						System.out.println("shortestDistance = "+shortestDistance+", clusterHeart=="+eachClusterHeart.getKey());
					}
				}
				currentClusterResult.get(clusterHeart).add(eachCustomVector);
				System.out.println("currentClusterResult.get("+clusterHeart+").add("+eachCustomVector+")");
			}
			System.out.println("lastClusterResult=="+lastClusterResult+", currentClusterResult=="+currentClusterResult);
			if(isEqualResult(lastClusterResult, currentClusterResult)){
				break;
			}
			lastClusterResult = currentClusterResult;
			currentClusterResult = new HashMap<Vector, List<Vector>>();
			for(Entry<Vector, List<Vector>> entry: lastClusterResult.entrySet()){
				currentClusterResult.put(updateClusterHeart(entry.getValue()), new ArrayList<Vector>());
			}
		}
		return currentClusterResult;
	}
	
	private static Vector updateClusterHeart(List<Vector> vectors){
		double[] sum = new double[vectors.get(0).attributes().size()];
		for(int i=0; i<sum.length; i++){
			for(Vector cv:vectors){
				sum[i] += cv.attributes().get(i);
			}
		}
		Vector newClusterHeart = new Vector("VirtualCustomVector");
		for(int i=0; i<sum.length; i++){
			newClusterHeart.attributes().add(sum[i]/vectors.size());
		}
		return newClusterHeart;
	}
	
	private static boolean isEqualResult(
			Map<Vector, List<Vector>> lastClusterResult, 
			Map<Vector, List<Vector>> currentClusterResult){
		boolean result = true;
		if(lastClusterResult == null || lastClusterResult.isEmpty() || lastClusterResult.size() == 0){
			return false;
		}
		List<List<Double>> currentClusterHeartList = new ArrayList<List<Double>>();
		for(Entry<Vector, List<Vector>> entry: currentClusterResult.entrySet()){
			currentClusterHeartList.add(entry.getKey().attributes());
		}
		for(Entry<Vector, List<Vector>> entry: lastClusterResult.entrySet()){
			if(!currentClusterHeartList.contains(entry.getKey().attributes())){
				return false;
			}
		}
		return result;
	}

	private static Map<Vector, List<Vector>> buildFirstClusterStatus(List<Vector> vectors, int k){
		Map<Vector, List<Vector>> firstClusterStatus = new HashMap<Vector, List<Vector>>();
		int randomIndex = -1;
		for(int i=0; i<k; i++){
			randomIndex = (int)(Math.random() * vectors.size());
			System.out.println("randomIndex=="+randomIndex);
			firstClusterStatus.put(vectors.get(randomIndex), new ArrayList<Vector>());
			System.out.println("firstClusterStatus.put("+vectors.get(randomIndex)+"), new ArrayList<Vector>();");
		}
		return firstClusterStatus;
	}
}



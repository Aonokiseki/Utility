package execrise.kmeans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;

import utility.MathOperator;

public class Kmeans {
	
	public static Map<CustomVector, List<CustomVector>> cluster(List<CustomVector> customVectorList, int k){
		if(k <= 1)
			throw new IllegalArgumentException("Illegal k value");
		if(customVectorList == null || customVectorList.size() == 0 || customVectorList.isEmpty())
			throw new NullPointerException();
		if(k > customVectorList.size())
			k = customVectorList.size();
		Map<CustomVector, List<CustomVector>> lastClusterResult = null;
		Map<CustomVector, List<CustomVector>> currentClusterResult = buildFirstClusterStatus(customVectorList, k);
		double shortestDistance = Double.MAX_VALUE;
		double distance = 0.0;
		CustomVector clusterHeart = null;
		while(true){
			for(CustomVector eachCustomVector:customVectorList){
				shortestDistance = Double.MAX_VALUE;
				for(Entry<CustomVector, List<CustomVector>> eachCluster: currentClusterResult.entrySet()){
					distance = MathOperator.minkowskiDistance(eachCustomVector.getChildVector(), eachCluster.getKey().getChildVector(), 2);
					if(distance < shortestDistance){
						shortestDistance = distance;
						clusterHeart = eachCluster.getKey();
					}
				}
				currentClusterResult.get(clusterHeart).add(eachCustomVector);
			}
			if(isEquals(lastClusterResult, currentClusterResult))
				break;
			lastClusterResult = currentClusterResult;
			currentClusterResult = new HashMap<CustomVector, List<CustomVector>>();
			for(Entry<CustomVector, List<CustomVector>> entry: lastClusterResult.entrySet())
				currentClusterResult.put(updateClusterHeart(entry.getValue()), new ArrayList<CustomVector>());
		}
		return currentClusterResult;
	}
	
	private static CustomVector updateClusterHeart(List<CustomVector> customVectorList){
		CustomVector newClusterHeart = null;
		double[] sum = new double[customVectorList.get(0).getChildVector().size()];
		for(int i=0; i<sum.length; i++){
			for(int j=0; j<customVectorList.size(); j++)
				sum[i] += customVectorList.get(j).getChildVector().get(i);
		}
		newClusterHeart = new CustomVector("VirtualCustomVector", new Vector<Double>());
		for(int i=0; i<sum.length; i++)
			newClusterHeart.getChildVector().add(sum[i]/customVectorList.size());
		return newClusterHeart;
	}
	
	private static boolean isEquals(Map<CustomVector, List<CustomVector>> last, Map<CustomVector, List<CustomVector>> current){
		boolean result = false;
		if(last == null || last.isEmpty() || last.size() == 0)
			return false;
		for(Entry<CustomVector, List<CustomVector>> eachNewCluster: current.entrySet()){
			if(!last.containsKey(eachNewCluster.getKey()))
				return false;
		}
		return result;
	}
		
	private static Map<CustomVector, List<CustomVector>> buildFirstClusterStatus(List<CustomVector> customVectorList, int k){
		Map<CustomVector, List<CustomVector>> result = new HashMap<CustomVector, List<CustomVector>>();
		int randomClusterHeartIndex = -1;
		for(int i=0; i<k; i++){
			randomClusterHeartIndex = (int)(Math.random() * customVectorList.size());
			result.put(customVectorList.get(randomClusterHeartIndex), new ArrayList<CustomVector>());
		}
		return result;
	}
}

package execrise;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import utility.Kmeans;
import utility.Kmeans.Vector;


public class K {
	public static void main(String[] args){
		Vector vector = null;
		List<Vector> vectors = new ArrayList<Vector>();
		for(int i=0; i<100; i++){
			vector = new Vector(String.valueOf(i), (int)(Math.random()*100), (int)(Math.random()*100));
			System.out.println("vector=="+vector);
			vectors.add(vector);
		}
		Map<Vector, List<Vector>> result = Kmeans.cluster(vectors, 3);
		for(Entry<Vector, List<Vector>> v : result.entrySet()){
			System.out.println("clusterHeart: "+v.getKey());
			for(int i=0,size=v.getValue().size(); i<size; i++){
				System.out.println(v.getValue().get(i));
			}
		}
	}
}

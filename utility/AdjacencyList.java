package utility;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class AdjacencyList {
	
	private List<Vertex> vertexList;
	
	public AdjacencyList(){
		vertexList = new LinkedList<Vertex>();
	}
	
	/**
	 * 查看邻接表<br>
	 * @return Map中的每个key都是一个顶点的id<br>
	 * value是每个顶点的相邻顶点id列表
	 */
	public Map<Integer, List<Integer>> view(){
		Map<Integer, List<Integer>> result = new HashMap<Integer, List<Integer>>();
		int vertexPointer = -1;
		List<Integer> adjacencyPerVertex = null;
		for(int i=0,size=vertexList.size(); i<size; i++){
			vertexPointer = vertexList.get(i).id;
			adjacencyPerVertex = new ArrayList<Integer>();
			for(int j=0, adjacencySize = vertexList.get(i).adjacentVertexList.size(); j<adjacencySize; j++){
				adjacencyPerVertex.add(vertexList.get(i).adjacentVertexList.get(j).id);
			}
			result.put(vertexPointer, adjacencyPerVertex);
		}
		return result;
	}
	
	/**
	 * 根据id获取指定节点的描述信息, 获取不到节点时返回null
	 * @param targetId
	 * @return
	 */
	public String getVertexInfo(int targetId){
		Vertex vertexPointer = findVertex(targetId);
		if(vertexPointer == null)
			return null;
		return vertexPointer.info;
	}
	
	/**
	 * 根据id获取指定节点的value, 获取不到节点时返回Integer.MIN_VALUE
	 * @param targetId
	 * @return
	 */
	public int getVertexValue(int targetId){
		Vertex vertexPointer = findVertex(targetId);
		if(vertexPointer == null)
			return Integer.MIN_VALUE;
		return vertexPointer.value;
	}
	
	public boolean setVertexInfo(int targetId, String info){
		Vertex vertexPointer = findVertex(targetId);
		if(vertexPointer == null)
			return false;
		vertexPointer.info = info;
		return true;
	}
	
	public boolean setVertexValue(int targetId, int value){
		Vertex vertexPointer = findVertex(targetId);
		if(vertexPointer == null)
			return false;
		vertexPointer.value = value;
		return true;
	}
	
	/**
	 * 对每个顶点, 以及每个顶点的所有邻接顶点, 按照顶点的id做自然排序
	 */
	public void naturalSortVertexListById(){
		Collections.sort(this.vertexList, new Comparator<Vertex>(){
			@Override
			public int compare(Vertex v1, Vertex v2){
				return v1.id - v2.id;
			}
		});
		for(int i=0,size=this.vertexList.size(); i<size; i++){
			this.vertexList.get(i).naturalSortAdjacentVertexById();
		}
	}
	
	/**
	 * 添加一个顶点到图中
	 * @param id 顶点id, 不允许和已添加的重复
	 * @param value 顶点的值/权重
	 * @param info 顶点描述信息
	 * @return boolean, 是否添加成功
	 */
	public boolean addVertex(int id, int value, String info){
		Vertex vertex = new Vertex(id, value, info);
		if(vertexList.contains(vertex))
			return false;
		vertexList.add(vertex);
			return true;
	}
	
	/**
	 * 给两个已存在的顶点连线<br>
	 * 仅当两个id不同, 且两个id在图中存在时连接成功。<br>
	 * @param fromId 顶点1的id
	 * @param toId 顶点2的id
	 * @return boolean, 是否连接成功
	 */
	public boolean linkTwoVertex(int fromId, int toId){
		if (fromId == toId)
			return false;
		Vertex vertexPointer = null;
		Vertex vertexFrom = null;
		Vertex vertexTo = null;
		for(int i=0,size=vertexList.size(); i<size; i++){
			vertexPointer = vertexList.get(i);
			if(vertexPointer.id == fromId)
				vertexFrom = vertexList.get(i);
			if(vertexPointer.id == toId)
				vertexTo = vertexList.get(i);
		}
		if(vertexFrom == null || 
		   vertexTo == null ||
		   vertexFrom.adjacentVertexList.contains(vertexTo) ||
		   vertexTo.adjacentVertexList.contains(vertexFrom))
			return false;
		return (vertexFrom.adjacentVertexList.add(vertexTo) && vertexTo.adjacentVertexList.add(vertexFrom));
	}
	
	/**
	 * 广度优先遍历
	 * @param startId 起始点id
	 * @return <code>List</code> id的列表
	 */
	public List<Integer> BreadthFirstSearch(int startId){
		Vertex vertexPointer = findVertex(startId);
		if(vertexPointer == null)
			return new ArrayList<Integer>();
		int startIndex = vertexList.indexOf(vertexPointer);
		List<Integer> searchPath = new ArrayList<Integer>();
		searchPath.add(vertexList.get(startIndex).id);
		Other.exchangeTwoElementOfList(this.vertexList, 0, startIndex);
		vertexPointer = null;
		for(int i=0,size=this.vertexList.size(); i<size; i++){
			vertexPointer = vertexList.get(i);
			for(int j=0,adjacenySize=vertexPointer.adjacentVertexList.size(); j<adjacenySize; j++){
				if(searchPath.contains(vertexPointer.adjacentVertexList.get(j).id))
					continue;
				searchPath.add(vertexPointer.adjacentVertexList.get(j).id);
			}
		}
		return searchPath;
	}
	
	/**
	 * 移除两个顶点的连线
	 * @param fromId 顶点1的id
	 * @param toId 顶点2的id
	 * @return 移除是否成功
	 */
	public boolean cancelLinkBetweenTwoVertex(int fromId, int toId){
		if(fromId == toId)
			return false;
		Vertex vertexPointer = null;
		Vertex vertexFrom = null;
		Vertex vertexTo = null;
		for(int i=0,size=vertexList.size(); i<size; i++){
			vertexPointer = vertexList.get(i);
			if(vertexPointer.id == fromId)
				vertexFrom = vertexList.get(i);
			if(vertexPointer.id == toId)
				vertexTo = vertexList.get(i);
		}
		if(vertexFrom == null || 
			vertexTo == null ||
			!vertexFrom.adjacentVertexList.contains(vertexTo) ||
			!vertexTo.adjacentVertexList.contains(vertexFrom))
					return false;
		return (vertexFrom.adjacentVertexList.remove(vertexTo) && vertexTo.adjacentVertexList.remove(vertexFrom));
	}
	
	/**
	 * 尝试在图中寻找目标节点,找不到则返回null
	 */
	private Vertex findVertex(int targetId){
		Vertex vertexPointer = null;
		for(int i=0,size=this.vertexList.size(); i<size ;i++){
			if(vertexList.get(i).id == targetId){
				vertexPointer = vertexList.get(i);
				break;
			}
		}
		return vertexPointer;
	}
	
	/**
	 * 从图中移除一个顶点
	 * @param targetId 移除顶点的id
	 * @param force 对于度不为0的顶点, 是否强制移除
	 * @return boolean 移除是否成功
	 */
	public boolean removeVertex(int targetId, boolean force){
		Vertex vertexPointer = findVertex(targetId);
		if(vertexPointer == null)
			return false;
		if(!force && vertexPointer.adjacentVertexList.size() > 0)
			return false;
		if(!force && vertexPointer.adjacentVertexList.size() == 0)
			return this.vertexList.remove(vertexPointer);
		if(force && vertexPointer.adjacentVertexList.size() == 0)
			return this.vertexList.remove(vertexPointer);
		for(int i=0,size=vertexList.size(); i<size; i++){
			if(vertexList.get(i).adjacentVertexList.contains(vertexPointer)){
				if(!vertexList.get(i).adjacentVertexList.remove(vertexPointer))
					return false;
			}
		}
		return this.vertexList.remove(vertexPointer);
	}

	class Vertex{
		private final int id;
		private int value;
		private String info;
		List<Vertex> adjacentVertexList;
	
		public Vertex(int id, int value, String info){
			this.id = id;
			this.value = value;
			this.info = info;
			this.adjacentVertexList = new LinkedList<Vertex>();
		}
		
		public void setValue(int value){
			this.value = value;
		}
		public int getValue(){
			return this.value;
		}
		public void setInfo(String info){
			this.info = info;
		}
		public String getInfo(){
			return this.info;
		}
		
		public void naturalSortAdjacentVertexById(){
			Collections.sort(this.adjacentVertexList, new Comparator<Vertex>(){
				@Override
				public int compare(Vertex v1, Vertex v2){
					return v1.id - v2.id;
				}
			});
		}
		
		@Override
		public int hashCode(){
			return Objects.hash(id);
		}
		@Override
		public boolean equals(Object o){
			if(o == this)
				return true;
			if(!(o instanceof Vertex))
				return false;
			Vertex vertex = (Vertex)o;
			return (vertex.id == this.id);
		}
		@Override
		public String toString(){
			StringBuilder sb = new StringBuilder();
			sb.append(this.id + " -> [ ");
			for(int i=0, size = this.adjacentVertexList.size()-1; i<size; i++){
				sb.append(this.adjacentVertexList.get(i).id + ", ");
			}
			sb.append(this.adjacentVertexList.get(this.adjacentVertexList.size()-1).id + " ]");
			return sb.toString();
		}
	}
	
}


package utility;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;

public class Astar {
	private String barrier = "X";
	private Point maze[][] = null;
	private Point start = null;
	private Point end = null;
	private List<Point> openQueue = null;
	private List<Point> closeQueue = null;
	private String[][] resultMap;
	private int step;
	
	public String[][] getResultMap(){
		return this.resultMap;
	}
	public int getStep(){
		return this.step;
	}
	
	private Astar(Point[][] maze, Point start, Point end, String barrier){
		this.maze = maze;
		this.start = start;
		this.end = end;
		this.barrier = barrier;
		openQueue = new LinkedList<Point>();
		this.start.setFGHValue(0, getDistance(start.getX(), end.getX(), start.getY(), end.getY()));
		openQueue.add(this.start);
		closeQueue = new LinkedList<Point>();
	}
	
	private void startSearch(){
		Point p = null;
		while((p = findShortestPoint())!=null){
			if(p.equals(end))
				return;
			updateNeighborPoint(p);
		}
	}
	
	private Point findShortestPoint(){
		Point result = null;
		double shortestFValue = Double.MAX_VALUE;
		for(Point p:openQueue){
			if(p.getFValue() <= shortestFValue){
				shortestFValue = p.getFValue();
				result = p;
			}
		}
		if(shortestFValue != Double.MAX_VALUE){
			openQueue.remove(result);
			closeQueue.add(result);
		}
		return result;
	}
	
	private void updateNeighborPoint(Point p){
		List<String> direction = new LinkedList<String>();
		direction.add("up");
		direction.add("down");
		direction.add("left");
		direction.add("right");
		String dir = null;
		int randomDirection = -1;
		while(!direction.isEmpty()){
			randomDirection = (int)(Math.random() * direction.size());
			dir = direction.remove(randomDirection);
			if("up".equals(dir)){
				if(checkPointValid(p.getX()-1, p.getY()))
					updatePoint(maze[p.getX()-1][p.getY()], p);
			}else if("down".equals(dir)){
				if(checkPointValid(p.getX()+1, p.getY()))
					updatePoint(maze[p.getX()+1][p.getY()], p);
			}else if("left".equals(dir)){
				if(checkPointValid(p.getX(), p.getY()-1))
					updatePoint(maze[p.getX()][p.getY()-1], p);
			}else{
				if(checkPointValid(p.getX(), p.getY()+1))
					updatePoint(maze[p.getX()][p.getY()+1], p);
			}
		}
	}
	
	private void updatePoint(Point current, Point last){
		int tempG = 1;
		int tempH = getDistance(current.getX(), end.getX(), current.getY(), end.getY());
		int tempF = tempG + tempH;
		if(!openQueue.contains(current) || tempF < current.getFValue()){
			if(!openQueue.contains(current)){
				openQueue.add(current);
			}
			current.setFGHValue(tempG, tempH);
			current.setParent(last);
		}
	}
	
	private boolean checkPointValid(int x, int y){
		if(x < 0 || x >= maze.length || y < 0 || y >= maze[0].length)
			return false;
		if(maze[x][y].getValue().equals(this.barrier))
			return false;
		if(closeQueue.contains(maze[x][y]))
			return false;
		return true;
	}
	
	public static Astar build(String[][] mazeRaw, Point start, Point end, String barrier){
		Point maze[][] = new Point[mazeRaw.length][mazeRaw[0].length];
		for(int i=0; i<maze.length; i++)
			for(int j=0; j<maze[0].length;j++)
				maze[i][j] = new Point(i, j, mazeRaw[i][j]);
		start = maze[start.getX()][start.getY()];
		end = maze[end.getX()][end.getY()];
		Astar astar = new Astar(maze, start, end, barrier);
		astar.startSearch();
		astar.generateResult();
		return astar;
	}
 	
	private static int getDistance(int x1, int x2, int y1, int y2){
		int expontent = 2;
		double distance = Math.pow((Math.pow(x1-x2,expontent)+Math.pow(y1-y2,expontent)), 1/expontent);
		if((int)(distance * 10)%10 >= 5){
			return (int)(distance) + 1;
		}
		return (int)distance;
	}
	
	private void generateResult(){
		this.resultMap = new String[maze.length][maze[0].length];
		for(int i=0; i<resultMap.length; i++){
			for(int j=0; j<resultMap[i].length; j++){
				if(maze[i][j].getValue().equals(barrier)){
					resultMap[i][j] = barrier;
				}else if(maze[i][j].equals(start)){
					resultMap[i][j] = "S";
				}
				else{
					resultMap[i][j] = ".";	
				}
			}
		}
		Point p = end;
		while(p.getParent() != null){
			if(p.equals(start)){
				resultMap[p.getX()][p.getY()] = "S";
			}else if(p.equals(end)){
				resultMap[p.getX()][p.getY()] = "E";
				step++;
			}else{
				resultMap[p.getX()][p.getY()] = "*";
				step++;
			}
			p = p.getParent();
		}
	}
	
	public void drawResultMap(String outputFilePath) throws IOException{
		if(this.resultMap == null || this.resultMap.length == 0){
			return;
		}
		File fPointer = new File(outputFilePath);
		BufferedImage image = new BufferedImage((this.resultMap[0].length+1)*25, (this.resultMap.length+1)*25, BufferedImage.TYPE_INT_BGR);
		Graphics graphicsObject = image.getGraphics();
		graphicsObject.setColor(Color.BLACK);
        graphicsObject.fillRect(0,0,(this.resultMap[0].length+1)*25, (this.resultMap.length+1)*25);
		for(int i=0; i< this.resultMap.length; i++){
			for(int j=0; j< this.resultMap[i].length; j++){
				if("S".equals(this.resultMap[i][j])){
					graphicsObject.setColor(Color.PINK);
				}else if("E".equals(this.resultMap[i][j])){
					graphicsObject.setColor(Color.RED);
				}else if(barrier.equals(this.resultMap[i][j])){
					graphicsObject.setColor(Color.ORANGE);
				}else if("*".equals(this.resultMap[i][j])){
					graphicsObject.setColor(Color.GREEN);
				}else{
					graphicsObject.setColor(Color.BLUE);
				}
				graphicsObject.drawString(this.resultMap[i][j], (j+1)*25, (i+1)*25);
			}
		}
		graphicsObject.dispose();
	    ImageIO.write(image, "png", fPointer);
	    image.flush();
	}
}

class Point{
	private int x;
	private int y;
	private String value;
	private Point parent;
	private int FValue;
	private int GValue;
	private int HValue;
	
	public Point(int x, int y){
		this.x = x;
		this.y = y;
		this.FValue = Integer.MAX_VALUE;
		this.GValue = Integer.MAX_VALUE;
		this.HValue = Integer.MAX_VALUE;
	}
	public Point(int x, int y, String value){
		this.x = x;
		this.y = y;
		this.value = value;
		this.FValue = Integer.MAX_VALUE;
		this.GValue = Integer.MAX_VALUE;
		this.HValue = Integer.MAX_VALUE;
	}
	public int getX(){
		return this.x;
	}
	public int getY(){
		return this.y;
	}
	public String getValue(){
		return this.value;
	}
	public void setFGHValue(int gValue, int hValue){
		this.GValue = gValue;
		this.HValue = hValue;
		this.FValue = this.GValue + this.HValue;
	}
	public int getFValue(){
		return this.FValue;
	}
	public int getGValue(){
		return this.GValue;
	}
	public int getHValue(){
		return this.HValue;
	}
	public void setParent(Point parent){
		this.parent = parent;
	}
	public Point getParent(){
		return this.parent;
	}
	public boolean coordinateEqualTo(Point p){
		if(this.x == p.x && this.y == p.y){
			return true;
		}
		return false;
	}
	public boolean valueEqualTo(Point p){
		if(this.value.equals(p.value)){
			return true;
		}
		return false;
	}
	public boolean strictlyEqualto(Point p){
		if(coordinateEqualTo(p) && valueEqualTo(p)){
			return true;
		}
		return false;
	}
	@Override
	public String toString(){
		return "("+this.x+", "+this.y+")";
	}
}

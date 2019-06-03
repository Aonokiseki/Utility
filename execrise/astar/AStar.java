package execrise.astar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.imageio.ImageIO;

import utility.MathOperator;


public class AStar {
	private static enum Directions{
		Up,Down,Left,Right;
	}
	private final static String BARRIER = "x";
	private final static String PATH = "*";
	private final static String GROUND = ".";
	private final static String START = "S";
	private final static String END = "E";
	private final static int UNIT_DISTANCE = 30;
	private AStarPoint[][] maze;
	private AStarPoint start;
	private AStarPoint end;
	private Queue<AStarPoint> openQueue = null;
	private Queue<AStarPoint> closeQueue = null;
	private String[][] resultMap;
	private int step;
	
	public String[][] resultMap() { return this.resultMap; }
	public int step() { return this.step; }
	
	
	
	public AStar(AStarPoint[][] maze, AStarPoint start, AStarPoint end){
		this.start = maze[start.x()][start.y()];
		this.end = maze[end.x()][end.y()];
		openQueue = new LinkedList<AStarPoint>();
		closeQueue = new LinkedList<AStarPoint>();
		initialization();
	}
	
	public AStar(String[][] mazeRaw){
		maze = new AStarPoint[mazeRaw.length][mazeRaw[0].length];
		boolean isBarrier = false;
		int start_x=0,start_y=0,end_x=0,end_y=0;int startCount=0;int endCount=0;
		for(int i=0; i<maze.length; i++){
			for(int j=0; j<maze[0].length;j++){
				isBarrier = false;
				if(BARRIER.equals(mazeRaw[i][j])){
					isBarrier = true;
				}else if(START.equals(mazeRaw[i][j])){
					if(startCount > 0)
						throw new IllegalArgumentException("The count of start point is not allowed to exceed 1.");
					start_x = i;
					start_y = j;
					startCount++;
				}else if(END.equals(mazeRaw[i][j])){
					if(endCount > 0)
						throw new IllegalArgumentException("The count of start point is not allowed to exceed 1.");
					end_x = i;
					end_y = j;
					endCount++;
				}
				maze[i][j] = new AStarPoint(i, j, isBarrier);
			}
		}
		this.start = maze[start_x][start_y];
		this.end = maze[end_x][end_y];
		openQueue = new LinkedList<AStarPoint>();
		closeQueue = new LinkedList<AStarPoint>();
		initialization();
	}
	
	public void execute(){
		findPath();
		generateResultMap();
	}
	
	private void generateResultMap(){
		resultMap = new String[maze.length][];
		for(int i=0; i<maze.length; i++){
			resultMap[i] = new String[maze[i].length];
			for(int j=0; j<maze[i].length; j++){
				if(maze[i][j].isBarrier())
					resultMap[i][j] = BARRIER;
				else if(maze[i][j].equals(start))
					resultMap[i][j] = START;
				else
					resultMap[i][j] = GROUND;
			}
		}
		AStarPoint pathPointer = end;
		while(pathPointer.father() != null){
			if(pathPointer.equals(start)){
				resultMap[pathPointer.x()][pathPointer.y()] = START;
			}else if(pathPointer.equals(end)){
				resultMap[pathPointer.x()][pathPointer.y()] = END;
				step++;
			}else{
				resultMap[pathPointer.x()][pathPointer.y()] = PATH;
				step++;
			}
			pathPointer = pathPointer.father();
		}
	}
	
	public void drawResultMap(String outputFilePath) throws IOException{
		if(this.resultMap == null || this.resultMap.length == 0){
			return;
		}
		File fPointer = new File(outputFilePath);
		BufferedImage image = new BufferedImage((this.resultMap[0].length+1)*UNIT_DISTANCE, (this.resultMap.length+1)*UNIT_DISTANCE, BufferedImage.TYPE_INT_BGR);
		Graphics graphicsObject = image.getGraphics();
		graphicsObject.setColor(Color.BLACK);
        graphicsObject.fillRect(0,0,(this.resultMap[0].length+1)*UNIT_DISTANCE, (this.resultMap.length+1)*UNIT_DISTANCE);
		for(int i=0; i< this.resultMap.length; i++){
			for(int j=0; j< this.resultMap[i].length; j++){
				if(START.equals(this.resultMap[i][j])){
					graphicsObject.setColor(Color.PINK);
				}else if(END.equals(this.resultMap[i][j])){
					graphicsObject.setColor(Color.RED);
				}else if(BARRIER.equals(this.resultMap[i][j])){
					graphicsObject.setColor(Color.ORANGE);
				}else if(GROUND.equals(this.resultMap[i][j])){
					graphicsObject.setColor(Color.GREEN);
				}else{
					graphicsObject.setColor(Color.BLUE);
				}
				graphicsObject.drawString(this.resultMap[i][j], (j+1)*UNIT_DISTANCE, (i+1)*UNIT_DISTANCE);
			}
		}
		graphicsObject.dispose();
	    ImageIO.write(image, "png", fPointer);
	    image.flush();
	}
	
	private void initialization(){
		for(int i=0; i<maze.length; i++)
			for(int j=0; j<maze[i].length; j++)
				maze[i][j].updateWeight(0, 0);
		openQueue.offer(this.start);
		List<Integer> startVector = new ArrayList<Integer>(2);
		startVector.add(0, start.x());startVector.add(1, start.y());
		List<Integer> endVector = new ArrayList<Integer>(2);
		endVector.add(0, end.x());endVector.add(1, end.y());
		this.start.updateWeight(0, MathOperator.minkowskiDistance(startVector, endVector, 2).intValue());
	}
	
	private void findPath(){
		AStarPoint pointWhichFValueIsLowestAtCurrent = null;
		while((pointWhichFValueIsLowestAtCurrent = findPointWhichFValueIsLowestFromOpenQueue()) != null){
			if(this.end.equals(pointWhichFValueIsLowestAtCurrent))
				return;
			updateNeighborPoints(pointWhichFValueIsLowestAtCurrent);
		}
	}
	
	private void updateNeighborPoints(AStarPoint current){
		List<Directions> direction = initializationDirections(current);
		while(!direction.isEmpty()){
			Directions currentDirection = direction.remove(0);
			switch(currentDirection){
			case Up:
				if(checkPositionCanBeReached(current.x() - 1, current.y()))
					updatePointStatus(maze[current.x()-1][current.y()], current);
				break;
			case Down:
				if(checkPositionCanBeReached(current.x() + 1, current.y()))
					updatePointStatus(maze[current.x()+1][current.y()], current);
				break;
			case Left:
				if(checkPositionCanBeReached(current.x(), current.y() - 1))
					updatePointStatus(maze[current.x()][current.y()-1], current);
				break;
			case Right:
				if(checkPositionCanBeReached(current.x(), current.y() + 1))
					updatePointStatus(maze[current.x()][current.y()+1], current);
				break;
			}
		}
	}
	
	private void updatePointStatus(AStarPoint current, AStarPoint last){
		int tempG = 1 + last.G();
		List<Integer> currentVector = new ArrayList<Integer>();
		currentVector.add(0, current.x()); currentVector.add(1, current.y());
		List<Integer> lastVector = new ArrayList<Integer>();
		lastVector.add(0, last.x()); lastVector.add(1, last.y());
		int tempH = MathOperator.minkowskiDistance(currentVector, lastVector, 2).intValue();
		int tempF = tempG + tempH;
		if(!openQueue.contains(current) || tempF < current.F()){
			if(!openQueue.contains(current))
				openQueue.add(current);
			current.updateWeight(tempG, tempH);
			current.setFather(last);
		}
	}
	/*
	 * 0-------------------------------------> y
	 * |
	 * |     ·Start
	 * |
	 * |
	 * |
	 * |
	 * |
	 * |
	 * |                           ·End
	 * ↓
	 * x
	 * 
	 * 
	 * differenceOfX = End.x - Start.x > 0  优先向下考虑
	 * differenceOfY = End.y - Start.y > 0  优先向右考虑
	 */
	private List<Directions> initializationDirections(AStarPoint current){
		List<Directions> direction = new LinkedList<Directions>();
		int differenceOfX = end.x() - current.x();
		int differenceOfY = end.y() - current.y();
		if(differenceOfX > 0 && differenceOfY > 0){
			if(Math.abs(differenceOfX) > Math.abs(differenceOfY)){
				direction.add(Directions.Right);
				direction.add(Directions.Down);
			}else{
				direction.add(Directions.Down);
				direction.add(Directions.Right);
			}
			direction.add(Directions.Up);
			direction.add(Directions.Left);
		}else if(differenceOfX > 0 && differenceOfY < 0){
			if(Math.abs(differenceOfX) > Math.abs(differenceOfY)){
				direction.add(Directions.Left);
				direction.add(Directions.Down);
			}else{
				direction.add(Directions.Down);
				direction.add(Directions.Left);
			}
			direction.add(Directions.Right);
			direction.add(Directions.Up);
		}else if(differenceOfX < 0 && differenceOfY > 0){
			if(Math.abs(differenceOfX) > Math.abs(differenceOfY)){
				direction.add(Directions.Right);
				direction.add(Directions.Up);
			}else{
				direction.add(Directions.Up);
				direction.add(Directions.Right);
			}
			direction.add(Directions.Left);
			direction.add(Directions.Down);
		}else{
			if(Math.abs(differenceOfX) > Math.abs(differenceOfY)){
				direction.add(Directions.Left);
				direction.add(Directions.Up);
			}else{
				direction.add(Directions.Up);
				direction.add(Directions.Left);
			}
			direction.add(Directions.Down);
			direction.add(Directions.Right);
		}
		return direction;
	}
	
	
	private boolean checkPositionCanBeReached(int x, int y){
		if(x < 0 || x >= maze.length || y < 0)
			return false;
		for(int i=0; i<maze.length; i++)
			if(y >= maze[i].length)
				return false;
		if(maze[x][y].isBarrier())
			return false;
		AStarPoint currentPoint = new AStarPoint(x, y, false);
		if(closeQueue.contains(currentPoint))
			return false;
		return true;
	}
	
	private AStarPoint findPointWhichFValueIsLowestFromOpenQueue(){
		AStarPoint currentPoint = null;	
		AStarPoint pointWhichFValueIsLowestAtCurrent = null;	
		double shorestFValueAtCurrent = Double.MAX_VALUE;
		Iterator<AStarPoint> iterator = openQueue.iterator();	
		while(iterator.hasNext()){	
			currentPoint = iterator.next();	
			if(currentPoint.F() <= shorestFValueAtCurrent){	
				pointWhichFValueIsLowestAtCurrent = currentPoint;	
				shorestFValueAtCurrent = currentPoint.F();
			}
		}
		if(shorestFValueAtCurrent < Double.MAX_VALUE){
			openQueue.remove(pointWhichFValueIsLowestAtCurrent);
			closeQueue.offer(pointWhichFValueIsLowestAtCurrent);
		}
		return pointWhichFValueIsLowestAtCurrent;
	}
}
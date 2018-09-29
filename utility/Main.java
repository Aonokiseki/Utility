package utility;

import java.io.IOException;


public class Main{
	public static void main(String[] args){
		String[][] mazeRaw = new String[][]{
				new String[]{" ", "x", " ", "x", " ", " ", " ", " ", " ", "E"},
				new String[]{" ", "x", " ", " ", " ", "x", " ", " ", "x", " "},
				new String[]{" ", " ", " ", "x", " ", "x", " ", "x", " ", " "},
				new String[]{" ", "x", " ", "x", " ", " ", "x", " ", " ", " "},
				new String[]{" ", "x", " ", "x", "x", " ", "x", " ", "x", " "},
				new String[]{" ", "x", " ", " ", " ", " ", "x", " ", " ", " "},
				new String[]{" ", "x", " ", " ", " ", " ", "x", " ", " ", "x"},
				new String[]{" ", " ", "x", " ", " ", " ", "x", " ", " ", "x"},
				new String[]{"x", " ", "x", " ", " ", "x", " ", " ", " ", " "},
				new String[]{"x", " ", " ", "x", "x", " ", " ", " ", "x", " "},
				new String[]{"x", "x", " ", " ", " ", " ", "x", " ", "x", " "},
				new String[]{" ", "x", " ", " ", "x", " ", " ", "x", " ", "x"},
				new String[]{" ", " ", " ", "x", " ", "x", " ", " ", " ", " "},
				new String[]{" ", "x", " ", "x", " ", " ", " ", "x", " ", " "},
				new String[]{" ", " ", " ", " ", " ", " ", "x", " ", " ", "x"},
				new String[]{"x", "x", " ", "x", " ", "x", "x", " ", " ", "x"},
				new String[]{" ", " ", " ", " ", " ", " ", " ", "x", " ", " "},
				new String[]{" ", " ", "x", "x", " ", " ", "x", " ", " ", " "},
				new String[]{" ", "x", " ", " ", " ", " ", " ", " ", "x", " "},
				new String[]{"S", " ", " ", " ", " ", "x", " ", " ", " ", "x"},
		};
		AStar astar = new AStar(mazeRaw);
		astar.execute();
		System.out.println("step == "+astar.step());
		try {
			astar.drawResultMap("C:/Users/trs/Desktop/out.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

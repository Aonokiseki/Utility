package utility;

import java.util.List;

public final class Other {
	private Other(){}
	 /**
     * 获得当前正在执行的方法名称
     */
    public static String getMethodName(){
    	StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();  
	    StackTraceElement e = stacktrace[2];  
	    String methodName = e.getMethodName();  
	    return methodName;
    }
    /**
     * 序列倒置
     * @param array 待处理序列
     */
    public static <T> void sequenceInversion(T[] array){
    	int i = -1;
    	int j = array.length;
    	T temp;
    	while(i++<j--){
    		temp = array[i];
    		array[i] = array[j];
    		array[j] = temp;
    	}
    }
    /**
     * 交换数组中两个元素的位置
     * @param array
     * @param index1
     * @param index2
     */
    public static <T> void exchangeTwoElementOfArray(T[] array, int index1, int index2){
    	if(index1 < 0 || index1 > (array.length - 1) || index2 < 0 || index2 > (array.length - 1))
    		return;
    	T temp = array[index1];
    	array[index1] = array[index2];
    	array[index2] = temp;
    }
    /**
     * 返回指定圆形范围内的一个坐标
     * @param x 圆心横坐标
     * @param y 圆心纵坐标
     * @param maxRadius 最大半径
     * @return Coordinate 坐标
     */
    public static Coordinate getRandomCoordinateByCircle(double x, double y, double maxRadius){
    	Coordinate c = new Coordinate(x, y);
    	return getRandomCoordinateByCircle(c, 2, maxRadius);
    }
    /**
     * 返回指定球形范围内的一个坐标
     * @param x 球心x轴坐标
     * @param y 球心y轴坐标
     * @param z 球心z轴坐标
     * @param maxRadius 最大半径
     * @return Coordinate 坐标
     */
    public static Coordinate getRandomCoordinateBySphere(double x, double y, double z, double maxRadius){
    	Coordinate c = new Coordinate(x, y, z);
    	return getRandomCoordinateByCircle(c, 3, maxRadius);
    }
    /**
     * 返回指定范围内的一个坐标<br>
     * 
     * 三维<br>
     * θ∈[0, 2π], φ∈[0, π]<br>
     * x = R * cos(θ) * sin(φ)<br>
     * y = R * sin(θ) * sin(φ)<br>
     * z = R * cos(φ)<br>
     * <br>
     * 二维<br>
     * θ∈[0, 2π]<br>
     * x = R * cos(θ)<br>
     * y = R * sin(θ)<br>
     * 
     * @param centerOfCircle 圆心/球心坐标
     * @param dimension 维数, 2或3
     * @param maxRadius 最大半径
     * @return Coordinate
     */
    private static Coordinate getRandomCoordinateByCircle(Coordinate centerOfCircle, int dimension, double maxRadius){
    	double theta = Math.random() * 2 * Math.PI;
    	double phi = Math.random() * Math.PI;
    	double x = Math.random() * maxRadius * Math.cos(theta);
    	double y = Math.random() * maxRadius * Math.sin(theta);
    	if(dimension == 3){
    		x = x * Math.sin(phi);
        	y = y * Math.sin(phi);
        	double z = Math.random() * maxRadius * Math.cos(phi);
        	return new Coordinate(x, y, z);
    	}
    	return new Coordinate(x, y);
    }
    /**
     * 返回平面矩形范围内一个随机坐标
     * @param x 矩形在x轴上的最小值
     * @param y 矩形在y轴上的最小值
     * @param length 矩形长度,决定最终的x
     * @param width 矩形的宽度，决定最终的y
     * @return Coordinate
     */
    public static Coordinate getRandomCoordinateBySquare(double x, double y, double length, double width){
    	double pX = Math.random() * length + x;
    	double pY = Math.random() * width + y;
    	return new Coordinate(pX, pY);
    }
    /**
     * 返回空间长方体范围内的一个随机坐标
     * @param x 长方体在x轴上的最小值
     * @param y 长方体在y轴上的最小值
     * @param z 长方体在z轴上的最小值
     * @param length 长方体长度,决定最终的x
     * @param width 长方体宽度,决定最终的y
     * @param heigh 长方体的高, 决定最终的z
     * @return Coordinate
     */
    public static Coordinate getRandomCoordinateByCuboid(double x, double y, double z, double length, double width, double heigh){
    	double pX = Math.random() * length + x;
    	double pY = Math.random() * width + y;
    	double pZ = Math.random() * heigh + z;
    	return new Coordinate(pX, pY, pZ);
    }
    
    /**
     * 返回堆栈字符串
     * 
     * @param throwable
     * @return String 堆栈信息
     */
    public static String exceptionToStackTrace(Throwable throwable){
    	StringBuilder stringBuilder = new StringBuilder();
    	stringBuilder.append(throwable.getMessage()+System.lineSeparator());
    	StackTraceElement[] traces = throwable.getStackTrace();
    	for(int i=0; i<traces.length-1; i++){
    		stringBuilder.append(traces[i].toString() + System.lineSeparator());
    	}
    	stringBuilder.append(traces[traces.length-1].toString());
    	return stringBuilder.toString();
    }
    
    /**
     * 获取当前正在运行的线程列表
     * 
     * @return Thread[]
     */
    public static Thread[] getListThreads(){
		Thread[] lstThreads;
		ThreadGroup currentGroup = Thread.currentThread().getThreadGroup();
		int noThreads = currentGroup.activeCount();
	    lstThreads = new Thread[noThreads];
	    currentGroup.enumerate(lstThreads);
	    return lstThreads;
    }
    
    /**
     * 交换列表中两个元素
     * @param list 目标列表
     * @param index1 第一个交换元素的位置
     * @param index2 第二个交换元素的位置
     */
    public static <T> void exchangeTwoElementOfList(List<T> list, int index1, int index2){
    	if(index1 < 0 || (index1 > list.size() - 1) || index2 < 0 || (index2 > list.size() - 1))
    		return;
    	list.add(index1, list.get(index2));
		list.add(index2 + 1, list.get(index1));
		list.remove(index1);
		list.remove(index2);
    }
}

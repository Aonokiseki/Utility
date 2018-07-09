package utility;

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
     * 返回圆形范围内一个随机的点
     * 
     * @param x 圆心的横坐标
     * @param y 圆心的纵坐标
     * @param maxRadius 最大半径
     * @return <code>Point</code> 落在圆内的一个点
     */
    public static Point getRandomPointByCircle(double x, double y, double maxRadius){
    	Point centerOfCircle = new Point(x, y);
    	return getRandomPointByCircle(centerOfCircle, maxRadius);
    }
    
    /**
     * 返回圆形范围内的一个随机的点
     * 
     * @param centerOfCircle 类型为<code>Point</code>,表示圆心
     * @param maxRadius 最大半径
     * @return <code>Point</code> 落在圆内的一个点
     */
    public static Point getRandomPointByCircle(Point centerOfCircle, double maxRadius){
    	double theta = Math.random() * 2 * Math.PI;
    	double x = Math.random() * maxRadius * Math.cos(theta);
    	double y = Math.random() * maxRadius * Math.sin(theta);
    	return new Point(x, y);
    }
    
    /**
     * 返回矩形范围内的一个随机的点
     * 
     * @param x 矩形左下角点的横坐标
     * @param y 矩形左下角点的纵坐标
     * @param length 矩形的长
     * @param width 矩形的宽
     * @return <code>Point</code> 落在矩形内的一个点
     */
    public static Point getRandomPointBySquare(double x, double y, double length, double width){
    	Point lowerLeftCorner = new Point(x, y);
    	return getRandomPointBySquare(lowerLeftCorner, length, width);
    }
    /**
     * 返回矩形范围内一个随机的点
     * 
     * @param lowerLeftCorner 矩形范围的左下角点,类型为<code>Point</code>
     * @param length 矩形的长
     * @param width 矩形的宽
     * @return <code>Point</code> 落在矩形内的一个点
     */
    public static Point getRandomPointBySquare(Point lowerLeftCorner, double length, double width){
    	double x = Math.random() * length + lowerLeftCorner.getX();
    	double y = Math.random() * width + lowerLeftCorner.getY();
    	return new Point(x, y);
    }
    
    /**
     * 返回堆栈字符串
     * 
     * @param throwable
     * @return String 堆栈信息
     */
    public static String exceptionToStackTrace(Throwable throwable){
    	StringBuilder stringBuilder = new StringBuilder();
    	String lineSeparator = System.lineSeparator();
    	StackTraceElement[] traces = throwable.getStackTrace();
    	for(StackTraceElement ste : traces){
    		stringBuilder.append(ste.toString()+lineSeparator);
    	}
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
}

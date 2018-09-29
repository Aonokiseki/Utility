package utility;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public final class MathOperator {
	private MathOperator(){}
	/**
	 * 求一个向量(或一组数)的期望和方差
	 * 
	 * @param vector 向量
	 * @return double[] double[0]是期望, double[1]是方差
	 */
	public static <T extends Number> Double[] getVariance(Vector<T> vector){
		Double[] result = new Double[2];
		result[0] = new Double(0.0);
		result[1] = new Double(0.0);
		double theSquareOfTheExpectation = 0.0;
		double theExpectationOfTheSquare = 0.0;
		double sumOfEachItem = 0.0;
		double sumOfSquareOfEachItem = 0.0;
		int length=vector.size();
		for(int i=0; i<length; i++){
			sumOfEachItem+=vector.get(i).doubleValue();
			sumOfSquareOfEachItem+=Math.pow(vector.get(i).doubleValue(), 2.0);
		}
		result[0] = sumOfEachItem / length;
		theSquareOfTheExpectation = Math.pow((sumOfEachItem/length),2.0);
		theExpectationOfTheSquare = sumOfSquareOfEachItem/length;
		result[1] = theExpectationOfTheSquare - theSquareOfTheExpectation;
		return result;
	}
	
	/**
     * 寻找序列中的最大值和最小值
     * 
     * @param array 待查找序列
     * @return double[0]是序列的最小值, double[1]是序列的最大值
     */
    public static double[] findMaxAndMin(double[] array){
    	double[] result = new double[]{Double.MAX_VALUE,Double.MIN_VALUE};
    	for(int i=0;i<array.length;i++){
    		if(array[i] < result[0])
    			result[0] = array[i];
    		if(array[i] > result[1])
    			result[1] = array[i];
    	}
    	return result;
    }
    
    public static <T extends Number> double[] findMaxAndMin(List<T> list){
    	double[] result = new double[]{Double.MAX_VALUE, Double.MIN_VALUE};
    	for(int i=0, size=list.size(); i<size; i++){
    		if(list.get(i).doubleValue() < result[0])
    			result[0] = list.get(i).doubleValue();
    		if(list.get(i).doubleValue() > result[1])
    			result[1] = list.get(i).doubleValue();
    	}
    	return result;
    }
    
    /**
     * 0-1标准化
     * 
     * @param array 待处理序列
     * @return double[] 处理后的序列
     */
    public static double[] minMaxNormalization(double[] array){
    	double[] result = new double[array.length];
    	double[] maxAndMin = findMaxAndMin(array);
    	double difference = maxAndMin[1] - maxAndMin[0];
    	for(int i=0; i<array.length; i++){
    		result[i] = (array[i] - maxAndMin[0])/difference;
    	}
    	return result;
    }
    
    /**
     * 判断n是否为素数
     * 
     * @param n
     * @return boolean
     */
    public static boolean isPrimes(int n){
    	if(n < 2){
    		return false;
    	}
    	for(int i=2; i<=Math.sqrt(n); i++){
    		if(n % i == 0){
    			return false;
    		}
    	}
    	return true;
    }
    
    /**
     * 阶乘运算
     * 
     * @param n n的阶乘
     * @return BigDecimal 大数
     */
    public static BigDecimal factorial(int n){
    	if(n < 0){
    		throw new IllegalArgumentException("n is a minus");
    	}
    	BigDecimal result = new BigDecimal("1");
    	BigDecimal multNumber = new BigDecimal("1");
    	BigDecimal addValue = new BigDecimal("1");
    	if(n == 0 || n == 1){
    		return result;
    	}
    	for(int i=1; i<=n; i++){
    		result = result.multiply(multNumber);
    		multNumber = multNumber.add(addValue);
    	}
    	return result;
    }
    
    /**
     * 两个向量之间的闵可夫斯基距离. <br>元素类型必须为Number类的子类,两个向量的长度必须相等.
     * 
     * @param vector1 向量1
     * @param vector2 向量2
     * @param dimension 维度, dimension不小于1. <br>1为曼哈顿距离;2为欧几里得距离;数字越大越接近切比雪夫距离
     * @return Double 距离
     */
    public static <T extends Number> Double minkowskiDistance(Vector<T> vector1, Vector<T> vector2, int dimension){
    	if(vector1.size() != vector2.size()){
    		throw new IllegalArgumentException("One vector's size don't equal the other's");
    	}
    	Double result = new Double(0.0);
    	if(dimension < 1){
    		dimension = 1;
    	}
    	for(int i=0, vectorSize=vector1.size(); i<vectorSize; i++){
    		result+=Math.pow((vector1.get(i).doubleValue() - vector2.get(i).doubleValue()), dimension);
    	}
    	result = Math.pow(result, (1.0/((double)dimension)));
    	return result;
    }
    
    /**
     * 两个点的平面距离
     */
    public static double distance2D(int x1, int y1, int x2, int y2){
    	Vector<Integer> vector1 = new Vector<Integer>();
    	vector1.add(x1); vector1.add(y1);
    	Vector<Integer> vector2 = new Vector<Integer>();
    	vector2.add(x2); vector2.add(y2);
    	return minkowskiDistance(vector1, vector2, 2).doubleValue();
    }
    
    /**
     * 求两个向量之间的余弦值,两个向量的长度必须相等
     * 
     * @param vector1
     * @param vector2
     * @return Double 余弦值
     */
    public static <T extends Number> Double cosine(Vector<T> vector1, Vector<T> vector2){
    	if(vector1.size() != vector2.size()){
    		throw new IllegalArgumentException("One vector's size don't equal the other's");
    	}
    	Double numerator = new Double(0.0);
    	Double denominatorPart1 = new Double(0.0);
    	Double denominatorPart2 = new Double(0.0);
    	for(int i=0, length=vector1.size(); i<length; i++){
    		numerator += vector1.get(i).doubleValue() * vector2.get(i).doubleValue();
    		denominatorPart1 += Math.pow(vector1.get(i).doubleValue(), 2.0);
    		denominatorPart2 += Math.pow(vector2.get(i).doubleValue(), 2.0);
    	}
    	return numerator / (Math.pow(denominatorPart1, 0.5) * Math.pow(denominatorPart2, 0.5));
    }
    
    /**
     * 不大于n的最大质数
     * 如果n是一个质数,返回;
     * 如果n是一个合数,找比n小的最大质数
     * @param n
     * @return 最大质数,最小返回2
     */
    public static int findMaxPrime(int n){
    	for(int i=n; i>1; i--){
    		if(isPrimes(i)){
    			return i;
    		}
    	}
    	return 2;
    }
    
    /**
     * 不小于n的最小质数
     * 如果n是一个质数,返回;
     * 如果n是一个合数,找比n大的最小质数
     * @param n 
     * @return
     */
    public static int findMinPrime(int n){
    	for(int i=n;i<=Integer.MAX_VALUE;i++){
    		if(isPrimes(i)){
    			return i;
    		}
    	}
    	return Integer.MAX_VALUE;
    }
    
    /**
     * 求两个正整数的最大公约数
     */
    public static int greatCommonDiviser(int a, int b){
    	if(a <= 0 || b <= 0)
    		throw new IllegalArgumentException("Parameter must greater than 0!");
    	int r = 0;
    	while(b > 0){
    		r = a % b;
    		a = b;
    		b = r;
    	}
    	return a;
    }
    
    /**
     * 求两个正整数的最小公倍数
     */
    public static long leastCommonMultiple(int a, int b){
    	return ((long)a * (long)b / greatCommonDiviser(a, b));
    }
    
    /**
     * 返回小于n的所有质数
     * 
     * @param n 自然数n, n不能小于2，否则返回空序列；
     * @return <b><code>List</code></b> 质数序列
     */
    public static List<Integer> getPrimesList(int n){
    	List<Integer> primesList = new ArrayList<Integer>();
    	if(n < 2)
    		return primesList;
    	primesList.add(2);
    	if(n == 2)
    		return primesList;
    	boolean isPrimes = false;
    	for(int i=3; i<n; i+=2){
    		for(int j=0,size=primesList.size(); j<size; j++){
    			if(i % primesList.get(j) == 0){
    				isPrimes = false;
    				break;
    			}
    			isPrimes = true;
    		}
    		if(isPrimes)
    			primesList.add(i);
    	}
    	return primesList;
    }
    /**
     * 取前n位质数
     * @param count 取出的数量,小于1时返回空表
     * @return List&ltInteger&gt质数表
     */
    public static List<Integer> getPreviousNPrimes(int count){
    	if(count < 1)
    		return new ArrayList<Integer>();
    	if(count == 1){
    		List<Integer> primes = new ArrayList<Integer>(1);
        	primes.add(2);
        	return primes;
    	}
    	List<Integer> primes = new ArrayList<Integer>(count);
    	primes.add(2);
    	boolean isPrimes = false;
    	for(int i=3; i<Integer.MAX_VALUE; i+=2){
    		for(int j=0,size=primes.size(); j<size; j++){
    			if(i % primes.get(j) == 0){
    				isPrimes = false;
    				break;
    			}
    			isPrimes = true;
    		}
    		if(isPrimes)
    			primes.add(i);
    		if(primes.size() >= count)
    			break;
    	}
    	return primes;
    }
}

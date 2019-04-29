package thinkinginjava;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Collections;
import java.util.List;

class NullRobotProxyHandler implements InvocationHandler{
	private String nullName;
	private Robot proxied = new NRobot();
	static{
		System.out.println("NullRobotProxyHandler static block;");
	}
	NullRobotProxyHandler(Class<? extends Robot> type){
		System.out.println("NullRobotProxyHandler();");
		nullName = type.getSimpleName() + " NullRobot";
	}
	private class NRobot implements Null, Robot{
		private NRobot(){
			System.out.println("NRobot();");
		}
		public String name(){
			System.out.println("NRobot.name();");
			return nullName;
		}
		public String model(){
			System.out.println("NRobot.model();");
			return nullName;
		}
		public List<Operation> operations(){
			System.out.println("NRobot.operations();");
			return Collections.emptyList();
		}
	}
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable{
		System.out.println("NullRobotProxyHandler.invoke();");
		return method.invoke(proxied, args);
	}	
}

public class NullRobot{
	public static Robot newNullRobot(Class<? extends Robot> type){
		return (Robot) Proxy.newProxyInstance(NullRobot.class.getClassLoader(), new Class[]{Null.class, Robot.class}, new NullRobotProxyHandler(type));
	}
	public static void main(String[] args){
		Robot nullRobot = newNullRobot(SnowRemovalRobot.class);
		System.out.println("Robot nullRobot = newNullRobot(SnowRemovalRobot.class);  -> completed");
		Robot.Test.test(nullRobot);
	}
}
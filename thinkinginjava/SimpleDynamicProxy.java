package thinkinginjava;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class SimpleDynamicProxy {
	public static void main(String[] args){
		RealObject real = new RealObject();
		Interface proxy = (Interface)Proxy.newProxyInstance(Interface.class.getClassLoader(), new Class[]{Interface.class}, new DynamicProxyHandler(real));
		consumer(proxy);
	}
	
	public static void consumer(Interface iFace){
		iFace.doSomething();
		iFace.somethingElse("bonobo");
	}
}

class DynamicProxyHandler implements InvocationHandler{

	private Object proxied;
	public DynamicProxyHandler(Object proxied){
		this.proxied = proxied;
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println(
				"proxy.getClass()=="+proxy.getClass()+System.lineSeparator()+
				"method == "+method+System.lineSeparator()
				);
		if(args != null){
			for(Object arg: args){
				System.out.println("arg: "+arg);
			}
		}
		return method.invoke(proxied, args);
	}
	
}
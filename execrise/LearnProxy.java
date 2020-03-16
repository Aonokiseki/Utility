package execrise;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class LearnProxy {
	public static void main(String[] args){
		Shape shape = (Shape)Proxy.newProxyInstance(Shape.class.getClassLoader(), new Class[]{Shape.class}, new NormalizeDynamicProxy(new Shape()
		{
			@Override
			public void draw() {
				System.out.println("new Shape().draw();");
			}
		}
		));
		shape.draw();
		
	}
}


interface Shape{
	public void draw();
}

class NormalizeDynamicProxy implements InvocationHandler{
	
	private Object proxied;
	
	public NormalizeDynamicProxy(Object proxied){
		this.proxied = proxied;
	}
	
	@Override
	public Object invoke(Object paramObject, Method paramMethod,
			Object[] paramArrayOfObject) throws Throwable {
		System.out.println("Before "+paramMethod.getName()+";");
		return paramMethod.invoke(proxied, paramArrayOfObject);
	}
	
}
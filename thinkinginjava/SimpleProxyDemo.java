package thinkinginjava;

public class SimpleProxyDemo {
	public static void main(String[] args){
		consumer(new RealObject());
		consumer(new SimpleProxy(new RealObject()));
	}
	
	public static void consumer(Interface iface){
		iface.doSomething();
		iface.somethingElse("bonobo");
	}
}

interface Interface{
	void doSomething();
	void somethingElse(String arg);
}

class RealObject implements Interface{
	public void doSomething(){
		System.out.println("do something.");
	}
	public void somethingElse(String arg){
		System.out.println("something else : "+ arg);
	}
}

class SimpleProxy implements Interface{
	private Interface proxied;
	public SimpleProxy(Interface proxied){
		this.proxied = proxied;
	}
	@Override
	public void doSomething() {
		System.out.println("SimpleProxy doSomething.");
		proxied.doSomething();
	}

	@Override
	public void somethingElse(String arg) {
		System.out.println("SimpleProxy Something else " + arg);
		proxied.somethingElse(arg);
	};
	
}
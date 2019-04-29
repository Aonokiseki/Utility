package thinkinginjava;

interface A{
	void f();
}

class B implements A{
	public void f(){}
	public void g(){}
}

public class IntefaceViolation {
	public static void main(String[] args){
		A a = new B();
		a.f();
		System.out.println(a.getClass().getName());
		if(a instanceof B){
			B b = (B)a;
			b.g();
		}
	}
}

class C implements A{
	public void f(){
		System.out.println("public C.f()");
	}
	public void g(){
		System.out.println("public C.g()");
	}
	void u(){
		System.out.println("package C.u()");
	}
	protected void v(){
		System.out.println("protected C.v()");
	}
	private void w(){
		System.out.println("private C.w()");
	}
}

 class HiddenC{
	public static A makeA(){
		return new C();
	}
}

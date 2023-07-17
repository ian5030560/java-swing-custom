public class Test {
	
	private int a;
	
	public Test() {
		a = 100;
	}
	
	public void setA(Integer v) {
		a = v;
		System.out.println(a);
	}
	
	public static void main(String[] args) {
		
		Effector e = new Effector(new Test()).duration(1000)
								.fromTo("a", 100, 1000);
		e.run();
	}
}

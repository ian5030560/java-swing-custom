package component;

public class Radius {
	
	public int topLeft;
	public int topRight;
	public int bottomRight;
	public int bottomLeft;
	
	public Radius(int radius) {
		this(radius, radius, radius, radius);
	}
	
	public Radius(int top, int bottom) {
		this(top, top, bottom, bottom);
	}
	
	public Radius(int topLeft, int topRight, int bottomRight, int bottomLeft) {
		this.topLeft = topLeft;
		this.topRight = topRight;
		this.bottomLeft = bottomLeft;
		this.bottomRight = bottomRight;
	}
	

}

package graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class RoundRectangle extends Area{
	
	public RoundRectangle(int x, int y, int width, int height, int radius) {
		this(x, y, width, height, radius, radius, radius, radius);
	}
	
	public RoundRectangle(int x, int y, int width, int height, int top, int bottom) {
		this(x, y, width, height, top, top, bottom, bottom);
	}
	
	public RoundRectangle(int x, int y, int width, int height, int topLeft, int topRight, int bottomRight, int bottomLeft){
		
		Area a = this.createTopLeft(x, y, width, height, topLeft);
		a.add(this.createTopRight(x, y, width, height, topRight));
		a.add(this.createBottomRight(x, y, width, height, bottomRight));
		a.add(this.createBottomLeft(x, y, width, height, bottomLeft));
		
		this.add(a);
	}
	
	private Area createTopLeft(int x, int y, int width, int height, int radius) {
		return new Area(new RoundRectangle2D.Double(x, y, (width + radius) / 2, (height + radius) / 2, radius, radius));
	}
	
	private Area createTopRight(int x, int y, int width, int height, int radius) {
		return new Area(new RoundRectangle2D.Double(x + (width - radius) / 2, y, (width + radius) / 2, (height + radius) / 2, radius, radius));
	}
	
	private Area createBottomRight(int x, int y, int width, int height, int radius) {
		return new Area(new RoundRectangle2D.Double(x, y + (height - radius) / 2, (width + radius) / 2, (height + radius) / 2, radius, radius));
	}
	
	private Area createBottomLeft(int x, int y, int width, int height, int radius) {
		return new Area(new RoundRectangle2D.Double(x + (width - radius) / 2, y + (height - radius) / 2, (width + radius) / 2, (height + radius) / 2, radius, radius));
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.getContentPane().add(new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				Graphics2D g2d = (Graphics2D) g;
				g2d.setColor(Color.red);
				Shape round = new RoundRectangle(10, 10, 50, 50, 10, 10, 10, 10);
				g2d.draw(round);
			}
		});
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setSize(300, 300);
		frame.setLocationRelativeTo(null);
	}
}

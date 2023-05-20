package border;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.AbstractBorder;

import Graphics.RoundRectangle;
import component.Radius;

public class RoundBorder extends AbstractBorder {
	
	private int borderWidth;
	private Radius radius;
	private Color color;
	
	public RoundBorder() {
		this(5, 1, Color.BLACK);
	}
	
	public RoundBorder(int borderWidth) {
		this(5, borderWidth, Color.BLACK);
	}

	public RoundBorder(Color color) {
		this(5, 1, color);
	}
	
	public RoundBorder(int radius, int borderWidth) {
		this(radius, borderWidth, Color.BLACK);
	}
	
	public RoundBorder(int borderWidth, Color color) {
		this(5, borderWidth, color);
	}
	
	public RoundBorder(int radius, int borderWidth, Color color) {
		this(new Radius(radius), borderWidth, color);
	}

	public RoundBorder(Radius radius, int borderWidth, Color color) {
		this.setRadius(radius);
		this.setBorderWidth(borderWidth);
		this.setColor(color);
	}
	
	public void setBorderWidth(int borderWidth) {
		this.borderWidth = borderWidth;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public void setRadius(Radius radius) {
		this.radius = radius;
	}
	
	@Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(color);
		g2d.setStroke(new BasicStroke(this.borderWidth));
		x = y = borderWidth / 2;
		RoundRectangle rect = new RoundRectangle(x, y, width - borderWidth, height - borderWidth, this.radius.topLeft, this.radius.topRight, this.radius.bottomRight, this.radius.bottomLeft);
		g2d.draw(rect);
	}
	
	@Override
    public Insets getBorderInsets(Component c, Insets insets) {
        insets.left = insets.top = insets.right = insets.bottom = this.borderWidth + 10;
        return insets;
    }
	
	public static void main(String[] args) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
//		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		JFrame frame = new JFrame();
		frame.getContentPane().setLayout(new FlowLayout());
		JButton label = new JButton("test");
		label.setPreferredSize(new Dimension(100, 100));
		RoundBorder border = new RoundBorder(20, 5);
		label.setBorder(border);
		frame.getContentPane().add(label);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}

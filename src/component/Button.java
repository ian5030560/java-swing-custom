package component;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

import Graphics.RoundRectangle;
import border.RoundBorder;

public class Button extends JButton {

	private Radius radius;
	
	private MouseActivity activity = new MouseActivity();
	
	public Button() {
		this(null, null);
	}

	public Button(Icon icon) {
		this(null, icon);
	}

	public Button(String text) {
		this(text, null);
	}

	public Button(Action a) {
		this();
		this.setAction(a);
	}

	public Button(String text, Icon icon) {
		super(text, icon);
		this.setContentAreaFilled(false);
		this.addMouseListener(this.activity);
		this.setBorder(new DefaultBorder());
	}
	
	public void setRadius(int radius) {
		this.setRadius(radius, radius);
	}
	
	public void setRadius(int top, int bottom) {
		this.setRadius(top, top, bottom, bottom);
	}
	
	public void setRadius(int topLeft, int topRight, int bottomRight, int bottomLeft) {
		this.setRadius(new Radius(topLeft, topRight, bottomRight, bottomLeft));
	}
	
	public void setRadius(Radius radius) {
		this.radius = radius;
		
		if(this.getBorder() instanceof DefaultBorder) {
			DefaultBorder border = (DefaultBorder) this.getBorder();
			border.setRadius(radius);
			this.setBorder(border);
		}
	}

	public Radius getRadius() {
		return this.radius;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		this.createShape(g2d, getBackground());
		
		if(this.getIcon() != null) {
			this.createImage(g2d);
		}
		this.createText(g2d, getForeground());
	}
	
	private void createShape(Graphics2D g, Color color) {
		g.setColor(color);
		RoundRectangle rect = new RoundRectangle(0, 0, this.getWidth(), this.getHeight(), this.radius.topLeft, this.radius.topRight, this.radius.bottomRight, this.radius.bottomLeft);
		g.fill(rect);
	}
	
	private void createText(Graphics2D g, Color color) {
		g.setColor(this.getForeground());
		g.setFont(this.getFont());
		FontMetrics fm = g.getFontMetrics();
		int width = fm.stringWidth(getText());
		int height = fm.getHeight();
		int x = (this.getWidth() - width) / 2;
		int y = (this.getHeight() - height) / 2 + fm.getAscent();
		g.drawString(this.getText(), x, y);
	}
	
	private void createImage(Graphics2D g) {
		ImageIcon imageIcon = (ImageIcon) this.getIcon();
		int x = this.getMargin().left + this.getInsets().left;
		int y = this.getMargin().top + this.getInsets().top;
		
		g.drawImage(imageIcon.getImage(), x, y, null);
		
	}
	
	@Override
	public void setBackground(Color color) {
		super.setBackground(color);
		if(this.activity != null) {
			this.activity.setColor(color);
		}
	}
	
	class MouseActivity extends MouseAdapter{
		
		private Color color = getBackground();
		
		@Override
		public void mousePressed(MouseEvent e) {
			setBackground(getBackground().darker());
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			setBackground(getBackground().brighter());
		}		
		
		public void setColor(Color color) {
			this.color = color;
		}
	}
	
	private class DefaultBorder extends RoundBorder{}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.getContentPane().setLayout(new FlowLayout());
		Button button = new Button("test");
//		ImageIcon icon = new ImageIcon(button.getClass().getClassLoader().getResource("chara_02.png"));
//		button.setIcon(icon);
		button.setRadius(10);
		frame.getContentPane().add(button);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}



import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JFrame;

import state.RunState;

public class SwitchButton extends Button {

	private int posX = 0;
	private Color pinColor = Color.BLACK;
	private Effector animator;
	
	public SwitchButton() {
		this(null, null, false);
	}

	public SwitchButton(Icon icon) {
		this(null, icon, false);
	}

	public SwitchButton(String text) {
		this(text, null, false);
	}

	public SwitchButton(Action a) {
		this();
		this.setAction(a);
	}

	public SwitchButton(Icon icon, boolean selected) {
		this(null, icon, selected);
		
	}

	public SwitchButton(String text, boolean selected) {
		this(text, null, selected);
		
	}

	public SwitchButton(String text, Icon icon) {
		this(null, icon, false);
		
	}

	public SwitchButton(String text, Icon icon, boolean selected) {
		super(text, icon);
		this.animator = new Effector(this);
		this.setSelected(selected);
		this.setContentAreaFilled(false);
		this.addActionListener(new Trigger());
		this.addComponentListener(new Resize());
	}

	private void setPosX(int x) {
		this.posX = x;
	}
	
	public void setPinColor(Color color) {
		this.pinColor = color;
	}
	
	public Color getPinColor() {
		return this.pinColor;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2d.setColor(pinColor);
		g2d.fillOval(this.posX, 5, this.getHeight() - getRadius().topLeft, this.getHeight() - getRadius().topLeft);
	}
	
	class Trigger implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			setSelected(!isSelected());
			if(isSelected()) {
				animator.run(RunState.normal);
			}
			else {
				animator.run(RunState.reverse);
			}
		}
	}
	
	class Resize extends ComponentAdapter{
		public void componentResized(ComponentEvent e) {
			animator.fromTo("posX", 0, getWidth() - getHeight() + getRadius().topLeft);
		}
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.getContentPane().add(new SwitchButton());
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}

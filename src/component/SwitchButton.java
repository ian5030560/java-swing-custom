package component;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.Action;
import javax.swing.Icon;
import animator.Animator;

public class SwitchButton extends Button {

	private int posX = 0;
	private Color pinColor = Color.BLACK;
	private Animator<Integer> animator;
	
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
		this.setSelected(selected);
		this.setContentAreaFilled(false);
//		this.addActionListener(new Trigger());
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
		this.posX = !this.isSelected() ? 0: this.getWidth() - this.getHeight() + 10;
		g2d.fillOval(this.posX, 5, this.getHeight() - 10, this.getHeight() - 10);
	}
}

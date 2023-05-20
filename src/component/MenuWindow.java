package component;

import java.awt.Component;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.AbstractButton;
import javax.swing.JWindow;

public class MenuWindow extends JWindow{
	
	private AbstractButton button;
	private Floating floating = new Floating();
	
	public MenuWindow() {
		this.init();
	}
	
	public MenuWindow(AbstractButton button) {
		this();
		this.setButton(button);
	}
	
	private void init() {
		this.setVisible(false);
		this.setAlwaysOnTop(true);
		this.addMouseListener(floating);
	}
	
	
	public void setButton(AbstractButton button) {
		this.button = button;
		this.button.addMouseListener(floating);
	}
	
	public AbstractButton getButton() {
		return this.button;
	}
	
	private void adjustLocation() {
		Point pos = this.button.getLocationOnScreen();
		Frame frame = this.findOrginalFrame();
		
		if(frame.contains(this.button.getX() + this.getWidth(), this.button.getY())) {
			this.setLocation(pos.x, pos.y + this.button.getHeight());
		}
		else {
			this.setLocation(pos.x - this.getWidth() / 2, pos.y + this.button.getHeight());
		}
	}
	
	private Frame findOrginalFrame() {
		Component parent = this.button.getParent();
		while(!(parent instanceof Frame)) {
			parent = parent.getParent();
		}
		
		return (Frame) parent;
	}

	
	private class Floating extends MouseAdapter{
		public void mousePressed(MouseEvent e) {
			if(e.getComponent() == button) {
				setVisible(!isVisible());
				adjustLocation();
			}
		}
		
		public void mouseExited(MouseEvent e) {
			if(isVisible()) {
				Rectangle self = new Rectangle(getLocationOnScreen(), getSize());
				setVisible(self.contains(e.getLocationOnScreen()));	
			}
		}
	}
}

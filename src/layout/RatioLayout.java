package layout;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
public class RatioLayout implements LayoutManager {
	
	private List<Integer> ratios;
	public enum Arrange{
		X, Y;
	}
	private Arrange arrange;
	
	public RatioLayout() {
		this(new ArrayList<Integer>(), Arrange.X);
	}
	
	public RatioLayout(List<Integer> ratios) {
		this(ratios, Arrange.X);
	}
	
	public RatioLayout(Integer... ratios) {
		this.setRatios(ratios);
		this.arrange = Arrange.X;
	}
	
	public RatioLayout(Arrange arrange) {
		this(new ArrayList<Integer>(), arrange);
	}
	
	public RatioLayout(List<Integer> ratios, Arrange arrange) {
		this.setRatios(ratios);
		this.arrange = arrange;
	}
	
	public void setRatios(Integer... ratios) {
		this.ratios = new ArrayList<Integer>();
		for(int i = 0; i < ratios.length; i ++) {
			this.ratios.add(ratios[i]);
		}
	}
	
	public void setRatios(List<Integer> ratios) {
		this.ratios = ratios;
	}
	
	public List<Integer> getRatios(){
		return this.ratios;
	}
	
	@Override
	public void addLayoutComponent(String name, Component comp) {
		

	}

	@Override
	public void removeLayoutComponent(Component comp) {
		this.ratios.remove(this.ratios.size() - 1);

	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return this.calculate(parent, "preferred");
	}
 
	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return this.calculate(parent, "minimum");
	}
	
	private Dimension calculate(Container parent, String layoutType) {
		Insets inset = parent.getInsets();
		int width = inset.top + inset.bottom;
		int height = inset.left + inset.right;
		
		Dimension compSize = null;
		for(int c = 0; c < parent.getComponentCount(); c ++) {
			Component comp = parent.getComponent(c);
			
			if(layoutType.equals("preferred")) {
				compSize = comp.getPreferredSize();
			}
			else if(layoutType.equals("minimum")) {
				compSize = comp.getMinimumSize();
			}
			
	        switch (arrange) {
	            case X:
	                width += ratios.get(c) * compSize.width;
	                height = Math.max(height, compSize.height);
	                break;
	            case Y:
	                width = Math.max(width, compSize.width);
	                height += ratios.get(c) * compSize.height;
	                break;
	        }
		}
		return new Dimension(width, height);
	}
	
	@Override
	public void layoutContainer(Container parent) {
		Insets insets = parent.getInsets();
		int x = parent.getX() + insets.left;
		int y = parent.getY() + insets.top;
		int width = parent.getWidth() - insets.left - insets.right;
		int height = parent.getHeight() - insets.top - insets.bottom;
		
		while(this.ratios.size() < parent.getComponentCount()) {
			this.ratios.add(1);
		}
		
		int sum = this.sumRatios();
		int cx = 0, cy = 0;
		int compWidth = 0, compHeight = 0;
		for(int c = 0; c < parent.getComponentCount(); c ++) {
			Component comp = parent.getComponent(c);
			
			double ratio = (double) this.ratios.get(c) / (double) sum;
			switch(this.arrange) {
				case X:
					compWidth = (int) (width * ratio);
					compHeight = height;
					break;
				case Y:
					compWidth = width;
					compHeight = (int) (height * ratio);
					break;
			}

			int compX = x + cx;
			int compY = y + cy;
			comp.setBounds(compX, compY, compWidth, compHeight);
			
			switch(this.arrange) {
				case X:
					cx += compWidth;
					break;
				case Y:
					cy += compHeight;
					break;
			}
		}
	}
	
	private int sumRatios() {
		int result = 0;
		for(int ratio: ratios) {
			result += ratio;
		}
		
		return result;
	}
	
	public static void main(String[] args) {

		JFrame frame = new JFrame();
		frame.setLayout(new RatioLayout());
		JPanel panel1 = new JPanel();
//		List<Integer> ratios = new ArrayList<Integer>();
//		ratios.add(1);
//		ratios.add(1);
//		ratios.add(1);
		RatioLayout layout = new RatioLayout(RatioLayout.Arrange.Y);
		panel1.setLayout(layout);
//		layout.setRatios(ratios);
		frame.add(panel1);
		panel1.add(new JButton("test"));
		panel1.add(new JButton("test1"));
		panel1.add(new JButton("test2"));
		panel1.add(new JButton("test3"));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.pack();
	}
}

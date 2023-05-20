package animator;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Animator <E>{

	private long duration;
	private E from;
	private E to;
	private List<E> motions = new ArrayList<E>();
	private Operator<E> operation = (Operator<E>) new DefaultOperator();
	private EaseCurve easeCurve = new DefaultCurve();
	private RunState state = RunState.normal;
	private Consumer<E> target;
	
	public Animator(Consumer<E> target) {
		this(target, null, null, 500);
	}
	
	public Animator(Consumer<E> target, long duration) {
		this(target, null, null, duration);
	}
	
	public Animator(Consumer<E> target, E from, E to) {
		this(target, from, to, 500);
	}
	
	public Animator(Consumer<E> target, E from, E to, long duration) {
		this.target = target;
		this.from = from;
		this.to = to;
		this.setDuration(duration);
	}
	
	public void setDuration(long duration) {
		this.duration = duration;
	}
	
	public void setStartValue(E value) {
		this.from = value;
	}
	
	public void setEndValue(E value) {
		this.to = value;
	}
	
	private void load(E from, E to) {
		this.motions.clear();
		for(int t = 0; t <= this.duration; t ++) {
			float fraction = easeCurve.excute((float)t / (float)this.duration);
			E value = this.operation.add(from, this.operation.multiply(fraction, this.operation.substract(to, from)));
			this.motions.add(value);
		}
	}
	
	public void defineOperation(Operator<E> operation) {
		this.operation = operation;
	}
	
	public void setEaseCurve(EaseCurve easeCurve) {
		this.easeCurve = easeCurve;
	}
	
	public void run() {
		this.run(RunState.normal);
	}
	
	public void run(RunState state) {
		synchronized(this) {
			if(state == RunState.reverse) {
				this.load(to, from);
			}
			else {
				this.load(from, to);
			}
				
			Thread thread = new Thread(new AnimationTask());
			thread.start();
		}
	}
	
	class DefaultCurve implements EaseCurve {

		@Override
		public float excute(float fraction) {
			return fraction;
		}
	}
	
	class DefaultOperator implements Operator<Number>{

		@Override
		public Number add(Number a, Number b) {
			if(a instanceof Integer && b instanceof Integer) {
				return (Integer)a + (Integer)b;
			}
			return (Double)a + (Double)b;
		}

		@Override
		public Number substract(Number a, Number b) {
			if(a instanceof Integer && b instanceof Integer) {
				return (Integer)a - (Integer)b;
			}
			return (Double)a - (Double)b;
		}

		@Override
		public Number multiply(double fraction, Number value) {
			if(value instanceof Integer) {
				return fraction * (Integer)value;
			}
			return fraction * (Double)value;
		}
		
	}
	
	class AnimationTask implements Runnable{

		@Override
		public void run() {
			for(E motion: motions) {
				target.accept(motion);
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					
					e.printStackTrace();
				}
			}
			
		}
	}

}

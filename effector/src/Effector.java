import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import easeCurve.EaseCurve;
import operator.Operator;
import operator.OperatorMap;
import state.RunState;

public class Effector implements EffectProperty<Effector>{

	private long duration;
	private Object from;
	private Object to;
	private List<Object> motions = new ArrayList<Object>();
	public static OperatorMap map = new OperatorMap();
	private EaseCurve easeCurve = new DefaultCurve();
	private RunState state = RunState.normal;
	private Method method;
	private Object obj;
	
	public Effector(Object obj){
		this.obj = obj;
	}
	
	@SuppressWarnings("unchecked")
	private void load(Object from, Object to) {
		this.motions.clear();
		Operator<Object> op = (Operator<Object>) map.get(from.getClass());
		for(int t = 0; t <= this.duration; t ++) {
			float fraction = easeCurve.excute((float)t / (float)this.duration);
			Object value = op.add(from, op.multiply(fraction, op.substract(to, from)));
			this.motions.add(value);
		}
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
	
	class AnimationTask implements Runnable{

		@Override
		public void run() {
			for(Object motion: motions) {
				try {
					method.invoke(obj, motion);
					Thread.sleep(1);
				} catch (InterruptedException | IllegalAccessException | InvocationTargetException Object) {
					
					Object.printStackTrace();
				}
			}
			
		}
	}

	public Effector fromTo(String target, Object from, Object to) {
		try {
			this.method = fetchMethod(obj, target, from.getClass());
			this.from = from;
			this.to = to;
		} catch (NoSuchMethodException | SecurityException e) {
			
			e.printStackTrace();
		}
		
		return this;
	}
	
	private Method fetchMethod(Object obj, String target, Class<?> type) throws NoSuchMethodException, SecurityException {
		String name = getSetterName(target);
		Method m = null;
		
		try {
			m = obj.getClass().getMethod(name, type);
		}
		catch(NoSuchMethodException e) {
			type = getPrimtiveClass(type);
			if(!type.equals(null)) {
				m = obj.getClass().getMethod(name, type);
			}
			else {
				e.printStackTrace();
			}
		}
		
		return m;
	}
	
	private final static class PrimitiveMap{
		
		private HashMap<Class<?>, Class<?>> pMap = new HashMap<Class<?>, Class<?>>();
		
		public PrimitiveMap() {
			pMap.put(Integer.class, int.class);
			pMap.put(Double.class, double.class);
			pMap.put(Long.class, long.class);
			pMap.put(Float.class, float.class);
			pMap.put(Short.class, short.class);
		}
		
		public Class<?> findPrimitiveClass(Class<?> type){
			return pMap.get(type);
		}
	}
	
	private final static PrimitiveMap pmap = new PrimitiveMap();
	
	private Class<?> getPrimtiveClass(Class<?> type){
		return pmap.findPrimitiveClass(type);
	}
	
	private String getSetterName(String target) {
		return "set" + upperHead(target) + target.substring(1);
	}
	
	private String upperHead(String target) {
		String head = target.substring(0, 1);
		return head.toUpperCase();
	}
	
	@Override
	public Effector easeCurve(EaseCurve easeCurve) {
		this.easeCurve = easeCurve;
		return this;
	}

	@Override
	public Effector state(RunState state) {
		this.state = state;
		return this;
	}

	@Override
	public Effector duration(long duration) {
		this.duration = duration;
		return this;
	}
}

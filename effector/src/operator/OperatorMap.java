package operator;

import java.util.HashMap;


public final class OperatorMap {
	
	private HashMap<Class<?>, Operator<?>> map = new HashMap<Class<?>, Operator<?>>();
	
	public OperatorMap() {
		map.put(Integer.class, new SupportInteger());
		map.put(Double.class, new SupportDouble());
		map.put(Float.class, new SupportFloat());
		map.put(Long.class, new SupportLong());
		map.put(Short.class, new SupportShort());
	}
	
	public void add(Class<?> className, Operator<?> operator) {
		map.put(className, operator);
	}
	
	public Operator<?> get(Class<?> className){
		return map.get(className);
	}
		
	private static class SupportInteger implements Operator<Integer>{

		@Override
		public Integer add(Integer a, Integer b) {
			
			return a + b;
		}

		@Override
		public Integer substract(Integer a, Integer b) {
			
			return a - b;
		}

		@Override
		public Integer multiply(double fraction, Integer value) {
			
			return (int) (fraction * value);
		}
		
	}
	
	private static class SupportDouble implements Operator<Double>{

		@Override
		public Double add(Double a, Double b) {
			
			return a + b;
		}

		@Override
		public Double substract(Double a, Double b) {
			
			return a - b;
		}

		@Override
		public Double multiply(double fraction, Double value) {
			
			return fraction * value;
		}

	}
	
	private static class SupportLong implements Operator<Long>{

		@Override
		public Long add(Long a, Long b) {
			
			return a + b;
		}

		@Override
		public Long substract(Long a, Long b) {
			
			return a - b;
		}

		@Override
		public Long multiply(double fraction, Long value) {
			
			return (long) (fraction * value);
		}
		
	}
	
	private static class SupportShort implements Operator<Short>{

		@Override
		public Short add(Short a, Short b) {
			
			return (short) (a + b);
		}

		@Override
		public Short substract(Short a, Short b) {
			
			return (short) (a - b);
		}

		@Override
		public Short multiply(double fraction, Short value) {
			
			return (short) (fraction * value);
		}
		
	}
	
	private static class SupportFloat implements Operator<Float>{

		@Override
		public Float add(Float a, Float b) {
			
			return a + b;
		}

		@Override
		public Float substract(Float a, Float b) {
			
			return a - b;
		}

		@Override
		public Float multiply(double fraction, Float value) {
			
			return (float) (fraction * value);
		}
		
	}
}

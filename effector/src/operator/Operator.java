package operator;

public interface Operator<E> {

	public abstract E add(E a, E b);
	public abstract E substract(E a, E b);
	public abstract E multiply(double fraction, E value);
}

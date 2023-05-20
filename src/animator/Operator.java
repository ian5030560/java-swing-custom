package animator;

public interface Operator<E> {
	public E add(E a, E b);
	public E substract(E a, E b);
	public E multiply(double fraction, E value);
}

import easeCurve.EaseCurve;
import state.RunState;

public interface EffectProperty<E> {
	public E fromTo(String target, Object from, Object to);
	public E easeCurve(EaseCurve easeCurve);
	public E state(RunState state);
	public E duration(long duration);
}

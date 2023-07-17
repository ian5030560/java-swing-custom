package event;

public interface MediaListener {
	public void played(MediaEvent e);
	public void stopped(MediaEvent e);
	public void ended(MediaEvent e);
}

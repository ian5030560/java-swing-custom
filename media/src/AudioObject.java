import java.net.URL;

public class AudioObject extends AbstractMediaObject{
	
	private int volume = 100;
	
	public AudioObject(String filePath) {
		super(filePath);
		
	}

	public AudioObject(URL fileUrl) {
		super(fileUrl);
		
	}

	public AudioObject(URL fileUrl, int volume) {
		this(fileUrl);
		this.volume = volume;
		
	}
	
	@Override
	public void play() {
		
		
	}

	@Override
	public void pause() {
		
	}
	
	public void setVolume(int volume) {
		this.volume = volume;
	}
	
	public int getVolume() {
		return this.volume;
	}
}

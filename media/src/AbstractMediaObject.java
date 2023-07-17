import java.net.URL;

public abstract class AbstractMediaObject {

	public AbstractMediaObject(URL fileUrl) {
		
	}

	public AbstractMediaObject(String filePath) {
		
	}
	
	public abstract void play();
	public abstract void pause();
	
}

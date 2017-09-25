package rpi.lmsgrabber;

/**
 * Hello world!
 *
 */
public class App {

	public App() {
		LmsGrab grabber = new LmsGrab();
		grabber.grab();
	}
	
	public static void main(String[] args) {
		new App();
	}
	
}

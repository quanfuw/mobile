import java.io.IOException;

import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Image;
import javax.microedition.midlet.MIDlet;

public class ScrollableImage
    extends MIDlet {
  public void startApp() {
    ScrollableImageCanvas sweeper;
	try {
		sweeper = new ScrollableImageCanvas(Image.createImage("/bingo.png"));
		Display.getDisplay(this).setCurrent(sweeper);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }

  public void pauseApp() {}

  public void destroyApp(boolean unconditional) {}
}
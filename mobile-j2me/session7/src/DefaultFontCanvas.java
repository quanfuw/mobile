import javax.microedition.lcdui.*;
import net.sergetk.mobile.lcdui.BitmapFont;

public class DefaultFontCanvas extends Canvas {
	BitmapFont myFont;

	public DefaultFontCanvas() {
		myFont = new BitmapFont("myfont.fnt");
	}

	protected void paint(Graphics g) {
		g.setColor(0x00FF00); // green
		myFont.drawString(g, "Hello, world!", 0, 0, Graphics.LEFT | Graphics.TOP);
	}
}
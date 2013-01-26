import java.io.IOException;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.midlet.MIDlet;

public class QuatshGame
    extends MIDlet{
  public void startApp() {
    Displayable d;
	try {
		d = new QuatschCanvas("/boy.png", "/tree.png", "/backgroun.png");
	

    d.addCommand(new Command("Exit", Command.EXIT, 0));
    d.setCommandListener(new CommandListener() {
      public void commandAction(Command c, Displayable s) {
        notifyDestroyed();
      }
    } );
    Display.getDisplay(this).setCurrent(d);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
   
  }

  public void pauseApp() { }

  public void destroyApp(boolean unconditional) { }
}
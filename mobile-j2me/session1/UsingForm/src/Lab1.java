import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.StringItem;
import javax.microedition.lcdui.TextField;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;


public class Lab1 extends MIDlet implements CommandListener{
	Form f ;
	Command exitCm; 
	public Lab1() {
		// TODO Auto-generated constructor stub
		f = new Form("Lab 1");
		f.append(new StringItem("Tile ", "value"));
		TextField input = new TextField("input ", "", 30, TextField.ANY);
		f.append(input);
		Command c = new Command("Exit", Command.EXIT, 1) ;
		input.addCommand(c);
		
	}

	protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
		// TODO Auto-generated method stub

	}

	protected void pauseApp() {
		// TODO Auto-generated method stub

	}

	protected void startApp() throws MIDletStateChangeException {
		// TODO Auto-generated method stub
		Display.getDisplay(this).setCurrent(f) ;
	}

	public void commandAction(Command c, Displayable d) {
		System.out.println();
		if(c  ==  exitCm ) {
			notifyDestroyed();
		}
		
	}

}

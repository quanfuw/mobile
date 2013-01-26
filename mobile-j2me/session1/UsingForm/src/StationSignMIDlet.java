import item.StationSign;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.StringItem;
import javax.microedition.midlet.MIDlet;

public class StationSignMIDlet
    extends MIDlet
    implements CommandListener {
  public void startApp() {
    Display display = Display.getDisplay(this);
    Form form = new Form("StationSignMIDlet");
    form.append(new StringItem("StringItem: ", "item one"));
    StationSign ss = new StationSign("Destination", display);
    ss.add("Albuquerque");
    ss.add("Savannah");
    ss.add("Pocatello");
    ss.add("Des Moines");
    form.append(ss);
    form.append(new StringItem("StringItem: ", "item two"));

    Command c = new Command("Exit", Command.EXIT, 0);
    form.addCommand(c);
    form.setCommandListener(this);

    display.setCurrent(form);
  }

  public void pauseApp() {}

  public void destroyApp(boolean unconditional) {}

  public void commandAction(Command c, Displayable s) {
    if (c.getCommandType() == Command.EXIT)
      notifyDestroyed();
  }
}
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.StringItem;
import javax.microedition.media.Manager;
import javax.microedition.midlet.MIDlet;

public class MediaInformationMIDlet
    extends MIDlet
    implements CommandListener {
  private Form mInformationForm;

  public void startApp() {
    if (mInformationForm == null) {
      mInformationForm =
          new Form("Content types and protocols");
      String[] contentTypes =
          Manager.getSupportedContentTypes(null);
      for (int i = 0; i < contentTypes.length; i++) {
        String[] protocols =
            Manager.getSupportedProtocols(contentTypes[i]);
        for (int j = 0; j < protocols.length; j++) {
          StringItem si = new StringItem(contentTypes[i] + ": ",
              protocols[j]);
          si.setLayout(Item.LAYOUT_NEWLINE_AFTER);
          mInformationForm.append(si);
        }
      }
      Command exitCommand = new Command("Exit", Command.EXIT, 0);
      mInformationForm.addCommand(exitCommand);
      mInformationForm.setCommandListener(this);
    }
    Display.getDisplay(this).setCurrent(mInformationForm);
  }

  public void pauseApp() {}

  public void destroyApp(boolean unconditional) {}

  public void commandAction(Command c, Displayable s) {
    notifyDestroyed();
  }
}
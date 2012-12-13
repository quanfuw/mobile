/**
 * Pyx4me framework
 * Copyright (C) 2006-2007 pyx4j.com.
 * 
 * @author vlads
 * @version $Id: ExampleMIDlet.java 2066 2008-06-21 23:16:18Z vlads $
 */
package com.pyx4me.example;

import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

public class ExampleMIDlet extends MIDlet implements CommandListener {

    private Command cmd_exit;

    private Display display;

    private Form form;

    public void startApp() throws MIDletStateChangeException {

        display = Display.getDisplay(this);
        form = new Form("Example");
        form.setCommandListener(this);

        System.out.println("Starting app");

        cmd_exit = new Command("Exit", Command.EXIT, 0);
        form.addCommand(cmd_exit);

        form.append("Hello j2me");

        Runtime rt = Runtime.getRuntime();
        form.append("Free Memory: " + rt.freeMemory());
        form.append("Total Memory: " + rt.totalMemory());

        display.setCurrent(form);
    }

    public void pauseApp() {

    }

    public void destroyApp(boolean unconditional) throws MIDletStateChangeException {
        notifyDestroyed();
    }

    public void commandAction(Command c, Displayable d) {
        if (c == cmd_exit) {
            try {
                destroyApp(true);
            } catch (MIDletStateChangeException e) {
                showException(e);
            }
        }
    }

    public void showException(Exception e) {
        Alert alert = new Alert("Error");
        alert.setString(e.getMessage());
        alert.setType(AlertType.ERROR);
        alert.setTimeout(Alert.FOREVER);
        display.setCurrent(alert);
    }
}

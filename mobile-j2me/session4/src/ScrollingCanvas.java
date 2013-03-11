import java.io.IOException;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class ScrollingCanvas extends Canvas {
 
 // Current location
 private int verticalLoc, horizontalLoc;
 // Last pointer (finger) location
 private int lastX, lastY;
 // Background image size
 private static final int IMAGE_WIDTH = 183;
 private static final int IMAGE_HEIGHT = 183;
 
 public ScrollingCanvas() {
   verticalLoc = 0;
   horizontalLoc = 0;
   setFullScreenMode(true);
 }
 
 protected void paint(Graphics g) {
 
   // Draw background with pattern image
   int x = horizontalLoc % IMAGE_WIDTH;
   if(x>0) {
     x -= IMAGE_WIDTH;
   }
   int origX = x;
 
   int y = verticalLoc % IMAGE_HEIGHT;
   if(y>0) {
     y -= IMAGE_HEIGHT;
   }
 
   boolean verticalDone = false;
   while(!verticalDone) {
     boolean horizontalDone = false;
     while(!horizontalDone) {
       try {
		g.drawImage(Image.createImage("/bingo.png"),
		      
		     x, y, Graphics.LEFT|Graphics.TOP);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
       x += IMAGE_WIDTH;
       if(x>getWidth()) {
         horizontalDone = true;
       }
     }
     x = origX;
     y += IMAGE_HEIGHT;
     if(y>getHeight()) {
       verticalDone = true;
     }
   }
 }
 
 /**
 * Finger is pressed on screen
 * @param x coordinate
 * @param y coordinate
 */
 protected void pointerPressed(int x, int y) {
   lastX = x;
   lastY = y;
 }
 
 /**
 * Finger is dragged on screen
 * @param x coordinate
 * @param y coordinate
 */
 protected void pointerDragged(int x, int y) {
   // Calculate how much we moved horizontally
   int deltaHorizontal = lastX-x;
   horizontalLoc -= deltaHorizontal;
   lastX = x;
 
   // Calculate how much we moved vertically
   int deltaVertical = lastY-y;
   verticalLoc -= deltaVertical;
   lastY = y;
 
   // Repaint the screen since we have scrolled
   repaint();
 }

 
}
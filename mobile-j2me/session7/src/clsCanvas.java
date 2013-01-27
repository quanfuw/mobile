import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;

public class clsCanvas extends GameCanvas implements Runnable {
private boolean isRunning = true;    
private Graphics g;
private midMain fParent;
private Image imgEarth;

    public clsCanvas(midMain m) {
        super(true);
        fParent = m;
        setFullScreenMode(true);
    }
    
    public void start(){
        Thread runner = new Thread(this);
        runner.start();
    }
    
    public void load(){
        try{
            // try to load the image file
            imgEarth = Image.createImage("/earthstrip.png");
        }catch(Exception ex){
            // exit the app if it fails to load the image
            isRunning = false;
            return;
        }
    }
    
    public void unload(){
        // make sure the object get's destroyed
        imgEarth = null;
    }

    public void run() {
       int iKey = 0;

       int imgX = 50; // x coordinate of the image
       int frameIndex = 0; // current frame to be drawn
       long lDelay = 250; //time to pause between frames in milliseconds
       long lStart = 0; //time we last changed frames in milliseconds
       long lCurrTick = 0; // current system time in milliseconds;       
       load();
       g = getGraphics();
       while(isRunning){
           
           iKey = getKeyStates();
           
           if ((iKey & GameCanvas.FIRE_PRESSED) != 0){
               isRunning = false;
           }
           
           lCurrTick = System.currentTimeMillis();
           if ((lCurrTick-lStart) >= lDelay){
               lStart = lCurrTick; // save the current time
               if (frameIndex < 8) {
                   frameIndex++; // skip to the next frame
               } else {
                   frameIndex = 0; // go back to first frame
               }
               imgX = 50 - (frameIndex * 16); // compute x relative to clip rect
           }
           
           //restore the clipping rectangle to full screen
           g.setClip(0, 0, getWidth(), getHeight());
           //set drawing color to black
           g.setColor(0x000000);
           //fill the whole screen
           g.fillRect(0, 0, getWidth(), getHeight());
           // set drawing color to white
           g.setColor(0xffffff);
           //display the key code last pressed
           g.drawString(Integer.toString(iKey), 2, 2, Graphics.TOP | Graphics.LEFT);

           g.drawString("Frame : " + Integer.toString(frameIndex), 2, 68, Graphics.TOP | Graphics.LEFT);
           g.drawString("X : " + Integer.toString(imgX), 2, 88, Graphics.TOP | Graphics.LEFT);
           
           //clip the drawing area to a single frame
           g.setClip(50, 50, 16, 16);
           //draw the image           
           g.drawImage(imgEarth, imgX, 50, Graphics.TOP | Graphics.LEFT);
           
           flushGraphics();
           
           try{
               Thread.sleep(30);
           } catch (Exception ex){
               
           }
       }
       g = null;
       unload();
       fParent.destroyApp(false);
       fParent = null;
    }
}
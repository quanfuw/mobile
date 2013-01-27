import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.game.GameCanvas;

public class FontCanvas extends GameCanvas implements Runnable {

// key repeat rate in milliseconds
public static final int keyDelay = 250;    

//key constants
public static final int upKey = 0;
public static final int leftKey = 1;
public static final int downKey = 2;
public static final int rightKey = 3;
public static final int fireKey = 4;

//key states for up, left, down, right, and fire key
private boolean[] isDown = {
    false, false, false, false, false
};

//last time the key changed state
private long[] keyTick = {
    0, 0, 0, 0, 0
};

//lookup table for key constants :P
private int[] keyValue = {
    GameCanvas.UP_PRESSED, GameCanvas.LEFT_PRESSED,
    GameCanvas.DOWN_PRESSED, GameCanvas.RIGHT_PRESSED, 
    GameCanvas.FIRE_PRESSED
};

private boolean isRunning = true;    
private Graphics g;
private midMain fParent;

private clsFont myFont;
    /** Creates a new instance of clsCanvas */
    public FontCanvas(midMain m) {
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
            // load the images here
            
        }catch(Exception ex){
            // exit the app if it fails to load the image
            isRunning = false;
            return;
        }
        
        myFont = new clsFont();
        myFont.load("/bitmapfontsample.png");
    }
    
    public void unload(){
        // make sure the object gets destroyed

        myFont.unload();
        myFont = null;
    }
    
    public void checkKeys(int iKey, long currTick){
        long elapsedTick = 0;
        //loop through the keys
        for (int i = 0; i < 5; i++){ 
            // by default, key not pressed by user
            isDown[i] = false;
            // is user pressing the key
            if ((iKey & keyValue[i]) != 0){ 
                elapsedTick = currTick - keyTick[i];
                //is it time to toggle key state?
                if (elapsedTick >= keyDelay){ 
                    // save the current time
                    keyTick[i] = currTick;  
                    // toggle the state to down or pressed
                    isDown[i] = true; 
                }
            }
        }
    }

    public void run() {
       int iKey = 0;
       int screenW = getWidth();
       int screenH = getHeight();
       long lCurrTick = 0; // current system time in milliseconds;
       
       load();
       g = getGraphics();
       while(isRunning){
           
           lCurrTick = System.currentTimeMillis();
           iKey = getKeyStates();
           
           checkKeys(iKey, lCurrTick);
           
           if (isDown[fireKey]){
               isRunning = false;    
           }
           
           //restore the clipping rectangle to full screen
           g.setClip(0, 0, screenW, screenH);
           //set drawing color to black
           g.setColor(0x000000);
           //fill the screen with blackness
           g.fillRect(0, 0, screenW, screenH);
           
           myFont.drawString(g, "Hello Neo...", 10, 50);
           myFont.drawString(g, "1234567890", 10, 70);
           myFont.drawString(g, "ABCDEFG abcdefg", 10, 90);           
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
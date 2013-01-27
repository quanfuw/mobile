import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class clsFont {
    // additional space between characters
    public int charS = 0;
    
    // max clipping area
    public int screenW = 176;
    public int screenH = 208;
    
    // flag: set to true to use the Graphics.drawString() method
    // this is just used as a fail-safe
    public boolean useDefault = false;
    
    // height of characters
    public int charH = 10;
    
    // lookup table for character widths
    public int[] charW = {
    // first 32 characters    
    9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 
    9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 
    9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 
    9, 9,
    // space
    9,
    // everything else XD
    3, 5, 8, 8, 7, 8, 3, 5, 5, 6, 
    7, 3, 7, 3, 9, 6, 4, 6, 6, 6, 
    6, 6, 6, 6, 6, 3, 3, 6, 6, 6, 
    6, 9, 6, 6, 6, 6, 6, 6, 6, 6, 
    3, 6, 6, 6, 9, 6, 6, 6, 6, 6, 
    6, 7, 6, 6, 9, 6, 6, 6, 5, 9, 
    5, 4, 6, 4, 6, 6, 6, 6, 6, 6, 
    6, 6, 3, 4, 6, 3, 9, 6, 6, 6, 
    6, 6, 6, 6, 6, 6, 9, 6, 6, 6, 
    5, 3, 5, 4,
    // delete
    9};
    
    // the bitmap font image
    private Image imgFont;
    
    public clsFont() {
    }
    
    public boolean load(String imagePath){
        useDefault = false;
        try{
            // load the bitmap font
            if (imgFont != null){
                imgFont = null;
            }
            imgFont = Image.createImage(imagePath);
        } catch (Exception ex){
            // oohh we got an error then use the fail-safe
            useDefault = true;
        }
        return (!useDefault);
    }
    
    public void unload(){
        // make sure the object gets destroyed
        imgFont = null;
    }
    
    public void drawChar(Graphics g, int cIndex, int x, int y, int w, int h){
         // non printable characters don't need to be drawn
        if (cIndex < 33){
            return;
        }

        // neither does the delete character 
        if (cIndex > 126){
            return;
        }

        // get the characters position
        int cx = cIndex * 9;

        // reset the clipping rectangle
        g.setClip(0, 0, screenW, screenH);

        // resize and reposition the clipping rectangle
        // to where the character must be drawn
        g.clipRect(x, y, w, h);

        // draw the character inside the clipping rectangle
        g.drawImage(imgFont, x - cx, y, Graphics.TOP | Graphics.LEFT);
    }
    
    public void drawString(Graphics g, String sTxt, int x, int y){
        // get the strings length
        int len = sTxt.length();

        // set the starting position
        int cx = x;
        
        // if nothing to draw return
        if (len == 0) {
            return;
        }
        
        // our fail-safe
        if (useDefault){
            g.drawString(sTxt, x, y, Graphics.TOP | Graphics.LEFT);
            return;
        }

        // loop through all the characters in the string      
        for (int i = 0; i < len; i++){

           // get current character 
           char c = sTxt.charAt(i);

           // get ordinal value or ASCII equivalent
           int cIndex = (int)c;

           // lookup the width of the character
           int w = charW[cIndex];

           // draw the character
           drawChar(g, cIndex, cx, y, w, charH);

           // go to the next drawing position
           cx += (w + charS);
        }
    }
    
    // extended methods ***************************************

    public void drawInt(Graphics g, int num, int x, int y){
        drawString(g, Integer.toString(num), x, y);
    }

    public void drawLong(Graphics g, long num, int x, int y){
        drawString(g, Long.toString(num), x, y);
    }
    
    // Right align methods ****************************************
    
    //draws string from right to left starting at x,y
    public void drawStringRev(Graphics g, String sTxt, int x, int y){
        // get the strings length
        int len = sTxt.length();

        // set the starting position
        int cx = x;
        
        // if nothing to draw return
        if (len == 0) {
            return;
        }
        
        // our fail-safe
        if (useDefault){
            g.drawString(sTxt, x, y, Graphics.TOP | Graphics.RIGHT);
            return;
        }

        // loop through all the characters in the string      
        for (int i = (len - 1); i >= 0; i--){

           // get current character 
           char c = sTxt.charAt(i);

           // get ordinal value or ASCII equivalent
           int cIndex = (int)c;

           // lookup the width of the character
           int w = charW[cIndex];

           // go to the next drawing position
           cx -= (w + charS);

           // draw the character
           drawChar(g, cIndex, cx, y, w, charH);

        }
    }

    public void drawIntRev(Graphics g, int num, int x, int y){
        drawString(g, Integer.toString(num), x, y);
    }

    public void drawLongRev(Graphics g, long num, int x, int y){
        drawString(g, Long.toString(num), x, y);
    }

    // Word wrap method ****************************************
    
    // space between lines in pixels
    public int lineS = 2; 
    
    // draws words that wrap between x and x1
    public void drawStringWrap(Graphics g, String s, int x, int y, int x1){
        int len = s.length();
        
        // current x
        int tx = x;
        
        // current y
        int ty = y;
        
        /*
           word buffer contents width -
           I just thought it would be faster than 
           calling the String.length() method
        */
        int ww = 0; 
        
        // word buffer
        String sWord = "";
        
        for (int i = 0; i < len; i++){
            char c = s.charAt(i);
            int cIndex = (int)c;
            int cw = charW[cIndex];
            
            if ((cIndex > 32) && (cIndex < 127)){
              //if not a space and the character is printable 
                
              //add the character to the buffer
              sWord += String.valueOf(c);
              
              //compute the length of the current word
              ww += cw;
            } else {
               //if space or non-printable character
               
               // check if there is a word in the buffer 
               if (ww > 0) {
                   
                   //check if it goes past the right margin
                   if ((tx + ww) > x1){
                       // carrage return
                       tx = x;
                       
                       // line feed
                       ty += (charH + lineS);
                   }
                   
                   // draw the contents of the word buffer
                   drawString(g, sWord, tx, ty);
               }
               
               //move to the next position
               tx += (ww + cw); 
               
               // clear the word buffer
               sWord = "";
               
               // word buffer width to zero
               ww = 0;
            }
        }
        
        // if there is a word remaining in the buffer then draw it
        if (ww > 0) {
            if ((tx + ww) > x1){
                tx = x;
                ty += (charH + lineS);
            }
            drawString(g, sWord, tx, ty);
        }
    }
}
package net.obviam.fonts;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class PrintingActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // turn off title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(new DrawingPanel(this));
    }
}
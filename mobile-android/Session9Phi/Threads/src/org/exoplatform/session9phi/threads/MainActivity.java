package org.exoplatform.session9phi.threads;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void download(View v) {
    	Intent i = new Intent(this, AsyncTaskActivity.class);
    	startActivity(i);
    }
    
    public void progress(View v) {
    	Intent i = new Intent(this, ProgressActivity.class);
    	startActivity(i);
    }
    
    public void handler(View v) {
    	Intent i = new Intent(this, HandlerActivity.class);
    	startActivity(i);
    }
}

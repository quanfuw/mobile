package com.exoplatform.session2tu;

/**
 * Created by The eXo Platform SAS
 * Author : Anh-Tu Nguyen
 *          tuna@exoplatform.com
 * Nov 7, 2012  
 */
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
  
  private static final String TAG = "MainActivity";
  
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");
        setContentView(R.layout.activity_main);
        
        Button sayHelloButton = (Button) findViewById(R.id.sayHelloButton);
        sayHelloButton.setOnClickListener(new OnClickListener() {
          
          @Override
          public void onClick(View v) {
            Intent displayMessage = new Intent(MainActivity.this, DisplayMessageActivity.class);
            displayMessage.setAction("com.exoplatform.DisplayMessage1");
            Log.i("onClick", "Component to handle the intent: " + displayMessage.getComponent().getClassName());
            startActivity(displayMessage); 
          }
        });
    }

    @Override
    public void onPause() 
    {
      super.onPause();
      Log.i(TAG, "onPause called - in PAUSE mode");
      Log.i(TAG, "focus is changing to DisplayMessageActivity");
    }
    
    @Override
    public void onResume()
    {
      super.onResume();
      Log.i(TAG, "onResume");
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}

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
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
  
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Button sayHelloButton = (Button) findViewById(R.id.sayHelloButton);
        sayHelloButton.setOnClickListener(new OnClickListener() {
          
          @Override
          public void onClick(View v) {
            Intent displayMessage = new Intent(MainActivity.this, DisplayMessageActivity.class);
            displayMessage.setAction("com.exoplatform.DisplayMessage");
            startActivity(displayMessage); 
          }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}

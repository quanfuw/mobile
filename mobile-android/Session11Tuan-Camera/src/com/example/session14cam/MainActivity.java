package com.example.session14cam;

import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    public void capture(View v) {
      Intent snap = new Intent(this, Camera.class);
      startActivity(snap);
    }
    
    public void startCam(View v) {
      Intent record = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
      startActivity(record);
    }
}

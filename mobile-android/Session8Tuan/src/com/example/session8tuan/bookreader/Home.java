package com.example.session8tuan.bookreader;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

public class Home extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
      // TODO Auto-generated method stub
      switch (item.getItemId()) {
        case R.id.menu_settings : 
          Intent setting  = new Intent(this, Setting.class) ;
          startActivity(setting) ;
         return true;
        default :
        return super.onOptionsItemSelected(item);
      }
      
    }

    
    
    
}

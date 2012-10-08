package com.example.session8tuan.bookreader;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuItem;
import android.view.TextureView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Home extends Activity {
  
    ListView listV ;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
      
        //applySetting() ;
    }
    
    
    
    @Override
    protected void onResume() {
      // TODO Auto-generated method stub
      super.onResume();
      applySetting();
    }



    public void applySetting() {
    
      SharedPreferences settings = getSharedPreferences(Setting.PREFS_NAME, 0) ; 
      
      System.out.println("=====================================view type  :" + settings.getString(Setting.VIEW_TYPE, null));
      
      ListView list = new ListView(this) ;
      GridView grid = new GridView(this) ;
      
      
      RelativeLayout myPanel =  (RelativeLayout) findViewById(R.id.mainlayour);
      
      if(!settings.getBoolean(Setting.SHOW_COVER, false)) {
        
      } 
       
      
      if("List view".equals(settings.getString(Setting.VIEW_TYPE, null))) {
        myPanel.removeAllViews();
       // myPanel.removeView(grid);
        myPanel.addView(list) ;
        
        String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
            "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
            "Linux", "OS/2" };

          // First paramenter - Context
          // Second parameter - Layout for the row
          // Third parameter - ID of the TextView to which the data is written
          // Forth - the Array of data
          ArrayAdapter<String> data = new ArrayAdapter<String>(this,
            android.R.layout.simple_list_item_1, android.R.id.text1, values);

          // Assign adapter to ListView
          list.setAdapter(data); 
          
      } else {
        myPanel.removeAllViews();
       // myPanel.removeView(list);
        grid.setColumnWidth(3);
        myPanel.addView(grid) ;
            
        grid.setAdapter(new ImageAdapter(this)); 
      }
      
      
      
      
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

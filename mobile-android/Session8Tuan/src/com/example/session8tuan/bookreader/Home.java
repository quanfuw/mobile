package com.example.session8tuan.bookreader;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RelativeLayout;

public class Home extends Activity {

  public static Context CONTEXT = null;
  ListView list  ;
  GridView grid ;
  ViewPager  awesomePager ;
  
  RelativeLayout myPanel;
  
ListView listV ;
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home);
    CONTEXT = this ;
     list = new ListView(this) ;
     grid = new GridView(this) ;
      awesomePager = new ViewPager(this);
      myPanel =  (RelativeLayout) findViewById(R.id.mainlayour);
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

    

    
     
    
    grid.setOnItemClickListener(new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			 System.out.println("\n\n add item listerner on grid ! ============");
			 try {
			 pageViewActivity adapter = new pageViewActivity() ;
			 //ViewPager  awesomePager = (ViewPager) findViewById(R.id.awesomepager);
			 
			 	myPanel.removeView(grid) ;
		        awesomePager.setAdapter(adapter);
			 } catch (Exception e) {
				 
				e.printStackTrace() ;
				return ;
			}
			
		}
    	
	}) ;
    
    list.setOnItemClickListener(new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			 System.out.println("\n\n add item listerner on grid ! ============");
			 try {
			 pageViewActivity adapter = new pageViewActivity() ;
			 
			 
			 myPanel.removeView(list) ;
		        awesomePager.setAdapter(adapter);
			 } catch (Exception e) {
				 
				e.printStackTrace() ;
				return ;
			}
			
		}
    	
	}) ;
    
    //RelativeLayout myPanel =  (RelativeLayout) findViewById(R.id.mainlayour);

    if(!settings.getBoolean(Setting.SHOW_COVER, false)) {

    } 


    if("List view".equals(settings.getString(Setting.VIEW_TYPE, null))) {
      myPanel.removeAllViews();
      // myPanel.removeView(grid);
      myPanel.addView(list) ;
      awesomePager.setAdapter(null);
      myPanel.addView(awesomePager) ;
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
      int iDisplayWidth = getResources().getDisplayMetrics().widthPixels ;

      int iImageWidth = iDisplayWidth/3;
      // iNumberOfColumns ; 
      grid.setNumColumns(3);
      grid.setColumnWidth( iImageWidth );
      grid.setStretchMode( GridView.NO_STRETCH );


      myPanel.addView(grid) ;
      awesomePager.setAdapter(null);
      myPanel.addView(awesomePager) ;


      grid.setAdapter(new ImageAdapter(this)); 
     // grid.addTo
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



@Override
public void onBackPressed() {
	// TODO Auto-generated method stub
	applySetting();
	//super.onBackPressed();
}
  
  
  
  




}

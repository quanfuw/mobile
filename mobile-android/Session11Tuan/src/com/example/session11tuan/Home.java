package com.example.session11tuan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

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

  public void view(View v) {
    Intent it = new Intent(this, ViewJSON.class) ;
    startActivity(it) ;
  }

  public void read(View v) {
    Intent it = new Intent(this, ReadingJSON.class) ;
    startActivity(it) ;
  }
  
  public void readXml(View v) {
    Intent it = new Intent(this, ReadingXML.class) ;
    startActivity(it) ;
  }




}

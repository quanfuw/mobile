package com.example.session10tuan;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class FileManager extends Activity {

  private  TextView message ;
  
  private List<Item> fileList = new ArrayList<Item>();
  
  private ArrayAdapter<Item> adapter;
  private ListView files ;
  
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_manager);
        message = (TextView) findViewById(R.id.message1);
        files = (ListView) findViewById(R.id.fileListView);
        String extState = Environment.getExternalStorageState();
       
      if(!extState.equals(Environment.MEDIA_MOUNTED)) {
          message.setText("The is NO media mounted !") ;
      }
      else {
          buildTree() ;
          this.createFileListAdapter();
          files.setAdapter(adapter);
      }
    }

    private void buildTree() {
      File sd = Environment.getExternalStorageDirectory();
      File[] sdDirList = sd.listFiles();
      Integer drawableID = R.drawable.file_icon;
      for(File f : sdDirList) {
        if (f.isDirectory()) {
          if(f.canRead()) {
            drawableID = R.drawable.folder_icon;
          }
          else { 
            drawableID = R.drawable.folder_icon_light;
          }
        
        }
        Item file = new Item(f.getName(), drawableID, f.canRead()) ;
        fileList.add(file);
      }  
       
    }
    
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
      Intent it = new Intent(this,SettingActivity.class);
      startActivity(it);
      return false;
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_file_manager, menu);
        return true;
    }
    
    
     
    private void createFileListAdapter(){
      adapter = new ArrayAdapter<Item>(this,
          android.R.layout.select_dialog_item, android.R.id.text1,
          fileList)
        {
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
          View view = super.getView(position, convertView, parent);
          TextView textView = (TextView) view
              .findViewById(android.R.id.text1);
          int drawableID = 0;
          if(fileList.get(position).icon != -1) {
            drawableID = fileList.get(position).icon;
          }
          textView.setCompoundDrawablesWithIntrinsicBounds(
              drawableID, 0, 0, 0);
          textView.setEllipsize(null);
          int dp5 = (int) (5 * getResources().getDisplayMetrics().density + 0.5f);
          int dp3 = (int) (3 * getResources().getDisplayMetrics().density + 0.5f);
          textView.setCompoundDrawablePadding(dp3);
          textView.setBackgroundColor(Color.LTGRAY);
          return view;
        } 
      };
      
    } 

    
    public class Item {
      public String file;
      public int icon;
      public boolean canRead;

      public Item(String file, Integer icon, boolean canRead) {
        this.file = file;
        this.icon = icon;
      }

      @Override
      public String toString() {
        return file;
      }
    }
    
    public class ItemFileNameComparator implements Comparator<Item> {
      public int compare(Item lhs, Item rhs) {
        return lhs.file.toLowerCase().compareTo(rhs.file.toLowerCase());
      }
      
    }
    
    
    
}

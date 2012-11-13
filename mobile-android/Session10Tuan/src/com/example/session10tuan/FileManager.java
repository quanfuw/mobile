package com.example.session10tuan;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class FileManager extends Activity implements android.content.DialogInterface.OnClickListener{

  private  TextView message ;

  private List<Item> fileList = new ArrayList<Item>();

  private ArrayAdapter<Item> adapter;
  private ListView files ;

  private File currentFile = null;
  private AlertDialog newFolderDialog = null;
  private AlertDialog warningMessage = null;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_file_manager);
    message = (TextView) findViewById(R.id.message1);
    files = (ListView) findViewById(R.id.listView1);
    String extState = Environment.getExternalStorageState();

    if(!extState.equals(Environment.MEDIA_MOUNTED)) {
      message.setText("The is NO media mounted !") ;
    }
    else {

      files.setOnItemClickListener(
                                   new AdapterView.OnItemClickListener() {
                                     public void onItemClick(AdapterView parent, View view, int position, long id) {

                                       String chosenFile = fileList.get(position).file;

                                       if(chosenFile.equals("...") ) upLevel(null); 
                                       else {

                                         File sel = new File (currentFile.getPath() + "/" + chosenFile);
                                         message("this file " + sel.isFile() + sel.getName() + "could not view detail") ;
                                         if(!sel.isDirectory()) {
                                           message("this file " + sel.getName() + "could not view detail") ;
                                         } else {

                                           if(sel != null) ;
                                           currentFile = sel ;
                                           System.out.println("reset current file === " + currentFile.getPath());
                                           System.out.println("Seleted file " + sel.getPath());
                                           initView() ;
                                         }
                                       }
                                     }
                                   });
      if(currentFile != null) message.setText(currentFile.getPath()) ;
      initView() ;
    }
  }

  private void initView() {
    buildTree() ;
    this.createFileListAdapter();
    files.setAdapter(adapter);

    runOnUiThread(new Runnable() {

      @Override
      public void run() {
        adapter.notifyDataSetChanged();
      }
    });


  }
  
  

  public void addFolder(View v) {
    if (newFolderDialog == null)
      newFolderDialog = createFolderNameDialog();
    newFolderDialog.show();
    EditText text = ((EditText)newFolderDialog.findViewById(R.id.field_new_folder)) ;
    if(text != null) text.setText("");

  }
  
  private AlertDialog message(String massage) {
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    
   builder
    .setTitle(massage)
    .setPositiveButton("OK", 
                       new DialogInterface.OnClickListener() {

      @Override
      public void onClick(DialogInterface dialog, int which) {
        // TODO Auto-generated method stub
      }
    });
   return builder.create() ;
  }

  private AlertDialog createFolderNameDialog() {
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    LayoutInflater inflater = getLayoutInflater();
    builder.setView(inflater.inflate(R.layout.dialog_new_folder, null))
    .setPositiveButton("OK", this)
    .setNegativeButton("Cancel", this);
    return builder.create();
  }

  public void upLevel(View v) {
    if (currentFile != null) {
      currentFile = currentFile.getParentFile() ;
      initView();
    } else  {
      message("you are at root !").show() ;

    }
  }
  private void buildTree() {
    fileList.clear();
    if(currentFile == null ) currentFile = Environment.getExternalStorageDirectory(); 

    File[] sdDirList = currentFile.listFiles();
    Integer drawableID = R.drawable.file_icon;
    for(File f : sdDirList) {
      String name = f.getName() ;
      if (f.isDirectory()) {
        if(f.canRead()) {
          drawableID = R.drawable.folder_icon;
        }
        else { 
          drawableID = R.drawable.folder_icon_light;
        }

        name = name + "/";
      } 
      Item file = new Item(name, drawableID, f.canRead()) ;
      fileList.add(file);
    } 
    if(fileList.size() <= 0) fileList.add(new Item("...", R.drawable.file_icon, false)) ;

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
    adapter = null ;
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

  @Override
  public void onClick(DialogInterface dialog, int which) {
    if (which == Dialog.BUTTON_NEGATIVE) {
      dialog.cancel();
    } else if (which == Dialog.BUTTON_POSITIVE) {
      EditText name = (EditText)newFolderDialog.findViewById(R.id.field_new_folder);
      if (name != null) {
        newFolder(name.getText().toString());
      }
    } 
  }

  private void newFolder(String name) {
    if (!name.equals("")) {
      File output = new File(currentFile, name);
      if (!output.exists() && currentFile.canWrite() && currentFile.isDirectory()) {
        output.mkdir();
        initView();
      } else {
        //Notification
      }
    } else {
      //Notification
    }
  }



}



package com.example.session10tuan;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
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
  private AlertDialog downloadFileDialog = null;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_file_manager);
    files = (ListView) findViewById(R.id.listView1);
    registerForContextMenu(files);
    String extState = Environment.getExternalStorageState();

    if(!extState.equals(Environment.MEDIA_MOUNTED)) {
      message.setText("The is NO media mounted !") ;
    }
    else {
      /*
      files.setOnItemClickListener(
                                   new AdapterView.OnItemClickListener() {
                                     public void onItemClick(AdapterView parent, View view, int position, long id) {

                                       String chosenFile = fileList.get(position).file;

                                       if(chosenFile.equals("...") ) upLevel(null); 
                                       else {

                                         File sel = new File (currentFile.getPath() + "/" + chosenFile);
                                         if(sel.isFile()) {
                                           if(!sel.canRead())
                                           message("this file " + sel.getName() + " could not view detail").show() ;
                                           else  if("jpg/png/gif".contains(MimeTypeMap.getFileExtensionFromUrl(sel.getAbsolutePath()))) {
                                             Intent viewer = new Intent(parent.getContext(), ViewPicActivity.class);
                                             viewer.putExtra("FILE_NAME", sel.getAbsolutePath());
                                             setIntent(viewer);
                                           }
                                         } else {

                                           if(sel != null) ;
                                           currentFile = sel ;
                                           initView() ;
                                         }
                                       }
                                     }
                                   });
       */
      //if(currentFile != null) message.setText(currentFile.getPath()) ;
      initView() ;
    }
  }
  
  @Override
  public void onCreateContextMenu(ContextMenu menu, View v,
                                  ContextMenuInfo menuInfo) {
      super.onCreateContextMenu(menu, v, menuInfo);
      MenuInflater inflater = getMenuInflater();
      inflater.inflate(R.menu.context_menu, menu);
  }

  
  @Override
  public boolean onContextItemSelected(MenuItem item) {
      AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
    
  String selectedWord = ((TextView) info.targetView).getText().toString();
  long selectedWordId = info.id;
  
  //System.out.println("\n\n file " + selectedWord);
  //System.out.println("\n\n id " + selectedWordId);
  
      switch (item.getItemId()) {
          case R.id.open:
              open(selectedWord, info.id);
              return true;
          case R.id.share:
              share(selectedWord, info.id);
              return true;
          default:
              return super.onContextItemSelected(item);
      }
  }
  
  private void open(String file, long id) {
    // TODO Auto-generated method stub
    
  }

  private void share(String file, long id) {
    Uri uri = Uri.parse("file://"+currentFile.getAbsolutePath() +"/" + file) ;
        //Uri.fromFile(new File(Environment.getExternalStorageDirectory(), file)) ;
    //System.out.println("file://"+currentFile.getAbsolutePath() +"/" + file);
    Intent i = new Intent(Intent.ACTION_SEND);
    //i.putExtra(Intent.EXTRA_CC, "tuanp@exoplatform.com");
    i.putExtra(Intent.EXTRA_SUBJECT, "Title");
    i.putExtra(Intent.EXTRA_TEXT, "Content");
    i.putExtra(Intent.EXTRA_STREAM, uri);
    i.setType("text/plain");
   
    startActivity(Intent.createChooser(i, "Send mail"));
    
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
    //Limitation user to access to root folder !
    if (currentFile != null && !currentFile.getPath().equalsIgnoreCase(Environment.getExternalStorageDirectory().getPath())) {
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
    
    if(item.getItemId() == R.id.menu_settings) {
    Intent it = new Intent(this,SettingActivity.class);
    startActivity(it);
    } else {
      Intent it = new Intent(this,OptionActivity.class);
      startActivity(it);
    }
    
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
    } else if (dialog == downloadFileDialog && currentFile.canWrite() ) {
      
      DownloadTask task = new DownloadTask();
      task.execute(which);
      
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
  
  private AlertDialog createDownloadDialog() {
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
      builder.setTitle("Pick a file")
        .setItems(R.array.list_downloads, this)
        .setNegativeButton("Cancel", this);
    return builder.create();
  }

  public void download(View v) {
    if (downloadFileDialog == null)
      downloadFileDialog = createDownloadDialog();
    
    downloadFileDialog.show();
    
  }

  
  private Notification buildDownloadNotification(File downloadedFile) {
    // Create a Bitmap from the image File
    Bitmap b= BitmapFactory.decodeFile(downloadedFile.getAbsolutePath());
    // Set parameters of the new Notification
    NotificationCompat.Builder mBuilder =
            new NotificationCompat.Builder(this)
          .setSmallIcon(R.drawable.ic_launcher) // icon of the app
          .setLargeIcon(b)            // large icon from the image (android 4+ only)
          .setContentTitle("Download successfull")
          .setContentText("File "+downloadedFile.getName()+" downloaded successfully.")
          .setAutoCancel(true);         // will be cancelled when the user presses it
    // Set style for the expanded view (android 4+ only)
    NotificationCompat.BigPictureStyle picStyle =
        new NotificationCompat.BigPictureStyle();
    picStyle.setBigContentTitle(downloadedFile.getName());
    picStyle.bigPicture(b);               // picture from the image
    mBuilder.setStyle(picStyle);
    // Create the intent to launch when the notification is pressed
    Intent i = new Intent(this, ViewPicActivity.class);
    i.putExtra("FILE_NAME", downloadedFile.getAbsolutePath());
    // Create a stack to return to the current activity when the user presses the back button
    TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
    stackBuilder.addParentStack(ViewPicActivity.class);
    stackBuilder.addNextIntent(i);
    PendingIntent resultPendingIntent =
            stackBuilder.getPendingIntent(
                0,
                PendingIntent.FLAG_UPDATE_CURRENT
            );
    mBuilder.setContentIntent(resultPendingIntent);
    // Build and return the Notification
    return mBuilder.build();
  }
  
private class DownloadTask extends AsyncTask<Integer, Void, File> {
    
    private final String[] URLs = {
      "http://www.google.com/logos/2012/Souza-Cardosa-2012-homepage.jpg",
      "https://gs1.wac.edgecastcdn.net/8019B6/data.tumblr.com/tumblr_mde5m7Tqs21qbko1eo1_1280.jpg",
      "http://www.exoplatform.com/company/rest-company/jcr/repository/collaboration/sites%20content/live/website/web%20contents/10.Homepage/Slider/images/connect-banner.png",
      "http://vfossa.vn/tailen/mininews/toadam-hanoi-danang-tphcm-opentech.jpg"
    };
    

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected File doInBackground(Integer... params) {
      // Download the file at the given URL
      String strUrl = URLs[params[0]];
      String[] array = getResources().getStringArray(R.array.list_downloads);
      File bitmap = new File(currentFile, array[params[0]]+".jpg");
      InputStream is;
        OutputStream os;
        try {
          Log.d("EXO_TAG", "*** Downloading from task");
            URL url = new URL(strUrl);
            is = new BufferedInputStream(url.openStream());
            os = new FileOutputStream(bitmap.getAbsolutePath());
              byte bdata[] = new byte[1024];
              int count;
              while ((count = is.read(bdata)) != -1) {
                  os.write(bdata, 0, count);
              }
            // Successfully finished
            os.flush();
            os.close();
            is.close();
        } catch (Exception e) {
          e.printStackTrace();
          return null;
        }
        return bitmap;
    }
    
    @Override
    protected void onPostExecute(File result) {
      // Notify the user of the downloaded image
      NotificationManager mNotificationManager =
            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
      mNotificationManager.notify(1234, buildDownloadNotification(result));
     // refresh();
      
    }
      
    }

}



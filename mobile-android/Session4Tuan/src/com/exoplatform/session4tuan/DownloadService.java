package com.exoplatform.session4tuan;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;

public class DownloadService extends IntentService {

  private int result = Activity.RESULT_CANCELED;
  private static MyService instance = null;

  public DownloadService() {
    super("DownloadService");
  }

  // Will be called asynchronously be Android
  @Override
  protected void onHandleIntent(Intent intent) {
    Uri data = intent.getData();
    String urlPath = intent.getStringExtra(MainActivity.KEY_URL);
    String fileName = data.getLastPathSegment();
    File output = new File(Environment.getExternalStorageDirectory(),
        fileName);
    if (output.exists()) {
      output.delete();
    }

    BufferedInputStream stream = null;
    BufferedOutputStream fos = null;
    try {

      URL url = new URL(urlPath);
      stream = new BufferedInputStream(url.openStream());
      //InputStreamReader reader = new InputStreamReader(stream);
      fos = new BufferedOutputStream(new FileOutputStream(output.getPath()));
      
      
      byte bdata[] = new byte[1024];
      int count;
      while ((count = stream.read(bdata)) != -1) {
          fos.write(bdata, 0, count);
      }
      
      
      result = Activity.RESULT_OK;

    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (stream != null) {
        try {
          stream.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      if (fos != null) {
        try {
          fos.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }

    Bundle extras = intent.getExtras();
    if (extras != null) {
      Messenger messenger = (Messenger) extras.get(MainActivity.KEY_MESSENGER);
      Message msg = Message.obtain();
      msg.arg1 = result;
      msg.obj = output.getAbsolutePath();
      try {
        messenger.send(msg);
        Intent downloadStatus = new Intent(getBaseContext(), MyReceiver.class);
	    downloadStatus.putExtra(MainActivity.KEY_STATUS, result);
	    downloadStatus.putExtra(MainActivity.KEY_FILE_LOCATION, output.getAbsolutePath());
	    // BroadCast event
	    sendBroadcast(downloadStatus);
	    
      } catch (android.os.RemoteException e1) {
        Log.e(getClass().getName(), "Exception sending message", e1);
        e1.printStackTrace();
      } finally {
    	  
    	  this.stopSelf() ;
      }

    }
  }

public static MyService getInstance() {
	return instance;
}

public static void setInstance(MyService instance) {
	DownloadService.instance = instance;
}
} 
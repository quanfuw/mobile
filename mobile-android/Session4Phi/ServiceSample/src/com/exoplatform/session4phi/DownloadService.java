package com.exoplatform.session4phi;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
	
	public DownloadService() {
		super("DownloadService");
	}
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Log.i(getClass().getName(), "Download service created");
	} 
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.i(getClass().getName(), "Download service killed");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub
		Uri data = intent.getData();
	    String urlPath = intent.getStringExtra(MainActivity.KEY_URL);
	    String fileName = data.getLastPathSegment();
	    File output = new File(Environment.getExternalStorageDirectory(),
	        fileName);
	    if (output.exists()) {
	      output.delete();
	    }

	    InputStream is;
	    OutputStream os;
	    try {

	      URL url = new URL(urlPath);
	      is = new BufferedInputStream(url.openStream());
          os = new FileOutputStream(output.getAbsolutePath());
          
          byte bdata[] = new byte[1024];
          int count;
          while ((count = is.read(bdata)) != -1) {
              os.write(bdata, 0, count);
          }

	      // Successfully finished
	      result = Activity.RESULT_OK;
	      
	      os.flush();
  		  os.close();
  		  is.close();	
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	    
	    Messenger messenger = (Messenger)intent.getExtras().get(MainActivity.KEY_MESSENGER);
	    Message msg = Message.obtain();
	    msg.obj = output.getAbsolutePath();
	    try {
	      messenger.send(msg);
	    } catch (android.os.RemoteException e1) {
	      Log.w(getClass().getName(), "Exception sending message", e1);
	    }
	    
	    Intent downloadStatus = new Intent(getBaseContext(), DownloadReceiver.class);
	    downloadStatus.putExtra(MainActivity.KEY_STATUS, result);
	    downloadStatus.putExtra(MainActivity.KEY_FILE_LOCATION, output.getAbsolutePath());
	    sendBroadcast(downloadStatus);
	    
	    this.stopSelf();
	}

}

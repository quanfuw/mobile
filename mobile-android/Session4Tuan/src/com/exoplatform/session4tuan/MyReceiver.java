package com.exoplatform.session4tuan;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {

  @Override
  public void onReceive(Context context, Intent intent) {
    Intent viewPicIntent = new Intent(context, ImagePrvivewActivity.class);
    //context.startService(service);
    
    int result = intent.getExtras().getInt(MainActivity.KEY_STATUS);
	String path = intent.getStringExtra(MainActivity.KEY_FILE_LOCATION);
	
	viewPicIntent.putExtra(MainActivity.KEY_FILE_LOCATION, path);
	viewPicIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK) ;
	context.startActivity(viewPicIntent) ;
	
	
	if (result == Activity.RESULT_OK && path != null) {
        Toast.makeText(context,
            "Downloaded: " + path.toString(), Toast.LENGTH_LONG)
            .show();
      } else {
        Toast.makeText(context, "Download failed.",
            Toast.LENGTH_LONG).show();
      }
    
  }
} 
package com.exoplatform.session4phi;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class DownloadReceiver extends BroadcastReceiver {
	
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		int result = intent.getExtras().getInt(MainActivity.KEY_STATUS);
		String path = intent.getStringExtra(MainActivity.KEY_FILE_LOCATION);
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

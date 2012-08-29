package com.exoplatform.session4phi;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

public class MainActivity extends Activity {
	
	public static final String KEY_URL     = "URL";
	public static final String KEY_STATUS  = "DOWNLOAD_STATUS";
	public static final String KEY_FILE_LOCATION = "FILE_LOCATION";
	public static final String KEY_MESSENGER = "MESSENGER";
	
	private Handler responseHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			ProgressBar progress = (ProgressBar)findViewById(R.id.progress_download);
	    	progress.setVisibility(View.INVISIBLE);
	    	
	    	Intent viewPicIntent = new Intent(getBaseContext(), ViewPicActivity.class);
	    	viewPicIntent.putExtra(KEY_FILE_LOCATION, msg.obj.toString());
	    	startActivity(viewPicIntent);
		}
	};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText urlField = (EditText)findViewById(R.id.text_url);
        urlField.setText("http://2.bp.blogspot.com/-_6agVqVYcDk/Try-zzbd-GI/AAAAAAAAAWE/TIgX6oe8lyw/s1600/android_42.png");
    }
    
    public void onStartDownload(View view) {
    	EditText URL = (EditText)findViewById(R.id.text_url);
        String strURL = URL.getText().toString();
        Messenger responseMsg = new Messenger(responseHandler);
    	Intent downloadIntent = new Intent(getBaseContext(), DownloadService.class);
    	downloadIntent.setData(Uri.parse(strURL));
    	downloadIntent.putExtra(KEY_URL, strURL);
    	downloadIntent.putExtra(KEY_MESSENGER, responseMsg);
    	startService(downloadIntent);
    	ProgressBar progress = (ProgressBar)findViewById(R.id.progress_download);
    	progress.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}

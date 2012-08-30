package com.exoplatform.session4tuan;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	Button startBnt ;
	EditText text1 ;
	Button downloadBnt ;
	
	public static final String KEY_URL     = "URL";
	public static final String KEY_STATUS  = "DOWNLOAD_STATUS";
	public static final String KEY_FILE_LOCATION = "FILE_LOCATION";
	public static final String KEY_MESSENGER = "MESSENGER";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startBnt = (Button) findViewById(R.id.button1) ;
        downloadBnt = (Button) findViewById(R.id.button2) ;
        text1 = (EditText) findViewById(R.id.editText1) ;
        text1.setText("http://2.bp.blogspot.com/-_6agVqVYcDk/Try-zzbd-GI/AAAAAAAAAWE/TIgX6oe8lyw/s1600/android_42.png") ;
        startBnt.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				
				Intent service = new Intent(getBaseContext(), MyService.class) ;
				if(MyService.isInstanceCreated()) { 
					stopService(service) ;
					//if(!isMyServiceRunning("com.exoplatform.session4tua.MyService"))
					Toast.makeText(getBaseContext(), "Service is already running, we will stop...", Toast.LENGTH_SHORT).show();
				}else {
					
					//if(isMyServiceRunning("com.exoplatform.session4tua.MyService"))
				    Toast.makeText(getBaseContext(), "Service starting....", Toast.LENGTH_SHORT).show();
				    startService(service) ;
				}
				 
				 
			}
		});
        
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    private boolean isMyServiceRunning(String svName) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (svName.equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
    
    private Handler handler = new Handler() {
    	@Override
        public void handleMessage(Message message) {
          Object path = message.obj;
          if (message.arg1 == RESULT_OK && path != null) {
            Toast.makeText(MainActivity.this,
                "Downloaded" + path.toString(), Toast.LENGTH_LONG)
                .show();
          } else {
            Toast.makeText(MainActivity.this, "Download failed.",
                Toast.LENGTH_LONG).show();
          }

        };
      };
    
    public void download(View view) {
        Intent intent = new Intent(this, DownloadService.class);
        // Create a new Messenger for the communication back
        Messenger messenger = new Messenger(handler);
        intent.putExtra(KEY_MESSENGER, messenger);
        
        //EditText text = (EditText)findViewById(R.id.editText1) ;
        intent.setData(Uri.parse(text1.getText().toString()));
        intent.putExtra(KEY_URL, text1.getText().toString());
        if(!isMyServiceRunning("com.exoplatform.session4tuan.DownloadService"))
        startService(intent);
        else {
         Toast.makeText(MainActivity.this, "Download is still runing", Toast.LENGTH_LONG).show();	
        	
        }
      }
}

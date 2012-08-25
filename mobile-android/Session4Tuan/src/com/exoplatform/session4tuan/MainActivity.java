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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startBnt = (Button) findViewById(R.id.button1) ;
        downloadBnt = (Button) findViewById(R.id.button2) ;
        text1 = (EditText) findViewById(R.id.editText1) ;
        text1.setText("http://www.vogella.com/index.html") ;
        startBnt.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent service = new Intent(getBaseContext(), MyService.class) ;
				if(MyService.isInstanceCreated()) { 
					stopService(service) ;
					//if(!isMyServiceRunning("com.exoplatform.session4tua.MyService"))
					Toast.makeText(getBaseContext(), "Service is already running, we will stop", Toast.LENGTH_SHORT).show();
				}else {
					startService(service) ;
					//if(isMyServiceRunning("com.exoplatform.session4tua.MyService"))
				    Toast.makeText(getBaseContext(), "Service stoped and restart", Toast.LENGTH_SHORT).show();
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
        intent.putExtra("MESSENGER", messenger);
        //EditText text = (EditText)findViewById(R.id.editText1) ;
        intent.setData(Uri.parse(text1.getText().toString()));
        intent.putExtra("urlpath", text1.getText().toString());
        if(!isMyServiceRunning("com.exoplatform.session4tuan.DownloadService"))
        startService(intent);
        else {
         Toast.makeText(MainActivity.this, "Download is still runing", Toast.LENGTH_LONG).show();	
        	
        }
      }
}

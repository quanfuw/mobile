package org.exoplatform.session3phi.message;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class WriteActivity extends Activity {
	
	public final static String LOG_TAG = "org.exoplatform.session3phi.LOG";
	public final static String EXTRA_MESSAGE = "org.exoplatform.session3phi.message.MESSAGE";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(WriteActivity.LOG_TAG, "WriteActivity: onCreate");
        setContentView(R.layout.activity_write);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_write, menu);
        return true;
    }
    
    public void sendMessage(View view) {
    	Intent intent = new Intent(this, DisplayActivity.class);
    	EditText message = (EditText) findViewById(R.id.write_text);
    	String messageText = message.getText().toString();
    	intent.putExtra(EXTRA_MESSAGE, messageText);
    	startActivity(intent);
    }

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
        Log.i(WriteActivity.LOG_TAG, "WriteActivity: onDestroy");

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
        Log.i(WriteActivity.LOG_TAG, "WriteActivity: onPause");

	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
        Log.i(WriteActivity.LOG_TAG, "WriteActivity: onRestart");

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
        Log.i(WriteActivity.LOG_TAG, "WriteActivity: onResume");

	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
        Log.i(WriteActivity.LOG_TAG, "WriteActivity: onStart");

	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
        Log.i(WriteActivity.LOG_TAG, "WriteActivity: onStop");

	}
    
}

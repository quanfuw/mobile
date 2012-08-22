package org.exoplatform.session3phi.message;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

public class DisplayActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(WriteActivity.LOG_TAG, "DisplayActivity: onCreate");
        setContentView(R.layout.activity_display);

        Intent intent = getIntent();
        String message = intent.getStringExtra(WriteActivity.EXTRA_MESSAGE);
    
        TextView txtView = (TextView)findViewById(R.id.display_text);
        txtView.setText(message);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_display, menu);
        return true;
    }
    
    @Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
        Log.i(WriteActivity.LOG_TAG, "DisplayActivity: onDestroy");

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
        Log.i(WriteActivity.LOG_TAG, "DisplayActivity: onPause");

	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
        Log.i(WriteActivity.LOG_TAG, "DisplayActivity: onRestart");

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
        Log.i(WriteActivity.LOG_TAG, "DisplayActivity: onResume");

	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
        Log.i(WriteActivity.LOG_TAG, "DisplayActivity: onStart");

	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
        Log.i(WriteActivity.LOG_TAG, "DisplayActivity: onStop");

	}
}

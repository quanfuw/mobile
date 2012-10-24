package org.exoplatform.session10phi.storage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
    }

    public void launchSettings(View v) {
    	Intent i = new Intent(this, SettingsActivity.class);
    	startActivity(i);
    }
    
    public void launchStorage(View v) {
    	Intent i = new Intent(this, StorageActivity.class);
    	startActivity(i);
    }

    public void launchReader(View v) {
    	Intent i = new Intent(this, ReaderActivity.class);
    	startActivity(i);
    }
}

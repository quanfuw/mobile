package org.exoplatform.session10phi.storage;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class ReaderActivity extends Activity {

	private Spinner sp;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader);
        
        sp = (Spinner)findViewById(R.id.settings_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.list_settings, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        sp.setAdapter(adapter);
    }

    public void readPref(View v) {
    	String key = sp.getItemAtPosition(sp.getSelectedItemPosition()).toString();
    	SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
    	String pref = null;
    	if (key.startsWith("pref_text")) pref = sharedPref.getString(key, "");
    	else if (key.startsWith("pref_chkbx")) pref = Boolean.toString(sharedPref.getBoolean(key, false));
    	else if (key.startsWith("pref_list")) pref = sharedPref.getString(key, "");

    	Toast.makeText(this, "Key: "+key+" | Value: "+pref, Toast.LENGTH_LONG).show();
    }
}

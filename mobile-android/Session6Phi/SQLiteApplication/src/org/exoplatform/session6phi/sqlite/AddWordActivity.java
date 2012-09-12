package org.exoplatform.session6phi.sqlite;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class AddWordActivity extends Activity {

	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_word);
	}
	
	public void addWord(View view) {
		PhiSQLiteHelper dbHelper = new PhiSQLiteHelper(this);
		String word = ((TextView)findViewById(R.id.field_word_word)).getText().toString();
		String desc = ((TextView)findViewById(R.id.field_word_desc)).getText().toString();
		boolean wordCreated = dbHelper.createWord(word, desc, null);
		if (wordCreated) {
			Toast.makeText(this, "Word '"+word+"' successfully created.", Toast.LENGTH_SHORT).show();
			Intent backIntent = new Intent(this, MainActivity.class);
			startActivity(backIntent);
		} else {
			Toast.makeText(this, "Cannot create the word '"+word+"'.", Toast.LENGTH_SHORT).show();
		}
	}
}

package org.exoplatform.session6phi.sqlite;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ShowWordActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_show_word);
		
		Intent intent = getIntent();
		String word = intent.getStringExtra(MainActivity.KEY_WORD);
		
		PhiSQLiteHelper dbHelper = new PhiSQLiteHelper(this);
		String def = dbHelper.getDefinitionOfWord(word);
		
		TextView wordTV = (TextView)findViewById(R.id.text_word);
		wordTV.setText(word);
		
		TextView defTV = (TextView)findViewById(R.id.text_def);
		defTV.setText(def);
	}
}

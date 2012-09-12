package org.exoplatform.session6phi.sqlite;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {

	public static final String KEY_WORD = "KEY_WORD";
	PhiSQLiteHelper dbHelper;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        dbHelper = new PhiSQLiteHelper(this);
        setContentView(R.layout.activity_main);
        loadWordsList("");
        
        ListView list = (ListView)findViewById(R.id.list_search_result);
        list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				TextView wordView = (TextView)arg1.findViewById(android.R.id.text1);
				Intent showWordIntent = new Intent(getBaseContext(), ShowWordActivity.class);
		    	showWordIntent.putExtra(KEY_WORD, wordView.getText().toString());
		    	startActivity(showWordIntent);
			}
		});
    }
    
    private void loadWordsList(String likeWord) {
    	ListView list = (ListView)findViewById(R.id.list_search_result);
    	List<String> words = dbHelper.searchWords(likeWord);
    	ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, words);
    	list.setAdapter(adapter);
    }
    
    public void searchWord(View view) {
    	String likeWord = ((EditText)findViewById(R.id.field_search_word)).getText().toString();
    	if (likeWord == null) likeWord = "";
    	loadWordsList(likeWord);
    }
    
    public void startAddWordActivity(View view) {
    	Intent addWordIntent = new Intent(this, AddWordActivity.class);
    	startActivity(addWordIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}

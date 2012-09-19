package org.exoplatform.session7phi.bookstore;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class AddBookActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }
    
    public void addBook(View view) {
    	String bookTitle = ((TextView)findViewById(R.id.text_book_title)).getText().toString();
    	String bookAuthor = ((TextView)findViewById(R.id.text_book_author)).getText().toString();
    	String bookPrice = ((TextView)findViewById(R.id.text_book_price)).getText().toString();
    	
    	BookStoreDBHelper db = new BookStoreDBHelper(this);
    	if (db.addBook(bookTitle, bookAuthor, bookPrice)) {
    		Toast.makeText(this, "Book created successfully.", Toast.LENGTH_SHORT).show();
    		this.backToMainActivity();
    	} else {
    		Toast.makeText(this, "Error while creating the book.", Toast.LENGTH_SHORT).show();
    	}
    }
    
    public void cancelAddBook(View view) {
    	this.backToMainActivity();
    }
    
    private void backToMainActivity() {
    	Intent mainActivity = new Intent(this, MainActivity.class);
		startActivity(mainActivity);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_add_book, menu);
        return true;
    }

    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

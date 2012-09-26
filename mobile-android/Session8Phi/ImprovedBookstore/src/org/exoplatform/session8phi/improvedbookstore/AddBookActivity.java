package org.exoplatform.session8phi.improvedbookstore;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class AddBookActivity extends Activity {

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
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
    	Intent mainActivity = new Intent(this, BookStoreMainActivity.class);
		startActivity(mainActivity);
    }
}

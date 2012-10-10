package org.exoplatform.session8phi.improvedbookstore;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class AddBookActivity extends Activity {
	
	private final String desc = "Lorem ipsum dolor sit amet, consectetur adipiscing elit." +
			" Mauris sagittis, lacus vehicula pulvinar dignissim, dui mauris pulvinar neque, et sagittis velit neque feugiat sapien.";
	private final String fullDesc = "Mauris at nunc purus, vitae tempus urna. " +
			"Morbi vel orci et eros dapibus feugiat non a eros. Aenean vitae urna id erat volutpat porta. " +
			"Donec euismod tincidunt erat, in placerat diam posuere in. Proin at leo nibh, eget iaculis dui. " +
			"Vivamus ac nisi quis quam sollicitudin tempor eu sed justo. Integer placerat placerat quam id congue. " +
			"Aliquam mattis laoreet elit, vel rhoncus purus cursus nec. Suspendisse nec sodales odio. " +
			"Duis euismod enim nec elit porttitor ac lobortis est sagittis. Integer in nibh sem. ";

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
    	if (db.addBook(bookTitle, bookAuthor, bookPrice, desc, fullDesc)) {
    		Toast.makeText(this, "Book created successfully.", Toast.LENGTH_SHORT).show();
    		finish();
    	} else {
    		Toast.makeText(this, "Error while creating the book.", Toast.LENGTH_SHORT).show();
    	}
    }
}

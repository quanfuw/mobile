package org.exoplatform.session7phi.bookstore;

import org.exoplatform.session7phi.bookstore.BookStoreDBHelper.Book;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {

	private BookStoreDBHelper booksDB;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        booksDB = new BookStoreDBHelper(this);
        
        setContentView(R.layout.activity_main);
        // Populate book list
        ListView bookList = (ListView)findViewById(R.id.list_books);
        BookListAdapter adapter = new BookListAdapter(this, R.layout.list_book_item, booksDB.getAllBooks());
        bookList.setAdapter(adapter);
        bookList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				String bookId = ((TextView)arg1.findViewById(R.id.text_book_item_id)).getText().toString();
				Intent showBookActivity = new Intent(getBaseContext(), ViewBookDetailsActivity.class);
				showBookActivity.putExtra(Book.BOOK_ID, bookId);
		    	startActivity(showBookActivity);
			}
		});
    }
    
    public void startSearchBookActivity(View view) {
    	Intent searchBookActivity = new Intent(this, SearchBookActivity.class);
    	String searchTerm = ((EditText)findViewById(R.id.text_search_book)).getText().toString();
    	searchBookActivity.putExtra(SearchBookActivity.KEY_SEARCH_EXTRA, searchTerm);
    	startActivity(searchBookActivity);
    }
    
    public void startAddBookActivity(View view) {
    	Intent addBookActivity = new Intent(this, AddBookActivity.class);
    	startActivity(addBookActivity);
    }
}

package org.exoplatform.session8phi.improvedbookstore;

import org.exoplatform.session8phi.improvedbookstore.BookStoreDBHelper.Book;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class BookStoreMainActivity extends ListActivity {

	private BookStoreDBHelper db;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_book_store_main);
        db = new BookStoreDBHelper(this);
        
        Cursor c = db.getAllBooks();
        String[] from = {
        		Book.BOOK_TITLE,
        		Book.BOOK_AUTHOR,
        		Book.BOOK_ID,
        		Book.BOOK_PRICE
        };
        int[] to = {
        		R.id.text_book_item_title,
        		R.id.text_book_item_author,
        		R.id.text_book_item_id,
        		R.id.text_book_item_price
        };
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
        		this,
        		R.layout.list_book_item,
        		c,
        		from,
        		to,
        		0);
        setListAdapter(adapter);
    }

    @Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
    	String bookId = ((TextView)v.findViewById(R.id.text_book_item_id)).getText().toString();
		Intent showBookActivity = new Intent(getBaseContext(), ViewBookDetailsActivity.class);
		showBookActivity.putExtra(Book.BOOK_ID, bookId);
    	startActivity(showBookActivity);
	}




	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_book_store_main, menu);
        return true;
    }


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
	    switch (item.getItemId()) {
	        case R.id.menu_add:
	            Intent addIntent = new Intent(this, AddBookActivity.class);
	            startActivity(addIntent);
	            return true;
	        case R.id.menu_search:
	            Intent searchIntent = new Intent(this, SearchBookActivity.class);
	            startActivity(searchIntent);
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
    
    
}

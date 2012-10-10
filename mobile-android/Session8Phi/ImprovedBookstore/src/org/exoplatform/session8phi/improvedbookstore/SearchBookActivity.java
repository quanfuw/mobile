package org.exoplatform.session8phi.improvedbookstore;

import org.exoplatform.session8phi.improvedbookstore.BookStoreDBHelper.Book;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class SearchBookActivity extends Activity {
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_book);
        ListView bookList = (ListView)findViewById(R.id.list_found_books);
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
    
    public void searchBooks(View view) {
    	String searchTerm = ((EditText)findViewById(R.id.text_search_book)).getText().toString();
    	this.searchAndDisplay(searchTerm);
    }
    
    private void searchAndDisplay(String searchTerm) {
    	// get search options
        CheckBox titleCB = (CheckBox)findViewById(R.id.checkbox_title);
        CheckBox authorCB = (CheckBox)findViewById(R.id.checkbox_author);
        String where = " ";
        if (titleCB.isChecked()) where += Book.BOOK_TITLE+" LIKE ?";
        if (titleCB.isChecked() && authorCB.isChecked()) where += " OR ";
        if (authorCB.isChecked()) where += Book.BOOK_AUTHOR+" LIKE ?";
        // Populate book list
        BookStoreDBHelper booksDB = new BookStoreDBHelper(this);
        ListView bookList = (ListView)findViewById(R.id.list_found_books);
        Cursor c = booksDB.searchBooks(searchTerm, where);
        if (c.getCount() > 0) {
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
        	bookList.setAdapter(adapter);
        } else {
        	AlertDialog.Builder builder = new AlertDialog.Builder(this);
        	builder.setMessage(getString(R.string.label_search_no_results))
        	       .setCancelable(false)
        	       .setNeutralButton("OK", new DialogInterface.OnClickListener() {
        	           public void onClick(DialogInterface dialog, int id) {
        	                dialog.cancel();
        	           }
        	       });
        	AlertDialog alert = builder.create();
        	alert.show();
        }
        
    }
    
    
}

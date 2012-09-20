package org.exoplatform.session7phi.bookstore;

import org.exoplatform.session7phi.bookstore.BookStoreDBHelper.Book;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class SearchBookActivity extends Activity {

	public static final String KEY_SEARCH_EXTRA = "SEARCH_TERM";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_book);
        // get search term
        String searchTerm = getIntent().getExtras().getString(KEY_SEARCH_EXTRA);
        // put it in search field
        ((EditText)findViewById(R.id.text_search_book)).setText(searchTerm);
        // search and display the results
        this.searchAndDisplay(searchTerm);
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
        BookListAdapter adapter = new BookListAdapter(this, R.layout.list_book_item, booksDB.searchBooks(searchTerm, where));
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
}

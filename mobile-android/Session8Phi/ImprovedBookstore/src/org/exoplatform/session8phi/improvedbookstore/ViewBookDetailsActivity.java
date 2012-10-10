package org.exoplatform.session8phi.improvedbookstore;

import org.exoplatform.session8phi.improvedbookstore.BookStoreDBHelper.Book;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class ViewBookDetailsActivity extends Activity {

	private BookStoreDBHelper db;
	private int currentBookId;

	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_book_details);
        
        String bookId = getIntent().getExtras().getString(Book.BOOK_ID);
        currentBookId = Integer.parseInt(bookId);
        db = new BookStoreDBHelper(this);
        Book book = db.getBookById(Integer.parseInt(bookId));
        ((TextView)findViewById(R.id.display_book_title)).setText(book.getTitle());
        ((TextView)findViewById(R.id.display_book_author)).setText(book.getAuthor());
        ((TextView)findViewById(R.id.display_book_price)).setText("$"+book.getPrice());
        ((TextView)findViewById(R.id.display_book_desc)).setText(book.getDesc());
        ((TextView)findViewById(R.id.display_book_description)).setText(book.getDesc()+book.getMoreDesc());
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_view_book_details, menu);
        return true;
    }
    
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
		case R.id.menu_delete_book:
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
        	builder.setMessage(getString(R.string.menu_delete_book)+"?")
        	       .setCancelable(true)
        	       .setPositiveButton(getString(R.string.word_yes), new DialogInterface.OnClickListener() {
        	           public void onClick(DialogInterface dialog, int id) {
        	        	   if (db.deleteBook(currentBookId)) {
        	   				Toast.makeText(getApplicationContext(),
        	   						R.string.message_book_deleted_ok,
        	   						Toast.LENGTH_SHORT).show();
        	   				finish();
        	   			} else {
        	   				Toast.makeText(getApplicationContext(),
        	   						R.string.message_book_not_deleted,
        	   						Toast.LENGTH_LONG).show();
        	   			}		
        	           }
        	       })
        	       .setNegativeButton(getString(R.string.word_no), new DialogInterface.OnClickListener() {
        	           public void onClick(DialogInterface dialog, int id) {
        	                dialog.cancel();
        	           }
        	       });
        	AlertDialog alert = builder.create();
        	alert.show();	
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
    }

	@Override
	public void onBackPressed() {
		// intercepts when the user presses the Back button
		SwipableView swipeView = (SwipableView)findViewById(R.id.main_detail_view);
		// let the view and the gesture listener handle
		boolean cont = swipeView.handleBackButtonPressed();
		// if true, we keep the normal behavior of the Back button
		if (cont)
			super.onBackPressed();
	}

}

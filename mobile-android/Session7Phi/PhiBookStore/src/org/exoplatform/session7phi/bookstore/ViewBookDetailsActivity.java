package org.exoplatform.session7phi.bookstore;

import org.exoplatform.session7phi.bookstore.BookStoreDBHelper.Book;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ViewBookDetailsActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_book_details);
        
        String bookId = getIntent().getExtras().getString(Book.BOOK_ID);
        BookStoreDBHelper db = new BookStoreDBHelper(this);
        Book book = db.getBookById(Integer.parseInt(bookId));
        ((TextView)findViewById(R.id.display_book_title)).setText(book.getTitle());
        ((TextView)findViewById(R.id.display_book_author)).setText(book.getAuthor());
        ((TextView)findViewById(R.id.display_book_price)).setText(book.getPrice());
    }
}

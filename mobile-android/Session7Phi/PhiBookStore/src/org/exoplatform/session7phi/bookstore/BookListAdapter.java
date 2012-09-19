package org.exoplatform.session7phi.bookstore;

import java.util.List;

import org.exoplatform.session7phi.bookstore.BookStoreDBHelper.Book;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class BookListAdapter extends ArrayAdapter<Book> {

	Context c;
	
	public BookListAdapter(Context context, int textViewResourceId, List<Book> objects) {
		super(context, textViewResourceId, objects);
		c = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.list_book_item, null);
        }
		
		Book b = getItem(position);
		((TextView)v.findViewById(R.id.text_book_item_title)).setText(b.getTitle());
		((TextView)v.findViewById(R.id.text_book_item_author)).setText(b.getAuthor());
		((TextView)v.findViewById(R.id.text_book_item_price)).setText(b.getPrice());
		((TextView)v.findViewById(R.id.text_book_item_id)).setText(b.getId());
		
		return v;
	}
	
	

}

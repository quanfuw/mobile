package com.example.com.exoplatform.session7tuan;

import java.util.ArrayList;
import java.util.List;

import com.example.com.exoplatform.session7tuan.data.Book;
import com.example.com.exoplatform.session7tuan.data.BookDataImpl;
import com.example.com.exoplatform.session7tuan.data.BookDataService;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener; 
import android.widget.Toast;

public class UIBookList extends Activity {
	
	BookDataService bookService = new BookDataImpl(this) ;
	ListView bookList ;
	
	EditText searchField ;
	List<Book> data = new ArrayList<Book>() ;
	public static String KEY_DATA = "dataList" ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);
        bookList = (ListView)findViewById(R.id.bookList) ;
        
        
        bookService.addBook(new Book("the sign of forth", "Ser Conand")) ;
        
        bookService.addBook(new Book("gone with the wind", "Heator")) ;
        //
        data = bookService.getAllBook();
        bookList.setAdapter(new BookAdapter(this, data)) ;
        
        bookList.setOnItemClickListener(new OnItemClickListener() {
        	@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
        		
        		
        		Intent it = new Intent(UIBookList.this, UIBookDetail.class) ;
        		it.putExtra(KEY_DATA, data.get(position)) ;
        		
        		startActivity(it) ;
        		
        	}
        	
		});
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_book_list, menu);
        return true;
    }
    
    public void search(View v) {
      EditText ed = (EditText)findViewById(R.id.searchField) ;
      
      if("".equals(ed.getText().toString().trim())) {
        
        data = bookService.getAllBook() ;
      } else {
        
        data = bookService.searchBook(ed.getText().toString()) ;
      }
      
      bookList.setAdapter(new BookAdapter(this, data)) ;
      
      
      
      
    }
     
}

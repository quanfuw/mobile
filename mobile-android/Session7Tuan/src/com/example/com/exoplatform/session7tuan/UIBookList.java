package com.example.com.exoplatform.session7tuan;

import java.util.ArrayList;
import java.util.List;

import com.example.com.exoplatform.session7tuan.data.Book;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener; 

public class UIBookList extends Activity {
	
	ListView bookList ;
	
	EditText searchField ;
	List<Book> data = new ArrayList<Book>() ;
	public static String KEY_DATA = "dataList" ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);
        bookList = (ListView)findViewById(R.id.bookList) ;
        
        data.add(new Book("the sign of forth", "Ser Conand")) ;
        
        data.add(new Book("gone with the wind", "Heator")) ;
        //
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
}

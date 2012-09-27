package com.example.com.exoplatform.session7tuan;

import java.util.ArrayList;
import java.util.List;

import com.example.com.exoplatform.session7tuan.data.Book;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.ListView;



public class UIBookDetail extends Activity {

	ListView bookDetail ;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        bookDetail = (ListView) findViewById(R.id.book_detail) ;
        Intent it = getIntent() ;
        Book selectedBook  = (Book)it.getParcelableExtra(UIBookList.KEY_DATA) ;
        List<String> pros = new ArrayList<String>() ;
        pros.add(selectedBook.getName()) ;
        pros.add(selectedBook.getAuthor()) ;
        bookDetail.setAdapter(new BookDetailAdapter(this, pros)) ;
    }

}

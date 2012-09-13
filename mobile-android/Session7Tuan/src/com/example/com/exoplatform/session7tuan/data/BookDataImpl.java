package com.example.com.exoplatform.session7tuan.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BookDataImpl extends SQLiteOpenHelper implements BookDataService{

	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "bookstore";
	
	// Book table Name
	private static final String TABLE_BOOK= "books";
	
	// Author table Name
	private static final String TABLE_AUTHOR = "author";
	
	
	public BookDataImpl(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public Book addBook(Book b) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Book updateBook(Book b) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int deleteBook(String bookId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int countBookData() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

}

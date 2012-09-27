package com.example.com.exoplatform.session7tuan.data;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
 

public class BookDataImpl extends SQLiteOpenHelper implements BookDataService{

	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "bookstore";
	
	// Book table Name
	private static final String TABLE_BOOK= "books";
	
	// Book Table Columns names
		private static final String KEY_ID = "id";
		private static final String KEY_NAME = "name";
		private static final String KEY_TITLE = "title";
		private static final String KEY_CATE = "category";
		private static final String KEY_AUTHOR = "author";
		private static final String KEY_PAGE_NUM = "page";
		private static final String KEY_PRE_FACE = "url";
		
		private String CREATE_BOOK_TABLE = "CREATE TABLE " + TABLE_BOOK + "("
				+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
				+ KEY_TITLE  + " TEXT," + KEY_CATE  + " TEXT," + KEY_AUTHOR+  " TEXT," + KEY_PAGE_NUM  + " INTEGER," + KEY_PRE_FACE  + " TEXT"  + ")";
		
		 
	// Author table Name
	private static final String TABLE_AUTHOR = "author";
	
	
	public BookDataImpl(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public Book addBook(Book b) {
		
		if(!isExist(b.getName())){
		
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_NAME, b.getName());  
		values.put(KEY_TITLE, b.getTitle());  
		values.put(KEY_CATE, b.getCategory());  
		values.put(KEY_AUTHOR, b.getAuthor());  
		values.put(KEY_PAGE_NUM, b.getPages());  
		values.put(KEY_PRE_FACE, b.getPreFaceUrl());  

		// Inserting Row
		db.insert(TABLE_BOOK, null, values);
		db.close(); // Closing database connection
		
		return b;
		} else {
			System.out.println("Book Alredy exits!");
			return null;
		}
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
	public void onCreate(SQLiteDatabase db) {
		
		db.execSQL(CREATE_BOOK_TABLE);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOK);

		// Create tables again
		onCreate(db);
		
	}

	@Override
	public Book getBook(String id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_BOOK, new String[] { KEY_ID,
				KEY_NAME, KEY_TITLE, KEY_CATE, KEY_AUTHOR, KEY_PAGE_NUM, KEY_PRE_FACE }, KEY_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null) cursor.moveToFirst();

		Book book = new Book(cursor.getString(0),
				cursor.getString(1), cursor.getString(2),cursor.getString(3),cursor.getString(4),Integer.parseInt(cursor.getString(5)),cursor.getString(6));
		
		// return contact
		return book;
		 
	}
	
	
	
	

	@Override
	public List<Book> getAllBook() {
		List<Book> list = new ArrayList<Book>();
		SQLiteDatabase data = this.getReadableDatabase() ;
		Cursor c = data.query(TABLE_BOOK, new String[]{KEY_NAME, KEY_AUTHOR},null, null, null, null,null);
		// TODO Auto-generated method stub
		while(c.moveToNext()) {
			list.add(new Book(c.getString(0), c.getString(1)));
		}
		c.close();
		return list;
	}

	@Override
	public boolean isExist(String bookName) {
		 
			SQLiteDatabase db = this.getReadableDatabase();

			Cursor cursor = db.query(TABLE_BOOK, new String[] { KEY_ID,
					KEY_NAME, KEY_TITLE, KEY_CATE, KEY_AUTHOR, KEY_PAGE_NUM, KEY_PRE_FACE }, KEY_NAME + "=?",
					new String[] { String.valueOf(bookName) }, null, null, null, null);
			return ((cursor != null) && cursor.moveToFirst());
		 
	}

  @Override
  public List<Book> searchBook(String key) {
    List<Book> list = new ArrayList<Book>();
    SQLiteDatabase db = this.getReadableDatabase() ;
    Cursor cursor = db.query(TABLE_BOOK, new String[] { KEY_ID,
        KEY_NAME, KEY_TITLE, KEY_CATE, KEY_AUTHOR, KEY_PAGE_NUM, KEY_PRE_FACE }, KEY_NAME + " like? or " + KEY_AUTHOR + " like?" ,
        new String[] { "%"+key+"%","%"+key+"%" }, null, null, null, null);
    //System.out.println("query ----->>>" + db.);
    while(cursor.moveToNext()) {
      list.add(new Book(cursor.getString(1), cursor.getString(4))) ;
    }
    return list;
  }

}

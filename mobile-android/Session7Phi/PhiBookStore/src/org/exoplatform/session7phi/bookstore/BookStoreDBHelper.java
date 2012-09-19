package org.exoplatform.session7phi.bookstore;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BookStoreDBHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME =    "PHI_BOOK_STORE";
	private static final int    DATABASE_VERSION = 1;
    private static final String BOOKS_TABLE_NAME = "phi_session7_books";
	
    // Create and Update DB
    public BookStoreDBHelper(Context c) {
    	super(c, DATABASE_NAME, null, DATABASE_VERSION);
    }
    
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(
				"CREATE TABLE "+BOOKS_TABLE_NAME+" ("+
				" "+Book.BOOK_ID+"     INTEGER PRIMARY KEY AUTOINCREMENT,"+
				" "+Book.BOOK_TITLE+"  TEXT,"+
				" "+Book.BOOK_AUTHOR+" TEXT,"+
				" "+Book.BOOK_PRICE+"  TEXT);"
		);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}
	
	// Create and Search Books
	public boolean addBook(String bookTitle, String bookAuthor, String bookPrice) {
		ContentValues values = new ContentValues();
		values.put(Book.BOOK_TITLE, bookTitle);
		values.put(Book.BOOK_AUTHOR, bookAuthor);
		values.put(Book.BOOK_PRICE, bookPrice);
		
		try {
			getWritableDatabase().insert(BOOKS_TABLE_NAME, null, values);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean addBook(Book b) {
		return this.addBook(b.getTitle(), b.getAuthor(), b.getPrice());
	}
	
	public ArrayList<Book> getAllBooks() {
		ArrayList<Book> books = new ArrayList<Book>();
		try {
			Cursor c = getReadableDatabase().rawQuery("SELECT * FROM "+BOOKS_TABLE_NAME, null);
			while (c.moveToNext()) {
				 books.add(new Book(
						 c.getString(c.getColumnIndex(Book.BOOK_TITLE)),
						 c.getString(c.getColumnIndex(Book.BOOK_AUTHOR)),
						 c.getString(c.getColumnIndex(Book.BOOK_PRICE)),
						 c.getString(c.getColumnIndex(Book.BOOK_ID))));
			}
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return books;
	}
	
	public Book getBookById(int bId) {
		Book book = null;
		try {
			Cursor c = getReadableDatabase().
			rawQuery("SELECT "+Book.BOOK_TITLE+", "+Book.BOOK_AUTHOR+", "+Book.BOOK_PRICE+" FROM "+BOOKS_TABLE_NAME+" WHERE "+Book.BOOK_ID+"=?",
					new String[]{""+bId});
			if (c.moveToNext()) {
				 book = new Book(c.getString(0), c.getString(1), c.getString(2), ""+bId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return book;
	}
	
	public ArrayList<Book> searchBooks(String searchTerm, String where) {
		ArrayList<Book> books = new ArrayList<Book>();
		ArrayList<String> selectionArgs = new ArrayList<String>();
		for (char c : where.toCharArray()) {
			if (c == '?') selectionArgs.add("%"+searchTerm+"%");
		}
		try {
			Cursor c = getReadableDatabase().query(
					BOOKS_TABLE_NAME,
					new String[]{Book.BOOK_TITLE,Book.BOOK_AUTHOR,Book.BOOK_PRICE,Book.BOOK_ID},
					where,
					(String[]) selectionArgs.toArray(new String[selectionArgs.size()]),
					null, null, null);
			while (c.moveToNext()) {
				 books.add(new Book(
						 c.getString(c.getColumnIndex(Book.BOOK_TITLE)),
						 c.getString(c.getColumnIndex(Book.BOOK_AUTHOR)),
						 c.getString(c.getColumnIndex(Book.BOOK_PRICE)),
						 c.getString(c.getColumnIndex(Book.BOOK_ID))));
			}
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return books;
	}
	
	// Book model
	public class Book {
		// BOOKS table row names
		public static final String BOOK_TITLE =  "BOOK_TITLE";
		public static final String BOOK_AUTHOR = "BOOK_AUTHOR";
		public static final String BOOK_PRICE =  "BOOK_PRICE";
		public static final String BOOK_ID =     "BOOK_ID";
		// Attributes
		private String id;
		private String title;
		private String author;
		private String price;
		// Constructor
		public Book(String bTitle, String bAuthor, String bPrice, String bId) {
			id = bId;
			title = bTitle;
			author = bAuthor;
			price = bPrice;
		}
		// Getters and Setters
		public String getTitle() {return title;}
		public void setTitle(String bTitle) {title = bTitle;}
		public String getAuthor() {return author;}
		public void setAuthor(String bAuthor) {author = bAuthor;}
		public String getPrice() {return price;}
		public void setPrice(String bPrice) {price = bPrice;}
		public String getId() {return id;}
	}

}

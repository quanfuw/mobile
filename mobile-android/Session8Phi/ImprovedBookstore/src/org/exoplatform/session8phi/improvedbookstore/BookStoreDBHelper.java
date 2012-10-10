package org.exoplatform.session8phi.improvedbookstore;


import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BookStoreDBHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME =    "PHI_BOOK_STORE";
	private static final int    DATABASE_VERSION = 2;
    private static final String BOOKS_TABLE_NAME = "phi_session8_books";
	
    // Create and Update DB
    public BookStoreDBHelper(Context c) {
    	super(c, DATABASE_NAME, null, DATABASE_VERSION);
    }
    
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(
				"CREATE TABLE "+BOOKS_TABLE_NAME+" ("+
				" "+Book.BOOK_ID+       " INTEGER PRIMARY KEY AUTOINCREMENT,"+
				" "+Book.BOOK_TITLE+    " TEXT,"+
				" "+Book.BOOK_AUTHOR+   " TEXT,"+
				" "+Book.BOOK_PRICE+    " TEXT," +
				" "+Book.BOOK_DESC_P1+  " TEXT," +
				" "+Book.BOOK_DESC_MORE+" TEXT);"
		);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if (oldVersion == 1 && newVersion == 2) {
			db.execSQL(
				"ALTER TABLE "+BOOKS_TABLE_NAME+
				" ADD COLUMN "+Book.BOOK_DESC_P1+" TEXT;"
			);
			db.execSQL(
					"ALTER TABLE "+BOOKS_TABLE_NAME+
					" ADD COLUMN "+Book.BOOK_DESC_MORE+" TEXT;"
			);
			ContentValues values = new ContentValues();
			values.put(Book.BOOK_DESC_P1, "Lorem ipsum dolor sit amet, consectetur adipiscing elit." +
					" Mauris sagittis, lacus vehicula pulvinar dignissim, dui mauris pulvinar neque, et sagittis velit neque feugiat sapien.");
			values.put(Book.BOOK_DESC_MORE, "Mauris at nunc purus, vitae tempus urna. " +
					"Morbi vel orci et eros dapibus feugiat non a eros. Aenean vitae urna id erat volutpat porta. " +
					"Donec euismod tincidunt erat, in placerat diam posuere in. Proin at leo nibh, eget iaculis dui. " +
					"Vivamus ac nisi quis quam sollicitudin tempor eu sed justo. Integer placerat placerat quam id congue. " +
					"Aliquam mattis laoreet elit, vel rhoncus purus cursus nec. Suspendisse nec sodales odio. " +
					"Duis euismod enim nec elit porttitor ac lobortis est sagittis. Integer in nibh sem. ");
			db.update(BOOKS_TABLE_NAME, values, null, null);
		}
	}
	
	/*
	 * Add a new book.
	 * Return true if the book was added successfully, false otherwise.
	 */
	public boolean addBook(String bookTitle, String bookAuthor, String bookPrice, String bookDesc, String bookMoreDesc) {
		ContentValues values = new ContentValues();
		values.put(Book.BOOK_TITLE, bookTitle);
		values.put(Book.BOOK_AUTHOR, bookAuthor);
		values.put(Book.BOOK_PRICE, bookPrice);
		values.put(Book.BOOK_DESC_P1, bookDesc);
		values.put(Book.BOOK_DESC_MORE, bookMoreDesc);
		
		try {
			getWritableDatabase().insert(BOOKS_TABLE_NAME, null, values);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean addBook(Book b) {
		return this.addBook(b.getTitle(), b.getAuthor(), b.getPrice(), b.getDesc(), b.getMoreDesc());
	}
	
	/*
	 * Get all books in a Cursor object.
	 */
	public Cursor getAllBooks() {
		Cursor c = null;
		try {
			c = getReadableDatabase().rawQuery("SELECT * FROM "+BOOKS_TABLE_NAME, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return c;
	}
	
	/*
	 * Get the book identified by its ID.
	 * Return the Book object.
	 */
	public Book getBookById(int bId) {
		Book book = null;
		Cursor c = null;
		try {
			c = getReadableDatabase().
			rawQuery("SELECT "+
						Book.BOOK_TITLE+", "+
						Book.BOOK_AUTHOR+", "+
						Book.BOOK_PRICE+", "+
						Book.BOOK_DESC_P1+", "+
						Book.BOOK_DESC_MORE+
						" FROM "+BOOKS_TABLE_NAME+" WHERE "+Book.BOOK_ID+"=?",
					new String[]{""+bId});
			if (c.moveToNext()) {
				 book = new Book(c.getString(0), c.getString(1), c.getString(2), ""+bId, c.getString(3), c.getString(4));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (c != null) c.close();
		}
		return book;
	}
	
	/*
	 * Find books by title or author or both, depending on where.
	 * Compare searchTerm against each parameter in where.
	 * Return the cursor of found books.
	 */
	public Cursor searchBooks(String searchTerm, String where) {
		Cursor c = null;
		ArrayList<String> selectionArgs = new ArrayList<String>();
		for (char ch : where.toCharArray()) {
			if (ch == '?') selectionArgs.add("%"+searchTerm+"%");
		}
		try {
			c = getReadableDatabase().query(
					BOOKS_TABLE_NAME,
					new String[]{Book.BOOK_TITLE,Book.BOOK_AUTHOR,Book.BOOK_PRICE,Book.BOOK_ID},
					where,
					(String[]) selectionArgs.toArray(new String[selectionArgs.size()]),
					null, null, null);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (c != null) c.close();
		}
		return c;
	}
	
	/*
	 * Delete the book identified by its ID.
	 * Return true if the book has been deleted, false otherwise.
	 */
	public boolean deleteBook(int bId) {
		int deleted = 0;
		try {
			deleted = getWritableDatabase().delete(BOOKS_TABLE_NAME,
					Book.BOOK_ID+"=?", 
					new String[]{String.valueOf(bId)});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (deleted == 1);
	}
	
	// Book model
	public class Book {
		// BOOKS table row names
		public static final String BOOK_TITLE     =  "BOOK_TITLE";
		public static final String BOOK_AUTHOR    =  "BOOK_AUTHOR";
		public static final String BOOK_PRICE     =  "BOOK_PRICE";
		public static final String BOOK_DESC_P1   =  "BOOK_DESC_P1";
		public static final String BOOK_DESC_MORE =  "BOOK_DESC_MORE";
		public static final String BOOK_ID        =  "_id";
		// Attributes
		private String id;
		private String title;
		private String author;
		private String price;
		private String desc_part1;
		private String desc_more;
		// Constructor
		public Book(String bTitle, String bAuthor, String bPrice, String bId, String pDesc, String pMore) {
			id = bId;
			title = bTitle;
			author = bAuthor;
			price = bPrice;
			desc_part1 = pDesc;
			desc_more = pMore;
		}
		// Getters and Setters
		public String getTitle() {return title;}
		public void setTitle(String bTitle) {title = bTitle;}
		public String getAuthor() {return author;}
		public void setAuthor(String bAuthor) {author = bAuthor;}
		public String getPrice() {return price;}
		public void setPrice(String bPrice) {price = bPrice;}
		public String getId() {return id;}
		public String getDesc() {return desc_part1;}
		public void setDesc(String pDesc) {desc_part1 = pDesc;}
		public String getMoreDesc() {return desc_more;}
		public void setMoreDesc(String pMore) {desc_more = pMore;}
	}

}

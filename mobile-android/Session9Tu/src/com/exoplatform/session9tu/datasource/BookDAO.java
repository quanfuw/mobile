/*
 * Copyright (C) 2003-2012 eXo Platform SAS.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.exoplatform.session9tu.datasource;

import java.util.ArrayList;
import java.util.List;

import com.exoplatform.session9tu.domain.Book;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Book Data Access Object to manage operation to SQLite database
 * 
 * @author Created by The eXo Platform SAS
 * <br/>Anh-Tu Nguyen
 * <br/><a href="mailto:tuna@exoplatform.com">tuna@exoplatform.com<a/>
 * <br/>Nov 20, 2012  
 */
public class BookDAO 
{
  private static final String TAG = "BookDAO";
  private SQLiteDatabase database = null;
  private BookSQLiteHelper dbHelper = null;
  private String[] allColumns = { 
      BookSQLiteHelper.COLUMN_ID, 
      BookSQLiteHelper.COLUMN_BOOK_NAME,
      BookSQLiteHelper.COLUMN_BOOK_URL };
  
  public BookDAO(Context context)
  {
    if (dbHelper != null) return;
    Log.i(TAG, "create new helper");
    dbHelper = new BookSQLiteHelper(context);
  }
  
  public void open() throws SQLException
  {
    /* calls onCreate and onUpgrade on dbHelper */
    if (database != null) return;
    database = dbHelper.getWritableDatabase();
  }
  
  public void close() { dbHelper.close(); }
  
  /**
   * insert book into database
   * 
   * @param aBook
   * @return newBook with id else -1 in case of error
   */
  public Book insertBook(Book aBook) {
    open();
    ContentValues values = new ContentValues();
    values.put(BookSQLiteHelper.COLUMN_BOOK_NAME, aBook.getName());
    values.put(BookSQLiteHelper.COLUMN_BOOK_URL , aBook.getUrlBookCover());
    
    long bookId = database.insert(BookSQLiteHelper.BOOK_TABLE, null, values);
    if (bookId == -1) return null;
    aBook.setId(bookId);
    return aBook;
  }
  
  /**
   * update book into database
   * 
   * @param aBook
   * @return newBook with id else -1 in case of error
   */
  public void updateBook(Book aBook) 
  {
    open();
    ContentValues values = new ContentValues();
    values.put(BookSQLiteHelper.COLUMN_BOOK_NAME, aBook.getName());
    values.put(BookSQLiteHelper.COLUMN_BOOK_URL , aBook.getUrlBookCover());

    database.update(BookSQLiteHelper.BOOK_TABLE, values, BookSQLiteHelper.COLUMN_ID + " = ?"
                                  , new String[] { String.valueOf(aBook.getId()) } );
  }
  
  public void deleteBook(Book aBook) 
  {
    open();
    database.delete(BookSQLiteHelper.BOOK_TABLE, BookSQLiteHelper.COLUMN_ID
        + " = " + aBook.getId(), null);
  }
  
  public List<Book> getAllBooks() 
  {
    open();
    List<Book> allBooks = new ArrayList<Book>();
    Cursor cursor = database.query(BookSQLiteHelper.BOOK_TABLE, allColumns, null, null, null, null, 
                                   BookSQLiteHelper.COLUMN_BOOK_NAME + " COLLATE LOCALIZED ASC");
    cursor.moveToFirst();
    while (!cursor.isAfterLast()) {
      Book aBook = toBookFrom(cursor);
      allBooks.add(aBook);
      cursor.moveToNext();
    }
    // Make sure to close the cursor
    cursor.close();
    Log.i(TAG, "number of books: " + allBooks.size());
    return allBooks;
  }
  
  private Book toBookFrom(Cursor cursor)
  {
    Book aBook = new Book(cursor.getLong(   cursor.getColumnIndex(BookSQLiteHelper.COLUMN_ID) ), 
                          cursor.getString( cursor.getColumnIndex(BookSQLiteHelper.COLUMN_BOOK_NAME) ),
                          cursor.getString( cursor.getColumnIndex(BookSQLiteHelper.COLUMN_BOOK_URL)));
    return aBook;
  }
}

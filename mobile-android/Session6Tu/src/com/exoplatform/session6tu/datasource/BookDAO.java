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
package com.exoplatform.session6tu.datasource;

import java.util.ArrayList;
import java.util.List;

import com.exoplatform.session6tu.domain.Book;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

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
  private SQLiteDatabase database;
  private BookSQLiteHelper dbHelper;
  private String[] allColumns = { BookSQLiteHelper.COLUMN_ID, BookSQLiteHelper.COLUMN_BOOK_NAME };
  
  public BookDAO(Context context)
  {
    dbHelper = new BookSQLiteHelper(context);
  }
  
  public void open() throws SQLException
  {
    /* calls onCreate and onUpgrade on dbHelper */
    database = dbHelper.getWritableDatabase();
  }
  
  public void close() { dbHelper.close(); }
  
  /**
   * insert book into database
   * 
   * @param aBook
   * @return newBook with id
   */
  public Book insertBook(Book aBook) {
    ContentValues values = new ContentValues();
    values.put(BookSQLiteHelper.COLUMN_BOOK_NAME, aBook.getName());
    long bookId = database.insert(BookSQLiteHelper.BOOK_TABLE, null, values);
    aBook.setId(bookId);
    return aBook;
  }
  
  public void deleteBook(Book aBook) {
    database.delete(BookSQLiteHelper.BOOK_TABLE, BookSQLiteHelper.COLUMN_ID
        + " = " + aBook.getId(), null);
  }
  
  public List<Book> getAllBooks() 
  {
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
    return allBooks;
  }
  
  private Book toBookFrom(Cursor cursor)
  {
    Book aBook = new Book(cursor.getLong( cursor.getColumnIndex(BookSQLiteHelper.COLUMN_ID) ), 
                          cursor.getString( cursor.getColumnIndex(BookSQLiteHelper.COLUMN_BOOK_NAME)) );
    return aBook;
  }
}

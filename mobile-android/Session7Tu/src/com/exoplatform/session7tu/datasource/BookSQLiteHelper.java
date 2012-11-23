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
package com.exoplatform.session7tu.datasource;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * SQLite helper class to create book database
 * 
 * @author Created by The eXo Platform SAS
 * <br/>Anh-Tu Nguyen
 * <br/><a href="mailto:tuna@exoplatform.com">tuna@exoplatform.com<a/>
 * <br/>Nov 20, 2012  
 */
public class BookSQLiteHelper extends SQLiteOpenHelper
{
  private static final String TAG = "BookSQLiteHelper";
  
  private static final String DATABASE_NAME = "books.db";
  
  private static final int DATABASE_VERSION = 1;
  
  public static final String BOOK_TABLE = "books";
  
  public static final String COLUMN_ID = "_id";
  
  public static final String COLUMN_BOOK_NAME = "name";
  
  public static final String COLUMN_BOOK_URL  = "url";
  
  /* database creation statement */
  private static final String CREATE_DATABASE = "create table " + BOOK_TABLE 
      + "(" + COLUMN_ID + " integer primary key autoincrement, " + COLUMN_BOOK_NAME
      + " text not null, " + COLUMN_BOOK_URL + " text not null);";
  
  public BookSQLiteHelper(Context context)
  {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }
  
  @Override
  public void onCreate(SQLiteDatabase database) 
  {
    Log.i(TAG, "sql statement: " + CREATE_DATABASE);
    database.execSQL(CREATE_DATABASE);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
  {
    Log.i(TAG, "onUpgrade from version " + oldVersion + " to version " + newVersion);
    db.execSQL("drop table if exists " + BOOK_TABLE);
  }
}

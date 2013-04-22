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
package com.exoplatform.session9tu.ui;

import com.exoplatform.session9tu.R;
import com.exoplatform.session9tu.datasource.BookDAO;
import com.exoplatform.session9tu.domain.Book;
import com.exoplatform.session9tu.util.BookConstant;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * represents the screen to add book
 * 
 * @author Created by The eXo Platform SAS
 * <br/>Anh-Tu Nguyen
 * <br/><a href="mailto:tuna@exoplatform.com">tuna@exoplatform.com<a/>
 * <br/>Nov 21, 2012  
 */
public class AddBookActivity extends BaseActivity
{
  private static final String TAG = "AddBookActivity";
    
  @Override
  public void onCreate(Bundle savedState)
  {
    Log.i(TAG, "onCreate");
    super.onCreate(savedState);
    setContentView(R.layout.add_book);
  }
  
  @Override
  public boolean onCreateOptionsMenu(Menu menu)
  {
    super.onCreateOptionsMenu(menu);
    menu.findItem(R.id.editBook).setEnabled(false); /* do not allow to edit and add book */
    menu.findItem(R.id.addBook).setEnabled(false);
    return true;
  }
  
  @Override
  public void onNewIntent(Intent intent)
  {
    Log.i(TAG, "onNewIntent");
    super.onNewIntent(intent);
  }
  
  /**
   * save the book in to SQLite then display result in a toast
   * return user to BookDetailActivity
   * 
   * @param view
   */
  public void saveBookClick(View saveButton)
  {
    BookDAO bookDAO = new BookDAO(this);
    EditText addBookName = (EditText) findViewById(R.id.addBookName);
    EditText editBookUrl = (EditText) findViewById(R.id.addBookCoverUrl);
    Book newBook = bookDAO.insertBook( 
      new Book( addBookName.getText().toString(), editBookUrl.getText().toString() ));
    Toast toast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
    
    if (newBook == null) 
    {
      /* error adding book */
      toast.setText("add book error");
      toast.show();
      return;
    }
    
    toast.setText("successful");
    toast.show();
    
    Intent showBookDetail = new Intent();
    showBookDetail.setAction(BookConstant.DISPLAY_BOOK_INTENT);
    showBookDetail.putExtra(BookConstant.BOOK, newBook );
    startActivity(showBookDetail);
  }
}

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
package com.exoplatform.session7tu.ui;

import com.exoplatform.session7tu.R;
import com.exoplatform.session7tu.datasource.BookDAO;
import com.exoplatform.session7tu.util.BookConstant;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * represents the screen to edit book
 * 
 * @author Created by The eXo Platform SAS
 * <br/>Anh-Tu Nguyen
 * <br/><a href="mailto:tuna@exoplatform.com">tuna@exoplatform.com<a/>
 * <br/>Nov 20, 2012  
 */
public class EditBookActivity extends BaseActivity
{
  private static final String TAG = "EditBookActivity";
    
  @Override
  public void onCreate(Bundle savedState)
  {
    Log.i(TAG, "onCreate");
    super.onCreate(savedState);
    
    setContentView(R.layout.edit_book);  
    EditText updateBookName = (EditText) findViewById(R.id.updateBookName);
    updateBookName.setText( mBook.getName() ); 
    
    EditText updateBookUrl = (EditText) findViewById(R.id.updateBookUrl);
    updateBookUrl.setText( mBook.getUrlBookCover() ); 
  }
  
  @Override
  public void onNewIntent(Intent intent)
  {
    Log.i(TAG, "onNewIntent");
    super.onNewIntent(intent);
    EditText updateBookName = (EditText) findViewById(R.id.updateBookName);
    updateBookName.setText( mBook.getName() ); 
    EditText updateBookUrl = (EditText) findViewById(R.id.updateBookUrl);
    updateBookUrl.setText( mBook.getUrlBookCover() ); 
  }
  
  @Override
  public boolean onCreateOptionsMenu(Menu menu)
  {
    super.onCreateOptionsMenu(menu);
    menu.findItem(R.id.editBook).setEnabled(false); /* do not allow to edit book */
    return true;
  }
  
  /**
   * update the book into SQLite
   * 
   * */
  public void updateBookClick(View view)
  {
    BookDAO bookDAO = new BookDAO(this);
    EditText updateBookName = (EditText) findViewById(R.id.updateBookName);
    EditText updateBookUrl = (EditText) findViewById(R.id.updateBookUrl);
    mBook.setName( updateBookName.getText().toString() );
    mBook.setUrlBookCover( updateBookUrl.getText().toString() );
    bookDAO.updateBook( mBook );
    Toast toast = Toast.makeText(this, "successful", Toast.LENGTH_SHORT);
    toast.show();
    
    /* refactor */
    Intent showBookDetail = new Intent();
    showBookDetail.setAction(BookConstant.DISPLAY_BOOK_INTENT);
    showBookDetail.putExtra(BookConstant.BOOK, mBook );
    startActivity(showBookDetail);
  }
}

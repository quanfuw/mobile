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

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.exoplatform.session7tu.R;
import com.exoplatform.session7tu.domain.Book;
import com.exoplatform.session7tu.util.BookConstant;

/**
 * share common ui to all activities including action bar
 * 
 * @author Created by The eXo Platform SAS
 * <br/>Anh-Tu Nguyen
 * <br/><a href="mailto:tuna@exoplatform.com">tuna@exoplatform.com<a/>
 * <br/>Nov 23, 2012  
 */
public class BaseActivity extends Activity
{
  protected Book mBook;
  
  @Override
  public void onCreate(Bundle savedState)
  {
    super.onCreate(savedState);
    if ( getIntent().hasExtra(BookConstant.BOOK) )
      mBook = (Book) getIntent().getExtras().getParcelable(BookConstant.BOOK);
  }
  
  @Override
  public void onNewIntent(Intent intent)
  {
    if ( getIntent().hasExtra(BookConstant.BOOK) )
      mBook = (Book) getIntent().getExtras().getParcelable(BookConstant.BOOK);
  }
  
  /* override this method to display option menu */
  @Override
  public boolean onCreateOptionsMenu(Menu menu)
  {
    MenuInflater menuInflater = getMenuInflater();
    menuInflater.inflate(R.menu.menu, menu); /* inflate menu from xml file into menu of this activity */
    return true;
  }
  
  /* handling on click of action bar items */
  @Override
  public boolean onOptionsItemSelected(MenuItem menuItem)
  {
    switch (menuItem.getItemId()) {
      case android.R.id.home: /* return home */
        Intent returnHome = new Intent();
        /* set this flag to do not create new instance of parent activity if it exists */
        returnHome.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        returnHome.setAction(BookConstant.RETURN_HOME_INTENT);
        startActivity(returnHome);
        break;
      case R.id.editBook: 
        Intent editBook = new Intent();
        editBook.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        editBook.setAction(BookConstant.EDIT_BOOK_INTENT);
        editBook.putExtra(BookConstant.BOOK, mBook );
        startActivity(editBook);
        break;
      case R.id.addBook: 
        Intent addBook = new Intent();
        addBook.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        addBook.setAction(BookConstant.ADD_BOOK_INTENT);
        addBook.putExtra(BookConstant.BOOK, mBook );
        startActivity(addBook);
        break;
    }
    return true;
  }
}

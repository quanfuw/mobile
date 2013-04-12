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
package com.exoplatform.session8tu.ui;

import com.exoplatform.session8tu.BookAdapter;
import com.exoplatform.session8tu.R;
import com.exoplatform.session8tu.datasource.BookDAO;
import com.exoplatform.session8tu.domain.Book;
import com.exoplatform.session8tu.util.BookConstant;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

/**
 * screen to list all books 
 * 
 * @author Created by The eXo Platform SAS
 * <br/>Anh-Tu Nguyen
 * <br/><a href="mailto:tuna@exoplatform.com">tuna@exoplatform.com<a/>
 * <br/>Nov 20, 2012  
 */
public class ListBookActivity extends ListActivity
{
  private static final String TAG = "ListBookActivity";
  
  private BookDAO mBookDAO;
  
  private BookAdapter mBookAdapter;
  
  @Override
  public void onCreate(Bundle savedInstance)
  {
    Log.i(TAG, "onCreate");
    
    super.onCreate(savedInstance);
    mBookDAO = new BookDAO(this);
    /* uses a single BookAdapter */
    mBookAdapter = new BookAdapter(this, mBookDAO.getAllBooks());
    setListAdapter(mBookAdapter);
    
    /* if no book found, display a message */
    if (mBookDAO.getAllBooks().size() == 0) {
      Toast toast = Toast.makeText(this, "add a book first", Toast.LENGTH_SHORT);
      toast.show();
    }
  }
  
  /* override this method to display option menu */
  @Override
  public boolean onCreateOptionsMenu(Menu menu)
  {
    MenuInflater menuInflater = getMenuInflater();
    menuInflater.inflate(R.menu.menu, menu); /* inflate menu from xml file into menu of this activity */
    menu.findItem(R.id.editBook).setEnabled(false); /* do not allow to edit book */
    return true;
  }
  
  /* handle user click on action menu */
  @Override
  public boolean onOptionsItemSelected(MenuItem menuItem)
  {
    switch(menuItem.getItemId()) 
    {
      case android.R.id.home: /* user clicks on Home icon of action bar */ 
        /* do not do anything since this is home already */ 
        break;
      case R.id.searchBook:
        break;
      case R.id.addBook:
        Intent addBook = new Intent();
        addBook.setAction(BookConstant.ADD_BOOK_INTENT);
        startActivity(addBook);
        break;
      default: 
        break;
    }
    return true;
  }
  
  /**
   * handles onClick on list item, redirects user to BookDetailActivity
   */
  @Override
  public void onListItemClick(ListView listView, View view, int position, long id)
  {
    super.onListItemClick(listView, view, position, id);
    Book aBook = (Book) getListAdapter().getItem(position);
    Intent showBookDetail = new Intent();
    showBookDetail.setAction(BookConstant.DISPLAY_BOOK_INTENT);
    showBookDetail.putExtra(BookConstant.BOOK, aBook );
    startActivity(showBookDetail);
  }
  
  @Override
  public void onNewIntent(Intent intent)
  {
    Log.i(TAG, "receives intent: " + intent.getAction());
    /* uses a single BookAdapter */
    mBookAdapter = new BookAdapter(this, mBookDAO.getAllBooks());
    /* update ui with new list of books - have to set new adapter */
    setListAdapter(mBookAdapter);
  }
  
  @Override
  public void onDestroy()
  {
	  Log.i(TAG, "onDestroy");
	  super.onDestroy();
  }
}

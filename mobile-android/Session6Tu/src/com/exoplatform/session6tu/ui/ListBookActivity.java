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
package com.exoplatform.session6tu.ui;

import com.exoplatform.session6tu.BookAdapter;
import com.exoplatform.session6tu.datasource.BookDAO;
import com.exoplatform.session6tu.domain.Book;

import android.app.ActionBar;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

/**
 * an activity that list all books 
 * 
 * @author Created by The eXo Platform SAS
 * <br/>Anh-Tu Nguyen
 * <br/><a href="mailto:tuna@exoplatform.com">tuna@exoplatform.com<a/>
 * <br/>Nov 20, 2012  
 */
public class ListBookActivity extends ListActivity
{
  private static final String TAG = "ListBookActivity";
  
  public static final String BOOK_NAME = "bookName";
  
  public static final String BOOK_ID   = "bookId";
  
  private BookDAO mBookDAO;
  
  @Override
  public void onCreate(Bundle savedInstance)
  {
    super.onCreate(savedInstance);
    mBookDAO = new BookDAO(this);
    mBookDAO.open();
    setListAdapter(new BookAdapter(this, mBookDAO.getAllBooks()));
    
    //final ActionBar actionBar = getActionBar();
    //actionBar.show();
  }
  
  @Override
  public void onListItemClick(ListView listView, View view, int position, long id)
  {
    super.onListItemClick(listView, view, position, id);
    Object o = getListAdapter().getItem(position);
    Intent showBookDetail = new Intent();
    showBookDetail.setAction("com.exoplatform.intent.showBookDetail");
    showBookDetail.putExtra(BOOK_NAME, ((Book)o).getName() );
    showBookDetail.putExtra(BOOK_ID, ((Book)o ).getId());
    startActivity(showBookDetail);
  }
  
}

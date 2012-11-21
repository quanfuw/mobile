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

import com.exoplatform.session6tu.R;
import com.exoplatform.session6tu.datasource.BookDAO;
import com.exoplatform.session6tu.domain.Book;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
public class EditBookActivity extends Activity
{
  private Book mBook;
  
  @Override
  public void onCreate(Bundle savedState)
  {
    super.onCreate(savedState);
    
    setContentView(R.layout.edit_book);
    
    EditText updateBookName = (EditText) findViewById(R.id.updateBookName);
    mBook = new Book( getIntent().getLongExtra(ListBookActivity.BOOK_ID, 0)
                     , getIntent().getStringExtra(ListBookActivity.BOOK_NAME));
    updateBookName.setText( mBook.getName() );
  }
  
  
  @Override
  public boolean onCreateOptionsMenu(Menu menu)
  {
    MenuInflater menuInflater = getMenuInflater();
    menuInflater.inflate(R.menu.menu, menu); /* inflate menu from xml file into menu of this activity */
    menu.findItem(R.id.editBook).setEnabled(false); /* do not allow to edit book */
    return true;
  }
  
  @Override
  public boolean onOptionsItemSelected(MenuItem menuItem)
  {
    switch (menuItem.getItemId()) {
      case android.R.id.home: /* return home */
        Intent returnHome = new Intent();
        /* set this flag to do not create new instance of parent activity if it exists */
        returnHome.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        returnHome.setAction("com.exoplatform.intent.returnHome");
        startActivity(returnHome);
        break;
      case R.id.addBook: 
        Intent addBook = new Intent();
        addBook.setAction("com.exoplatform.intent.addBook");
        addBook.putExtra(ListBookActivity.BOOK_NAME, mBook.getName());
        addBook.putExtra(ListBookActivity.BOOK_ID, mBook.getId());
        startActivity(addBook);
        break;
    }
    return true;
  }
  
  /**
   * update the book into SQLite
   * 
   * */
  public void updateBookClick(View view)
  {
    BookDAO bookDAO = new BookDAO(this);
    EditText editBook = (EditText) findViewById(R.id.updateBookName);
    mBook.setName( editBook.getText().toString() );
    bookDAO.updateBook( mBook );
    Toast toast = Toast.makeText(this, "successful", Toast.LENGTH_SHORT);
    toast.show();
    
    Intent showBookDetail = new Intent();
    showBookDetail.setAction("com.exoplatform.intent.showBookDetail");
    showBookDetail.putExtra(ListBookActivity.BOOK_NAME, mBook.getName() );
    showBookDetail.putExtra(ListBookActivity.BOOK_ID, mBook.getId());
    startActivity(showBookDetail);
  }
}

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
package com.exoplatform.session8tu;

import java.util.List;

import com.exoplatform.session8tu.R;
import com.exoplatform.session8tu.domain.Book;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * adapter to list book
 * 
 * @author Created by The eXo Platform SAS
 * <br/>Anh-Tu Nguyen
 * <br/><a href="mailto:tuna@exoplatform.com">tuna@exoplatform.com<a/>
 * <br/>Nov 20, 2012  
 */
public class BookAdapter extends ArrayAdapter<Book>
{
  private List<Book> mBooks;
  private final Activity mContext;
  
  public BookAdapter(Activity context, List<Book> books)
  {
    super(context, R.layout.book_list_item, books);
    mBooks = books;
    mContext = context;
  }
  
  public void setBooks(List<Book> books)
  {
    mBooks = books;
  }
  
  static class ViewHolder 
  {
    protected TextView text;
  }
  
  public Book getItem(int position)
  {
    return mBooks.get(position);
  }
  
  @Override
  public View getView(int position, View convertView, ViewGroup parent)
  {
    ViewHolder viewHolder;
    if (convertView == null) /* convertView null, create a new View for item */
    {
      LayoutInflater inflater = mContext.getLayoutInflater();
      convertView = inflater.inflate(R.layout.book_list_item, null);
      viewHolder = new ViewHolder();
      viewHolder.text = (TextView) convertView.findViewById(R.id.bookName);
      convertView.setTag(viewHolder); /* stores viewHolder */
    }
    else viewHolder = (ViewHolder) convertView.getTag();
    
    viewHolder.text.setText(getItem(position).getName());
    return convertView;
  }
}

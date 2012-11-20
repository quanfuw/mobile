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
package com.exoplatform.session5tu;

import java.util.List;

import com.exoplatform.session5tu.domain.Contact;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * adapter to contact 
 * 
 * @author Created by The eXo Platform SAS
 * <br/>Anh-Tu Nguyen
 * <br/><a href="mailto:tuna@exoplatform.com">tuna@exoplatform.com<a/>
 * <br/>Nov 12, 2012  
 */
public class ContactAdapter extends ArrayAdapter<Contact>
{
  private final List<Contact> mContactList;
  private final Activity mContext;
  
  public ContactAdapter(Activity context, List<Contact> contacts)
  {
    super(context, R.layout.contact_list_item, contacts);
    mContactList = contacts;
    mContext = context;
  }
  
  /** 
   * saves text and contact to inner class to avoid using findViewById 
   * 
   * */
  static class ViewHolder
  {
    protected TextView text = null;
    protected ImageView image = null;
  }
  
  @Override 
  public Contact getItem(int position)
  {
    return mContactList.get(position);
  }
  
  @Override
  public View getView(int position, View convertView, ViewGroup parent)
  {
    ViewHolder viewHolder;
    if (convertView == null) /* convertView null, create a new View for item */
    {
      LayoutInflater inflater = mContext.getLayoutInflater();
      convertView = inflater.inflate(R.layout.contact_list_item, null);
      viewHolder = new ViewHolder();
      viewHolder.text = (TextView) convertView.findViewById(R.id.txtDisplayName);
      viewHolder.image = (ImageView) convertView.findViewById(R.id.contactPhoto);
      convertView.setTag(viewHolder); /* stores viewHolder */
    }
    else viewHolder = (ViewHolder) convertView.getTag();
    
    viewHolder.text.setText(getItem(position).getDisplayName());
    if (getItem(position).getPhotoUri() != null)
      viewHolder.image.setImageURI(getItem(position).getPhotoUri());
    else viewHolder.image.setImageResource(R.drawable.exo_icon);
    return convertView;
  }
}

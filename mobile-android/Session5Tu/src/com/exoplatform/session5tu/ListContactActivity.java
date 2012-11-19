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

import java.util.ArrayList;
import java.util.List;

import com.exoplatform.session5tu.domain.Contact;

import android.app.ListActivity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

/**
 * screen to browse phone contacts
 * 
 * @author Created by The eXo Platform SAS
 * <br/>Anh-Tu Nguyen
 * <br/><a href="mailto:tuna@exoplatform.com">tuna@exoplatform.com<a/>
 * <br/>Nov 12, 2012  
 */
public class ListContactActivity extends ListActivity
{
  @Override
  public void onCreate(Bundle savedInstance)
  {
    super.onCreate(savedInstance);
    setListAdapter(new ContactAdapter(this, getContacts()));
  }
  
  @Override
  protected void onListItemClick(ListView listView, View view, int position, long id)
  {
    super.onListItemClick(listView, view, position, id);
    Object o = getListAdapter().getItem(position);
    Toast.makeText(this, ((Contact)o ).getDisplayName() , Toast.LENGTH_SHORT).show();
  }
  
  private List<Contact> getContacts()
  {
    List<Contact> contactList = new ArrayList<Contact>();
    Uri uri = ContactsContract.Contacts.CONTENT_URI;
    ContentResolver resolver = getContentResolver();
    
    /* sort contact in ascending ASCII order */
    String sortOrder = ContactsContract.Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC";
    Cursor cursor = resolver.query(uri, null, null, null, sortOrder);
    
    if (cursor.getCount() > 0)
    {
      while (cursor.moveToNext())
      {
        Contact aContact = new Contact( cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID)), 
                                        cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));
        contactList.add(aContact);
      }
    }
    cursor.close();
    return contactList;
  }
}

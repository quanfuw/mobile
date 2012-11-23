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
package com.exoplatform.session7tu.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * represents a book, implementing parcelable to be passed in Intent
 * 
 * @author Created by The eXo Platform SAS
 * <br/>Anh-Tu Nguyen
 * <br/><a href="mailto:tuna@exoplatform.com">tuna@exoplatform.com<a/>
 * <br/>Nov 20, 2012  
 */
public class Book implements Parcelable
{
  private long id;
  private String name;
  private String urlBookCover;
  
  public Book(long id, String name, String url)
  {
    this.id = id;
    this.name = name;
    this.urlBookCover = url;
  }

  public Book(String name, String url)
  {
    this.name = name;
    this.urlBookCover = url;
  }
  
  /**
  * constructor to use when re-constructing object from a parcel
  *
  * @param in a parcel from which to read this object
  */
  public Book(Parcel parcel) {
    readFromParcel(parcel);
  }
  
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getUrlBookCover() {
    return urlBookCover;
  }

  public void setUrlBookCover(String urlBookCover) {
    this.urlBookCover = urlBookCover;
  }
  
  @Override
  public void writeToParcel(Parcel parcel, int flags) 
  {
    /* write each field into the parcel */
    parcel.writeLong(id);
    parcel.writeString(name);
    parcel.writeString(urlBookCover);
  }
 
  /**
   * called from the constructor to create this
   * object from a parcel.
   *
   * @param parcel parcel from which to re-create object
   */
  private void readFromParcel(Parcel parcel) 
  {
    /* We just need to read back each field in the order that it was
    written to the parcel */
    id = parcel.readLong();
    name = parcel.readString();
    urlBookCover = parcel.readString();
  }
  
  @Override
  public int describeContents() {
    return 0;
  }
  
  public static final Parcelable.Creator<Book> CREATOR = new Parcelable.Creator<Book>() 
  {
    public Book createFromParcel(Parcel parcel) 
    {
      return new Book(parcel);
    }
 
    public Book[] newArray(int size) 
    {
      return new Book[size];
    }
  };
}

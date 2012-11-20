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
package com.exoplatform.session5tu.domain;

import android.net.Uri;

/**
 * represents contact in android contact
 * 
 * @author Created by The eXo Platform SAS
 * <br/>Anh-Tu Nguyen
 * <br/><a href="mailto:tuna@exoplatform.com">tuna@exoplatform.com<a/>
 * <br/>Nov 1, 2012  
 */
public class Contact 
{
  private String id;
  private String name;
  private Uri photoUri;
  
  public Contact(String id, String name, Uri uri) 
  {
    this.id = id;
    this.name = name;
    this.setPhotoUri(uri);
  }
  
  public String getId() { return id; }
  public void setId(String id) { this.id = id; }
  public String getDisplayName() { return name; }
  public void setDisplayName(String name) { this.name = name; }

  public Uri getPhotoUri() {
    return photoUri;
  }

  public void setPhotoUri(Uri photoUri) {
    this.photoUri = photoUri;
  }
}

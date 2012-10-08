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
package com.example.session8tuan.bookreader;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * Created by The eXo Platform SAS
 * Author : eXoPlatform
 *          exo@exoplatform.com
 * Oct 8, 2012  
 */
public class ImageAdapter extends BaseAdapter {
  private Context mContext;

  public ImageAdapter(Context c) {
      mContext = c;
  }

  public int getCount() {
      return mThumbIds.length;
  }

  public Object getItem(int position) {
      return null;
  }

  public long getItemId(int position) {
      return 0;
  }

  // create a new ImageView for each item referenced by the Adapter
  public View getView(int position, View convertView, ViewGroup parent) {
      ImageView imageView;
      if (convertView == null) {  // if it's not recycled, initialize some attributes
          imageView = new ImageView(mContext);
          imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
          imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
          imageView.setPadding(8, 8, 8, 8);
      } else {
          imageView = (ImageView) convertView;
      }

      imageView.setImageResource(mThumbIds[position]);
      return imageView;
  }

  // references to our images
  private Integer[] mThumbIds = {
          R.drawable.myimate, R.drawable.myimate,
          R.drawable.myimate, R.drawable.myimate,
          R.drawable.myimate, R.drawable.myimate,
          R.drawable.myimate, R.drawable.myimate,
          R.drawable.myimate, R.drawable.myimate,
          R.drawable.myimate, R.drawable.myimate,
          R.drawable.myimate, R.drawable.myimate,
          R.drawable.myimate, R.drawable.myimate
         
  };
}
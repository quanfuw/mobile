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

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import com.exoplatform.session7tu.BookPhotoManager;
import com.exoplatform.session7tu.R;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.LruCache;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * an activity to display book detail
 * 
 * @author Created by The eXo Platform SAS
 * <br/>Anh-Tu Nguyen
 * <br/><a href="mailto:tuna@exoplatform.com">tuna@exoplatform.com<a/>
 * <br/>Nov 20, 2012  
 */
public class BookDetailActivity extends BaseActivity
{
  private static final String TAG = "BookDetailActivity";
          
  @Override
  public void onCreate(Bundle savedState)
  {
    Log.i(TAG, "onCreate");
    super.onCreate(savedState);
    
    setContentView(R.layout.book_detail);
    TextView bookName = (TextView) findViewById(R.id.name);
    bookName.setText(mBook.getName());
    
    if (mBook.getUrlBookCover() != null)
    {
      ImageView coverPhoto = (ImageView) findViewById(R.id.coverPhoto);
      new LoadImageTask( mBook.getUrlBookCover() ).execute(coverPhoto);
    }  
  }
  
  @Override
  public void onNewIntent(Intent intent)
  {
    Log.i(TAG, "onNewIntent");
    super.onNewIntent(intent);
    TextView bookName = (TextView) findViewById(R.id.name);
    bookName.setText(mBook.getName());
  }
    
  @Override
  public boolean onCreateOptionsMenu(Menu menu)
  {
    super.onCreateOptionsMenu(menu);    
    return true;
  }  
  
  /**
   * async task to load image from the internet
   *
   */
  private class LoadImageTask extends AsyncTask<ImageView, Void, ImageView> 
  {
    private static final String TAG = "LoadImageTask";
    
    private String url = null;
    
    private Bitmap cover = null;
    
    private BookPhotoManager mPhotoManager;
    
    public LoadImageTask(String url)
    {
      Log.i(TAG, "constructor");  
      this.url = url;
      mPhotoManager = BookPhotoManager.createInstance(getApplicationContext());
    }
    
    @Override
    protected void onPreExecute() 
    { 
      Log.i(TAG, "onPreExecute");  
    }

    @Override
    protected ImageView doInBackground(ImageView... params) 
    {
      Log.i(TAG, "doInBackground");
      /* check if image is in the cache then set cover */
      if (mPhotoManager.getBitmapFromMemoryCache( Long.toString( mBook.getId() )) != null)
      {
        Log.i(TAG, "cover " + mBook.getId() + " is in cache ");    
        cover = mPhotoManager.getBitmapFromMemoryCache( Long.toString( mBook.getId() ));
    	return params[0];
      }
    	
      Log.i(TAG, "cover not in the cache " + mBook.getId());
      try 
      {
        Bitmap image = BitmapFactory.decodeStream( (InputStream) new URL(url).getContent());
        cover = Bitmap.createScaledBitmap(image, params[0].getWidth(), params[0].getHeight(), true);
      } 
      catch (MalformedURLException e) 
      {
        e.printStackTrace();
      } 
      catch (IOException e) 
      {
        e.printStackTrace();
      }
      return params[0]; /* should always return params[0] which is the imageView for onPostExecute */
    }

    @Override
    public void onPostExecute(ImageView imageView) 
    {
      Log.i(TAG, "onPostExecute");
      if (cover != null) { 
        imageView.setImageBitmap(cover);
        mPhotoManager.addBitmapToMemoryCache( Long.toString(mBook.getId()), cover);
      }
    }
  }

}

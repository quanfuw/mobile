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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import com.exoplatform.session7tu.R;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
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
      
  /* disk cache config */
  private DiskLruCache mDiskLruCache;
  private final Object mDiskCacheLock = new Object();
  private boolean mDiskCacheStarting = true;
  private static final int DISK_CACHE_SIZE = 1024 * 1024 * 30; // 30MB
  private static final String DISK_CACHE_SUBDIR = "thumbnails";
  
  @Override
  public void onCreate(Bundle savedState)
  {
    Log.i(TAG, "onCreate");
    super.onCreate(savedState);
    
    File cacheDir = getDiskCacheDir(this, DISK_CACHE_SUBDIR);
    new InitDiskCacheTask().execute(cacheDir);
    
    setContentView(R.layout.book_detail);
    TextView bookName = (TextView) findViewById(R.id.name);
    bookName.setText(mBook.getName());
    
    if (mBook.getUrlBookCover() != null)
    {
      ImageView coverPhoto = (ImageView) findViewById(R.id.coverPhoto);
      new LoadImageTask( mBook.getUrlBookCover() ).execute(coverPhoto);
      addBitmapToCache( Long.toBinaryString( mBook.getId() ), coverPhoto);
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
    
    public LoadImageTask(String url)
    {
      Log.i(TAG, "constructor");  
      this.url = url;
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
      try 
      {
        cover = BitmapFactory.decodeStream( (InputStream) new URL(url).getContent());
      } 
      catch (MalformedURLException e) 
      {
        e.printStackTrace();
      } 
      catch (IOException e) 
      {
        e.printStackTrace();
      }
      return params[0];
    }

    @Override
    public void onPostExecute(ImageView imageView) 
    {
      Log.i(TAG, "onPostExecute");
      if (cover != null) imageView.setImageBitmap(cover);
    }
  }
  
  
  //Creates a unique subdirectory of the designated app cache directory. Tries to use external
  //but if not mounted, falls back on internal storage.
  public static File getDiskCacheDir(Context context, String uniqueName) 
  {
    // Check if media is mounted or storage is built-in, if so, try and use external cache dir
    // otherwise use internal cache dir
    final String cachePath = Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) 
        || ! Environment.isExternalStorageRemovable() ? context.getExternalCacheDir().getPath() 
        : context.getCacheDir().getPath();

    return new File(cachePath + File.separator + uniqueName);
  }
  
  class InitDiskCacheTask extends AsyncTask<File, Void, Void> {
    @Override
    protected Void doInBackground(File... params) {
        synchronized (mDiskCacheLock) {
            File cacheDir = params[0];
            mDiskLruCache = DiskLruCache.open(cacheDir, DISK_CACHE_SIZE);
            mDiskCacheStarting = false; // Finished initialization
            mDiskCacheLock.notifyAll(); // Wake any waiting threads
        }
        return null;
    }
    
    public void addBitmapToCache(String key, Bitmap bitmap) {
      // Add to memory cache as before
      if (getBitmapFromMemCache(key) == null) {
          mMemoryCache.put(key, bitmap);
      }

      // Also add to disk cache
      synchronized (mDiskCacheLock) {
          if (mDiskLruCache != null && mDiskLruCache.get(key) == null) {
              mDiskLruCache.put(key, bitmap);
          }
      }
  }
}

}

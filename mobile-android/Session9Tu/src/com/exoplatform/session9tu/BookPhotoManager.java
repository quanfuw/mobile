package com.exoplatform.session9tu;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.LruCache;

/**
 * simple class that maintains a cache of photo
 * implemented as a singleton 
 * 
 * @author Created by The eXo Platform SAS
 * <br/>Anh-Tu Nguyen
 * <br/><a href="mailto:tuna@exoplatform.com">tuna@exoplatform.com<a/>
 * <br/>Nov 22, 2012  
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
public class BookPhotoManager 
{
  private LruCache<String, Bitmap> mMemoryCache;
  
  private static BookPhotoManager instance;
  
  public static BookPhotoManager createInstance(Context context)
  {
	if (instance != null) return instance;
	instance = createNewInstance(context);
	return instance;
  }
  
  public static synchronized BookPhotoManager createNewInstance(Context context)
  {
	return new BookPhotoManager(context);
  }
  
  /**
   * initialize the memory cache
   *  
   **/
  @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
  private BookPhotoManager(Context context)
  {
	final int memClass = ((ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE))
				.getMemoryClass(); 
	/* use 1/8th of the available memory for this memory cache */
    final int cacheSize = 1024 * 1024 * memClass / 8;
		    
    mMemoryCache = new LruCache<String, Bitmap>(cacheSize) 
    {
	  @Override
	  public int sizeOf(String key, Bitmap bitmap)
	  {
		return bitmap.getByteCount();
	  }
    };
    
  }
  
  public void addBitmapToMemoryCache(String key, Bitmap bitmap)
  {
    if (getBitmapFromMemoryCache(key) == null )
	  mMemoryCache.put(key, bitmap);
  }
  
  public Bitmap getBitmapFromMemoryCache(String key)
  {
	return mMemoryCache.get(key);
  }
}

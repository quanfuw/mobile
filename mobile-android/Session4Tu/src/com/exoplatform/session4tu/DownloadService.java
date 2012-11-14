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
package com.exoplatform.session4tu;

import android.app.IntentService;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

/**
* @author Created by The eXo Platform SAS
 * <br/>Anh-Tu Nguyen
 * <br/><a href="mailto:tuna@exoplatform.com">tuna@exoplatform.com<a/>
 * Nov 13, 2012  
 */
public class DownloadService extends IntentService
{
  private static final String TAG = "DownloadService";

  public DownloadService() {
    super("DownloadService");
  }
  
  /**
   * handles the downloading task
   * 
   */
  @Override
  protected void onHandleIntent(Intent intent) {
    Log.i(TAG, "onHandleIntent - intent received: " + intent.getAction());
    
    String url = intent.getStringExtra("url");
    Log.i(TAG, "url " + url);
    Uri urlPath = Uri.parse(url);
    String fileName = urlPath.getLastPathSegment();
    Log.i(TAG, "filename " + fileName);
    
    File output = new File(Environment.getExternalStorageDirectory(),
            fileName);
    if (output.exists()) {
      output.delete();
    }
    
    InputStream stream = null;
    FileOutputStream fos = null;
    try {

      URL url = new URL(urlPath);
      stream = url.openConnection().getInputStream();
      InputStreamReader reader = new InputStreamReader(stream);
      fos = new FileOutputStream(output.getPath());
      int next = -1;
      while ((next = reader.read()) != -1) {
        fos.write(next);
      }
      // Sucessful finished
      result = Activity.RESULT_OK;

    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (stream != null) {
        try {
          stream.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      if (fos != null) {
        try {
          fos.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    
    
    Intent downloadBroadcast = new Intent();
    downloadBroadcast.setAction("com.exoplatform.intent.DownloadBroadcast");
    Log.i(TAG, "broadcast intent");
    sendBroadcast(downloadBroadcast);
  }
  
}

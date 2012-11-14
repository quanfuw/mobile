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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

import android.app.IntentService;
import android.content.Intent;
import android.os.Environment;

/**
 * handles the downloading task
 * 
 * @author Created by The eXo Platform SAS
 * <br/>Anh-Tu Nguyen
 * <br/><a href="mailto:tuna@exoplatform.com">tuna@exoplatform.com<a/>
 * Nov 13, 2012  
 */
public class DownloadService extends IntentService
{    
  public DownloadService() 
  {
    super("DownloadService");
  }
  
  /**
   * handles the downloading task
   * 
   */
  @Override
  protected void onHandleIntent(Intent intent) 
  {    
    String urlPath = "http://www." + intent.getStringExtra(HomeActivity.URL);
    
    downloadFile(urlPath, HomeActivity.SAVED_FILE);
    
    Intent downloadBroadcast = new Intent();
    downloadBroadcast.setAction("com.exoplatform.intent.DownloadBroadcast");
    downloadBroadcast.putExtra(HomeActivity.URL, urlPath);
    downloadBroadcast.putExtra(HomeActivity.FILE_NAME, HomeActivity.SAVED_FILE);
    /* sends the result back to HomeActivity using broadcast intent */
    sendBroadcast(downloadBroadcast);
  }
  
  private void downloadFile(String fromUrl, String saveToFile)
  {
    File output = new File(Environment.getExternalStorageDirectory(), saveToFile);
    if (output.exists()) output.delete();

    BufferedInputStream stream = null;
    BufferedOutputStream fos = null;
    try 
    {
      URL url = new URL(fromUrl);
      stream = new BufferedInputStream( url.openStream() );
      fos = new BufferedOutputStream(new FileOutputStream( output.getPath() ));
                     
      byte bdata[] = new byte[1024];
      int count;
      while ((count = stream.read(bdata)) != -1) 
        /* read 1024 byte each time and write into output file */
        fos.write(bdata, 0, count);
    } 
    catch (Exception e) {
      e.printStackTrace();
    } 
    finally {
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
  }
  
}

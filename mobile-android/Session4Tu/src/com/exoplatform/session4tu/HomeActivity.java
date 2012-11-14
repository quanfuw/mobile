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

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * home screen allows to enter url of file to be downloaded
 * 
 * @author Created by The eXo Platform SAS
 * <br/>Anh-Tu Nguyen
 * <br/><a href="mailto:tuna@exoplatform.com">tuna@exoplatform.com<a/>
 * Nov 13, 2012  
 */
public class HomeActivity extends Activity
{  
  public static final String URL = "url";
  
  public static final String FILE_NAME = "downloadFile";
  
  public static final String SAVED_FILE = "downloadFile";

  
  @Override
  public void onCreate(Bundle bundle)
  {
    super.onCreate(bundle);
    
    IntentFilter filter = new IntentFilter();
    filter.addAction("com.exoplatform.intent.DownloadBroadcast");
    registerReceiver(downloadReceiver, filter);
    setContentView(R.layout.home_activity);
  }
  
  /**
   * invoked when click on "start download" button
   * @param view
   */
  public void onClickDownLoad(View view)
  {
    TextView downloadResult = (TextView) findViewById(R.id.downloadResult);
    downloadResult.setText("downloading.. please wait");
    downloadResult.setVisibility(View.VISIBLE);
    
    Intent downloadIntent = new Intent();
    downloadIntent.setAction("com.exoplatform.intent.Download");
    EditText editUrl = (EditText) findViewById(R.id.editUrl);
    downloadIntent.putExtra(URL, editUrl.getText().toString());
    startService(downloadIntent);
  }
  
  /**
   * broadcast receiver to catch the notification of download task
   * 
   */
  public BroadcastReceiver downloadReceiver = new BroadcastReceiver() 
  {    
    @Override
    public void onReceive(Context context, Intent intent) {
      TextView downloadResult = (TextView) findViewById(R.id.downloadResult);
      downloadResult.setText("download successfully from " + intent.getStringExtra(URL) 
                             + " into file " + intent.getStringExtra(FILE_NAME));
      downloadResult.setVisibility(View.VISIBLE);
    }
  };
  
  @Override
  public void onStop() 
  {
    super.onStop();
    unregisterReceiver(downloadReceiver);
  }
}

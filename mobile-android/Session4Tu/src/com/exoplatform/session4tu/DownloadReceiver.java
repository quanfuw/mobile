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

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * receives 
 * 
 * @author Created by The eXo Platform SAS
 * <br/>Anh-Tu Nguyen
 * <br/><a href="mailto:tuna@exoplatform.com">tuna@exoplatform.com<a/>
 * Nov 13, 2012  
 */
public class DownloadReceiver extends BroadcastReceiver
{
  private static final String TAG = "DownloadReceiver";
  
  @Override
  public void onReceive(Context context, Intent intent) {
    Log.i(TAG, "onReceive - intent: " + intent.getAction());    
  }
  
}

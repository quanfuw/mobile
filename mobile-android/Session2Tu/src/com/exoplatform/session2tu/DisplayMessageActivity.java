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
package com.exoplatform.session2tu;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by The eXo Platform SAS
 * Author : Anh-Tu Nguyen
 *          tuna@exoplatform.com
 * Nov 7, 2012  
 */
public class DisplayMessageActivity extends Activity
{  
  private static final String TAG = "DisplayMessageActivity";
  
  @Override
  public void onCreate(Bundle bundle)
  {
    super.onCreate(bundle);
    Log.i(TAG, "onCreate");
    LinearLayout layout = new LinearLayout(this);
    layout.setOrientation(LinearLayout.VERTICAL);
    TextView text = new TextView(this);
    text.setText("hello");
    text.setGravity(Gravity.CENTER_HORIZONTAL);  
    layout.addView(text);
    setContentView(layout);
  }
  
  @Override
  public void onResume()
  {
    super.onResume();
    Log.i(TAG, "onResume");
  }
  
  @Override
  public void onPause()
  {
    super.onPause();
    Log.i(TAG, "onPause");
  }
  
  @Override 
  public void onDestroy()
  {
    super.onDestroy();
    Log.i(TAG, "onDestroy");
  }
}

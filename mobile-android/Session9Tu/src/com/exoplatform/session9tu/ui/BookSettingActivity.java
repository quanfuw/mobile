/*
 * Copyright (C) 2003-2013 eXo Platform SAS.
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
package com.exoplatform.session9tu.ui;

import com.exoplatform.session9tu.R;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.View;
import android.widget.Button;

/**
 * screen for book settings
 * 
 * Created by The eXo Platform SAS
 * Author : Anh-Tu Nguyen
 *          tuna@exoplatform.com
 * Apr 18, 2013  
 */
public class BookSettingActivity extends PreferenceActivity implements View.OnClickListener {

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    
    /**
     * instead of setting view from xml.settings, we use normal layout
     * R.xml.setting will override ListView in layout
     * 
     * normal layout allows to add button
     */
    addPreferencesFromResource(R.xml.settings);
    setContentView(R.layout.book_setting);
    
    /* add listener for Save button */
    ((Button) findViewById(R.id.saveSettingButton)).setOnClickListener(this);
  }
  

  @Override
  public void onClick(View v) {
    /* pass in this activity instead of getApplicationContext() */
    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
   
    // set title
    alertDialogBuilder.setTitle("save settings?");
   
    // set dialog message
    alertDialogBuilder
        .setMessage("Save settings and exit?")
        .setCancelable(false)
        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
          
            public void onClick(DialogInterface dialog,int id) {
              // close current activity
              BookSettingActivity.this.finish();
            }
         })
         .setNegativeButton("No", new DialogInterface.OnClickListener() {
            
             public void onClick(DialogInterface dialog,int id) {
               // the dialog box and do nothing
               dialog.cancel();
             }
         });
   
    // create alert dialog
    AlertDialog alertDialog = alertDialogBuilder.create();
   
    // show it
    alertDialog.show();
  }
}

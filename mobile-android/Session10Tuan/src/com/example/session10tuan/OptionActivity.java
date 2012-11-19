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
package com.example.session10tuan;


import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;

/**
 * Created by The eXo Platform SAS
 * Author : eXoPlatform
 *          exo@exoplatform.com
 * Nov 14, 2012  
 */
public class OptionActivity extends PreferenceActivity implements OnSharedPreferenceChangeListener {
  
  @Override
  public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      addPreferencesFromResource(R.xml.preferences);
  }
  
  @SuppressWarnings("deprecation")
 @Override
   protected void onResume() {
       super.onResume();
       getPreferenceScreen().getSharedPreferences()
               .registerOnSharedPreferenceChangeListener(this);
   }

   @SuppressWarnings("deprecation")
 @Override
   protected void onPause() {
       super.onPause();
       getPreferenceScreen().getSharedPreferences()
               .unregisterOnSharedPreferenceChangeListener(this);
   }

 @SuppressWarnings("deprecation")
 @Override
 public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
   // Set summary to be the user-description for the selected value
   if (key.equals("pref_text") || key.equals("pref_list") || key.equals("pref_text_sub") || key.equals("pref_list_sub")) {
     Preference connectionPref = findPreference(key);
           connectionPref.setSummary(sharedPreferences.getString(key, ""));
   }
 }

}

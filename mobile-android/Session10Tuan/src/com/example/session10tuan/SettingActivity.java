package com.example.session10tuan;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RatingBar;
import android.widget.Toast;
import android.widget.ToggleButton;

public class SettingActivity extends Activity {
  
    private RatingBar rb ;
    private CheckBox cb ;
    private ToggleButton bt ;
    private Button savebt ;
    
    public final static String MY_PREF="my_preferences";
    public final static String MY_CHECKED="is_check";
    public final static String MY_RATE="my_rate";
    public final static String MY_STATUS="my_status";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        rb = (RatingBar)this.findViewById(R.id.ratingBar1);
        cb = (CheckBox)this.findViewById(R.id.checkBox1);
        bt = (ToggleButton)this.findViewById(R.id.toggleButton1);
        
       
        
    }
    
    
    public void savePreferences() {
     SharedPreferences pref = getSharedPreferences(MY_PREF, Context.MODE_PRIVATE) ;
     Editor e = pref.edit() ;
     e.putBoolean(MY_CHECKED, cb.isChecked());
     e.putFloat(MY_RATE, rb.getRating());
     e.putBoolean(MY_STATUS, bt.isChecked()) ;
     e.apply();
     
     clearState() ;
     
     Toast.makeText(this, "Save shered preferences success !", Toast.LENGTH_SHORT).show();
      
    }
    
    public void clearState() {
      
      rb.setRating(0);
      cb.setChecked(false);
      bt.setChecked(false) ;
    }
    
    public void loadPreferences() {
      SharedPreferences pref = getSharedPreferences(MY_PREF, Context.MODE_PRIVATE) ;
      if(pref == null ) {
        Toast.makeText(this, "No data in shered preferences  !", Toast.LENGTH_SHORT).show();
      } else { 
      
       cb.setChecked(pref.getBoolean(MY_CHECKED, false)) ;
       rb.setRating(pref.getFloat(MY_RATE,0));
       bt.setChecked(pref.getBoolean(MY_STATUS, false)) ;
       
      }
       
     }
    
    public void save(View v) {
      savePreferences() ;
       
    }
    
    public void load(View v) {
      loadPreferences() ;
       
    }

    
    
    
    

    
    
   
}

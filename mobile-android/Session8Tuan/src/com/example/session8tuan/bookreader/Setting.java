package com.example.session8tuan.bookreader;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Map;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

public class Setting extends Activity implements OnItemSelectedListener {


  Spinner spinner ;
  CheckBox isShowCover ;
  public static final String PREFS_NAME = "my_setting";
  public static final String VIEW_TYPE = "view_type";
  public static final String SHOW_COVER = "isShowCover";

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_setting);
    spinner = (Spinner) findViewById(R.id.viewtypes);
    spinner.setOnItemSelectedListener(this);
    isShowCover = (CheckBox) findViewById(R.id.checkBox1) ;


    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                                                                         R.array.view_types, android.R.layout.simple_spinner_item);
    // Specify the layout to use when the list of choices appears
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    // Apply the adapter to the spinner
    spinner.setAdapter(adapter);
    
    loadOption();


  }

  /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_setting, menu);
        return true;
    }
   */

  public void loadOption() {
    SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
    
    String viewType = settings.getString(VIEW_TYPE, null);
    boolean isShowCover = settings.getBoolean(SHOW_COVER, false) ;
    
    ArrayAdapter myAdap = (ArrayAdapter) spinner.getAdapter(); //cast to an ArrayAdapter

    int spinnerPosition = myAdap.getPosition(viewType);

    //set the default according to value
    spinner.setSelection(spinnerPosition);
    
    this.isShowCover.setChecked(isShowCover);
    
    
     
  }

  @Override
  public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

    String seleted = parent.getItemAtPosition(pos).toString();
    saveOption(seleted, this.isShowCover.isChecked());

  }

  public void onCheckboxClicked(View view) {
    // Is the view now checked?
    boolean checked = ((CheckBox)view).isChecked();
    saveOption(this.spinner.getSelectedItem().toString() ,checked) ;
  }

  public void saveOption(String selected, boolean isShowCover) {
    
    
    SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
    Editor edit = settings.edit();
    edit.putString(VIEW_TYPE,selected) ;
    edit.putBoolean(SHOW_COVER, isShowCover) ;
    edit.commit();
    
    saveToFile();
    Toast.makeText(this, "Setting saved! view type: " + selected + " is show cover :" + isShowCover, Toast.LENGTH_SHORT).show();

  }
  
  public void saveToFile () {
    
    
 // BEGIN EXAMPLE
    SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
    File myPath = new File(Environment.getExternalStorageDirectory().toString());
    File myFile = new File(myPath, "MySharedPreferences.dat");

    try
    {
        FileWriter fw = new FileWriter(myFile);
        PrintWriter pw = new PrintWriter(fw);

        Map<String,?> prefsMap = settings.getAll();

        for(Map.Entry<String,?> entry : prefsMap.entrySet())
        {
            pw.println(entry.getKey() + ": " + entry.getValue().toString());            
        }

        pw.close();
        fw.close();
    }
    catch (Exception e)
    {
        // what a terrible failure...
        Log.wtf(getClass().getName(), e.toString());
    }
  }

  @Override
  public void onNothingSelected(AdapterView<?> arg0) {
    // TODO Auto-generated method stub

  }
}

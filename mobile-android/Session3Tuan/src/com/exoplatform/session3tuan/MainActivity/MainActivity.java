package com.exoplatform.session3tuan.MainActivity;

import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	Button btn ;
	TextView text1 ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = (Button)findViewById(R.id.btnid) ;
        text1 = (TextView) findViewById(R.id.textView1) ;
		text1.setText("On Create ") ;
		
        btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent intent = new Intent(getBaseContext(),ViewActivity.class) ;
			
				startActivity(intent) ;
				
			}
		 
		}) ;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		//text1 = (TextView) findViewById(R.id.textView1) ;
		text1.setText("On Resume") ;
		
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}
	
	public void clickMouse(View v) {
		
		text1.setText(new Date().toGMTString()) ;
		
		//if("On click".equals(text1.getText()))text1.setText("On Resume") ;
		//else
		//if("On Resume".equals(text1.getText()))text1.setText("On click") ;
		
		//EditText edt = new EditText(getBaseContext()) ;
		
		
	}
    
}

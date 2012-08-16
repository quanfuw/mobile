package com.exoplatform.session3tuan.MainActivity;
import com.exoplatform.session3tuan.MainActivity.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
/**
 * 
 */

/**
 * @author leo
 *
 */
public class ViewActivity extends Activity {

	Button btn ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_activity);
		btn = (Button)findViewById(R.id.btnid2) ;
		
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 Intent intent = new Intent(getBaseContext(), MainActivity.class) ;
				 startActivity(intent);
			}
		
		});
	}

}

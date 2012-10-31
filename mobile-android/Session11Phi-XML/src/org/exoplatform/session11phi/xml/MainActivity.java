package org.exoplatform.session11phi.xml;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void start(View v) {
    	switch (v.getId()) {
    	case R.id.button_xml:
    		Intent x = new Intent(this, XMLActivity.class);
    		startActivity(x);
    		break;
    	case R.id.button_json:
    		Intent j = new Intent(this, JSONActivity.class);
    		startActivity(j);
    		break;
    	case R.id.button_binding:
    		
    		break;
    	}
    }
   
}

package com.exoplatform.session4tuan;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import android.widget.Toast;

public class ImagePrvivewActivity extends Activity {
	
	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState) ;
		setContentView(R.layout.image_preview) ;
		Intent intent = getIntent() ;
		String imageName = intent.getStringExtra(MainActivity.KEY_FILE_LOCATION);
		
		ImageView imv = (ImageView) findViewById(R.id.imageView1) ;
		imv.setImageBitmap(
        		BitmapFactory.decodeFile(imageName));
		imv.setContentDescription(imageName);
		
		Toast.makeText(this, imageName, Toast.LENGTH_LONG).show();
		
	};

}

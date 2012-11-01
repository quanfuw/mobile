package org.exoplatform.session11phi.cammedia;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

public class ViewPicActivity extends Activity {
	
	public static final String IMAGE = "IMAGE_DATA";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pic);
        
        Intent intent = getIntent();
        byte[] data = intent.getByteArrayExtra(IMAGE);
        
        if (data != null) {
        	ImageView imgView = (ImageView)findViewById(R.id.image_captured);
        	imgView.setImageBitmap(BitmapFactory.decodeByteArray(data, 0, data.length));
        }
    }
}

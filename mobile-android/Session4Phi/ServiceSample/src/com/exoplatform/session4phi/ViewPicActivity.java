package com.exoplatform.session4phi;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewPicActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pic);
        
        Intent intent = getIntent();
        String imageName = intent.getStringExtra(MainActivity.KEY_FILE_LOCATION);
        
        TextView imgName = (TextView)findViewById(R.id.image_name);
        imgName.setText(imageName);
        
        ImageView imgView = (ImageView)findViewById(R.id.image_view);
        imgView.setImageBitmap(
        		BitmapFactory.decodeFile(imageName));
        imgView.setContentDescription(imageName);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_view_pic, menu);
        return true;
    }
}

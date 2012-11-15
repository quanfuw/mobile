package com.example.session10tuan;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

public class ViewPicActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pic);
        
        Intent intent = getIntent();
        String imageName = intent.getStringExtra("FILE_NAME");
        
        ImageView imgView = (ImageView)findViewById(R.id.image_view);
        imgView.setImageBitmap(
        		BitmapFactory.decodeFile(imageName));
        imgView.setContentDescription(imageName);
    }
}

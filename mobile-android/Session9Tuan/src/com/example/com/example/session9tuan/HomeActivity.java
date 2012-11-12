package com.example.com.example.session9tuan;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HomeActivity extends Activity{
	

	 @Override
   public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_home);
   }


   public void RunTask(View v) {
     Intent i = new Intent(this, DownloadTask.class);
     startActivity(i);
   }
   
   public void RunProgress(View v) {
     Intent i = new Intent(this, DownloadProcess.class);
     startActivity(i);
   }
   
   public void RunThread(View v) {
     Intent i = new Intent(this, DownloadHandler.class);
     startActivity(i);
   }

}

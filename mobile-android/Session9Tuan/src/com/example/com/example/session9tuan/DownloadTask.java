package com.example.com.example.session9tuan;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;


import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.AsyncTask.Status;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;


public class DownloadTask extends Activity{
  private ProgressBar pBar;
  private Button btn;
  private ImageView imgView;
  private AsyncTask task;
  
  
  @Override
  public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_download_task);
      
      pBar = (ProgressBar)findViewById(R.id.progressBar1);
      pBar.setVisibility(View.INVISIBLE);
      
      btn = (Button)findViewById(R.id.button1);
      
      imgView = (ImageView)findViewById(R.id.imageView1);
      
  }
  
  @Override
  protected void onPause() {
    if (task != null) {
      task.cancel(true);
      Log.d("EXO_TAG", "*** Cancelling the task");
    }
    super.onPause();
  }

  
  
  public void download(View v) {
    if (task == null || task.getStatus() != Status.RUNNING) {
      task = new AsyncTask();
      try {
        // execute the task that download the image at the following URL
        task.execute(new URL("https://lh6.googleusercontent.com/-9Y8lOqdNaoE/UJUmEKE65NI/AAAAAAAAQ_s/BhPki36wSoM/s736/DSC_0006.JPG"));
      } catch (MalformedURLException e) {
        System.out.println("Check URL problem: " + e.getMessage());
      }
    } else {
      // cannot execute the task again
    }
  }
  
  
  
  
  
  public class AsyncTask extends android.os.AsyncTask<URL, Void, Bitmap>{

    
    @Override
    protected void onPreExecute() {
      pBar.setVisibility(View.VISIBLE);
      btn.setText("Getting image...");
      Log.d("EXO_TAG", "*** Starting task");
    }

    @Override
    protected Bitmap doInBackground(URL... params) {
      Bitmap bitmap = null;
          try {
            Log.d("EXO_TAG", "*** Downloading from task");
              URL url = params[0];
                bitmap = BitmapFactory.decodeStream((InputStream) url.getContent());
          } catch (Exception e) {
            e.printStackTrace();
            return null;
          }
          return bitmap;
    }
    
    @Override
    protected void onPostExecute(Bitmap result) {
      if (result != null) {
        Log.d("EXO_TAG", "*** Finishing task");
        pBar.setVisibility(View.INVISIBLE);
        btn.setText("Done!");
        // display the downloaded image in the image view
        imgView.setImageBitmap(result);
      } else {
        btn.setText("Error !");
      }
    }
    
  }

}

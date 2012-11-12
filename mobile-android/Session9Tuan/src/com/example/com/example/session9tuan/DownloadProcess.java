package com.example.com.example.session9tuan;


import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.AsyncTask.Status;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

public class DownloadProcess extends Activity{

  private ProgressBar pBar;
  private Button btn;
  private int max = 100;
  private ProgressTask task;
  
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_progress);
        
        pBar = (ProgressBar)findViewById(R.id.progressBar1);
        pBar.setVisibility(View.INVISIBLE);
        
        btn = (Button)findViewById(R.id.button1);
    }
    
    @Override
  protected void onPause() {
    if (task != null) {
      task.cancel(true);
      Log.d("EXO_TAG", "*** Cancelling the task");
    }
    super.onPause();
  }

   public void progress(View v) {
     
     if (task == null || task.getStatus() != Status.RUNNING) {
       task = new ProgressTask();
       task.execute(max);
     }
   }
   
   private class ProgressTask extends AsyncTask<Integer, Integer, Boolean> {

  @Override
  protected void onPreExecute() {
    pBar.setVisibility(View.VISIBLE);
    pBar.setMax(max);
    btn.setText("Running...");
    Log.d("EXO_TAG", "*** Starting task");
  }
     
  @Override
  protected Boolean doInBackground(Integer... params) {
    Log.d("EXO_TAG", "*** Task in progress...");
    for (int i=0; i<=params[0]; i++) {
      try {
        // slow down to see progress
        Thread.sleep(1000);
        publishProgress(i);
      } catch (InterruptedException e) {
        e.printStackTrace();
        return false;
      }
    }
    return true;
  }
  
  @Override
  protected void onProgressUpdate(Integer... values) {
    Log.d("EXO_TAG", "*** Updating progress bar: "+values[0]+"/"+pBar.getMax());
    pBar.setProgress(values[0]);
  }

  @Override
  protected void onPostExecute(Boolean result) {
    if (result) {
      pBar.setVisibility(View.INVISIBLE);
      btn.setText("Done!");
      Log.d("EXO_TAG", "*** Task done!");
    }
  }
     
   }
}

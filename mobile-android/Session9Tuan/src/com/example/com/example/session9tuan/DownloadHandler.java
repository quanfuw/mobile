package com.example.com.example.session9tuan;

import java.lang.ref.WeakReference;
import java.util.Date;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.AsyncTask.Status;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class DownloadHandler extends Activity{
  private TextView txt;
  private ThisHandler handler;
  private Status status1 = Status.PENDING;
  private Status status2 = Status.PENDING;
  
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_handler);
        
        txt = (TextView)findViewById(R.id.textView1);
        handler = new ThisHandler(this);
    }
    
    /*
     * Called by a tap on the button
     */
    public void start(View v) {
      // Start in main thread 
      if (status1 != Status.RUNNING) {
        Runnable worker = new Runnable() {
        @Override
        public void run() {
          Log.d("EXO_TAG", "*** Starting worker #1");
          status1 = Status.RUNNING;
          for (int i=0; i<=10; i++) {
            runOnUiThread(new Runnable() { // call the handler of the UI thread
              @Override
              public void run() {
                txt.setText(null);
                txt.append("TH1 "+System.currentTimeMillis()+"\n");  
              }
            });
            try {
              Thread.sleep(1000);
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
          }
          Log.d("EXO_TAG", "*** Worker #1 finished");
          status1 = Status.FINISHED;
        }
        };
        new Thread(worker).start();
      }
    }
    
    public void start2(View v) {
      // Create and start worker #2 only if it doesn't run already
      if (status2 != Status.RUNNING) {
        Runnable worker = new Runnable() {
        @Override
        public void run() {
          status2 = Status.RUNNING;
          Log.d("EXO_TAG", "*** Starting worker #2");
          for (int i=0; i<=10; i++) {
            Message msg = new Message();
            Bundle data = new Bundle();
            data.putLong("TIME", System.currentTimeMillis());
            msg.setData(data);
            handler.sendMessage(msg); // send the message to the UI thread
            try {
              Thread.sleep(1000);
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
          }
          Log.d("EXO_TAG", "*** Worker #2 finished");
          status2 = Status.FINISHED;
        }
      };
      new Thread(worker).start();
      }
    }
    
    /*
     * Specific Handler
     * It is static and uses a WeakReference to the parent activity to avoid memory leaks
     * cf http://blog.andresteingress.com/2011/10/12/anonymous-inner-classes-in-android/
     *    http://android-developers.blogspot.com/2009/01/avoiding-memory-leaks.html
     * In summary, to avoid context-related memory leaks, remember the following:
     *    Do not keep long-lived references to a context-activity (a reference to an activity should have the same life cycle as the activity itself)
     *    Try using the context-application instead of a context-activity
     *    Avoid non-static inner classes in an activity if you don't control their life cycle, use a static inner class and make a weak reference to the activity inside. The solution to this issue is to use a static inner class with a WeakReference to the outer class, as done in ViewRoot and its W inner class for instance
     *    A garbage collector is not an insurance against memory leaks
     */
    private static class ThisHandler extends Handler {
      private WeakReference<DownloadHandler> activity;
      public ThisHandler(DownloadHandler a){
        activity = new WeakReference<DownloadHandler>(a);
      }
    @Override
    public void handleMessage(Message msg) {
      // handle the message on the UI thread
      long time = msg.getData().getLong("TIME");
      Log.d("EXO_TAG", "*** MyHandler received message "+time);
      activity.get().txt.setText(null);
      activity.get().txt.append("Thread2 message: "+new Date(time)+"\n");
    }
      
    }
}

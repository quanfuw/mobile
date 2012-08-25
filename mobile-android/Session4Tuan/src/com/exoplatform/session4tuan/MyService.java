package com.exoplatform.session4tuan;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MyService extends IntentService {

	
private static MyService instance = null;
 
	
public MyService () {
    super("Pham's service");
}

public static boolean isInstanceCreated() { 
    return instance != null; 
 }
	
public MyService(String name) {
    super(name);
    // TODO Auto-generated constructor stub
}

@Override
public IBinder onBind(Intent arg0) {
    // TODO Auto-generated method stub
    return null;
}

@Override
public void onCreate() {
    // TODO Auto-generated method stub
    super.onCreate();
    Log.e("Service Example", "Service Started.. ");
    instance = this ;
    // pushBackground();

}

@Override
public void onDestroy() {
    // TODO Auto-generated method stub
    super.onDestroy();
    Log.e("Service Example", "Service Destroyed.. ");
    instance = null ;
}

@Override
protected void onHandleIntent(Intent arg0) {
    // TODO Auto-generated method stub
    for (long i = 0; i <= 1000000; i++) {
        Log.e("Service Example", " " + i);
        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
 }



}

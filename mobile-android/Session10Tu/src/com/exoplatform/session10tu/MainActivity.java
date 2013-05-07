package com.exoplatform.session10tu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

  private static final String TAG = "MainActivity";
  
  // change name to set your twitter's username =>
  public static final String NAME = "exoplatform";
  
  private Camera camera;
  private int cameraId = 0;
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    
    LinearLayout main = (LinearLayout) getLayoutInflater().inflate(R.layout.activity_main, null);
    setContentView(main);
    
    ImageView coverPhoto = (ImageView) findViewById(R.id.coverPhoto);

    // execute background tasks 
    new RetrieveAvatarTask().execute(coverPhoto);
    
    new RetrieveProfileTask().execute(main);
    
    new RetrieveTweetsTask().execute(main);
    
    // test camera
    if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
      Toast.makeText(this, "No camera on this device", Toast.LENGTH_LONG)
          .show();
    } else {
      cameraId = findFrontFacingCamera();
      if (cameraId < 0) {
        Toast.makeText(this, "No front facing camera found.",
            Toast.LENGTH_LONG).show();
      } else {
        camera = Camera.open(cameraId);
      }
    }
  }

  // take photo
  public void onClick(View view) {
    camera.takePicture(null, null,
        new PhotoHandler(getApplicationContext()));
  }
  
  // get id of camera
  private int findFrontFacingCamera() {
    int cameraId = -1;
    int numberOfCameras = Camera.getNumberOfCameras();
    for (int i = 0; i < numberOfCameras; i++) {
      CameraInfo info = new CameraInfo();
      Camera.getCameraInfo(i, info);
      if (info.facing == CameraInfo.CAMERA_FACING_FRONT) {
        Log.i(TAG, "Camera found");
        cameraId = i;
        break;
      }
    }
    return cameraId;
  }

  @Override
  protected void onPause() {
    if (camera != null) {
      camera.release();
      camera = null;
    }
    super.onPause();
  }
  
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }
  
  // retrieve the profile image via Twitter REST and set it to ImageView
  class RetrieveAvatarTask extends AsyncTask<ImageView, Void, ImageView> {
    
    private Bitmap cover = null;
    
    public static final String AVA_URL = "https://api.twitter.com/1/users/profile_image";
    
    protected ImageView doInBackground(ImageView... imageView) {
      try {
        String url = AVA_URL + "?screen_name=" + MainActivity.NAME + "&size=bigger";
        Bitmap image = BitmapFactory.decodeStream( (InputStream) new URL(url).getContent());
        cover = Bitmap.createScaledBitmap(image, imageView[0].getWidth(), imageView[0].getHeight(), true);
      } 
      catch (MalformedURLException e) 
      {
        e.printStackTrace();
      } 
      catch (IOException e) 
      {
        e.printStackTrace();
      }
    
      return imageView[0]; 
    }
    
    protected void onPostExecute(ImageView imageView) {
      imageView.setImageBitmap(cover);
    }
  }
  
  // retrieve profile information in XML format, parse in into data stored in XMLGettersSetters then
  // display it
  class RetrieveProfileTask extends AsyncTask<View, Void, View> {
    
    public static final String PROFILE_URL = "https://api.twitter.com/1/users/show.xml";
    
    protected View doInBackground(View... view) {
      try {
       /**
        * Create a new instance of the SAX parser
        **/
        SAXParserFactory saxPF = SAXParserFactory.newInstance();
        SAXParser saxP = saxPF.newSAXParser();
        XMLReader xmlR = saxP.getXMLReader();
        URL url = new URL(PROFILE_URL + "?screen_name=" + MainActivity.NAME); // URL of the XML
       
       /**
        * Create the Handler to handle each of the XML tags.
        **/
        XMLHandler myXMLHandler = new XMLHandler();
        xmlR.setContentHandler(myXMLHandler);
        xmlR.parse(new InputSource(url.openStream()));
      } catch (Exception e) {
        e.printStackTrace();
      }
      return view[0];
    }
    
    protected void onPostExecute(View main) {
      XMLGettersSetters xmlGS = XMLHandler.getXMLData();
      TextView url = new TextView(getApplicationContext());
      url.setText("Url: " + xmlGS.getUrl());
      
      TextView name = new TextView(getApplicationContext());
      name.setText("Name: " + xmlGS.getName());
      
      TextView des = new TextView(getApplicationContext());
      des.setText("Description: " + xmlGS.getDescription());
      
      TextView location = new TextView(getApplicationContext());
      location.setText("Location: " + xmlGS.getLocation());
      
      ((ViewGroup) main).addView(name);
      ((ViewGroup) main).addView(url);
      ((ViewGroup) main).addView(des);
      ((ViewGroup) main).addView(location);
    }
  }
  
  // retrieve tweets in JSON format
  class RetrieveTweetsTask extends AsyncTask<View, Void, View> {

    private String tweets;
    
    public static final String TWEET_URL = "http://api.twitter.com/1/statuses/user_timeline.json";
    
    protected View doInBackground(View... views) {
      StringBuilder builder = new StringBuilder();
      HttpClient client = new DefaultHttpClient();
      HttpGet httpGet = new HttpGet(TWEET_URL + "?screen_name=" + MainActivity.NAME + "&count=5");

      try {
        HttpResponse response = client.execute(httpGet);
        StatusLine statusLine = response.getStatusLine();
        int statusCode = statusLine.getStatusCode();
        if (statusCode == 200) {
          HttpEntity entity = response.getEntity();
          InputStream content = entity.getContent();
          BufferedReader reader = new BufferedReader(new InputStreamReader(content));
          String line;
          while ((line = reader.readLine()) != null) {
            builder.append(line);
          }
        } else {
          Log.e(TAG, "Failed to download file");
        }
      } catch (ClientProtocolException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }
      this.tweets = builder.toString();
      return views[0];
    }
    
    // UI manipulation
    protected void onPostExecute(View main) {
      try {
        JSONArray jsonArray = new JSONArray(this.tweets);
        Log.i(TAG, "Number of entries " + jsonArray.length());
        
        if (jsonArray.length() > 0) {
          TextView tweets = new TextView(getApplicationContext());
          tweets.setText("Tweets: ");
          ((ViewGroup) main).addView(tweets);
          
          for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Log.i(TAG, jsonObject.getString("text"));
            TextView tweet = new TextView(getApplicationContext());
            tweet.setText(i + "> " + jsonObject.getString("text"));
            ((ViewGroup) main).addView(tweet);
          }         
        }
        
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}

package com.example.session14.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class Home extends Activity {

  private ArrayAdapter<String> adapter;
  private TextView textWaiting;
  private ProgressBar progressWaiting;
  private ListView list ;
  public static String message ;

  private Dialog loginForm ;
  private static String  PUBLIC_URL = "http://int.exoplatform.org/rest/platform/info";
  private static String PRIVATE_URL = "http://int.exoplatform.org/rest/private/platform/info";



  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home);
    list = (ListView) findViewById(R.id.list);
    adapter = new ArrayAdapter<String>(this,R.id.list); adapter = new ArrayAdapter<String>(this, 
        android.R.layout.simple_list_item_1, android.R.id.text1);
    textWaiting = (TextView) findViewById(R.id.textView1);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.activity_home, menu);
    return true;
  }

  public void accessPublic(View v) {
    new ReadingTask().execute(PUBLIC_URL);
    textWaiting.setText(PUBLIC_URL);
  }

  public void accessPrivate(View v) {
    new ReadingTask().execute(PRIVATE_URL);
    textWaiting.setText(PRIVATE_URL);
  }

  public static String buildResponse(String url) {
    StringBuilder builder = new StringBuilder();
    HttpClient client = new DefaultHttpClient();
    HttpGet httpGet = new HttpGet(url);
    httpGet.setHeader("Authorization", "Basic " + Base64.encodeToString("tuanp:123456".getBytes(), Base64.NO_WRAP));
    try {
      HttpResponse response = client.execute(httpGet);
      StatusLine statusLine = response.getStatusLine();
      int statusCode = statusLine.getStatusCode();
      switch (statusCode) {
      case 404:
        Log.e(Home.class.toString(), "Service not found: " + statusCode);
        message = "Service not found: " + statusCode;
        break;
      case 401:
        Log.e(Home.class.toString(), "Need to login: " + statusCode);
        message = "Need to login: " + statusCode;
        break;
      case 200:
        HttpEntity entity = response.getEntity();
        InputStream content = entity.getContent();
        BufferedReader reader = new BufferedReader(new InputStreamReader(content));
        String line;
        while ((line = reader.readLine()) != null) {
          builder.append(line);
        }
        break;
      default:
        Log.e(Home.class.toString(), "Error : " + statusCode);
        message = "Error: " + statusCode;
        break;
      }

    } catch (ClientProtocolException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return builder.toString();
  }

  public class ReadingTask extends AsyncTask<String, Void, ArrayList<String>> {


    @Override
    protected void onPreExecute() {
      //progressWaiting.setVisibility(View.VISIBLE);
    }

    @Override
    protected ArrayList<String> doInBackground(String... urls) {
      // TODO Auto-generated method stub

      ArrayList<String> contents = new ArrayList<String>();
      String jsonResponse = buildResponse(urls[0]);
      if(jsonResponse != null && !"".equals(jsonResponse))
        try {
          JSONObject json = new JSONObject(jsonResponse) ;
          contents.add("userHomeNodePath:" +json.getString("userHomeNodePath"));
          contents.add("platformVersion:" + json.getString("platformVersion"));
          contents.add("platformRevision:" + json.getString("platformRevision"));
          contents.add("platformEdition:" + json.getString("platformEdition"));
          contents.add("runningProfile:" + json.getString("runningProfile"));
          contents.add("platformBuildNumber:" + json.getString("platformBuildNumber"));
          contents.add("currentRepoName:" + json.getString("currentRepoName"));
          contents.add("defaultWorkSpaceName:" + json.getString("defaultWorkSpaceName"));
          contents.add("isMobileCompliant:" + json.getString("isMobileCompliant"));
        } catch (JSONException je) {
          Log.e(Home.class.toString(), je.getMessage());
        }
      else {
        return null;
      } 

      return contents;
    }

    @Override
    protected void onPostExecute(ArrayList<String> result) {
      adapter.clear();
      if (result != null) {
        Iterator<String> it = result.iterator();
        while (it.hasNext()) {
          adapter.add(it.next());
        }
        //textWaiting.setText("display JSON");
        //progressWaiting.setVisibility(View.GONE);
        list.setAdapter(adapter);
      } else {
        Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT).show();
      }
    }


  }
}

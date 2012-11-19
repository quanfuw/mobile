/*
 * Copyright (C) 2003-2012 eXo Platform SAS.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.example.session11tuan;

/**
 * Created by The eXo Platform SAS
 * Author : eXoPlatform
 *          exo@exoplatform.com
 * Nov 19, 2012  
 */

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
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;


public class ReadingJSON extends Activity {
  private ArrayAdapter<String> adapter;
  private TextView textWaiting;
  private ProgressBar progressWaiting;
  private String json_url =  "http://int.exoplatform.org/rest/platform/info";
  private ListView list ;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.reading);
    list = (ListView)findViewById(R.id.list) ;
    textWaiting = (TextView)findViewById(R.id.content);
    progressWaiting = (ProgressBar)findViewById(R.id.progress_waiting);

    adapter = new ArrayAdapter<String>(this,R.id.list); adapter = new ArrayAdapter<String>(this, 
        android.R.layout.simple_list_item_1, android.R.id.text1);
    new ReadingTask().execute(json_url);
  }



  public static String readTwitterFeed(String url) {
    StringBuilder builder = new StringBuilder();
    HttpClient client = new DefaultHttpClient();
    HttpGet httpGet = new HttpGet(url);
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
        Log.e(Home.class.toString(), "Failed to download file");
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
      progressWaiting.setVisibility(View.VISIBLE);
    }

    @Override
    protected ArrayList<String> doInBackground(String... urls) {
      // TODO Auto-generated method stub
      ArrayList<String> contents = new ArrayList<String>();

      String readTwitterFeed = ReadingJSON.readTwitterFeed(urls[0]);
      try {
        JSONObject json = new JSONObject(readTwitterFeed) ;

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
        je.printStackTrace();
      }

      return contents;
    }

    @Override
    protected void onPostExecute(ArrayList<String> result) {
      if (result != null) {
        Iterator<String> it = result.iterator();
        while (it.hasNext()) {
          adapter.add(it.next());
        }
        textWaiting.setText("display JSON");
        progressWaiting.setVisibility(View.GONE);
        list.setAdapter(adapter);
      }
    }


  }


} 


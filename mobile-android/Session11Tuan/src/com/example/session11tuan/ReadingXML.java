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

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;


public class ReadingXML extends Activity {
  private ArrayAdapter<String> adapter;
  private TextView textWaiting;
  private ProgressBar progressWaiting;
  private String xml_url =  "http://repository.exoplatform.org/content/groups/public/org/exoplatform/calendar/calendar/maven-metadata.xml";
  private WebView view ;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.reading_xml);
    view = (WebView)findViewById(R.id.webView1) ;
    textWaiting = (TextView)findViewById(R.id.content_xml);
    progressWaiting = (ProgressBar)findViewById(R.id.progress_waiting_xml);
    new ReadingTask().execute(xml_url);
  }




  public class ReadingTask extends AsyncTask<String, Void, String> {


    @Override
    protected void onPreExecute() {
      progressWaiting.setVisibility(View.VISIBLE);
    }

    @Override
    protected String doInBackground(String... urls) {
      // TODO Auto-generated method stub
      StringBuilder sb = new StringBuilder() ;
      try {
      URL url = new URL(urls[0]);
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      conn.setReadTimeout(10000 /* milliseconds */);
      conn.setConnectTimeout(15000 /* milliseconds */);
      conn.setRequestMethod("GET");
      conn.setDoInput(true);
      // Starts the query
      conn.connect();
      InputStream stream = conn.getInputStream();    
       
      } catch (IOException e) {
        // TODO: handle exception
      }
      
       
      //TODO convert to Document objec and list by tree;
      
      return sb.toString() ;
    }

    @Override
    protected void onPostExecute(String result) {
      if (result != null) {
        textWaiting.setText("display xml");
        progressWaiting.setVisibility(View.GONE);
        view.loadData(result, "text/html", null);
      }
    }


  }


} 


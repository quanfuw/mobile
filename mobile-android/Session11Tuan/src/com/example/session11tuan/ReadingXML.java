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

import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;


public class ReadingXML extends ListActivity {
  private TextView textWaiting;
  private ProgressBar progressWaiting;
  private String xml_url = "http://p-xr.com/xml";
  public ArrayList<HashMap<String, String>> mylist;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.reading_xml);
    textWaiting = (TextView)findViewById(R.id.content_xml);
    progressWaiting = (ProgressBar)findViewById(R.id.progress_waiting_xml);
    new ReadingTask().execute(xml_url);
    mylist = new ArrayList<HashMap<String,String>>();
  }


  public final static String getElementValue( Node elem ) {
    Node kid;
    if( elem != null){
      if (elem.hasChildNodes()){
        for( kid = elem.getFirstChild(); kid != null; kid = kid.getNextSibling() ){
          if( kid.getNodeType() == Node.TEXT_NODE  ){
            return kid.getNodeValue();
          }
        }
      }
    }
    return "";
  }

  public static String getValue(Element item, String str) {   
    NodeList n = item.getElementsByTagName(str);    
    return getElementValue(n.item(0));
  }

  private int numResults(Document doc) {
    Node results = doc.getDocumentElement();
    int res = -1;

    try{
      res = Integer.valueOf(results.getAttributes().getNamedItem("count").getNodeValue());
    }catch(Exception e ){
      res = -1;
    }

    return res;
  }


  public static String getXML(String url){
    String line = null;

    try {
      DefaultHttpClient httpClient = new DefaultHttpClient();
      HttpPost httpPost = new HttpPost(url);
      HttpResponse httpResponse = httpClient.execute(httpPost);
      HttpEntity httpEntity = httpResponse.getEntity();
      line = EntityUtils.toString(httpEntity);
    } catch (UnsupportedEncodingException e) {
      line = "<results status=\"error\"><msg>Can't connect to server</msg></results>";
    } catch (MalformedURLException
        e) {
      line = "<results status=\"error\"><msg>Can't connect to server</msg></results>";
    } catch (IOException e) {
      line = "<results status=\"error\"><msg>Can't connect to server</msg></results>";
    }
    return line;
  }

  public Document XMLfromString(String xml){
    Document doc = null;
    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    try {
      DocumentBuilder db = dbf.newDocumentBuilder();
      InputSource is = new InputSource();
      is.setCharacterStream(new StringReader(xml));
      doc = db.parse(is);

    } catch (ParserConfigurationException e) {
      System.out.println("XML parse error: " + e.getMessage());
      return null;
    } catch (SAXException e) {
      System.out.println("Wrong XML file structure: " + e.getMessage());
      return null;
    } catch (IOException e) {
      System.out.println("I/O exeption: " + e.getMessage());
      return null;
    }
    return doc;
  }



  public class ReadingTask extends AsyncTask<String, Void, String> {


    @Override
    protected void onPreExecute() {
      progressWaiting.setVisibility(View.VISIBLE);
    }

    @Override
    protected String doInBackground(String... urls) {



      return getXML(urls[0]);
    }

    @Override
    protected void onPostExecute(String result) {
      if (result != null) {
        Document doc = XMLfromString(result);
        int numResults = numResults(doc);
        if((numResults <= 0)){
          Toast.makeText(ReadingXML.this, "Geen resultaten gevonden", Toast.LENGTH_LONG).show();
          finish();
        }
        NodeList nodes = doc.getElementsByTagName("result");
        for (int i = 0; i < nodes.getLength(); i++) {
          HashMap<String,String> map = new HashMap<String,String>();  

          Element e = (Element)nodes.item(i);
          map.put("id", getValue(e, "id"));
          map.put("name", "Naam:" + getValue(e, "name"));
          map.put("Score", "Score: " +getValue(e, "score"));
          mylist.add(map);

        }
        progressWaiting.setVisibility(View.GONE);
        textWaiting.setVisibility(View.GONE);
        ListAdapter adapter = new SimpleAdapter(ReadingXML.this, mylist , R.layout.read_xml_view, 
                                                new String[] { "name", "Score" }, 
                                                new int[] { R.id.item_title, R.id.item_subtitle });

        setListAdapter(adapter);

        final ListView lv = getListView();
        lv.setTextFilterEnabled(true);  
        lv.setOnItemClickListener(new OnItemClickListener() {
          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {            
            @SuppressWarnings("unchecked")
            HashMap<String, String> o = (HashMap<String, String>) lv.getItemAtPosition(position);             
            Toast.makeText(ReadingXML.this, "ID '" + o.get("id") + "' was clicked.", Toast.LENGTH_LONG).show(); 

          }
        });
      }
    }
  }
} 


package org.exoplatform.session11phi.xml;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

public class XMLActivity extends ListActivity {

	private TextView textWaiting;
	private ProgressBar progressWaiting;
	private ArrayAdapter<String> adapter;
	private final String URL = "http://repository.exoplatform.org/" +
			"content/groups/public/org/exoplatform/platform/exo.platform.packaging.tomcat/maven-metadata.xml";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xml);
        
        textWaiting = (TextView)findViewById(R.id.text_waiting);
        progressWaiting = (ProgressBar)findViewById(R.id.progress_waiting);
        
        adapter = new ArrayAdapter<String>(this, 
        		android.R.layout.simple_list_item_1, android.R.id.text1);
        
        new XMLParserTask().execute(URL);
    }

    
    private class XMLParserTask extends AsyncTask<String, Void, ArrayList<String>> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressWaiting.setVisibility(View.VISIBLE);
		}

		@Override
		protected ArrayList<String> doInBackground(String... urlPath) {
			InputStream is;
			ArrayList<String> versions = null;
			try {
				URL url = new URL(urlPath[0]);
				is = new BufferedInputStream(url.openStream());
				SAXParserFactory spf = SAXParserFactory.newInstance();
				SAXParser saxParser = spf.newSAXParser();
				XMLReader xmlReader = saxParser.getXMLReader();
				XMLHandler handler = new XMLHandler();
				xmlReader.setContentHandler(handler);
				xmlReader.parse(new InputSource(is));
				versions = handler.getVersions();
				is.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return versions;
		}
		
		@Override
		protected void onPostExecute(ArrayList<String> result) {
			super.onPostExecute(result);
			if (result != null) {
				Iterator<String> it = result.iterator();
				while (it.hasNext()) {
					adapter.add(it.next());
				}
				textWaiting.setText(R.string.label_versions);
				progressWaiting.setVisibility(View.GONE);
				setListAdapter(adapter);
			}
		}
    	
    }

}

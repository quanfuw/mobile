package org.exoplatform.session11phi.xml;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

public class JSONActivity extends ListActivity {

	private TextView textWaiting;
	private ProgressBar progressWaiting;
	private ArrayAdapter<String> adapter;
	private final String URL = "http://www.iaosi.fr/site/assets/json/historique.json";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json);
        
        textWaiting = (TextView)findViewById(R.id.text_waiting);
        progressWaiting = (ProgressBar)findViewById(R.id.progress_waiting);
        
        adapter = new ArrayAdapter<String>(this, 
        		android.R.layout.simple_list_item_1, android.R.id.text1);
        
        new JSONParserTask().execute(URL);
    }

    private class JSONParserTask extends AsyncTask<String, Void, ArrayList<String>> {

		@Override
		protected void onPreExecute() {
			progressWaiting.setVisibility(View.VISIBLE);
		}

		@Override
		protected ArrayList<String> doInBackground(String... urlPath) {
			ArrayList<String> histo = new ArrayList<String>();
			URL url;
			try {
				url = new URL(urlPath[0]);
				BufferedReader breader = new BufferedReader(new InputStreamReader(url.openStream()));
				StringBuilder stringBuilder = new StringBuilder();
				String line;
				while((line = breader.readLine()) != null) {
					stringBuilder.append(line);
				}
				JSONObject jsonObj = new JSONObject(stringBuilder.toString());
				JSONArray datas = new JSONArray(jsonObj.getString("historique_en"));
				for (int i = 0; i < datas.length(); i++) {
					JSONObject objHisto = datas.getJSONObject(i);
					String strHisto = objHisto.getString("annee")+": "+objHisto.getString("text");
					histo.add(strHisto);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return histo;
		}

		@Override
		protected void onPostExecute(ArrayList<String> result) {
			if (result != null) {
				Iterator<String> it = result.iterator();
				while (it.hasNext()) {
					adapter.add(it.next());
				}
				textWaiting.setText(R.string.label_history);
				progressWaiting.setVisibility(View.GONE);
				setListAdapter(adapter);
			}
		}
    	
    }
   

}

package org.exoplatform.session14phi.rest;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private ListView lv;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = (ListView)findViewById(R.id.list);
    }
    
    public void go(View v) {
    	new ListReposTask().execute();
    }

    /**
     * Gets the list of public repositories of eXo on GitHub.
     * @author paristote
     */
   private class ListReposTask extends AsyncTask<Void, Void, ArrayList<String>> {

	 protected String getASCIIContentFromEntity(HttpEntity entity) throws IllegalStateException, IOException {
		InputStream in = entity.getContent();
		StringBuffer out = new StringBuffer();
		int n = 1;
		while (n>0) {
		   byte[] b = new byte[4096];
		   n =  in.read(b);
		   if (n>0) out.append(new String(b, 0, n));
		}
		return out.toString();
	 }
	   
	@Override
	protected ArrayList<String> doInBackground(Void... params) {
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet("https://api.github.com/orgs/exoplatform/repos");
		String text = null;
		ArrayList<String> result = new ArrayList<String>();
		try {
			HttpResponse response = httpClient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			text = getASCIIContentFromEntity(entity);
			
			if (text == null) return null;
			JSONObject jsonObj = new JSONObject("{\"repos\":"+text+"}");
			JSONArray datas = new JSONArray(jsonObj.getString("repos"));
			for (int i = 0; i < datas.length(); i++) {
				JSONObject objRepo = datas.getJSONObject(i);
				String strRepo = objRepo.getString("name")+" - "+objRepo.getString("clone_url");
				result.add(strRepo);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return result;
	}
	
	@Override
	protected void onPostExecute(ArrayList<String> result) {
		if (result != null) {
			if (lv != null) {
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(), 
		        		android.R.layout.simple_list_item_1, android.R.id.text1);
				Iterator<String> i = result.iterator();
				while (i.hasNext()) {
					String a = i.next();
					adapter.add(a);
				}
				lv.setAdapter(adapter);
			}
		} else {
			Toast.makeText(getBaseContext(), "Cannot perform the operation.", Toast.LENGTH_LONG).show();
		}
		super.onPostExecute(result);
	}
	   
   }
}

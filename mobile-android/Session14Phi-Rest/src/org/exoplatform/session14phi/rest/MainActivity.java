package org.exoplatform.session14phi.rest;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONObject;
import org.restlet.data.ChallengeScheme;
import org.restlet.data.MediaType;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends Activity {

	private ListView lv;
	private ProgressBar progress;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = (ListView)findViewById(R.id.list);
        progress = (ProgressBar)findViewById(R.id.progressBar);
        progress.setVisibility(View.GONE);
    }
    
    /**
     * Called when the user presses the GO button on the screen.
     */
    public void go(View v) {
    	// Infos to get the activity stream of the logged user
//    	final String URL = "http://int.exoplatform.org/rest/api/social/v1-alpha3/portal/activity_stream/feed.json";
//    	final String objName = "activities";
//    	final String propName = "title";
    	// Infos to get the list of github repos owned by exoplatform (http://developer.github.com/v3/repos/)
    	final String URL = "https://api.github.com/orgs/exoplatform/repos";
    	final String objName = "repos";
    	final String propName = "name";
    	new ListReposTask().execute(URL, objName, propName);
    }

    /**
     * Gets the list of public repositories of eXo on GitHub.
     * @author paristote
     */
   private class ListReposTask extends AsyncTask<String, Void, ArrayList<String>> {
	   
	   @Override
		protected void onPreExecute() {
		   progress.setVisibility(View.VISIBLE);
			super.onPreExecute();
		}

	 private String getASCIIContentFromEntity(HttpEntity entity) throws IllegalStateException, IOException {
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
	 
	 private DefaultHttpClient initHttpClient() {
		    HttpParams httpParameters = new BasicHttpParams();
		    HttpConnectionParams.setConnectionTimeout(httpParameters, 30*1000);
		    HttpConnectionParams.setSoTimeout(httpParameters, 30*1000);
		    HttpConnectionParams.setTcpNoDelay(httpParameters, true);
		    return new DefaultHttpClient(httpParameters);
		  }
	 
	 /**
	  * Using HttpClient.
	  * @param URL The URL to connect to.
	  * @param login Whether you should use the credentials to sign in Platform. Must be false if you connect to Github.
	  * @return The response as a String.
	  * @throws IllegalStateException
	  * @throws IOException
	  */
	 private String getTextWithHttpClient(String URL, String usr, String pwd) throws IllegalStateException, IOException {
		DefaultHttpClient httpClient = initHttpClient();
		HttpGet httpGet = new HttpGet(URL);
		if (usr != null && pwd != null) {
			// http://en.wikipedia.org/wiki/Basic_access_authentication#Protocol			
			String cred = usr+":"+pwd;
//			httpGet.setHeader("Authorization", "Basic "+ Base64.encodeBytes(cred.getBytes()));
			httpGet.setHeader("Authorization", "Basic "+Base64.encodeToString(cred.getBytes(), Base64.DEFAULT));
		}
		HttpResponse response = httpClient.execute(httpGet);
		HttpEntity entity = response.getEntity();
		return getASCIIContentFromEntity(entity);
	 }
	 
	 /**
	  * Using RestLet framework.
	  * @param URL The URL to connect to.
	  * @param login Whether you should use the credentials to sign in Platform. Must be false if you connect to Github.
	  * @return The response as a String.
	  * @throws ResourceException
	  * @throws IOException
	  */
	 private String getTextWithRestLet(String URL, String usr, String pwd) throws ResourceException, IOException {
		ClientResource cr = new ClientResource(URL);
		if (usr != null && pwd != null) {
			// http://wiki.restlet.org/docs_2.0/13-restlet/27-restlet/46-restlet/112-restlet.html
			cr.setChallengeResponse(ChallengeScheme.HTTP_BASIC, usr, pwd);
		}
		return cr.get(MediaType.APPLICATION_JSON).getText();
	 }
	   
	@Override
	protected ArrayList<String> doInBackground(String... params) {
		final String URL = params[0];
		final String objName = params[1];
		final String propName = params[2];
		
		String text = null;
		ArrayList<String> result = new ArrayList<String>();
		try {
			String usr=null; String pwd=null;
//			       usr="james";     pwd="gtn"; // comment this line to disable login, for github
			//text = getTextWithRestLet(URL, usr, pwd);
			text = getTextWithHttpClient(URL, usr, pwd);
			
			if (text == null) return null;
			// Use "{\"repos\":"+text+"}" (with quotes) in the JSONObject constructor if you connect to github.
			// The response starts with '[' so it's wrapped in a root object '{' as JSONObject expects.
			// Otherwise simply pass the 'text' variable.
			JSONObject jsonObj = new JSONObject("{\"repos\":"+text+"}");
			JSONArray datas = new JSONArray(jsonObj.getString(objName));
			for (int i = 0; i < datas.length(); i++) {
				JSONObject objRepo = datas.getJSONObject(i);
				String strRepo = objRepo.getString(propName);
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
		progress.setVisibility(View.GONE);
		super.onPostExecute(result);
	}
	   
   }
}

package net.sitsec.reverselookup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class IncomingCallListener extends PhoneStateListener {

	private static final String TAG = "ReverseLookup";
	private Context appContext;
	
	private class LookupTask extends AsyncTask<String, Void, String> {
		
		@Override
		protected String doInBackground(String... urls) {
			String result = "";
			
			// request
			
			try {
				String jsonresult = "";
				
				DefaultHttpClient client = new DefaultHttpClient();
				HttpGet httpGet = new HttpGet(urls[0]);
				HttpResponse execute = client.execute(httpGet);
				InputStream content = execute.getEntity().getContent();

				BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
				String s = "";
				while ((s = buffer.readLine()) != null) {
					jsonresult += s;
				}
				
				JSONArray jsonArray = new JSONArray(jsonresult);
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject = jsonArray.getJSONObject(i);				
					result += jsonObject.getString("reverse") + "\n";
				}

			} catch (ClientProtocolException e1) {
				result = e1.toString();
			} catch (IOException e1) {
				result = e1.toString();
			}
			catch (JSONException e1) {
				result = e1.toString();
			}
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			for (int i=0; i < 2; i++)
				Toast.makeText(appContext, result, Toast.LENGTH_LONG).show();		
		}
	}
	
	public IncomingCallListener(Context c) {
		appContext = c;
	}
	
	public void onCallStateChanged(int state, String incomingNumber) {
		
		if(state == TelephonyManager.CALL_STATE_RINGING) {
			Log.d(TAG, "RINGING. Call from " + incomingNumber);

			SharedPreferences settings = appContext.getSharedPreferences(ConfigActivity.PREFS_NAME, 0);
	    	String uri = settings.getString(ConfigActivity.PREF_KEY, "");

			LookupTask task = new LookupTask();
			    	
			try {
				task.execute(new String[] { uri + java.net.URLEncoder.encode(incomingNumber, "UTF-8")});
			} catch (UnsupportedEncodingException e) {
				Toast.makeText(appContext, "Failed to create URL, because UTF-8 is not supported.", Toast.LENGTH_LONG).show();	
			}
		}
	} 


} // end of IncomingCallListener

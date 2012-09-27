package net.sitsec.reverselookup;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class ConfigActivity extends Activity {

	public static final String PREFS_NAME = "ReverseLookupPrefsFile";
	public static final String PREF_KEY = "reverseLookupUri";
	private static final String DEFAULT_URI = "https://enterhostnamehere/cgi-bin/lookup.pl?number=";
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Log.i("ConfigActivity", "onCreate()");

    	SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
    	String uri = settings.getString(PREF_KEY, DEFAULT_URI);

    	EditText uriEdit = (EditText) findViewById(R.id.reverseLookupUriTextEdit);
    	uriEdit.setText(uri);
    	
        InitializationSingleton.initialize(getApplicationContext());
    }
    
    public void saveConfig(View view) {
        
    	EditText uriEdit = (EditText) findViewById(R.id.reverseLookupUriTextEdit);
    	    	
    	SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
    	SharedPreferences.Editor editor = settings.edit();
    	editor.putString(PREF_KEY, uriEdit.getText().toString());
    	editor.commit();
    	
    	Toast.makeText(this, getResources().getString(R.string.configuration_saved), Toast.LENGTH_LONG).show();	
    }
}



	

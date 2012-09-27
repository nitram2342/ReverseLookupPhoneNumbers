package net.sitsec.reverselookup;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class StartupIntentReceiver extends BroadcastReceiver {
	
	private static final String TAG = "ReverseLookup";

	@Override
    public void onReceive(Context context, Intent intent) {

    	Toast.makeText(context, "Phone booted", Toast.LENGTH_LONG).show();	

        InitializationSingleton.initialize(context.getApplicationContext());
                
        Log.v(TAG, "started");
    }
}

package net.sitsec.reverselookup;

import android.content.Context;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class InitializationSingleton {

	private static InitializationSingleton instance = null;

	private InitializationSingleton() {
	}

	public static void initialize(Context context) {
		if (instance == null) {
			instance = new InitializationSingleton();
			
			Toast.makeText(context, context.getResources().getString(R.string.reverse_lookup_service_started), Toast.LENGTH_LONG).show();	
			
			// register for phone events
			IncomingCallListener l = new IncomingCallListener(context.getApplicationContext());
			TelephonyManager telephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);        
			telephony.listen(l, PhoneStateListener.LISTEN_CALL_STATE);		 
		}
	}

}


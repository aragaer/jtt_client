package com.aragaer.jtt;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class JttClient {
	private static final String TAG = JttClient.class.getSimpleName();

	private final static Intent service_intent = new Intent(
			"com.aragaer.jtt.SERVICE");

	private IJttService service;

	private final ServiceConnection conn = new ServiceConnection() {
		public void onServiceConnected(ComponentName name, IBinder boundService) {
			service = IJttService.Stub.asInterface((IBinder) boundService);
			Log.d(TAG, "Service connected");
			onConnected();
		}

		public void onServiceDisconnected(ComponentName name) {
			service = null;
			Log.d(TAG, "Service disconnected");
		}
	};

	public JttClient() {}

	public void onConnected() {};

	public void bind(Context context) {
		context.bindService(service_intent, conn, 0);
	}

	public void unbind(Context context) {
		context.unbindService(conn);
	}

	public long[] getTr(long jdn) {
		if (service == null)
			throw new IllegalStateException("Not registered");
		try {
			return service.getTr(jdn);
		} catch (RemoteException e) {
			Log.e(TAG, "getTr("+jdn+"): "+e);
			return null;
		}
	}
}
